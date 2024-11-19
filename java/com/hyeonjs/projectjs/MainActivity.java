package com.hyeonjs.projectjs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);

        String[] menus = {"실시간 전철 운행 정보", "속력 측정기", "여자친구 노래 노래방번호"};
        Button[] btns = new Button[menus.length];
        for(int n=0;n<menus.length;n++){
            btns[n] = new Button(this);
            btns[n].setText(menus[n]);
            btns[n].setId(n);
            btns[n].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {

                    }
                }
            });
            layout.addView(btns[n]);
        }

        TextView dev = new TextView(this);
        dev.setText("© 2022-2024 Hyeon.js, All rights reserved.");
        dev.setTextSize(12);
        dev.setGravity(Gravity.CENTER);
        dev.setPadding(0, dip2px(8), 0, dip2px(8));
        layout.addView(dev);


        int pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }

    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

}