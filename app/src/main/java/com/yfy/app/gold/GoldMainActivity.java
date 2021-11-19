package com.yfy.app.gold;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.hander.PicAsyncTask;
import com.yfy.final_tag.hander.SaveImageAsync;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class GoldMainActivity extends BaseActivity implements SaveImageAsync {
    private static final String TAG = GoldMainActivity.class.getSimpleName();

    private GoldMainAdapter adapter;


    @BindView(R.id.tv_content_gold_main)
    TextView tv_content;
    @BindView(R.id.tv_title_gold_main)
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gold_main);
        list_title= StringUtils.listToArrays(StringUtils.getResourceStringArray(mActivity,R.array.gold_type));
        select_gold_type=list_title.get(0);

        initDialogList();
        setCPWlListBeanData();
        initRecycler();
        initSQToolbar();
        getDBData();
    }



    public List<String> list_title=new ArrayList<>();
    public String select_gold_type;
    public TextView tv_top_title;
    private void initSQToolbar() {
        assert toolbar!=null;
        tv_top_title=toolbar.setTitle(select_gold_type);
        toolbar.addMenuText(TagFinal.ONE_INT,"切换");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position) {

                cpwListBeanView.showAtCenter();
            }
        });


    }

    public CPWListBeanView cpwListBeanView;
    public List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            for(String s:list_title){
                CPWBean cpwBean =new CPWBean();
                cpwBean.setName(s);
                cpwBean.setId(s);
                cpwBeans.add(cpwBean);
            }
        }
        cpwListBeanView.setDatas(cpwBeans);
    }
    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastPopClick(CPWBean cpwBean, String type) {
                cpwListBeanView.dismiss();
                select_gold_type=cpwBean.getId();
                tv_top_title.setText(select_gold_type);

                getDBData();

            }
        });
    }



    public List<KeyValueDb> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.Gray)));
        adapter=new GoldMainAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent, String type) {

                if (type.equalsIgnoreCase("del")){
                    getDBData();
                }
            }
        });

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);

    }









    /**
     * ----------------------------retrofit-----------------------
     */




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }











    /*数据读写都是耗时·读函数*/


    /*PicAsyncTask*/

    public void getDBData(){
        num_exit=0;
        num_enter=0;
        embezzle_num=0;
        gold_enter_list.clear();
        gold_exit_list.clear();
        gold_embezzle_list.clear();
        keyValue_adapter.clear();
        mTask=new PicAsyncTask(GoldMainActivity.this);
        mTask.execute("zxx");
    }


    public PicAsyncTask mTask;
    public int num_exit=0,num_enter=0,embezzle_num=0;
    public List<KeyValueDb> gold_enter_list=new ArrayList<>();
    public List<KeyValueDb> gold_exit_list=new ArrayList<>();
    public List<KeyValueDb> gold_embezzle_list=new ArrayList<>();
    @Override
    public List<String> doIn(String... arg0) {
        List<String> list=new ArrayList<>(Arrays.asList(arg0));


        gold_embezzle_list = NormalDataSaveTools.getInstance().getGoldToGreenDao("gold_embezzle");
        gold_enter_list = NormalDataSaveTools.getInstance().getGoldToGreenDao("gold_enter");
        gold_exit_list = NormalDataSaveTools.getInstance().getGoldToGreenDao("gold_exit");

        keyValue_adapter=NormalDataSaveTools.getInstance().getGoldToGreenDao(select_gold_type);

        switch (select_gold_type){
            case "gold_enter":
                if (StringJudge.isEmpty(gold_enter_list)){
                    tv_content.setText("没有记录");
                }else{
                    for (KeyValueDb db:gold_enter_list){
                        num_enter+= MathTool.stringToInt(db.getValue());
                    }
                    tv_title.setText(StringUtils.stringToGetTextJoint("enter:%1$d",num_enter));
                }
                break;
            case "gold_exit":
                if (StringJudge.isEmpty(gold_exit_list)){
                    tv_content.setText("没有记录");
                }else{
                    for (KeyValueDb db:gold_exit_list){
                        num_exit+= MathTool.stringToInt(db.getValue());
                    }
                }
                break;
            case "gold_embezzle":
                if (StringJudge.isEmpty(gold_embezzle_list)){
                    tv_content.setText("没有记录");
                }else{
                    for (KeyValueDb db:gold_embezzle_list){
                        if (db.getRank().equalsIgnoreCase(TagFinal.TRUE))continue;
                        embezzle_num+= MathTool.stringToInt(db.getValue());
                    }
                    tv_content.setText(StringUtils.stringToGetTextJoint("还有挪用:%1$d",embezzle_num));
                }
                break;
        }

        return list;
    }
    @Override
    public void doUpData(List<String> list) {
        ViewTool.dismissProgressDialog();
        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
        switch (select_gold_type){
            case "gold_enter":
                if (StringJudge.isEmpty(gold_enter_list)){
                    tv_title.setText("gold_enter");
                }else{
                    tv_title.setText(StringUtils.stringToGetTextJoint("enter:%1$d",num_enter));
                }
                break;
            case "gold_exit":
                if (StringJudge.isEmpty(gold_exit_list)){
                    tv_title.setText("exit");
                }else{
                    tv_title.setText(StringUtils.stringToGetTextJoint("exit:%1$d",num_exit));
                }
                break;
            case "gold_embezzle":
                tv_title.setText(StringUtils.stringToGetTextJoint("还有挪用:%1$d",embezzle_num));
                break;
        }

        int num=num_exit-num_enter;
        tv_content.setText(StringUtils.stringToGetTextJoint("金额:%1$d",num));
        if(num>0){
            tv_content.setTextColor(ColorRgbUtil.getResourceColor(mActivity,R.color.main_red));
        }else{
            tv_content.setTextColor(ColorRgbUtil.getResourceColor(mActivity,R.color.Green));
        }

    }
    @Override
    public void onPre() {
        ViewTool.showProgressDialog(mActivity,"");
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }







}
