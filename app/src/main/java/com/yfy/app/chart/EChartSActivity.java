package com.yfy.app.chart;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Pie;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EChartSActivity extends BaseActivity {
    private static final String TAG = EChartSActivity.class.getSimpleName();

    public EchartView wv_analysis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_e_main);
        initSQToolbar();

    }

    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("EChartActivity");

        wv_analysis =  findViewById(R.id.e_chart_web);
        wv_analysis.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        WebSettings webSettings = wv_analysis.getSettings();



        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setDefaultTextEncodingName("gbk");

        wv_analysis.removeJavascriptInterface("searchBoxJavaBridge_");
        wv_analysis.removeJavascriptInterface("accessibility");
        wv_analysis.removeJavascriptInterface("accessibilityTraversal");
        wv_analysis.setWebViewClient(new MyWebViewClient());
        wv_analysis.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }




    /**
     * ----------------------------retrofit-----------------------
     */


    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            refreshLineChart();
        }

        private void refreshLineChart() {
            //处理数据，调用工具类生成option对象的json数据
            Object[] oneData = new Object[6];
            List<Map<String, Object>> twoData = new ArrayList<>();
            for (int i = 0; i <5; i++) {
                oneData[i] = "name"+i;
                Map<String, Object> data = new HashMap<>();
                data.put("value", MathTool.getRandomInt(30,40));
                data.put("name", "name"+i);
                twoData.add(data);
            }
            GsonOption optionString = EchartOptionUtil.getBingTuChartOptions(oneData, twoData);//生成json
            wv_analysis.refreshEchartsWithOption(optionString);
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





    public static class EchartOptionUtil {

        /**
         * 饼状图
         *
         * @param data
         * @param yAxis
         * @return
         */
        public static GsonOption getBingTuChartOptions(Object[] data, List<Map<String, Object>> yAxis) {
            GsonOption option = new GsonOption();
            option.title("支付方式占比");
            option.title().setX(X.center);

            option.tooltip().trigger(Trigger.item);
            option.tooltip().formatter("{a} {b}:{c} ({d}%)");

            option.legend().left(X.left).orient(Orient.vertical);
            option.legend().data(data);

            Pie pie = new Pie();
            pie.name("");
            pie.type(SeriesType.pie);
            pie.center("50%", "70%").radius("55%");
            pie.itemStyle().emphasis().shadowBlur(10).shadowOffsetX(0).shadowColor("rgba(0, 0, 0, 0.5)");
            pie.setData(yAxis);
            option.series(pie);
            return option;
        }

        /**
         * 折线和柱状图混合图
         *
         * @return
         */
        public Map<String, Object> getLineChartOptions(
                Object[] xAxisData, double yAxisOneMin, double yAxisOneMax, double yAxisOneInterval,
                double yAxisTwoMin, double yAxisTwoMax, double yAxisTwoMaxInterval, Object[] yAxisOneData,
                Object[] yAxisTwoData, Object[] yAxisThreeData) {
            Map<String, Object> option = new HashMap<String, Object>();
            Map<String, Object> tooltip = new HashMap<String, Object>();
            tooltip.put("trigger", "axis");
            Map<String, Object> axisPointer = new HashMap<String, Object>();
            axisPointer.put("type", "cross");
            Map<String, Object> crossStyle = new HashMap<String, Object>();
            crossStyle.put("color", "#999");
            axisPointer.put("crossStyle", crossStyle);
            tooltip.put("axisPointer", axisPointer);
            option.put("tooltip", tooltip);

            Map<String, Object> toolbox = new HashMap<String, Object>();
            Map<String, Object> feature = new HashMap<String, Object>();
            Map<String, Object> dataView = new HashMap<String, Object>();
            dataView.put("show", true);
            dataView.put("readOnly", false);
            feature.put("dataView", dataView);

            Map<String, Object> magicType = new HashMap<String, Object>();
            magicType.put("show", true);
            List<String> type = new ArrayList<String>();
            type.add("line");
            type.add("bar");
            magicType.put("type", type);
            feature.put("magicType", magicType);
            Map<String, Object> restore = new HashMap<String, Object>();
            restore.put("show", true);
            feature.put("restore", restore);
            Map<String, Object> saveAsImage = new HashMap<String, Object>();
            saveAsImage.put("show", true);
            feature.put("saveAsImage", saveAsImage);
            toolbox.put("feature", feature);

            option.put("toolbox", toolbox);

            Map<String, Object> legend = new HashMap<String, Object>();
            legend.put("data", new Object[]{"月销售额", "日均销售额", "毛利率"});
            option.put("legend", legend);
            List<Map<String, Object>> xAsis = new ArrayList<Map<String, Object>>();
            Map<String, Object> xAsisMap = new HashMap<String, Object>();
            xAsisMap.put("type", "category");
            xAsisMap.put("data", xAxisData);
            Map<String, Object> axisPointers = new HashMap<String, Object>();
            axisPointers.put("type", "shadow");
            xAsisMap.put("axisPointer", axisPointers);
            xAsis.add(xAsisMap);
            option.put("xAxis", xAsis);
            List<Map<String, Object>> yAsis = new ArrayList<Map<String, Object>>();
            Map<String, Object> yAsisOne = new HashMap<String, Object>();
            yAsisOne.put("type", "value");
            yAsisOne.put("name", "销售额");
            yAsisOne.put("min", yAxisOneMin);
            yAsisOne.put("max", yAxisOneMax);
            yAsisOne.put("interval", yAxisOneInterval);
            Map<String, Object> axisLabel = new HashMap<String, Object>();
            axisLabel.put("formatter", "{value}");
            yAsisOne.put("axisLabel", axisLabel);
            yAsis.add(yAsisOne);
            Map<String, Object> yAsisTwo = new HashMap<String, Object>();
            yAsisTwo.put("type", "value");
            yAsisTwo.put("name", "毛利率");
            yAsisTwo.put("min", yAxisTwoMin);
            yAsisTwo.put("max", yAxisTwoMax);
            yAsisTwo.put("interval", yAxisTwoMaxInterval);
            Map<String, Object> axisLabelTwo = new HashMap<String, Object>();
            axisLabelTwo.put("formatter", "{value}");
            yAsisTwo.put("axisLabel", axisLabel);
            yAsis.add(yAsisTwo);

            option.put("yAxis", yAsis);
            List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
            Map<String, Object> seriesOne = new HashMap<String, Object>();
            seriesOne.put("name", "月销售额");
            seriesOne.put("type", "bar");
            seriesOne.put("data", yAxisOneData);
            series.add(seriesOne);

            Map<String, Object> seriesTwo = new HashMap<String, Object>();
            seriesTwo.put("name", "日均销售额");
            seriesTwo.put("type", "bar");
            seriesTwo.put("data", yAxisTwoData);
            series.add(seriesTwo);

            Map<String, Object> seriesThree = new HashMap<String, Object>();
            seriesThree.put("name", "毛利率");
            seriesThree.put("type", "line");
            seriesThree.put("yAxisIndex", 1);
            seriesThree.put("data", yAxisThreeData);
            series.add(seriesThree);
            option.put("series", series);
            return option;
        }

    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
