package com.yfy.app.PEquality;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.bean.SchoolRes;
import com.yfy.app.PEquality.bean.ScoreBean;
import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.bean.TermBean;
import com.yfy.app.login.LoginActivity;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.charting_mp.charts.RadarChart;
import com.yfy.charting_mp.components.Legend;
import com.yfy.charting_mp.components.XAxis;
import com.yfy.charting_mp.components.YAxis;
import com.yfy.charting_mp.data.Entry;
import com.yfy.charting_mp.data.RadarData;
import com.yfy.charting_mp.data.RadarDataSet;
import com.yfy.charting_mp.utils.ColorTemplate;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.Logger;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.CPWListView;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.recycerview.DividerGridItemDecoration;
import com.yfy.final_tag.recycerview.RecycAnimator;
import com.yfy.view.SQToolBar;
import com.yfy.view.multi.MultiPictureView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Response;

public class PEQualityMainActivity extends BaseActivity {
    private static final String TAG = PEQualityMainActivity.class.getSimpleName();


    @Bind(R.id.p_e_main_user_head)
    AppCompatImageView user_head;
    @Bind(R.id.p_e_main_user_name)
    AppCompatTextView user_name;
    @Bind(R.id.p_e_main_user_class)
    AppCompatTextView user_class;
    @Bind(R.id.p_e_main_user_content)
    AppCompatTextView user_content;
    @Bind(R.id.p_e_user_grade_title)
    AppCompatTextView grade_title;
    @Bind(R.id.p_e_user_grade_sub)
    AppCompatTextView grade_sub;
    @Bind(R.id.p_e_main_recipe)
    AppCompatTextView recipe_title;
    @Bind(R.id.p_e_main_recipe_flow_layout)
    FlowLayout recipe_flowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_quality_stu_main);
        initSQToolbar();
        initView();
        initChartView();
    }


    private TermBean select_term;
    private TextView menu_one;
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("体育素质");
        menu_one=toolbar.addMenuText(TagFinal.ONE_INT,"add");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,SelectedTermActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);
            }
        });
        toolbar.setOnNaviClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Base.user==null){
                    Intent intent=new Intent(mActivity,LoginActivity.class);
                    startActivityForResult(intent,TagFinal.UI_ADD);
                }else{
                    finish();
                }

            }
        });
        select_term=new TermBean();
        select_term.setName(UserPreferences.getInstance().getTermName());
        select_term.setId(UserPreferences.getInstance().getTermId());
        menu_one.setText(select_term.getName());

    }



    private void initView(){
        Typeface mTypeface=Typeface.createFromAsset(getAssets(),"OpenSans-Bold.ttf");
        if (Base.user==null)return;
        GlideTools.chanCircle(mActivity, Base.user.getHeadPic(), user_head, R.drawable.ic_parent_head);
        user_name.setText(Base.user.getName());
        user_class.setText("三年级二十五班");
        grade_title.setText("优");
        grade_sub.setText("95.5");
        user_content.setText(StringUtils.getTextJoint("身高:\t%1$scm\t\t体重:\t%2$skg",145,39));
        recipe_title.setText("运动处方");
        grade_title.setTypeface(mTypeface);
        grade_sub.setTypeface(mTypeface);
        String grade="优";
        switch (grade){
            case "优":
                grade_title.setTextColor(ColorRgbUtil.getBaseColor());
                grade_sub.setTextColor(ColorRgbUtil.getBaseColor());
                break;
            default:
                grade_title.setTextColor(ColorRgbUtil.getGray());
                grade_sub.setTextColor(ColorRgbUtil.getGray());
                break;
        }
        List<KeyValue> list=new ArrayList<>();
        list.add(new KeyValue("运动处方1","加强运动"));
        list.add(new KeyValue("运动处方1","加强运动"));
        list.add(new KeyValue("运动处方1","加强运动"));
        list.add(new KeyValue("运动处方1","加强运动"));
        list.add(new KeyValue("运动处方1","加强运动"));
        setFlowLayoutTop(list);
        initRecyclerView();
    }



    private void setFlowLayoutTop(List<KeyValue> top_jz){

        LayoutInflater mInflater = LayoutInflater.from(mActivity);
        if (recipe_flowlayout.getChildCount()!=0){
            recipe_flowlayout.removeAllViews();
        }
        for (KeyValue bean:top_jz){
            RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.public_detail_top_item,recipe_flowlayout, false);
            TextView key=layout.findViewById(R.id.seal_detail_key);
            TextView value=layout.findViewById(R.id.seal_detail_value);
            RatingBar ratingBar=layout.findViewById(R.id.seal_detail_value_star);
            LinearLayout linearLayout=layout.findViewById(R.id.public_detail_txt_layout);
            MultiPictureView multi=layout.findViewById(R.id.public_detail_layout_multi);

            key.setTextColor(ColorRgbUtil.getGrayText());
            value.setTextColor(ColorRgbUtil.getBaseText());
            key.setText(bean.getKey());
            linearLayout.setVisibility(View.VISIBLE);
            multi.setVisibility(View.GONE);
            value.setText(bean.getValue());
            ratingBar.setVisibility(View.GONE);
            value.setVisibility(View.VISIBLE);

            recipe_flowlayout.addView(layout);
        }
    }



    public RecyclerView recycler_view;
    public PEQualityMainAdapter adapter;
    public List<KeyValue> adapter_data_show =new ArrayList<>();
    public DividerGridItemDecoration white_line=new DividerGridItemDecoration(Color.parseColor("#ffffff"));

    public void initRecyclerView(){
        recycler_view =findViewById(R.id.p_e_main_type_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        recycler_view.setLayoutManager(manager);
        recycler_view.setItemAnimator(new RecycAnimator());
        //添加线
//        recycler_view.addItemDecoration(white_line);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        adapter=new PEQualityMainAdapter(mActivity);
        recycler_view.setAdapter(adapter);
        adapter.setItemOnc(new PEQualityMainAdapter.ItemOnc() {
            @Override
            public void onc(KeyValue bean) {
                switch (bean.getType()){
                    case "":
                        break;
                }
            }
        });
        adapter_data_show.add(new KeyValue("体育荣誉证书",R.drawable.ic_parent_head));
        adapter_data_show.add(new KeyValue("体育比赛成绩",R.drawable.ic_parent_head));
        adapter_data_show.add(new KeyValue("课堂表现",R.drawable.ic_parent_head));
        adapter_data_show.add(new KeyValue("膳食建议",R.drawable.ic_parent_head));
        adapter.setDataList(adapter_data_show);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_TAG:
                    select_term=data.getParcelableExtra(Base.data);
                    menu_one.setText(select_term.getName());
                    break;
                case TagFinal.UI_ADD:
                    getTerm();
                    break;
            }
        }
    }






    private Typeface tf;
    private RadarChart mChart;
    private void initChartView(){
        GlideTools.chanCircle(mActivity, Base.user.getHeadPic(),user_head,R.drawable.ic_parent_head);
        user_name.setText(StringUtils.getTextJoint("用户名: %1$s",Base.user.getName()));
//        user_one.setText(StringUtils.getTextJoint("职能: %1$s",Base.user.getTerm()));
//        user_two.setText(StringUtils.getTextJoint("所属: %1$s",Base.user.getSchoolname()));


        mChart = (RadarChart) findViewById(R.id.stu_main_pie);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setDescription("");
        mChart.setTouchEnabled(false);

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        mChart.setWebColor(Color.WHITE);
        mChart.setWebColorInner(Color.WHITE);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//
//        // set the marker to the chart
//        mChart.setMarkerView(mv);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setStartAtZero(true);
        yAxis.setTextColor(Color.WHITE);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTypeface(tf);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }


    private List<ScoreBean> scoreBeanList=new ArrayList<>();

    public void setData() {


        List<Integer> score=new ArrayList<>(R.array.p_e_score);
        List<String> types=new ArrayList<>(R.array.p_e_score);
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < score.size(); i++) {
            yVals1.add(new Entry((float)score.get(i) , i));
        }
//        for (int i = 0; i < scoreBeanList.size(); i++) {
//            yVals2.add(new Entry((float) ConvertObjtect.getInstance().getFloat(scoreBeanList.get(i).getScores().get(1).getExamscore()), i));
//        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < types.size(); i++){
            xVals.add(types.get(i));
        }


        RadarDataSet set1 = new RadarDataSet(yVals1, scoreBeanList.get(0).getScores().get(0).getExamname());
        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

//        RadarDataSet set2 = new RadarDataSet(yVals2, scoreBeanList.get(0).getScores().get(1).getExamname());
//        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
//        set2.setDrawFilled(true);
//        set2.setLineWidth(2f);

        ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();
        sets.add(set1);
//        sets.add(set2);

        RadarData data = new RadarData(xVals, sets);
        data.setValueTypeface(tf);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        mChart.invalidate();
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        if (Base.user==null)return;
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_term_list(env);
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
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){

                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
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
