package com.yfy.app.PEquality;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.yfy.app.PEquality.adapter.PEAttendListAdapter;
import com.yfy.app.PEquality.adapter.PEAttitudeStuMainAdapter;
import com.yfy.app.PEquality.tea.PEAttendListActivity;
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
import com.yfy.charting_mp.animation.Easing;
import com.yfy.charting_mp.charts.BarChart;
import com.yfy.charting_mp.charts.LineChart;
import com.yfy.charting_mp.components.Legend;
import com.yfy.charting_mp.components.XAxis;
import com.yfy.charting_mp.components.YAxis;
import com.yfy.charting_mp.data.BarData;
import com.yfy.charting_mp.data.BarDataSet;
import com.yfy.charting_mp.data.BarEntry;
import com.yfy.charting_mp.data.Entry;
import com.yfy.charting_mp.data.LineData;
import com.yfy.charting_mp.data.LineDataSet;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PEAttitudeStuMainActivity extends BaseActivity {
    private static final String TAG = PEAttitudeStuMainActivity.class.getSimpleName();

    public PEAttitudeStuMainAdapter adapter_attitude;
    public PEAttendListAdapter adapter_attend;


    @Bind(R.id.p_e_score_line_chart)
    LineChart mChart;
    @Bind(R.id.p_e_score_title)
    TextView left_title;


    @Bind(R.id.p_e_attend_class_chart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_attitude_stu_main);
        getData();
        initSQToolbar();
        initChart(mChart);
        setData(100);


        initBarView();

    }


    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);




        String list_content="18-上,18-下,19-上,19-下,20-上,1,2,3,4";
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
        toolbar.setTitle(title);


    }



    private void setData(float range) {

        List<String> yearlist=StringUtils.listToStringSplitCharacters("9月,10月,11月,12月,1月,2,3,4",",");
        ArrayList<String> xVals = new ArrayList<>();
        for (String s:yearlist) {
            xVals.add(s);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        int i = 0;
        for (String s:yearlist) {

            float mult = (range + 1);
            float val = MathTool.getRandomInt(50,50);// + (float)

            yVals.add(new Entry(val, i));
            i++;
            if (i==5){
                left_title.setText(StringUtils.stringToGetTextJoint("学习态度当前得分%1$S",String.valueOf(val)));
            }
        }

        // 折线图链接线设置
        LineDataSet set1 = new LineDataSet(yVals, "");
        //设置线样式
        set1.enableDashedLine(10f, 5f, 0f);
        //line 颜色
        set1.setColor(ColorRgbUtil.getBaseColor());
        //点颜色
        set1.setCircleColor(ColorRgbUtil.getBaseColor());
        //线粗细
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(7f);


        //链接线数组
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);


        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        float ratio = (float) xVals.size()/(float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        mChart.zoom(ratio,1f,0,0);
        // set data
        mChart.setData(data);
    }
    //
    public void initChart(LineChart mChart){

        // set this to true to draw the grid background, false if not
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately

        //
        mChart.setPinchZoom(false);

        //Set this to true to enable scaling (zooming in and out by gesture)
        mChart.setScaleEnabled(false);
        mChart.setScaleXEnabled(false);
        mChart.setScaleYEnabled(false);
        //绘制边框4边
        mChart.setDrawBorders(false);
//            mChart.setBorderColor(ColorRgbUtil.getBaseColor());
        //动画
        mChart.animateX(1500, Easing.EasingOption.EaseInOutQuart);



        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        //X轴线绘制 true false
        xAxis.setDrawAxisLine(true);
        //X网格图
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(8f);

        YAxis leftAxis = mChart.getAxisLeft();//
        YAxis rightAxis = mChart.getAxisRight();//右边数据
//            rightAxis.setDrawTopYLabelEntry(false);
        //是否显示
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);

        //是否显示
        leftAxis.setEnabled(true);
        //Y轴线绘制 true false
        leftAxis.setDrawAxisLine(true);
        //Y网格图
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaxValue(120f);
        leftAxis.setAxisMinValue(0f);
        //从0开始
        leftAxis.setStartAtZero(true);
        leftAxis.setTextSize(7f);


        Legend l = mChart.getLegend();
        l.setEnabled(false);//

        setData(100);
    }









    private void initBarView(){
        BarData data=generateData();
        data.setValueTextColor(Color.BLACK);
        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        barChart.setPinchZoom(false);
        barChart.setTouchEnabled(true);
        //设置是否可以拖拽
        barChart.setDragEnabled(true);
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

        float ratio = (float) values.size()/(float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        barChart.zoom(ratio,1f,0,0);
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

    @OnClick(R.id.attend_card)
    void setAttend(){
        Intent intent=new Intent(mActivity,PEAttendListActivity.class);
        intent.putExtra(Base.title,"请假记录");
        intent.putExtra(Base.type,TagFinal.FALSE);
        startActivity(intent);
    }

    @OnClick(R.id.attitude_card)
    void setAttitude(){
        Intent intent=new Intent(mActivity,PEAttitudeStuActivity.class);
        intent.putExtra(Base.title,"学习态度");
        startActivity(intent);
    }



    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        req.setSession_key(Base.user.getSession_key());

        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_term_list_api(env);
        call.enqueue(this);
    }
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
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
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
