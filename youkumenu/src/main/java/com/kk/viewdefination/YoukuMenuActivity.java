package com.kk.viewdefination;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class YoukuMenuActivity extends Activity {
    private static final String TAG = "main";
    ImageButton ibMenu, ibHome;
    RelativeLayout rlLevel1, rlLevel2, rlLevel3;
    Boolean visiable = new Boolean(true);
    Boolean invisiable = new Boolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youku_menu);
        initUI();
        setClickListener();
    }

    private void setClickListener() {
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "YoukuMenuActivity.onClick: ibMenu 点击了");
                // 设置标志 是否可见
                if (rlLevel3.getTag() == invisiable) {
                    RotateAnimatioUtil.makeInAnimation(rlLevel3);
                    rlLevel3.setTag(visiable);
                } else {
                    RotateAnimatioUtil.makeOutAnimation(rlLevel3, 0);
                    rlLevel3.setTag(invisiable);
                }

            }
        });
        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "YoukuMenuActivity.onClick: ibhome 点击了");
                // 设置标志 是否可见
                long delay = 0L;
                if (rlLevel2.getTag() == invisiable) {
                    RotateAnimatioUtil.makeInAnimation(rlLevel2);
                    rlLevel2.setTag(visiable);
                } else {
                    if (rlLevel3.getTag() == visiable) {
                        RotateAnimatioUtil.makeOutAnimation(rlLevel3, 0);
                        rlLevel3.setTag(invisiable);
                        delay = 350L;
                    }
                    RotateAnimatioUtil.makeOutAnimation(rlLevel2, delay);
                    rlLevel2.setTag(invisiable);
                }


            }
        });
    }

    private void initUI() {
        ibMenu = (ImageButton) findViewById(R.id.ib_menu);
        ibHome = (ImageButton) findViewById(R.id.ib_home);
        rlLevel1 = (RelativeLayout) findViewById(R.id.rl_level1);
        rlLevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
        rlLevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (rlLevel1.getTag() == invisiable) {
                    RotateAnimatioUtil.makeOutAnimation(rlLevel1, 0);
                    rlLevel1.setTag(visiable);
                } else {
                    RotateAnimatioUtil.makeInAnimation(rlLevel1);
                    rlLevel1.setTag(visiable);
                }
                break;
        }
        return true;
    }
}
