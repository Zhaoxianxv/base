package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.app.bean.StuBean;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateTeaDoTabAdapter;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.DateUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWMatchListMinWidthView;
import com.yfy.final_tag.recycerview.GridDividerLineNotBottom;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.view.time.CustomDatePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class DutyEvaluateTeaDoActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateTeaDoActivity.class.getSimpleName();



    @BindView(R.id.confirm_tab_layout_one)
    TabLayout one;
    @BindView(R.id.confirm_tab_layout_two)
    TabLayout two;
    //时间
    @BindView(R.id.duty_evaluate_select_date_value)
    AppCompatTextView selected_date;
    @OnClick(R.id.duty_evaluate_select_date_relative_layout)
    void setSelectDate(){
        customDatePicker1.show(DateUtils.getDateTime("yyyy-MM-dd HH:mm"));
    }

    //stu
    @BindView(R.id.public_type_choice_value)
    AppCompatTextView selected_stu;
    public List<CPWBean> scanStateList=new ArrayList<>();
    private CPWBean select_stu=null;

    @OnClick(R.id.duty_evaluate_select_stu_relative_layout)
    void setSelectStu(){
        closeKeyWord();
        if (StringJudge.isEmpty(scanStateList)){
            ViewTool.showToastShort(mActivity,"正在获取学生");
            setStuData();
            return;
        }

        CPWMatchListMinWidthView confirmPopWindow=new CPWMatchListMinWidthView(mActivity);
        confirmPopWindow.setAnimationStyle(R.style.pop_window_anim_style);
        confirmPopWindow.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastPopClick( CPWBean bean,String type) {
                select_stu=bean;
                selected_stu.setText(select_stu.getName());
                selected_stu.setTextColor(ColorRgbUtil.getBaseText());
                recycler_layout.setVisibility(View.VISIBLE);
            }
        });
        confirmPopWindow.showAtBottom(line,scanStateList);
    }





    @BindView(R.id.tea_evaluate_select_score)
    TextView select_score_tv;

    @BindView(R.id.duty_evaluate_stu_tab_data_recycler_layout)
    RelativeLayout recycler_layout;
    @BindView(R.id.p_e_tea_add_score_line)
    View line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_tea_do);
        getData();
        initRecyclerView();
        initDatePicker();
        setStuData();
    }

    public String class_id;
    public KeyValue classBean;
    private void getData(){
        classBean=getIntent().getParcelableExtra(Base.class_bean);
        class_id=classBean.getValue();
        initSQToolbar("五育评价班评");

        initView();
    }

    private void setStuData(){
        List<StuBean> stu_list= NormalDataSaveTools.getInstance().getStuBeanToGreenDao();
        if (StringJudge.isEmpty(stu_list)){
            stu_list=new ArrayList<>();

        }
        Logger.e(""+stu_list.size());
        scanStateList.clear();
        for (StuBean stuBean:stu_list){
            CPWBean one=new CPWBean(stuBean.getStuname(),stuBean.getStuid());

            scanStateList.add(one);
        }



    }

    public void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"记录");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position){

                Intent intent=new Intent(mActivity, DutyEvaluateTeaRecordActivity.class);
                intent.putExtra(Base.class_bean,classBean);
                startActivity(intent);
            }
        });

    }


    public DutyEvaluateTeaDoTabAdapter adapter_tab;
    public RecyclerView tab_recycler;
    public List<KeyValue> adapter_data_list=new ArrayList<>();
    private void initRecyclerView(){
        recycler_layout.setVisibility(View.GONE);
        tab_recycler=findViewById(R.id.duty_evaluate_stu_tab_data_recycler);

        GridLayoutManager manager = new GridLayoutManager(mActivity,3);
        tab_recycler.setLayoutManager(manager);

        tab_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
        adapter_tab =new DutyEvaluateTeaDoTabAdapter(mActivity);
        tab_recycler.setAdapter(adapter_tab);

        List<String> list=StringUtils.listToStringSplitCharacters("学生评,家长评,老师评",",");
        List<String> stu_score=StringUtils.listToStringSplitCharacters("0,1,2",",");
        for (String s:list){
            KeyValue keyValue=new KeyValue(s,R.mipmap.main_delay_service);
            keyValue.setTitle(s);
            switch (s){
                case "老师评":
                    keyValue.setValue("否");
                    break;
                default:
                    keyValue.setValue(MathTool.randomStringAtList(stu_score));
                    break;
            }
            adapter_data_list.add(keyValue);
        }
        adapter_tab.setDataList(adapter_data_list);
        adapter_tab.setLoadState(TagFinal.LOADING_END);
    }



    private List<KeyValue> score_list=new ArrayList<>();

    public KeyValue select_score_data;
    List<String> list=StringUtils.listToStringSplitCharacters("守时守信,遵纪尊规,广播体操,会值日,会服务",",");

    private void initView(){
        score_list.add(new KeyValue("0",R.color.gray));
        score_list.add(new KeyValue("1",R.color.light_gray));
        score_list.add(new KeyValue("2",R.color.MistyRose));
        score_list.add(new KeyValue("3",R.color.Pink));
        score_list.add(new KeyValue("4",R.color.Pink));
        score_list.add(new KeyValue("5",R.color.DeepSkyBlue));
        score_list.add(new KeyValue("6",R.color.DeepSkyBlue));
        score_list.add(new KeyValue("7",R.color.IndianRed));
        score_list.add(new KeyValue("8",R.color.Red));
        score_list.add(new KeyValue("9",R.color.Red));
        score_list.add(new KeyValue("10",R.color.Red));

        select_score_data=score_list.get(score_list.size()-1);
        select_score_tv.setText(select_score_data.getName());
        ViewTool.alterGradientDrawableColor(select_score_tv,mActivity.getResources().getColor(select_score_data.getRes_image()));

        //-----------------------tab layout
        one.setTabTextColors(Color.BLACK,Color.WHITE);
        two.setTabTextColors(Color.BLACK,Color.WHITE);
        initCreateOne();
        initCreateTwo(MathTool.randomLIstAtList(list,2,3));
        one.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();


                two.setVisibility(View.VISIBLE);
                initCreateTwo(MathTool.randomLIstAtList(list,2,3));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        two.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void initCreateOne(){
        List<String> list=StringUtils.listToStringSplitCharacters("遵守纪律,热爱学习,强健体魄,表率文雅,勤于劳动",",");
        for (String s:list){

            one.addTab(one.newTab().setText(s),s.equalsIgnoreCase("遵守纪律")?true:false);
        }
    }


    private void initCreateTwo(List<String> list){
        two.removeAllTabs();
        boolean is_select=true;
        for (String bean:list){
            two.addTab(two.newTab().setText(bean),is_select);
            is_select=false;
        }
    }






    @OnClick(R.id.duty_evaluate_submit_button)
    void setSubmit(){
        closeKeyWord();
        if (select_stu==null){
            ViewTool.showToastShort(mActivity,"没有选择学生");
            return;
        }
        ViewTool.showProgressDialog(mActivity,1000,"");
        for (KeyValue keyValue:adapter_data_list){
            if (keyValue.getTitle().equalsIgnoreCase("老师评")){
                keyValue.setValue(select_score_data.getName());
            }
        }
        adapter_tab.setDataList(adapter_data_list);
        adapter_tab.setLoadState(TagFinal.LOADING_END);
    }



    public String year_s,month_s;
    public CustomDatePicker customDatePicker1;
    public String select_date_value="";
    private void initDatePicker() {

        DateBean bean=new DateBean();
        bean.setValue_long(System.currentTimeMillis(),false);

        year_s=String.valueOf(bean.getSelectYearNameInt());
        month_s=bean.getSelectMonthNameString();

        selected_date.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
        select_date_value=StringUtils.stringToGetTextJoint("%1$s-%2$s-01",year_s,month_s);
        customDatePicker1 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String data=time.split(" ")[0].substring(0,time.split(" ")[0].lastIndexOf("-"));
                year_s=data.split("-")[0];
                month_s=data.split("-")[1];
                selected_date.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
                select_date_value=StringUtils.stringToGetTextJoint("%1$s-%2$s-01",year_s,month_s);
            }
        }, "2000-01-01 00:00", DateUtils.getDateTime("yyyy-MM-dd HH:mm"));
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动


    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch(requestCode){
                case TagFinal.UI_SELECT_USER_SINGLE:
                    assert data!=null;
                    String stu_name=data.getStringExtra(Base.data);
                    selected_stu.setText(stu_name);
                    selected_stu.setTextColor(ColorRgbUtil.getBaseText());
                    recycler_layout.setVisibility(View.VISIBLE);
                    break;
                case -1:
                    break;
            }
        }
    }

    /**
     * ----------------------------retrofit-----------------------
     */


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }





    //---------------onClick--------
    @OnClick(R.id.tea_evaluate_zero)
    void setTvZero(){
        initSelectScoreTv(0);
    }
    @OnClick(R.id.tea_evaluate_one)
    void setTvOne(){
        initSelectScoreTv(1);
    }
    @OnClick(R.id.tea_evaluate_two)
    void setTvTwo(){
        initSelectScoreTv(2);
    }
    @OnClick(R.id.tea_evaluate_three)
    void setTvThree(){
        initSelectScoreTv(3);
    }
    @OnClick(R.id.tea_evaluate_four)
    void setTvFour(){
        initSelectScoreTv(4);
    }
    @OnClick(R.id.tea_evaluate_five)
    void setTvFive(){
        initSelectScoreTv(5);
    }
    @OnClick(R.id.tea_evaluate_six)
    void setTvSix(){
        initSelectScoreTv(6);
    }
    @OnClick(R.id.tea_evaluate_seven)
    void setTvSeven(){
        initSelectScoreTv(7);
    }
    @OnClick(R.id.tea_evaluate_eight)
    void setTvEight(){
        initSelectScoreTv(8);
    }
    @OnClick(R.id.tea_evaluate_nine)
    void setTvNine(){
        initSelectScoreTv(9);
    }
    @OnClick(R.id.tea_evaluate_ten)
    void setTvTen(){
        initSelectScoreTv(10);
    }
    private void initSelectScoreTv(int index){
        select_score_data=score_list.get(index);
        select_score_tv.setText(select_score_data.getName());
        ViewTool.alterGradientDrawableColor(select_score_tv,mActivity.getResources().getColor(select_score_data.getRes_image()));
    }
}
