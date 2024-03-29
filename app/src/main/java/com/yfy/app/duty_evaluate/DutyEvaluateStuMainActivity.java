package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yfy.app.bean.DateBean;
import com.yfy.app.chart.bean.AngleAxis;
import com.yfy.app.chart.bean.Legend;
import com.yfy.app.chart.bean.PileRes;
import com.yfy.app.chart.bean.Polar;
import com.yfy.app.chart.bean.RadiusAxis;
import com.yfy.app.chart.bean.Series;
import com.yfy.final_tag.DateUtils;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.glide.DrawableLess;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.TextToolSpan;
import com.yfy.app.bean.TermBean;
import com.yfy.app.duty_evaluate.bean.DutyEvaluateRes;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.view.time.CustomDatePicker;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("NonConstantResourceId")
public class DutyEvaluateStuMainActivity extends BaseActivity implements AssetsGetFileData {
    private static final String TAG = DutyEvaluateStuMainActivity.class.getSimpleName();




    @BindView(R.id.duty_evaluate_stu_rank)
    TextView stu_rank;
    @BindView(R.id.duty_evaluate_bg)
    AppCompatImageView top_bg;

    @BindView(R.id.duty_evaluate_stu_head)
    AppCompatImageView top_head;

    @BindView(R.id.duty_evaluate_bottom_left_bg)
    AppCompatImageView bg_left;
    @BindView(R.id.duty_evaluate_bottom_right_bg)
    AppCompatImageView bg_right;
    @BindView(R.id.card_background)
    AppCompatImageView card_bg;



    //----------常规性评价-----------
    @BindView(R.id.stu_select_date)
    AppCompatTextView select_date;
    public String year_s, month_s;
    public CustomDatePicker customDatePicker1;
    @OnClick(R.id.stu_select_date)
    void setEChartDate() {

        if (customDatePicker1 == null) {

            customDatePicker1 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) { // 回调接口，获得选中的时间
                    String data = time.split(" ")[0].substring(0, time.split(" ")[0].lastIndexOf("-"));
                    year_s = data.split("-")[0];
                    month_s = data.split("-")[1];
                    select_date.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s", year_s, month_s));
                }
            }, "2000-01-01 00:00", DateUtils.getDateTime("yyyy-MM-dd HH:mm"));
            // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
            customDatePicker1.showSpecificTime(false); // 不显示时和分
            customDatePicker1.setIsLoop(true); // 不允许循环滚动
        }

        customDatePicker1.show(StringUtils.stringToGetTextJoint("%1$s-%2$s-01 01:01", year_s, month_s));
    }


    //    排名
    @BindView(R.id.duty_evaluate_stu_normal_title)
    AppCompatTextView normal_score_rank;
    @BindView(R.id.duty_evaluate_stu_normal_value)
    AppCompatTextView normal_score_value;
    //按钮文字
    @BindView(R.id.duty_span_stu)
    AppCompatTextView duty_span_stu;
    @BindView(R.id.duty_span_family)
    AppCompatTextView duty_span_family;



//    @BindView(R.id.stu_self_event_bg)
//    AppCompatImageView stu_event_bg;
//    @BindView(R.id.tea_event_bg)
//    AppCompatImageView tea_event_bg;

    @OnClick(R.id.stu_self_event_bg)
    void setStuEvent() {
//        Intent  intent = new Intent();
//        intent.setClass(mActivity, DutyEvaluateStuAddActivity.class);
//        intent.putExtra(Base.year_value, year_s);
//        intent.putExtra(Base.month_value, month_s);
//        startActivity(intent);
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.tea_event_bg)
    void setTea() {
        Intent  intent = new Intent();
        intent.setClass(mActivity, DutyEvaluateStuDetailActivity.class);
        intent.putExtra(Base.year_value, year_s);
        intent.putExtra(Base.month_value, month_s);
        startActivity(intent);
    }



    //-----------------发展性评价-----------------
    @BindView(R.id.labour_score_value)
    AppCompatTextView labour_score_value;

    //校内活动
    @BindView(R.id.develop_school_in_title)
    AppCompatTextView develop_school_in_title;
    @BindView(R.id.develop_school_in_title_sub)
    AppCompatTextView develop_school_in_title_sub;

    @OnClick(R.id.develop_school_in_layout)
    void setDevelopIn() {
        Intent intent = new Intent();
        intent.setClass(mActivity, DutyEvaluateSchoolActivity.class);
        intent.putExtra(Base.term_bean, selected_termBean);
        intent.putExtra(Base.title, "校内活动");
        startActivity(intent);
    }


    //校外实践
    @BindView(R.id.develop_school_out_title)
    AppCompatTextView develop_school_out_title;
    @BindView(R.id.develop_school_out_title_sub)
    AppCompatTextView develop_school_out_title_sub;

    @OnClick(R.id.develop_school_out_layout)
    void setDevelopOut() {
        Intent intent = new Intent();
        intent.setClass(mActivity, DutyEvaluateSchoolActivity.class);
        intent.putExtra(Base.term_bean, selected_termBean);
        intent.putExtra(Base.title, "校外实践");
        startActivity(intent);
    }
    //比赛成绩
    @BindView(R.id.develop_school_score_title)
    AppCompatTextView develop_school_score_title;
    @BindView(R.id.develop_school_score_title_sub)
    AppCompatTextView develop_school_score_title_sub;

    @OnClick(R.id.develop_school_score_layout)
    void setDevelopScore() {
        Intent intent = new Intent();
        intent.setClass(mActivity, DutyEvaluateSchoolActivity.class);
        intent.putExtra(Base.term_bean, selected_termBean);
        intent.putExtra(Base.title, "比赛成绩");
        startActivity(intent);
    }



    //----------------补充性----------------
    @BindView(R.id.replenish_term_name)
    AppCompatTextView replenish_term_name;
    //家庭劳动
    @BindView(R.id.replenish_duty_labour_title)
    AppCompatTextView labour_title;
    @BindView(R.id.replenish_duty_labour_title_sub)
    AppCompatTextView labour_title_sub;
    @BindView(R.id.replenish_duty_labour_family)
    AppCompatTextView labour_family;
    @BindView(R.id.replenish_duty_labour_stu)
    AppCompatTextView labour_stu;
    @OnClick(R.id.replenish_duty_labour_stu)
    void setLabourStu(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.replenish_duty_labour_family)
    void setLabourTea(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }

    //文雅少年
    @BindView(R.id.replenish_duty_young_title)
    AppCompatTextView young_title;
    @BindView(R.id.replenish_duty_young_title_sub)
    AppCompatTextView young_title_sub;
    @BindView(R.id.replenish_duty_young_family)
    AppCompatTextView young_family;
    @BindView(R.id.replenish_duty_young_stu)
    AppCompatTextView young_stu;
    @OnClick(R.id.replenish_duty_young_stu)
    void setYoungStu(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.replenish_duty_young_family)
    void setYoungTea(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }

    //更多项目
    @BindView(R.id.replenish_duty_more_title)
    AppCompatTextView more_title;
    @BindView(R.id.replenish_duty_more_title_sub)
    AppCompatTextView more_title_sub;

    @BindView(R.id.replenish_duty_more_family)
    AppCompatTextView more_family;
    @BindView(R.id.replenish_duty_more_stu)
    AppCompatTextView more_stu;

    @OnClick(R.id.replenish_duty_more_stu)
    void setMoreStu(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.replenish_duty_more_family)
    void setMoreTea(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluateScoreAddActivity.class);
        startActivity(intent);
    }

    DateBean dateBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_stu_main);
        Logger.e(TAG);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//        }


        initView();

        initCollapsing();

        GlideTools.loadImage(mActivity, R.mipmap.honor_one, top_head);
        changeBgColor(
                ColorRgbUtil.getParseColor(parse_color[0]),
                "一",
                "12",
                ColorRgbUtil.getParseColor(parse_color_start[0]),
                ColorRgbUtil.getParseColor(parse_color_end[0]));


//        ViewTool.alterGradientStartEndColor(stu_event_bg,ColorRgbUtil.getParseColor("#F4A668"),ColorRgbUtil.getParseColor("#E94F4F"));
//        ViewTool.alterGradientStartEndColor(
//                tea_event_bg,
//                ColorRgbUtil.getParseColor("#5FB0E8"),
//                ColorRgbUtil.getParseColor("#2876E5"));

        initEChart();

        getAssetsData("duty_evaluate_get_stu_detail.txt");
    }

    public void initView() {
        selected_termBean = NormalDataSaveTools.getInstance().getTermBeanToGreenDao();

        stu_rank.setText("雅生四星勋章\n总计25雅币");
        normal_score_rank.setText(StringUtils.stringToGetTextJoint("排名：%1$s名", "23"));
        normal_score_value.setText(StringUtils.stringToGetTextJoint("获得雅币 %1$s 枚", "5"));
        normal_score_value.setText(StringUtils.stringToGetTextJoint("获得雅币 %1$s 枚", "5"));


        String stu_self_duty = "学生自评1";
        TextToolSpan.$spannableAddIconColor(
                duty_span_stu,
                stu_self_duty,
                DrawableLess.getResourceDrawable(mActivity, R.drawable.ic_arrow_right_gray_24dp),
                ColorRgbUtil.getResourceColor(mActivity, R.color.White),
                ColorRgbUtil.getResourceColor(mActivity, R.color.White),
                stu_self_duty.length() - 1,
                stu_self_duty.length()
        );
        String family_duty = "家长评价1";
        TextToolSpan.$spannableAddIconColor(
                duty_span_family,
                family_duty,
                DrawableLess.getResourceDrawable(mActivity, R.drawable.ic_arrow_right_gray_24dp),
                ColorRgbUtil.getResourceColor(mActivity, R.color.White),
                ColorRgbUtil.getResourceColor(mActivity, R.color.White),
                family_duty.length() - 1,
                family_duty.length()
        );

        dateBean = new DateBean();
        dateBean.setValue_long(System.currentTimeMillis(), true);
        year_s = String.valueOf(dateBean.getSelectYearNameInt());
        month_s = String.valueOf(dateBean.getSelectMonthNameString());

        select_date.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s", year_s, month_s));



        //发展性评价
        labour_score_value.setText(StringUtils.stringToGetTextJoint("%1$s分%2$s雅币", "120", "5"));
        develop_school_in_title.setText("校内活动");
        develop_school_in_title_sub.setText("校内活动");

        develop_school_out_title.setText("校外实践");
        develop_school_out_title_sub.setText("校外实践");


        develop_school_score_title.setText("比赛成绩");
        develop_school_score_title_sub.setText("比赛成绩");



        //补充性
        replenish_term_name.setText("第五期");

        labour_title.setText("家庭劳动");
        labour_title_sub.setText("家庭劳动");
        labour_stu.setText("学生评");
        labour_family.setText("家长评");

        young_title.setText("文雅少年");
        young_title_sub.setText("文雅少年");
        young_stu.setText("学生评");
        young_family.setText("家长评");

        more_title.setText("更多项目");
        more_title_sub.setText("更多项目");
        more_stu.setText("学生评");
        more_family.setText("家长评");
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();

    }


    public TextView menu_title;
    public TermBean selected_termBean;


    @OnClick(R.id.main_navi)
    void setNavi() {
        finish();

    }

    int num = 0;

    @OnClick(R.id.duty_evaluate_stu_head)
    void setHead() {
        num++;
        switch (num % 5) {
            case 0:
                GlideTools.loadImage(mActivity, R.mipmap.honor_one, top_head);
                GlideTools.loadImage(mActivity, R.mipmap.rank_one, top_bg);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[0]),
                        "一",
                        "12",
                        ColorRgbUtil.getParseColor(parse_color_start[0]),
                        ColorRgbUtil.getParseColor(parse_color_end[0]));
                break;
            case 1:
                GlideTools.loadImage(mActivity, R.mipmap.honor_two, top_head);
                GlideTools.loadImage(mActivity, R.mipmap.rank_two, top_bg);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[1]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[1]),
                        ColorRgbUtil.getParseColor(parse_color_end[1]));
                break;
            case 2:
                GlideTools.loadImage(mActivity, R.mipmap.rank_three, top_bg);
                GlideTools.loadImage(mActivity, R.mipmap.honor_three, top_head);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[2]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[2]),
                        ColorRgbUtil.getParseColor(parse_color_end[2]));
                break;
            case 3:
                GlideTools.loadImage(mActivity, R.mipmap.rank_four, top_bg);
                GlideTools.loadImage(mActivity, R.mipmap.honor_four, top_head);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[3]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[3]),
                        ColorRgbUtil.getParseColor(parse_color_end[3]));
                break;
            case 4:
                GlideTools.loadImage(mActivity, R.mipmap.rank_five, top_bg);
                GlideTools.loadImage(mActivity, R.mipmap.honor_five, top_head);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[4]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[4]),
                        ColorRgbUtil.getParseColor(parse_color_end[4]));
                break;
            default:
                break;

        }


    }


    //配置CollapsingToolbarLayout布局
    public void initCollapsing() {
        CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.answer_collapsing);
        mCollapsingToolbarLayout.setTitle("返回");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //设置收缩后Toolbar上字体的颜色
    }


//
//    public DutyEvaluateStuNormalAdapter adapter_normal;
//    public RecyclerView normal_recycler;
//    public List<KeyValue> adapter_data_list=new ArrayList<>();
//    private void initRecyclerView(){
//        normal_recycler =findViewById(R.id.duty_evaluate_stu_normal_recycler);
//        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
//        normal_recycler.setLayoutManager(manager);
//
//        normal_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
//        adapter_normal =new DutyEvaluateStuNormalAdapter(mActivity);
//        normal_recycler.setAdapter(adapter_normal);
//
//        List<String> list=StringUtils.listToStringSplitCharacters("9月·35分,10月·30分,11月·38分,12月·未完成,1月·未完成",",");
//        List<String> stu_score=StringUtils.listToStringSplitCharacters("0,1,2",",");
//        for (String s:list){
//            KeyValue keyValue=new KeyValue(s,R.mipmap.main_delay_service);
//            keyValue.setTitle(s);
//            keyValue.setRight_name("2020");
//            keyValue.setRight_key("2020");
//
//            switch (s){
//                case "9月·35分":
//                    keyValue.setRight_name("2020");
//                    keyValue.setRight_key("9");
//                    break;
//                case "10月·30分":
//                    keyValue.setRight_name("2020");
//                    keyValue.setRight_key("10");
//
//                    break;
//                case "11月·38分":
//                    keyValue.setRight_name("2020");
//                    keyValue.setRight_key("11");
//                    break;
//                case "12月·未完成":
//                    keyValue.setRight_name("2020");
//                    keyValue.setRight_key("12");
//                    break;
//                case "1月·未完成":
//                    keyValue.setRight_name("2021");
//                    keyValue.setRight_key("1");
//                    break;
//                default:
//                    keyValue.setValue(MathTool.randomStringAtList(stu_score));
//                    break;
//            }
//            adapter_data_list.add(keyValue);
//        }
//        adapter_normal.setDataList(adapter_data_list);
//        adapter_normal.setLoadState(TagFinal.LOADING_END);
//
//
//        adapter_normal.setOnClieckAdapterLayout(new DutyEvaluateStuNormalAdapter.OnClickAdapterLayout() {
//            @Override
//            public void layoutOnClick(KeyValue keyValue) {
//
//                Intent intent;
//                if (keyValue.getTitle().equalsIgnoreCase("1月·未完成")){
//                    intent=new Intent(mActivity,DutyEvaluateStuAddActivity.class);
//
//                    intent.putExtra(Base.year_value,keyValue.getRight_name());
//                    intent.putExtra(Base.month_value,keyValue.getRight_key());
//                    startActivity(intent);
//                }else{
//
//
//
//                    intent=new Intent(mActivity,DutyEvaluateStuDetailActivity.class);
//
//                    intent.putExtra(Base.year_value,keyValue.getRight_name());
//                    intent.putExtra(Base.month_value,keyValue.getRight_key());
//                    startActivity(intent);
//
//                }
//            }
//        });
//    }


//    public RecyclerView develop_recycler;
//    public DutyEvaluateStuDevelopAdapter adapter_develop;
//    public List<KeyValue> adapter_develop_data=new ArrayList<>();
//    private void initRecyclerViewDevelop(){
//        develop_recycler =findViewById(R.id.duty_evaluate_stu_develop_recycler);
//        GridLayoutManager manager = new GridLayoutManager(mActivity,3);
//        develop_recycler.setLayoutManager(manager);
//
//        develop_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
//        adapter_develop =new DutyEvaluateStuDevelopAdapter(mActivity);
//        develop_recycler.setAdapter(adapter_develop);
//        adapter_develop.setOnClieckAdapterLayout(new DutyEvaluateStuDevelopAdapter.OnClickAdapterLayout() {
//            @Override
//            public void layoutOnClick(KeyValue keyValue) {
//                KeyValue stu=new KeyValue();
//                stu.setName(Base.user.getName());
//                stu.setId(Base.user.getIdU());
//
//                Intent intent;
//                switch(keyValue.getTitle()){
//                    case "班级认定":
//                    case "家长认定":
//
//                        DateBean dateBean=new DateBean();
//                        dateBean.setValue_long(System.currentTimeMillis(),true);
//
//                        intent=new Intent(mActivity,DutyEvaluateStuDetailActivity.class);
//                        intent.putExtra(Base.stu_bean, stu);
//                        intent.putExtra(Base.year_value,dateBean.getYearName());
//                        intent.putExtra(Base.month_value,dateBean.getMonthName());
//                        startActivity(intent);
//                        break;
//                    case "校内活动":
//                    case "校外实践":
//                    case "比赛成绩":
//                        intent=new Intent(mActivity,DutyEvaluatePracticeActivity.class);
//                        intent.putExtra(Base.title,keyValue.getTitle());
//                        intent.putExtra(Base.term_bean,selected_termBean);
//                        startActivity(intent);
//                        break;
//                        default:
//                            break;
//                }
//
//            }
//        });
//    }
//
//


    public String[] parse_color = new String[]{"#B88256", "#A0B8DA", "#CFA4A1", "#866AB5", "#EECE95"};
    public String[] parse_color_start = new String[]{"#EBB77B", "#FDFDFD", "#E3CECB", "#D9D1E9", "#F2D49D"};
    public String[] parse_color_end = new String[]{"#A16948", "#7195C9", "#830D0B", "#4A1F92", "#A17A37"};

    public void changeBgColor(int color, String content, String num, int startColor, int endColor) {
        stu_rank.setText(StringUtils.stringToGetTextJoint("雅生%1$s星勋章\n总计%2$s雅币", content, num));
        ViewTool.alterVectorDrawableColor(bg_left, color);
        ViewTool.alterVectorDrawableColor(bg_right, color);
        ViewTool.alterGradientStartEndColor(card_bg, startColor, endColor);
        stu_rank.setTextColor(ColorRgbUtil.getWhite());
    }


    public WebView webView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    public void initEChart() {

        webView = findViewById(R.id.event_chart_web_view);
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


    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl(initEChartData());
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
            if (url.contains("http") || url.contains("www") || url.contains("https")) {
                String response = "<html><body>该数据不存在</body></html>";
                WebResourceResponse weResourceResponse = new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(response.getBytes()));
                return weResourceResponse;
            } else {
                return super.shouldInterceptRequest(view, url);
            }
        }

        /**
         * url重定向会执行此方法以及点击页面某些链接也会执行此方法
         * 当加载的网页需要重定向的时候就会回调这个函数告知我们应用程序是否需要接管控制网页加载，如果应用程序接管，并且return
         * true意味着主程序接管网页加载，如果返回false让webview自己处理。
         *
         * @param view 当前webview
         * @param url  即将重定向的url
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


    public String initEChartData() {

        List<String> title_name = StringUtils.listToStringSplitCharacters("遵纪守法,热爱学习,强健体魄,表率文雅,勤于劳动", ",");
        List<String> tag_name = StringUtils.listToStringSplitCharacters("教师,家长,学生", ",");

        List<Integer> datas = new ArrayList<>();
        datas.add(3);
        datas.add(1);
        datas.add(2);
        datas.add(3);
        datas.add(2);


        PileRes res = new PileRes();

        AngleAxis angleAxis = new AngleAxis();
        res.setA(angleAxis);

        RadiusAxis radiusAxis = new RadiusAxis();
        radiusAxis.setA("category");
        radiusAxis.setB(title_name);
        radiusAxis.setC(10);
        res.setB(radiusAxis);

        Polar polar = new Polar();
        res.setC(polar);


        List<Series> seriesList = new ArrayList<>();
        for (String s : tag_name) {
            Series series = new Series("bar", "polar", s, "a");
            series.setB(datas);
            seriesList.add(series);
        }
        res.setD(seriesList);


        Legend legend = new Legend();
        legend.setA(true);
        legend.setD(tag_name);
        res.setE(legend);


        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        String call = StringUtils.stringToGetTextJoint(
                "javascript:loadEcharts('%1$s')",
                gson.toJson(res, PileRes.class));


        return call;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.UI_TAG:
                    assert data != null;
                    selected_termBean = data.getParcelableExtra(Base.data);
                    menu_title.setText(selected_termBean.getName());
                    break;
                case -1:
                    break;

            }
        }
    }


    /**
     * -------------------------async task----------
     */


    //async task

    AssetsAsyncTask mTask;

    public void getAssetsData(String file_name) {
        ViewTool.showProgressDialog(mActivity,"");
        mTask = new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }


    @Override
    public void doUpData(String content) {
        ViewTool.dismissProgressDialog();
        if (StringJudge.isEmpty(content)) {
            ViewTool.showToastShort(mActivity, "没有数据，请从新尝试");
        } else {
            DutyEvaluateRes res = gson.fromJson(content, DutyEvaluateRes.class);
//            initAdapterData(res);
            webView.setWebViewClient(new MyWebViewClient());
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.pauseTimers();

        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
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


    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
    }


}




