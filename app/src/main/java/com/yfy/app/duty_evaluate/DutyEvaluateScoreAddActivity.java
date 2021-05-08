package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.yfy.app.TabFragmentAdapter;
import com.yfy.app.duty_evaluate.bean.ReportOne;
import com.yfy.app.duty_evaluate.bean.ReportThree;
import com.yfy.app.duty_evaluate.bean.ReportTwo;
import com.yfy.app.duty_evaluate.bean.StuReportRes;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.json.JsonParser;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("NonConstantResourceId")
public class DutyEvaluateScoreAddActivity extends BaseActivity implements AssetsGetFileData {
    private static final String TAG = DutyEvaluateScoreAddActivity.class.getSimpleName();

    @BindView(R.id.tab_layout_button)
    Button button;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager_view)
    ViewPager viewPager;
    @BindView(R.id.stu_report_get_vote_layout)
    RelativeLayout buttom_layout;

    TabFragmentAdapter adapter;
    List<Fragment> fragments=new ArrayList<>();
    List<String> titles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_report_get_vote_main);
        Logger.e(TAG);



        initSQToolbar();
        getData();
        initView();
        getAssetsData("duty_score_add.txt");
    }


    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    public String year_value,month_value;
    public void getData(){
        Intent intent=getIntent();
        year_value=intent.getStringExtra(Base.year_value);
        month_value=intent.getStringExtra(Base.month_value);
        initSQToolbar();
    }

    public TextView title_bar;
    public void initSQToolbar(){
        assert toolbar!=null;
        title_bar=toolbar.setTitle("title");
        toolbar.setOnNaviClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String show_type="stu",user_type="";

    @BindView(R.id.stu_report_get_vote_check)
    CheckedTextView all_select;
    @OnClick(R.id.stu_report_get_vote_check)
    void setCheck(){
        all_select.setChecked(!all_select.isChecked());
        String type=all_select.isChecked()? TagFinal.TRUE: TagFinal.FALSE;
        for (Fragment stuReportVoteFragment:fragments){
            DutyEvaluateScoreAddFragment fragment= (DutyEvaluateScoreAddFragment) stuReportVoteFragment;
            fragment.selectItem(type);
        }
        for (ReportOne one:tab_title_list){
            for (ReportTwo two:one.getTablelist()){
                for (ReportThree reportThree:two.getTablelist()){
                    if (show_type.equalsIgnoreCase("tea_vote")){
                        reportThree.setTeacherselect(type);
                    }else{
                        reportThree.setStuselect(type);
                    }
                }
            }
        }
    }

    private List<ReportOne> tab_title_list=new ArrayList<>();

    private void initView() {
        tabLayout.setTabTextColors( Color.GRAY,getResources().getColor(R.color.base_color));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.base_color));
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);//滚动样式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//滚动样式
        TableLayout.LayoutParams params=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tabLayout.setLayoutParams(params);//MATCH_PARENT满屏均分布

        adapter=new TabFragmentAdapter(fragments, titles,mActivity.getSupportFragmentManager(),mActivity);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager .setOffscreenPageLimit(fragments.size());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }




























    //async task

    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        showProgressDialog("");
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }

    @Override
    public void doUpData(String result) {
        dismissProgressDialog();
        if (StringJudge.isEmpty(result)){
            ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
        }else{
            StuReportRes res=gson.fromJson(result, StuReportRes.class);
            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                switch (show_type){
//                    case "tea_vote":
//                        title_bar.setText(StringUtils.getTextJoint("%1$s-%2$s分",menu_name,res.getTeacherscore()));
//                        break;
//                    case "stu_vote":
//                        title_bar.setText(StringUtils.getTextJoint("%1$s-%2$s分",menu_name,res.getStuscore()));
//                        break;
//                }
                tab_title_list = res.getTablelist();
                titles.clear();
                fragments.clear();
                if (StringJudge.isEmpty(tab_title_list)){
                    button.setVisibility(View.GONE);
                    return;
                }

                for (ReportOne one:tab_title_list){
                    if (one.getTablename().equalsIgnoreCase("一级指标"))continue;
                    titles.add(one.getTablename());
                    DutyEvaluateScoreAddFragment fragment=new DutyEvaluateScoreAddFragment();
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList(Base.data, (ArrayList<? extends Parcelable>) one.getTablelist());
                    bundle.putString(Base.type,user_type);
                    bundle.putString(Base.type_name,show_type);
                    fragment.setArguments(bundle);
                    fragments.add(fragment);
                }
                adapter.setData(fragments,titles);
            }else{
                toastShow(JsonParser.getErrorCode(result));
            }
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
