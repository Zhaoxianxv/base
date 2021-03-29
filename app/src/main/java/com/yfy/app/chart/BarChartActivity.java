package com.yfy.app.chart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.charting_mp.charts.BarChart;
import com.yfy.charting_mp.components.XAxis;
import com.yfy.charting_mp.components.YAxis;
import com.yfy.charting_mp.data.BarData;
import com.yfy.charting_mp.data.BarDataSet;
import com.yfy.charting_mp.data.BarEntry;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

@SuppressLint("NonConstantResourceId")
public class BarChartActivity extends BaseActivity {
    private static final String TAG = BarChartActivity.class.getSimpleName();

    @BindView(R.id.p_e_attend_class_chart)
    BarChart barChart;
    @BindView(R.id.p_e_attend_class_title)
    TextView attend_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_bar_main);
        getData();
        initSQToolbar();
        initView();
        attend_title.setText("");

    }


    private void getData(){
        values.clear();

        String list_content="18-上,18-下,19-上,19-下,20-上";
        List<String> list=StringUtils.listToStringSplitCharacters(list_content,",");
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setName(s);
            one.setRight_value(String.valueOf(MathTool.getRandom(10,90)));
            one.setValue(String.valueOf(MathTool.getRandom(10,90)));
            values.add(one);
        }

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("BarChartActivity");


    }



    private void initView(){
        BarData data=generateData();


        data.setValueTextColor(Color.BLACK);
        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        barChart.setPinchZoom(false);
        barChart.setTouchEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);//双击zoom
        barChart.getLegend().setEnabled(true);//显示标注说明
        barChart.setDrawBorders(false);//绘制边框4边

        barChart.animateX(500);
        barChart.animateY(1500);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
        xAxis.setDrawGridLines(false);//X轴网格线
        xAxis.setTextSize(6f);

        YAxis leftAxis = barChart.getAxisLeft();
//            leftAxis.setTypeface(mTf);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//Y轴数值显示位置
        leftAxis.setLabelCount(3, false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setDrawGridLines(false);
//            leftAxis.setInverted(true);//反转

        YAxis rightAxis = barChart.getAxisRight();
//            rightAxis.setTypeface(mTf);
        rightAxis.setDrawLabels(false);//是否显示右边数值
        rightAxis.setDrawAxisLine(false);//是否显示右边线
//        rightAxis.setLabelCount(0, false);
//        rightAxis.setSpaceTop(15f);
        rightAxis.setDrawGridLines(false);

//        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // set data
        barChart.setData(data);

        // do not forget to refresh the chart
        barChart.invalidate();
//            chart.animateY(700, Easing.EasingOption.EaseInCubic);
    }


    public List<KeyValue> values=new ArrayList<>();
    private BarData generateData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        ArrayList<BarDataSet> yDatas = new ArrayList<>();
        int i=0;
        for (KeyValue score:values) {
            yVals1.add(new BarEntry(ConvertObjtect.getInstance().getFloat(score.getValue()), i));
            yVals2.add(new BarEntry(ConvertObjtect.getInstance().getFloat(score.getRight_value()), i));
            i++;
        }

        BarDataSet set = new BarDataSet(yVals1,"我的成绩");
        set.setColor(Color.parseColor("#77942328"));
        set.setDrawValues(true);//在图上显示数值
        set.setBarSpacePercent(0f);
        BarDataSet set2 = new BarDataSet(yVals2, "班级平均成绩");
        set2.setColor(Color.parseColor("#773182c4"));
        set2.setDrawValues(true);
        set2.setBarSpacePercent(0f);

        yDatas.add(set);
        yDatas.add(set2);


        return new BarData(getXDatas(), yDatas);
    }

    private ArrayList<String> getXDatas() {

        ArrayList<String> m = new ArrayList<>();
        for (KeyValue score:values) {
            m.add(score.getName());
        }

        return m;
    }
    /**
     * ----------------------------retrofit-----------------------
     */

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e("");
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
                assert response.errorBody() != null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.stringToGetTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        toastShow(R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
