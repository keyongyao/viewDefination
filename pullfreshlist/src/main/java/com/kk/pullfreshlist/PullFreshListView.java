package com.kk.pullfreshlist;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/10/4.
 */

public class PullFreshListView extends ListView {
    final static int LOADINGDINISHED = 1;
    private static final String TAG = "main";
    View mHeaderView;
    int mHeaderViewHeight;
    int mFooterViewHeight;
    View mFooterView;
    float mStartY;
    float mMoveY;
    OnLoadingData mOnLoadingData;
    boolean mIsLoadingData = false;
    Handler mHandler;
    int mListItemCount;

    public PullFreshListView(Context context) {
        this(context, null);
    }

    public PullFreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullFreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        initHeaderView();
        initFooterView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == LOADINGDINISHED) {
                    mFooterView.setPadding(0, 0, 0, -mFooterViewHeight);
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                    mIsLoadingData = false;
                }
            }
        };
        mListItemCount = getCount();
    }

    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.footer_view, null);
        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        addFooterView(mFooterView);
        mFooterView.setPadding(0, 0, 0, -mFooterViewHeight);
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.header_view, null);
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        addHeaderView(mHeaderView);
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 区分  用户想 加载 还是滑动
                mMoveY = ev.getY();
                float offsetY = mMoveY - mStartY;
                mStartY = mMoveY;

                // 可视条目为第一条时才刷新
                Log.i(TAG, "PullFreshListView.onTouchEvent:  getFirstVisiblePosition() " + getFirstVisiblePosition());
                if (getFirstVisiblePosition() == 0) {
                    if (offsetY > 100 && !mIsLoadingData) {
                        // 下拉
                        Log.i(TAG, "PullFreshListView.onTouchEvent: offsetY" + offsetY);
                        mHeaderView.setPadding(0, 0, 0, 0);
                        mIsLoadingData = true;
                        mOnLoadingData.loadingOnTop(mHandler);
                    }
                }
                // 可视条目为最后一条时 才刷新
                Log.i(TAG, "PullFreshListView.onTouchEvent:  getLastVisiblePosition() " + getLastVisiblePosition());
                if (getLastVisiblePosition() == getCount() - 1) {
                    if (offsetY < -100 && !mIsLoadingData) {
                        // 上划
                        Log.i(TAG, "PullFreshListView.onTouchEvent: offsetY" + offsetY);
                        mFooterView.setPadding(0, 0, 0, 0);
                        mIsLoadingData = true;
                        mOnLoadingData.loadingOnBotton(mHandler);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnLoadingData(OnLoadingData onLoadingData) {
        mOnLoadingData = onLoadingData;

    }

    interface OnLoadingData {
        /**
         * 在头部加载数据 <br>
         *
         * @param handler 当调用者完成数据加载，发送一个PullFreshListView.LOADINGDINISHED消息，隐藏加载的加载进度条
         */
        void loadingOnTop(Handler handler);

        /**
         * 在底部加载数据 <br>
         *
         * @param handler 当调用者完成数据加载，发送一个PullFreshListView.LOADINGDINISHED消息，隐藏加载的加载进度条
         */
        void loadingOnBotton(Handler handler);
    }
}
