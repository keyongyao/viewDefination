package com.kk.myswitchbutton;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "main";
    MySwitch mMySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mMySwitch = (MySwitch) findViewById(R.id.ms_test);
        mMySwitch.setOnMySwitchStatusChange(new MySwitch.OnMySwitchStatusChange() {
            @Override
            public void onStatusChange(boolean isChecked) {
                Log.i(TAG, "MainActivity.onStatusChange isChecked: " + isChecked);
            }
        });
    }
}
