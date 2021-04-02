package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.greendao.bean.TermBean;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateStuDevelopAdapter;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateStuNormalAdapter;
import com.yfy.app.duty_evaluate.bean.DutyEvaluateRes;
import com.yfy.app.duty_evaluate.bean.InfoBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.recycerview.GridDividerLineNotBottom;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

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

    //https://echarts.apache.org/v4/examples/zh/editor.html?c=sunburst-label-rotate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_stu_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
//        initSQToolbar("五育评价");
        stu_rank.setText("雅生四星勋章\n总计25雅币");
        initCollapsing();
        initToolbar();
        initRecyclerView();

        initRecyclerViewDevelop();
        getAssetsData("duty_evaluate_get_stu_develop_detail.txt");
        GlideTools.loadImage(mActivity,R.mipmap.honor_one,top_head);
        changeBgColor(
                ColorRgbUtil.getParseColor(parse_color[0]),
                "一",
                "12",
                ColorRgbUtil.getParseColor(parse_color_start[0]),
                ColorRgbUtil.getParseColor(parse_color_end[0]));
    }


    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }








    public TextView menu_title;
    public TermBean selected_termBean;


    @OnClick(R.id.main_navi)
    void setNavi(){
        finish();

    }
    int num=0;
    @OnClick(R.id.duty_evaluate_stu_head)
    void setHead(){
        num++;
        switch (num%5){
            case 0:
                GlideTools.loadImage(mActivity,R.mipmap.honor_one,top_head);
                GlideTools.loadImage(mActivity,R.mipmap.rank_one,top_bg);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[0]),
                        "一",
                        "12",
                        ColorRgbUtil.getParseColor(parse_color_start[0]),
                        ColorRgbUtil.getParseColor(parse_color_end[0]));
                break;
            case 1:
                GlideTools.loadImage(mActivity,R.mipmap.honor_two,top_head);
                GlideTools.loadImage(mActivity,R.mipmap.rank_two,top_bg);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[1]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[1]),
                        ColorRgbUtil.getParseColor(parse_color_end[1]));
                break;
            case 2:
                GlideTools.loadImage(mActivity,R.mipmap.rank_three,top_bg);
                GlideTools.loadImage(mActivity,R.mipmap.honor_three,top_head);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[2]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[2]),
                        ColorRgbUtil.getParseColor(parse_color_end[2]));
                break;
            case 3:
                GlideTools.loadImage(mActivity,R.mipmap.rank_four,top_bg);
                GlideTools.loadImage(mActivity,R.mipmap.honor_four,top_head);
                changeBgColor(
                        ColorRgbUtil.getParseColor(parse_color[3]),
                        "二",
                        "26",
                        ColorRgbUtil.getParseColor(parse_color_start[3]),
                        ColorRgbUtil.getParseColor(parse_color_end[3]));
                break;
            case 4:
                GlideTools.loadImage(mActivity,R.mipmap.rank_five,top_bg);
                GlideTools.loadImage(mActivity,R.mipmap.honor_five,top_head);
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









    /**
     * Toolbar 的NavigationIcon大小控制mipmap
     */
    public void initToolbar() {
//        Toolbar mToolbar =  findViewById(R.id.tool_bar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationIcon(R.drawable.ic_navi_back);
        //隐藏掉返回键比如首页，可以调用
    }

    //配置CollapsingToolbarLayout布局
    public void initCollapsing() {
        CollapsingToolbarLayout mCollapsingToolbarLayout =  findViewById(R.id.answer_collapsing);
        mCollapsingToolbarLayout.setTitle("返回");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //设置收缩后Toolbar上字体的颜色
    }






    public DutyEvaluateStuNormalAdapter adapter_normal;
    public RecyclerView normal_recycler;
    public List<KeyValue> adapter_data_list=new ArrayList<>();
    private void initRecyclerView(){
        normal_recycler =findViewById(R.id.duty_evaluate_stu_normal_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        normal_recycler.setLayoutManager(manager);

        normal_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
        adapter_normal =new DutyEvaluateStuNormalAdapter(mActivity);
        normal_recycler.setAdapter(adapter_normal);

        List<String> list=StringUtils.listToStringSplitCharacters("9月·35分,10月·30分,11月·38分,12月·未完成,1月·未完成",",");
        List<String> stu_score=StringUtils.listToStringSplitCharacters("0,1,2",",");
        for (String s:list){
            KeyValue keyValue=new KeyValue(s,R.mipmap.main_delay_service);
            keyValue.setTitle(s);
            keyValue.setRight_name("2020");
            keyValue.setRight_key("2020");

            switch (s){
                case "9月·35分":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("9");
                    break;
                case "10月·30分":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("10");

                    break;
                case "11月·38分":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("11");
                    break;
                case "12月·未完成":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("12");
                    break;
                case "1月·未完成":
                    keyValue.setRight_name("2021");
                    keyValue.setRight_key("1");
                    break;
                default:
                    keyValue.setValue(MathTool.randomStringAtList(stu_score));
                    break;
            }
            adapter_data_list.add(keyValue);
        }
        adapter_normal.setDataList(adapter_data_list);
        adapter_normal.setLoadState(TagFinal.LOADING_END);


        adapter_normal.setOnClieckAdapterLayout(new DutyEvaluateStuNormalAdapter.OnClickAdapterLayout() {
            @Override
            public void layoutOnClick(KeyValue keyValue) {

                Intent intent;
                if (keyValue.getTitle().equalsIgnoreCase("1月·未完成")){
                    intent=new Intent(mActivity,DutyEvaluateStuAddActivity.class);

                    intent.putExtra(Base.year_value,keyValue.getRight_name());
                    intent.putExtra(Base.month_value,keyValue.getRight_key());
                    startActivity(intent);
                }else{



                    intent=new Intent(mActivity,DutyEvaluateStuDetailActivity.class);

                    intent.putExtra(Base.year_value,keyValue.getRight_name());
                    intent.putExtra(Base.month_value,keyValue.getRight_key());
                    startActivity(intent);

                }
            }
        });
    }


    public RecyclerView develop_recycler;
    public DutyEvaluateStuDevelopAdapter adapter_develop;
    public List<KeyValue> adapter_develop_data=new ArrayList<>();
    private void initRecyclerViewDevelop(){
        develop_recycler =findViewById(R.id.duty_evaluate_stu_develop_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,3);
        develop_recycler.setLayoutManager(manager);

        develop_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
        adapter_develop =new DutyEvaluateStuDevelopAdapter(mActivity);
        develop_recycler.setAdapter(adapter_develop);
        adapter_develop.setOnClieckAdapterLayout(new DutyEvaluateStuDevelopAdapter.OnClickAdapterLayout() {
            @Override
            public void layoutOnClick(KeyValue keyValue) {
                KeyValue stu=new KeyValue();
                stu.setName(Base.user.getName());
                stu.setId(Base.user.getIdU());

                Intent intent;
                switch(keyValue.getTitle()){
                    case "班级认定":
                    case "家长认定":

                        DateBean dateBean=new DateBean();
                        dateBean.setValue_long(System.currentTimeMillis(),true);

                        intent=new Intent(mActivity,DutyEvaluateStuDetailActivity.class);
                        intent.putExtra(Base.stu_bean, stu);
                        intent.putExtra(Base.year_value,dateBean.getYearName());
                        intent.putExtra(Base.month_value,dateBean.getMonthName());
                        startActivity(intent);
                        break;
                    case "校内活动":
                    case "校外实践":
                    case "比赛成绩":
                        intent=new Intent(mActivity,DutyEvaluatePracticeActivity.class);
                        intent.putExtra(Base.title,keyValue.getTitle());
                        intent.putExtra(Base.term_bean,selected_termBean);
                        startActivity(intent);
                        break;
                        default:
                            break;
                }

            }
        });
    }







    public String[] parse_color=new String[]{"#B88256","#A0B8DA","#CD9D99","#8CCFFA","#8CCFFA"};
    public String[] parse_color_start=new String[]{"#EBB77B","#FDFDFD","#824834","#8CCFFA","#8CCFFA"};
    public String[] parse_color_end=new String[]{"#A16948","#7195C9","#B67571","#8CCFFA","#8CCFFA"};

    public void changeBgColor(int color,String content,String num,int startColor,int endColor){
        stu_rank.setText(StringUtils.stringToGetTextJoint("雅生%1$s星勋章\n总计%2$s雅币",content,num));
        ViewTool.alterVectorDrawableColor(bg_left,color);
        ViewTool.alterVectorDrawableColor(bg_right,color);
        ViewTool.alterGradientStartEndColor(card_bg,startColor,endColor);
//
        stu_rank.setTextColor(ColorRgbUtil.getWhite());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_TAG:
                    assert data!=null;
                    selected_termBean=data.getParcelableExtra(Base.data);
                    menu_title.setText(selected_termBean.getName());
                    break;
                case -1:
                    break;

            }
        }
    }




    /**
     * ----------------------------retrofit-----------------------
     */


    /**
     * -------------------------async task----------
     */






    //async task

    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        showProgressDialog("");
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }



    private void initAdapterData(DutyEvaluateRes res){
        adapter_develop_data.clear();


        for (InfoBean info:res.getInfo()){
            KeyValue bean=new KeyValue();
            bean.setView_type(TagFinal.TYPE_ITEM);
            bean.setTitle(info.getParent_title());
            bean.setValue(info.getParent_all_score());

            adapter_develop_data.add(bean);
        }


        adapter_develop.setDataList(adapter_develop_data);
        adapter_develop.setLoadState(TagFinal.LOADING_END);

    }


    @Override
    public void doUpData(String content) {
        dismissProgressDialog();
        if (StringJudge.isEmpty(content)){
            ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
        }else{
            DutyEvaluateRes res=gson.fromJson(content,DutyEvaluateRes.class);
            initAdapterData(res);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()==AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }
}




