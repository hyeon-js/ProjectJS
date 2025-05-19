package com.hyeonjs.projectjs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SubwayActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        WebView web = new WebView(this);
        web.loadUrl("https://hyeon-js.github.io/subway/");
        web.clearCache(true);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, final String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:removeHeader();");
                super.onPageFinished(view, url);
            }
        });
        layout.addView(web);

        drawer = new DrawerLayout(this);
        drawer.addView(layout);
        drawer.addView(createLeftDrawer(web));
        setContentView(drawer);
    }

    private LinearLayout createLeftDrawer(final WebView web) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);

        final String[] lines = {"수도권 1호선", "서울 2호선", "수도권 3호선", "수도권 4호선", "수도권 5호선", "서울 6호선", "서울 7호선", "수도권 8호선", "서울 9호선",
                "경의·중앙선", "수인·분당선", "신분당선", "경춘선", "용인경전철 (에버라인)", "의정부경전철", "경강선",
                "우이신설선", "서해선", "김포골드라인", "신림선", "공항철도", "인천1호선", "인천2호선", "GTX - A", //23
                "부산 1호선", "부산 2호선", "부산 3호선", "부산 4호선", "동해선", "부산김해경전철", //29
                "대구 1호선", "대구 2호선", "대구 3호선", "대경선",
                "닫기"};
        final int[] lineIds = {
                1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009,
                1101, 1102, 1103, 1104, 1105, 1106, 1107,
                1108, 1109, 1110, 1111, 1500, 1501, 1502, 1601,

                1001, 1002, 1003, 1004, 1101, 1102,

                1001, 1002, 1003, 1101
        };
        ListView list = new ListView(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lines);
        list.setAdapter(adapter);
        list.setOnItemClickListener((adapterView, view, pos, id) -> {
            if (pos < lines.length - 1) {
                if (pos < 24) web.loadUrl("javascript:loadData('seoul', " + lineIds[pos] + ");");
                else if (pos < 30) web.loadUrl("javascript:loadData('busan', " + lineIds[pos] + ");");
                else web.loadUrl("javascript:loadData('daegu', " + lineIds[pos] + ");");
            }
            drawer.closeDrawer(Gravity.LEFT);
        });
        layout.addView(list);

        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(-1, -1);
        params.gravity = Gravity.LEFT;
        layout.setLayoutParams(params);
        layout.setBackgroundColor(Color.WHITE);
        int pad = dip2px(5);
        list.setPadding(pad, pad, pad, pad);
        return layout;
    }

    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }
}