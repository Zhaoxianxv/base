package com.yfy.app.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by zxx.
 * Date: 2021/3/29
 */
public class EChartView extends WebView {
    private static final String TAG = EChartView.class.getSimpleName();

    public EChartView(Context context) {
        this(context, null);
    }

    public EChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);

//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setAllowFileAccess(false);
//        webSettings.setAllowFileAccessFromFileURLs(false);
//        webSettings.setAllowUniversalAccessFromFileURLs(false);
//        webSettings.setDefaultTextEncodingName("gbk");

//        loadUrl("file:///android_asset/echarts.html");
    }

}