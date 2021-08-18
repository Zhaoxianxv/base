package com.yfy.app.chart;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.abel533.echarts.json.GsonOption;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yfy.app.chart.bean.AngleAxis;
import com.yfy.app.chart.bean.Legend;
import com.yfy.app.chart.bean.PileRes;
import com.yfy.app.chart.bean.Polar;
import com.yfy.app.chart.bean.RadiusAxis;
import com.yfy.app.chart.bean.Series;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class EChartSActivity extends BaseActivity {
    private static final String TAG = EChartSActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_e_main);
        initSQToolbar();
        initDialog();
        initEChart();

    }



    private ConfirmContentWindow confirmContentWindow;
    private void initDialog(){
        confirmContentWindow = new ConfirmContentWindow(mActivity);
        confirmContentWindow.setPopClickListener(new NoFastClickListener() {
            @Override
            public void fastPopClick(View view) {

                switch (view.getId()){
                    case R.id.pop_dialog_title:
                        break;
                    case R.id.pop_dialog_ok:
                        confirmContentWindow.dismiss();
                        break;
                }
            }
        });
    }

    public void showDialog(String title,String content){
        if (confirmContentWindow==null)return;
        confirmContentWindow.setTitle_s(title,content);
        confirmContentWindow.showAtCenter();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("EChartActivity");
        toolbar.addMenuText(TagFinal.ONE_INT,"Json");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position) {
                initString();
            }
        });


    }


    /**刷新图表
     * java调用js的loadEcharts方法刷新echart
     * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
     */

    @BindView(R.id.e_chart_web)
    WebView webView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    public void initEChart(){

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("gbk");
        webView.loadUrl("file:///android_asset/echarts.html");


        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        webView.setWebViewClient(new WebViewClient());

        webView.setWebViewClient(new MyWebViewClient());
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }



    private String initString(){

        List<String> title_name=StringUtils.listToStringSplitCharacters("周一,周二,周三,周四",",");
        List<String> tag_name= StringUtils.listToStringSplitCharacters("A,B,C",",");

        List<Integer> datas=new ArrayList<>();
        datas.add(1);
        datas.add(2);
        datas.add(3);
        datas.add(2);







        PileRes res=new PileRes();

        AngleAxis angleAxis=new AngleAxis();
        res.setA(angleAxis);

        RadiusAxis radiusAxis=new RadiusAxis();
        radiusAxis.setA("category");
        radiusAxis.setB(title_name);
        radiusAxis.setC(10);
        res.setB(radiusAxis);

        Polar polar=new Polar();
        res.setC(polar);


        List<Series> seriesList=new ArrayList<>();
        for (String s:tag_name){
            Series series=new Series("bar","polar",s,"a");
            series.setB(datas);
            seriesList.add(series);
        }
        res.setD(seriesList);




        Legend legend=new Legend();
        legend.setA(true);
        legend.setD(tag_name);
        res.setE(legend);



        Gson gson= new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        String call = StringUtils.stringToGetTextJoint(
                "javascript:loadEcharts('%1$s')",
                gson.toJson(res,PileRes.class)) ;


        return call;

    }






    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl( initString());
        }

        /**
         * shouldInterceptRequest WebView
         * 中调用的每个请求都会经过该拦截器，如果一个页面有超链接，那么依然会经过该拦截器
         * 参数说明：
         *
         * @param view 接收WebViewClient的那个实例，前面看到webView.setWebViewClient(new
         *             MyAndroidWebViewClient())，即是这个webview。
         * @param url  raw url 制定的资源
         * @return 返回WebResourceResponse包含数据对象，或者返回null
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          String url) {
            if (url.contains("http") || url.contains("www")
                    || url.contains("https")) {
                String response = "<html><body>该数据不存在</body></html>";
                WebResourceResponse weResourceResponse = new WebResourceResponse(
                        "text/html", "utf-8", new ByteArrayInputStream(
                        response.getBytes()));
                return weResourceResponse;
            } else {
                return super.shouldInterceptRequest(view, url);
            }
        }

        /*
         * url重定向会执行此方法以及点击页面某些链接也会执行此方法
         * 当加载的网页需要重定向的时候就会回调这个函数告知我们应用程序是否需要接管控制网页加载，如果应用程序接管，并且return
         * true意味着主程序接管网页加载，如果返回false让webview自己处理。
         *
         * @param view 当前webview
         *
         * @param url 即将重定向的url
         *
         * @return true:表示当前url已经加载完成，即使url还会重定向都不会再进行加载, false
         * 表示此url默认由系统处理，该重定向还是重定向，直到加载完成
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http") || url.contains("www") || url.contains("https")) {
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }







    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        webView.pauseTimers();
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        webView.resumeTimers();
//    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
