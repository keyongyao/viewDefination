package com.kk.drawmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    ImageButton ibMenu;
    DrawMenu drawMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setClickListener();
    }

    private void setClickListener() {
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawMenu.openOrCloseMenu();
            }
        });
    }

    private void initUI() {
        ibMenu = (ImageButton) findViewById(R.id.ib_content_ibmenu);
        drawMenu = (DrawMenu) findViewById(R.id.dm);
    }


}
