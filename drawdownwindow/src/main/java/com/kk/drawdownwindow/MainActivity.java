package com.kk.drawdownwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    EditText input;
    ImageButton imageButton;
    Activity mActivity;
    PopupWindow mPopupWindow;
    ArrayList<String> mNum;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initData();
        initUI();
        setClickListener();
    }

    private void initData() {
        mNum = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            mNum.add(10000 + i + "");
        }
    }

    private void setClickListener() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow = new PopupWindow(mListView, input.getWidth(), 300);
                //    mPopupWindow.setFocusable(true);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.showAsDropDown(input, 0, 0);

            }
        });

    }

    private void initUI() {
        input = (EditText) findViewById(R.id.et_input);
        imageButton = (ImageButton) findViewById(R.id.ib);
        mListView = new ListView(mActivity);
        mListView.setAdapter(new MyListAdapter());
    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mNum.size();
        }

        @Override
        public String getItem(int position) {
            return mNum.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(mActivity, R.layout.layout, null);
            ImageView del = (ImageView) inflate.findViewById(R.id.iv_del);
            final TextView tv = (TextView) inflate.findViewById(R.id.tv_num);
            tv.setText(getItem(position));
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = tv.getText().toString().trim();
                    input.setText(num);
                    mPopupWindow.dismiss();
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNum.remove(position);
                    notifyDataSetChanged();
                }
            });
            return inflate;
        }
    }
}
