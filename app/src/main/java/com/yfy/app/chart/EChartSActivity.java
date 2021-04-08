package com.yfy.app.chart;

import android.annotation.SuppressLint;
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
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.json.JsonTool;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NonConstantResourceId")
public class EChartSActivity extends BaseActivity {
    private static final String TAG = EChartSActivity.class.getSimpleName();

    public EchartView wv_analysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_e_main);
        initSQToolbar();
        initDialog();

    }



    private ConfirmContentWindow confirmContentWindow;
    private void initDialog(){
        confirmContentWindow = new ConfirmContentWindow(mActivity);
        confirmContentWindow.setPopClickListener(new NoFastClickListener() {
            @Override
            public void popClick(View view) {

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
        res.setAngleAxis(angleAxis);

        RadiusAxis radiusAxis=new RadiusAxis();
        radiusAxis.setZ(10);
        radiusAxis.setType("category");
        radiusAxis.setData(title_name);
        res.setRadiusAxis(radiusAxis);

        Polar polar=new Polar();
        res.setPolar(polar);


        List<Series> seriesList=new ArrayList<>();
        for (String s:tag_name){
            Series series=new Series("bar","polar",s,"a");
            series.setData(datas);
            seriesList.add(series);
        }
        res.setSeries(seriesList);




        Legend legend=new Legend();
        legend.setShow(true);
        legend.setData(tag_name);
        res.setLegend(legend);



        Gson gson= new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        Logger.e(gson.toJson(res, PileRes.class));
//        showDialog("title",JsonTool.objectToJson(res,gson));

        return  gson.toJson(res, PileRes.class);
    }

    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("EChartActivity");
        toolbar.addMenuText(TagFinal.ONE_INT,"Json");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void onClick(View view, int position) {
                initString();
            }
        });

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
            wv_analysis.refreshEchartsWithOption(initString());
        }
    }


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
