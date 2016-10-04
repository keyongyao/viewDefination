package com.kk.myswitchbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义开关按钮
 * Created by Administrator on 2016/10/3.
 */

public class MySwitch extends View {
    private static final String TAG = "main";
    private Bitmap mSwitchBackground;
    private Bitmap mSwitchSlidingImg;
    private boolean mIsCheck;
    private boolean mLastCheck;
    private OnMySwitchStatusChange mStatusInterface;
    private Paint mPaint;
    private float mCurrentX;

    public MySwitch(Context context) {
        this(context, null);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        String NAMESPACE = "http://schemas.android.com/apk/res/com.kk.myswitchbutton";
        // 从xml 获取属性
        int backgroundRes = attrs.getAttributeResourceValue(NAMESPACE, "backgroundRes", -1);
        int slidingImgRes = attrs.getAttributeResourceValue(NAMESPACE, "slidingImgRes", -1);
        boolean isCheck = attrs.getAttributeBooleanValue(NAMESPACE, "isCheck", false);
        init(backgroundRes, slidingImgRes, isCheck);
    }

    /**
     * @param backgroundResource    按钮背景图片ID
     * @param switchSlidingResource 按钮拖拽图片ID
     * @param isCheck               按钮是否选中
     */
    private void init(int backgroundResource, int switchSlidingResource, Boolean isCheck) {
        Log.d(TAG, "init() called with: backgroundResource = [" + backgroundResource + "], switchSlidingResource = [" + switchSlidingResource + "], isCheck = [" + isCheck + "]");
        setBackgroundResource(backgroundResource);
        setSwitchSlidingResource(switchSlidingResource);
        setCheck(isCheck);
        mPaint = new Paint();
    }

    // 设置 测量的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSwitchBackground.getWidth(), mSwitchBackground.getHeight());
    }

    // 绘制 图片
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mSwitchBackground, 0, 0, mPaint);
        canvas.drawBitmap(mSwitchSlidingImg, mCurrentX, 0, mPaint);
    }

    // 设置触摸事件监听
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float center = mSwitchBackground.getWidth() / 2;
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                mCurrentX = event.getX();

                if (mCurrentX < center) {
                    mCurrentX = 0;
                    mIsCheck = false;
                } else if (mCurrentX > center) {
                    mCurrentX = mSwitchBackground.getWidth() - mSwitchSlidingImg.getWidth();
                    mIsCheck = true;

                }
                Log.i(TAG, "MySwitch.onTouchEvent  mCurrentX :" + mCurrentX);

                break;

        }

        if (mStatusInterface != null && mLastCheck != mIsCheck) {
            mLastCheck = mIsCheck;
            mStatusInterface.onStatusChange(mIsCheck);
        }
        invalidate();
        return true;
    }

    // 设置背景图片
    @Override
    public void setBackgroundResource(int resid) {
        mSwitchBackground = BitmapFactory.decodeResource(getResources(), resid);
    }

    // 设置 按钮 拖拽图片
    public void setSwitchSlidingResource(int imgRsid) {
        mSwitchSlidingImg = BitmapFactory.decodeResource(getResources(), imgRsid);

    }

    public boolean isCheck() {
        return mIsCheck;
    }

    public void setCheck(boolean isCheck) {
        mIsCheck = isCheck;
        if (mIsCheck) {
            mCurrentX = mSwitchBackground.getWidth() - mSwitchSlidingImg.getWidth();
            invalidate();
        } else {
            mCurrentX = 0;
            invalidate();
        }

    }

    public void setOnMySwitchStatusChange(OnMySwitchStatusChange onMySwitchStatusChange) {
        mStatusInterface = onMySwitchStatusChange;
        mLastCheck = mIsCheck;
    }

    // 按钮选中状态 变化接口，用户回调
    interface OnMySwitchStatusChange {
        void onStatusChange(boolean isChecked);
    }

}
