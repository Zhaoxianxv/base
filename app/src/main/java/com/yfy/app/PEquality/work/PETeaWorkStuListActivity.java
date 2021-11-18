package com.yfy.app.PEquality.work;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.StuBean;
import com.yfy.app.bean.TermBean;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.hander.AssetsApi;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;


public class PETeaWorkStuListActivity extends HttpPostActivity implements HttpNetHelpInterface , AssetsGetFileData {
    private static final String TAG = PETeaWorkStuListActivity.class.getSimpleName();

    public PETeaWorkStuListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_tea_work_stu_list);
        Logger.eLogText(TAG);
        getData();
        initRecycler();
        initSQToolbar();
        getStu(true);
    }



    public TermBean select_term;
    public CPWBean classCPWBean;
    public DateBean select_date;
    private void getData(){
        classCPWBean=getIntent().getParcelableExtra(Base.class_bean);
        select_term=getIntent().getParcelableExtra(Base.term_bean);
        select_date=getIntent().getParcelableExtra(Base.date);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(select_date.getName());
    }
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.recycler_view_work_stu_p_e_work);
        GridLayoutManager manager = new GridLayoutManager(mActivity,5, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new PETeaWorkStuListAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent) {

                intent.setClass(mActivity,PETeaWorkStuDetailSetStateActivity.class);
                startActivityForResult(intent,TagFinal.UI_ADD);

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    Logger.eLogText(TAG);
                    break;
                case -1:
                    break;
            }
        }
    }


    /**
     * ----------------------------retrofit-----------------------
     */






    public List<StuBean> stuBeanList=new ArrayList<>();
    public void getStu(boolean is){


        if (Base.user==null){
            ViewTool.showToastShort(mActivity,"没登陆");
            getAssetsData(AssetsApi.GET_CLASS_ALL_STU_API);

            return;
        }

        Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().satisfaction_tea_get_stu_api(
                Base.user.getSession_key(),
                ConvertObject.getInstance().getInt(classCPWBean.getId()),
                MathTool.stringToInt(select_term.getId())
        );
        setNetHelper(this,bodyCall,is, ApiUrl.SATISFACTION_TEA_GET_STU);
    }

    @Override
    public void success(String api_name, String result) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        if (api_name.equalsIgnoreCase(ApiUrl.SATISFACTION_TEA_GET_STU)){
            BaseRes res=gson.fromJson(result, BaseRes.class);
            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                stuBeanList.clear();

                if (StringJudge.isEmpty(res.getIncompletestu())){
                    ViewTool.showToastShort(mActivity,"没有获取到学生");
                }else{
                    stuBeanList.addAll(res.getIncompletestu());
                    adapter.setDataList(stuBeanList);
                    adapter.setLoadState(TagFinal.LOADING_END);
                }
            }else{
                ViewTool.showToastShort(mActivity,res.getError_code());
            }
        }
    }


    @Override
    public void fail(String api_name) {
    }






    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




    /*async task*/
    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        ViewTool.showProgressDialog(mActivity,"");
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }



    /*async 返回数据到UI线程*/
    @Override
    public void doUpData(String content) {
        ViewTool.dismissProgressDialog();
        if (StringJudge.isEmpty(content)){
            ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
        }else{
            int lastIndexOf = content.lastIndexOf("#&#");
            String result=content.substring(0,lastIndexOf);
            String api_name=content.substring(lastIndexOf).substring(3);
            Logger.eShowResultText(api_name,result);
            switch (api_name){
                case AssetsApi.GET_CLASS_ALL_STU_API:
                    BaseRes res=gson.fromJson(result,BaseRes.class);
                    if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                        stuBeanList.clear();

                        Logger.eShowResultText(api_name,result);
                        if (StringJudge.isEmpty(res.getIncompletestu())){
                            ViewTool.showToastShort(mActivity,"没有获取到学生");
                        }else{
                            stuBeanList.addAll(res.getIncompletestu());
                            adapter.setDataList(stuBeanList);
                            adapter.setLoadState(TagFinal.LOADING_END);
                        }
                    }else{
                        ViewTool.showToastShort(mActivity,res.getError_code());
                    }
                    break;
                case AssetsApi.PE_GET_MONTH_WORK_STATE_API:

                    break;
            }
        }

    }

    /*async 处理*/
    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }






}
