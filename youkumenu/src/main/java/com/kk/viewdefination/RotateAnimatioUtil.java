package com.kk.viewdefination;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by Administrator on 2016/10/2.
 */

public class RotateAnimatioUtil {
    // 菜单弹出动画
    public static void makeOutAnimation(View view, long delay) {
        RotateAnimation animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        animation.setStartOffset(delay);
        view.startAnimation(animation);
    }

    // 菜单淡入 动画
    public static void makeInAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
}
