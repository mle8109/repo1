package com.project.nra;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
    private static final String TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra("url");
        Log.d(TAG, "url = " + url);

        loadWebPage(url);
    }

    private void loadWebPage(String url) {
        Log.d(TAG, "loadWebPage: " + url);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private class WebViewBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
