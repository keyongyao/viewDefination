package com.kk.pullfreshlist;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends Activity {
    PullFreshListView listView;
    ArrayList<String> mListData;
    Activity mActivity;
    MyListAdapter myListAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        iniData();
        initUI();
        setAdapter();

    }

    private void setAdapter() {
        listView.setAdapter((myListAdapter = new MyListAdapter()));
        listView.setDividerHeight(1);
        listView.setOnLoadingData(new PullFreshListView.OnLoadingData() {
            @Override
            public void loadingOnTop(final Handler handler) {
                for (int i = 0; i < 3; i++) {

                    mListData.add(0, "我是头部新增的数据" + dateFormat.format(System.currentTimeMillis()));
                }
                // 延时 2 秒
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = PullFreshListView.LOADINGDINISHED;
                        handler.sendMessage(msg);
                    }
                }, 2000);
                myListAdapter.notifyDataSetChanged();
            }

            @Override
            public void loadingOnBotton(final Handler handler) {
                for (int i = 0; i < 3; i++) {

                    mListData.add("我是尾部新增的数据" + dateFormat.format(System.currentTimeMillis()));
                }
                // 延时 2 秒
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = PullFreshListView.LOADINGDINISHED;
                        handler.sendMessage(msg);
                    }
                }, 2000);
                myListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void iniData() {
        mListData = new ArrayList<>();
        for (int i = 0; i < 70; i++) {
            mListData.add("原本的ListView的数据");
        }
    }

    private void initUI() {
        listView = (PullFreshListView) findViewById(R.id.pslv_test);
    }

    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public String getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(mActivity);
            view.setText(getItem(position));
            view.setTextSize(20);
            return view;
        }
    }
}
