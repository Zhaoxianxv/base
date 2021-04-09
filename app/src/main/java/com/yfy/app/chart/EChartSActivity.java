package com.yfy.app.chart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NonConstantResourceId")
public class EChartSActivity extends BaseActivity {
    private static final String TAG = EChartSActivity.class.getSimpleName();

    public EChartView wv_analysis;

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


    }


    /**刷新图表
     * java调用js的loadEcharts方法刷新echart
     * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
     */
    public void initEChart(){

        wv_analysis =  findViewById(R.id.e_chart_web);
        wv_analysis.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);



        wv_analysis.removeJavascriptInterface("searchBoxJavaBridge_");
        wv_analysis.removeJavascriptInterface("accessibility");
        wv_analysis.removeJavascriptInterface("accessibilityTraversal");
        wv_analysis.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl(initString());
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv_analysis != null) {
            wv_analysis.removeAllViews();
            wv_analysis.destroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        wv_analysis.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        wv_analysis.resumeTimers();
    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
