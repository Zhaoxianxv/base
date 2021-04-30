package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateStuDetailAdapter;
import com.yfy.app.duty_evaluate.bean.DutyEvaluateRes;
import com.yfy.app.duty_evaluate.bean.InfoBean;
import com.yfy.app.duty_evaluate.bean.SeBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.DateUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.time.CustomDatePicker;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class DutyEvaluateStuDetailActivity extends BaseActivity implements AssetsGetFileData {
    private static final String TAG = DutyEvaluateStuDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        Logger.e(TAG);
        getData();
        initRecycler();
        getAssetsData("duty_evaluate_get_stu_detail.txt");
    }
    public KeyValue stu_bean;
    private void getData(){
        stu_bean=getIntent().getParcelableExtra(Base.stu_bean);
        year_s=getIntent().getStringExtra(Base.year_value);
        month_s=getIntent().getStringExtra(Base.month_value);

        initSQToolbar("");

        select_date_tv.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
        initDatePicker();
    }

    private TextView select_date_tv;
    public void initSQToolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);
        select_date_tv=toolbar.addMenuText(TagFinal.TWO_INT,"");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                customDatePicker1.show(StringUtils.stringToGetTextJoint("%1$s-%2$s-01 01:01",year_s,month_s));

            }
        });
    }





    public String year_s,month_s;
    public CustomDatePicker customDatePicker1;
    private void initDatePicker() {




        customDatePicker1 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String data=time.split(" ")[0].substring(0,time.split(" ")[0].lastIndexOf("-"));
                year_s=data.split("-")[0];
                month_s=data.split("-")[1];
                select_date_tv.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
            }
        }, "2000-01-01 00:00", DateUtils.getDateTime("yyyy-MM-dd HH:mm"));
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动


    }












    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public DutyEvaluateStuDetailAdapter adapter;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        swipeRefreshLayout =  findViewById(R.id.public_swip);
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据

                getAssetsData("duty_evaluate_get_stu_detail.txt");
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线

        adapter=new DutyEvaluateStuDetailAdapter(mActivity);
        recyclerView.setAdapter(adapter);


    }






    public void closeSwipeRefresh(){
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 200);
        }
    }











    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    break;

            }
        }
    }








    //async task

    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        showProgressDialog("");
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }


    public List<KeyValue> adapter_data_list=new ArrayList<>();

    private void initAdapterData(DutyEvaluateRes res){
        adapter_data_list.clear();

        KeyValue top=new KeyValue();
        top.setView_type(TagFinal.TYPE_TOP);
        top.setTitle("总分");
        top.setValue(res.getAll_score());
        List<CPWBean> tab_list=new ArrayList<>();
        for (InfoBean info:res.getInfo()){
            CPWBean cpwBean=new CPWBean();
            cpwBean.setName(info.getParent_title());
            tab_list.add(cpwBean);
        }
        top.setCpwBeanArrayList(tab_list);
        adapter_data_list.add(top);
        KeyValue tag=new KeyValue();
        tag.setView_type(TagFinal.TYPE_TXT);
        adapter_data_list.add(tag);
        for (InfoBean info:res.getInfo()){
            KeyValue bean=new KeyValue();
            bean.setView_type(TagFinal.TYPE_ITEM);
            bean.setTitle(info.getParent_title());
            bean.setValue(info.getParent_all_score());
            List<CPWBean> item_list=new ArrayList<>();
            for (SeBean seBean:info.getEvaluate_list()){
                CPWBean cpwBean=new CPWBean();
                cpwBean.setName(seBean.getTitle());
                cpwBean.setOne(seBean.getStu_score());
                cpwBean.setTwo(seBean.getFather_score());

                item_list.add(cpwBean);
            }

            bean.setCpwBeanArrayList(item_list);
            adapter_data_list.add(bean);
        }


        adapter.setDataList(adapter_data_list);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    @Override
    public void doUpData(String content) {
        dismissProgressDialog();
        closeSwipeRefresh();
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


