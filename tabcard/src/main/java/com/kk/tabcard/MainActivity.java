package com.kk.tabcard;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String TAG = "main";
    ViewPager viewPager;
    LinearLayout ll_point_container;
    ArrayList<ImageView> mImgList;
    int[] mImgIDs;
    String[] mDes;
    TextView tvDes;
    private int previousPoint;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        iniData();
        setAdapter();
        setListener();
        setLoop();
    }

    private void setLoop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    SystemClock.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            Log.i(TAG, "MainActivity.run: 图片切换中。。。。");
                        }
                    });
                }

            }
        }).start();
    }

    private void setListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "MainActivity.onPageScrolled: viewPager 滚动了");
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "MainActivity.onPageSelected: viewPager 选择了");
                ll_point_container.getChildAt(previousPoint % 5).setEnabled(false);
                ll_point_container.getChildAt(position % 5).setEnabled(true);
                tvDes.setText(mDes[position % 5]);
                previousPoint = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG, "MainActivity.onPageScrollStateChanged: viewPager 状态改变了");
            }
        });
    }

    private void setAdapter() {
        // 初始化的一些数据
        ll_point_container.getChildAt(0).setEnabled(true);
        tvDes.setText(mDes[0]);
        previousPoint = 0;

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(50);
    }

    private void iniData() {
        mImgIDs = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
        mDes = new String[]{"巩俐不低俗,我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"};

        mImgList = new ArrayList<>();
        for (int i = 0; i < mImgIDs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(mImgIDs[i]);
            mImgList.add(imageView);

            View pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);

            if (i != 0) {
                params.leftMargin = 10;
            }
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, params);
        }

    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.vp);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
        tvDes = (TextView) findViewById(R.id.tv_desc);
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG, "MyPagerAdapter.instantiateItem: 添加条目 position :" + position);
            ImageView imageView = mImgList.get(position % 5);
            container.addView(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i(TAG, "MyPagerAdapter.destroyItem: 删除条目 position :" + position);

            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mImgList.size() * 100;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}



