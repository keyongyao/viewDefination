package com.kk.drawmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/10/4.
 */

public class DrawMenu extends ViewGroup {
    private static final String TAG = "main";
    float mStartX;
    float mMoveX;
    Scroller mScroller;
    boolean mIsShow;
    // 在菜单上透传 横向 滑动的 事件
    float downX = 0, downY = 0;

    public DrawMenu(Context context) {
        this(context, null);
    }

    public DrawMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawMenu(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    // 测量 布局大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 指定左面板的宽高
        View leftMenu = getChildAt(0);
        leftMenu.measure(leftMenu.getLayoutParams().width, heightMeasureSpec);

        // 指定主面板的宽高
        View mainContent = getChildAt(1);
        mainContent.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 摆放 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 摆放内容, 左面板
        View leftMenu = getChildAt(0);
        leftMenu.layout(-leftMenu.getMeasuredWidth(), 0, 0, b);

        // 主面板
        getChildAt(1).layout(l, t, r, b);
    }

    // 设置触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getX();
                float offsetX = mMoveX - mStartX;
                // 判断 左边界
                if ((-getScrollX() + offsetX) > getChildAt(0).getMeasuredWidth()) {
                    scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
                    mIsShow = true;
                } else if ((-offsetX + getScrollX()) >= 0) {  // 判断右边界
                    scrollTo(0, 0);
                    mIsShow = false;
                } else {
                    scrollBy((int) -offsetX, 0);
                }

                mStartX = mMoveX;
                break;
            case MotionEvent.ACTION_UP:
                // 用户滑动到一半就放手，控件自动滑动
                int centerPositon = -getChildAt(0).getMeasuredWidth() / 2;
                if (getScrollX() < centerPositon) {
                    // 自动打开菜单
                    int duration = (getChildAt(0).getMeasuredWidth() + getScrollX()) * 1;
                    Log.i(TAG, "DrawMenu.onTouchEvent: duration" + duration);
                    mScroller.startScroll(getScrollX(), 0, -getChildAt(0).getMeasuredWidth() - getScrollX(), 0, duration);
                    invalidate();
                    mIsShow = true;
                } else {
                    // 自动收回菜单
                    int duration = (-getScrollX()) * 1;
                    Log.i(TAG, "DrawMenu.onTouchEvent: duration" + duration);
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, duration);
                    invalidate();
                    mIsShow = false;
                }
                Log.i(TAG, "DrawMenu.onTouchEvent: 用户放手了");
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) { // 直到duration事件以后, 结束
            // true, 动画还没有结束
            // 获取当前模拟的数据, 也就是要滚动到的位置
            int currX = mScroller.getCurrX();
            Log.i(TAG, "DrawMenu.computeScroll: currX " + currX);
            scrollTo(currX, 0); // 滚过去

            invalidate(); // 重绘界面-> drawChild() -> computeScroll();循环
        }
    }

    // 开关菜单
    public void openOrCloseMenu() {
        if (!mIsShow) {
            scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
            mIsShow = true;
        } else {
            scrollTo(0, 0);
            mIsShow = false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float xOffset = Math.abs(ev.getX() - downX);
                float yOffset = Math.abs(ev.getY() - downY);

                if (xOffset > yOffset && xOffset > 5) { // 水平方向超出一定距离时,才拦截
                    return true; // 拦截此次触摸事件, 界面的滚动
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
