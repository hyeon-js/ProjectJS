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

        final String[] lines = {"1호선", "2호선", "3호선", "4호선", "5호선", "6호선", "7호선", "8호선", "9호선",
                "경의·중앙선", "수인·분당선", "신분당선", "경춘선", "용인경전철 (에버라인)", "의정부경전철", "경강선",
                "우이신설선", "서해선", "김포골드라인", "신림선",
                "공항철도", "인천1호선", "인천2호선", "GTX - A", "닫기"};
        ListView list = new ListView(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lines);
        list.setAdapter(adapter);
        list.setOnItemClickListener((adapterView, view, pos, id) -> {
            if(!lines[pos].equals("닫기")) web.loadUrl("javascript:loadData('" + lines[pos]+ "');");
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