package com.yfy.app.PEquality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.PEquality.adapter.PEQualityMainAdapter;
import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.app.bean.TermBean;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.RadarPointBean;
import com.yfy.base.RadarUtil;
import com.yfy.base.XAxisFormatter;
import com.yfy.base.activity.BaseActivity;
import com.yfy.charting_mp_test.charts.RadarChart;
import com.yfy.charting_mp_test.components.XAxis;
import com.yfy.charting_mp_test.components.YAxis;
import com.yfy.charting_mp_test.data.RadarData;
import com.yfy.charting_mp_test.data.RadarDataSet;
import com.yfy.charting_mp_test.data.RadarEntry;
import com.yfy.charting_mp_test.interfaces.datasets.IRadarDataSet;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.glide.DrawableLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.recycerview.GridDividerLineNotBottom;
import com.yfy.final_tag.stringtool.TextToolSpan;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

@SuppressLint("NonConstantResourceId")
public class PEQualityMainTestActivity extends BaseActivity {
    private static final String TAG = PEQualityMainTestActivity.class.getSimpleName();


    @BindView(R.id.p_e_main_user_head)
    AppCompatImageView user_head;
    @BindView(R.id.p_e_main_user_name)
    AppCompatTextView user_name;
    @BindView(R.id.p_e_main_user_class)
    AppCompatTextView user_class;
    @BindView(R.id.p_e_main_user_height)
    AppCompatTextView user_height;
    @BindView(R.id.p_e_main_user_weight)
    AppCompatTextView user_weight;
    @BindView(R.id.p_e_user_grade_title)
    AppCompatTextView grade_title;
    @BindView(R.id.p_e_user_grade_sub)
    AppCompatTextView grade_sub;
    @BindView(R.id.p_e_main_recipe)
    AppCompatTextView recipe_title;

    @BindView(R.id.p_e_main_chart_reason)
    AppCompatTextView chart_sup_reason;
//    @Bind(R.id.p_e_main_recipe_flow_layout)
//    FlowLayout recipe_flowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_quality_stu_main_test);
        initSQToolbar();
        initView();
        initChartView();
        setData();
        initRecyclerView();
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                List<RadarPointBean> pointBeans = RadarUtil.computePosition(mChart);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        for (int i = 0; i < pointBeans.size(); i++) {
                            RadarPointBean pointBean = pointBeans.get(i);
                            if (pointBean.isIn(x, y)) {
                                Intent intent;
                                String type=Arrays.asList(getResources().getStringArray(R.array.p_e_type)).get(i);
                                switch (type){
                                    case "学习态度":
                                        intent=new Intent(mActivity,PEAttitudeStuMainActivity.class);
                                        intent.putExtra(Base.title,type);
                                        startActivity(intent);
                                        break;
                                    case "健康教育知识"://PEQualityKnowledgeActivity
                                        intent=new Intent(mActivity,PEQualityKnowledgeActivity.class);
                                        intent.putExtra(Base.title,type);
                                        startActivity(intent);
                                        break;
                                    case "运动技能":
                                        intent=new Intent(mActivity,PEQualityStandardListActivity.class);
                                        intent.putExtra(Base.title,type);
                                        intent.putExtra(Base.type,TagFinal.FALSE);
                                        startActivity(intent);
                                        break;
                                    case "体能":
                                        intent=new Intent(mActivity,PEQualityStandardListActivity.class);
                                        intent.putExtra(Base.title,type);
                                        intent.putExtra(Base.type,TagFinal.FALSE);
                                        startActivity(intent);
                                        break;
                                    case "体育课后作业":
                                        intent=new Intent(mActivity,PEQualityHomeworkActivity.class);
                                        intent.putExtra(Base.title,type);
                                        intent.putExtra(Base.type,TagFinal.FALSE);
                                        startActivity(intent);
                                        break;
                                    case "国家体质健康标准":
                                        intent=new Intent(mActivity,PEQualityStandardListActivity.class);
                                        intent.putExtra(Base.title,type);
                                        intent.putExtra(Base.type,TagFinal.FALSE);
                                        startActivity(intent);
                                        break;

                                    default:
                                        ViewTool.showToastShort(mActivity,"暂未开放");
                                        break;
                                }
                                return true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        return  true;
                    default:

                        break;
                }
                return false;
            }
        });
    }


    public TermBean select_term;
    private TextView menu_one;
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("体育素质");
        menu_one=toolbar.addMenuText(TagFinal.ONE_INT,"add");

//        toolbar.cancelNavi();
//        toolbar.setNaviText("教师");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position){
                Intent intent=new Intent(mActivity,SelectedTermActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);
            }
        });



        menu_one.setText("19-20上期");
    }



    private void initView(){
        Typeface mTypeface=Typeface.createFromAsset(getAssets(),"OpenSans-Bold.ttf");
        GlideTools.chanCircle(mActivity, R.mipmap.user_head, user_head);
        user_name.setText("李霞");
        user_class.setText("一年级7班");
        grade_title.setText("优");
        grade_sub.setText("92.5");
        user_height.setText(StringUtils.getTextJoint("身高:\t%1$scm",145,39));
        user_weight.setText(StringUtils.getTextJoint("体重:\t%2$skg",145,39));
        recipe_title.setText("运动处方");
        recipe_title.setTypeface(mTypeface);
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

//        setFlowLayoutTop(list);

    }



//    private void setFlowLayoutTop(List<KeyValue> top_jz){
//
//        LayoutInflater mInflater = LayoutInflater.from(mActivity);
//        if (recipe_flowlayout.getChildCount()!=0){
//            recipe_flowlayout.removeAllViews();
//        }
//        for (KeyValue bean:top_jz){
//            RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.public_detail_top_item,recipe_flowlayout, false);
//            TextView key=layout.findViewById(R.id.seal_detail_key);
//            TextView value=layout.findViewById(R.id.seal_detail_value);
//            RatingBar ratingBar=layout.findViewById(R.id.seal_detail_value_star);
//            LinearLayout linearLayout=layout.findViewById(R.id.public_detail_txt_layout);
//            MultiPictureView multi=layout.findViewById(R.id.public_detail_layout_multi);
//
//            key.setTextColor(ColorRgbUtil.getGrayText());
//            value.setTextColor(ColorRgbUtil.getBaseText());
//            key.setText(bean.getKey());
//            linearLayout.setVisibility(View.VISIBLE);
//            multi.setVisibility(View.GONE);
//            value.setText(bean.getValue());
//            ratingBar.setVisibility(View.GONE);
//            value.setVisibility(View.VISIBLE);
//
//            recipe_flowlayout.addView(layout);
//        }
//    }



    public RecyclerView recycler_view;
    public PEQualityMainAdapter adapter;
    public List<KeyValue> adapter_data_show =new ArrayList<>();

    public void initRecyclerView(){
        recycler_view =findViewById(R.id.p_e_main_type_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        recycler_view.setLayoutManager(manager);
//        recycler_view.setItemAnimator(new RecycAnimator());
        //添加线

//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1;
//            }
//        });
        recycler_view.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
        adapter=new PEQualityMainAdapter(mActivity);
        recycler_view.setAdapter(adapter);
        adapter.setItemOnc(new PEQualityMainAdapter.ItemOnc() {
            @Override
            public void onc(KeyValue bean) {
               Intent intent;
                switch (bean.getName()) {
                    case "荣誉比赛":
                        intent=new Intent(mActivity,PEHonorMainActivity.class);
                        intent.putExtra(Base.title,bean.getName());
                        intent.putExtra(Base.type,TagFinal.FALSE);
                        startActivity(intent);
                        break;

                    case "膳食建议":
                        intent=new Intent(mActivity,PEQualitySuggestActivity.class);
                        intent.putExtra(Base.title,bean.getName());
                        intent.putExtra(Base.name,"膳食建议标题");
                        intent.putExtra(Base.content,"苦瓜、桑叶、洋葱、香菇、柚子、可降低血糖，是糖尿病人最理想食物，如能长期食用，则降血糖和预防并发症的效果会更好。");
                        startActivity(intent);
                        break;
                    case "我的健康":
                        intent=new Intent(mActivity,PEStuHealthActivity.class);
                        intent.putExtra(Base.title,bean.getName());
                        startActivity(intent);
                        break;
                }
            }
        });
        List<String> list=StringUtils.listToStringSplitCharacters("荣誉比赛,我的健康,膳食建议",",");
        for (String s:list){
            KeyValue keyValue;
            switch (s){
                case "膳食建议":
                    keyValue=new KeyValue(s,R.mipmap.dealbook);
                    keyValue.setRes_color(Color.parseColor("#fab067"));
                    break;
                case "荣誉比赛":
                    keyValue=new KeyValue(s,R.mipmap.deyu);
                    keyValue.setRes_color(Color.parseColor("#67bafa"));
                    break;
                    default:
                        keyValue=new KeyValue(s,R.mipmap.main_delay_service);
                        keyValue.setRes_color(Color.parseColor("#fa676c"));
                        break;
            }
            adapter_data_show.add(keyValue);
        }
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


                     initView();
                    initChartView();
                    setData();
                    initRecyclerView();
                    break;
            }
        }
    }





    float x = 0;
    float y = 0;
    private Typeface tf;
    private RadarChart mChart;
    private void initChartView(){
        GlideTools.chanCircle(mActivity, R.mipmap.user_head,user_head);

        mChart = (RadarChart) findViewById(R.id.stu_main_pie_test);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//        mChart.getDescription().setText("点击图表文字查看详情");//点击图表文字查看详情
        mChart.getDescription().setEnabled(false);
        //y轴数据说明
        mChart.getLegend().setEnabled(false);
//        mChart.getLegend().setForm(Legend.LegendForm.CIRCLE);//图案形状
//        //说明文字图的位置
//        mChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//        mChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//        mChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        mChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);


        //图表点击响应
        mChart.setTouchEnabled(true);
        //
        mChart.setDragDecelerationEnabled(false);
        mChart.setWebLineWidth(1f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);
        mChart.setWebColor(Color.BLACK);
        mChart.setWebColorInner(Color.BLACK);



        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawLabels(true);
//        xAxis.setTypeface(tf);
//        xAxis.setTextSize(9f);
        xAxis.setTextColor(ColorRgbUtil.getBaseColor());




        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(tf);
        // Y坐标值标签个数
        yAxis.setLabelCount(6, false);
        // Y坐标值字体大小
        yAxis.setTextSize(5f);
        //最大值
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawAxisLine(true);
        // 是否显示y值在图表上
        yAxis.setDrawLabels(false);
        // Y坐标值是否从0开始
        yAxis.setStartAtZero(true);
        //设置图例：




    }



    public void setData() {

        final List<String> list=Arrays.asList(getResources().getStringArray(R.array.p_e_type));
        List<String> score=Arrays.asList(getResources().getStringArray(R.array.p_e_score));
        List<String> score_class=Arrays.asList(getResources().getStringArray(R.array.p_e_score_class));
        ArrayList<RadarEntry> yVals1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> yVals2 = new ArrayList<RadarEntry>();

        for (int i = 0; i < score.size(); i++) {
            yVals1.add(new RadarEntry(ConvertObject.getInstance().getFloat(score.get(i))));
        }
        for (int i = 0; i < score_class.size(); i++) {
            yVals2.add(new RadarEntry(ConvertObject.getInstance().getFloat(score_class.get(i))));
        }

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new XAxisFormatter(list));


        //设置显示内容块：
        RadarDataSet set1 = new RadarDataSet(yVals1,"我的成绩");
        // 数据颜色设置
        set1.setColor(Color.parseColor("#3182c4"));
        // 实心填充区域颜色
        set1.setFillColor(Color.parseColor("#553182c4"));
        // 是否实心填充区域
        set1.setDrawFilled(true);
        // 数据线条宽度

        set1.setLineWidth(1f);
        set1.setDrawHorizontalHighlightIndicator(false); // 是否绘制高亮水平线，默认为true
        set1.setDrawVerticalHighlightIndicator(false); // 是否绘制高亮垂直线，默认为true

        //设置显示内容块：
        RadarDataSet set2 = new RadarDataSet(yVals2,"班级平均成绩");
        // 数据颜色设置
        set2.setColor(Color.parseColor("#942328"));
        // 实心填充区域颜色
        set2.setFillColor(Color.parseColor("#00942328"));
        // 是否实心填充区域
        set2.setDrawFilled(true);
        // 数据线条宽度
        set2.setLineWidth(1f);
        set2.setDrawHorizontalHighlightIndicator(false); // 是否绘制高亮水平线，默认为true
        set2.setDrawVerticalHighlightIndicator(false); // 是否绘制高亮垂直线，默认为true

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);//最上层
        sets.add(set2);


        RadarData data = new RadarData(sets);
//        data.setValueFormatter(new XAxisFormatter(list));
        data.setValueTypeface(tf);
        data.setValueTextSize(8f);
        //显示Y值

        data.setDrawValues(true);
        data.setValueTextColor(Color.BLACK);

//text
        Drawable drawable_two=DrawableLess.$tint(mActivity.getResources().getDrawable(R.drawable.rectangle_square10_gray),Color.parseColor("#942328"));
        Drawable drawable_one=DrawableLess.$tint(mActivity.getResources().getDrawable(R.drawable.rectangle_square10_gray),Color.parseColor("#3182c4"));
        drawable_two.setBounds(0, 0, chart_sup_reason.getLineHeight(),chart_sup_reason.getLineHeight());//让图片与文字对齐
        drawable_one.setBounds(0, 0, chart_sup_reason.getLineHeight(),chart_sup_reason.getLineHeight());//让图片与文字对齐
        ImageSpan two = new ImageSpan(drawable_two,ImageSpan.ALIGN_CENTER);
        ImageSpan imgSpan = new ImageSpan(drawable_one,ImageSpan.ALIGN_CENTER);

        SpannableStringBuilder sb=new SpannableStringBuilder();
        SpannableString sb_one = new SpannableString("0 我的成绩");
        SpannableString sb_two = new SpannableString("0 班级平均成绩");
        TextToolSpan.$spannableStringColor(sb_two,Color.parseColor("#942328"));
        TextToolSpan.$spannableStringColor(sb_one,Color.parseColor("#3182c4"));

        sb_one.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb_two.setSpan(two, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append(sb_one).append("\t").append(sb_two);
        chart_sup_reason.setText(sb);


        mChart.setData(data);
        mChart.invalidate();
    }


    @OnClick(R.id.p_e_main_recipe_layout)
    void setRecipe(){

        Intent intent=new Intent(mActivity,PERecipeActivity.class);
        intent.putExtra(Base.title,"运动处方");
        intent.putExtra(Base.type,TagFinal.FALSE);
        startActivity(intent);
    }

    @OnClick(R.id.p_e_main_grade_layout)
    void setGrade(){
        Intent intent=new Intent(mActivity,PEScoreMainActivity.class);
        intent.putExtra(Base.title,"详细得分");
        startActivity(intent);
    }

    /**
     * ----------------------------retrofit-----------------------
     */

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
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
                    ViewTool.showToastShort(mActivity,"error");
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
            ViewTool.showToastShort(mActivity,StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
