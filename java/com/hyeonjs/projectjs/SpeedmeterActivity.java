package com.hyeonjs.projectjs;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SpeedmeterActivity extends AppCompatActivity {

    private LocationListener listener;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);

        web = new WebView(this);
        web.setBackgroundColor(Color.TRANSPARENT);
        StringBuilder str = new StringBuilder("<meta name='viewport' content='user-scalable=no width=device-width' />")
                .append("<style>table{border-top: 1px solid black; color: black; border-collapse: collapse; text-align: center;}\n" +
                        "td{border-bottom: 1px solid black; padding: 16px; font-size: 32px;}</style>")
                .append("<table width=100%>")
                .append("<tr align=center><td>속력 측정 전</td></tr>")
                .append("</table>");
        if (Build.VERSION.SDK_INT > 23) {
            web.loadDataWithBaseURL(null, str.toString(), "text/html; charset=UTF-8", null, null);
        } else {
            web.loadData(str.toString(), "text/html; charset=UTF-8", null);
        }
        layout.addView(web);

        TextView dev = new TextView(this);
        dev.setText("© 2022-2025 Hyeon.js, All rights reserved.");
        dev.setTextSize(12);
        dev.setGravity(Gravity.CENTER);
        dev.setPadding(0, dip2px(8), 0, dip2px(8));
        layout.addView(dev);


        int pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);

        startLocationListener();
    }

    private void startLocationListener() {
        if(!checkPermission()) {
            Toast.makeText(this, "위치 권한 없음", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = location -> {
            updateScreen(location.getSpeed());
//            txt.setText(round(speed) + " m/s\n" + round(speed * 3.6) + " km/h\n" +
//                    round(speed * 1.944) + " knot\n" + round(speed * 2.237) + " mph");
        };
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }

    private void stopLocationListener() {
        if (listener == null) return;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(listener);
    }

    private void updateScreen(float speed) {
        StringBuilder str = new StringBuilder("<meta name='viewport' content='user-scalable=no width=device-width' />")
                .append("<style>table{border-top: 1px solid black; color: black; border-collapse: collapse; text-align: center;}\n" +
                        "td{border-bottom: 1px solid black; padding: 16px; font-size: 32px;}</style>")
                .append("<table width=100%>")
                .append("<tr align=center><td>" + round(speed) + "</td><td>m/s</td></tr>")
                .append("<tr align=center><td>" + round(speed * 3.6) + "</td><td>km/h</td></tr>")
                .append("<tr align=center><td>" + round(speed * 1.944) + "</td><td>knot</td></tr>")
                .append("<tr align=center><td>" + round(speed * 22.237) + "</td><td>mph</td></tr>")
                .append("</table>");
        if (Build.VERSION.SDK_INT > 23) {
            web.loadDataWithBaseURL(null, str.toString(), "text/html; charset=UTF-8", null, null);
        } else {
            web.loadData(str.toString(), "text/html; charset=UTF-8", null);
        }
    }

    private String round(double num) {
        num = (double)Math.round(num * 100) / 100;
        return String.valueOf(num);
    }

    public boolean checkPermission() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT < 23) return true;
        for (String permission : permissions) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationListener();
    }

    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }
}
