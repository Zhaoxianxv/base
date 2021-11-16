package com.yfy.app.PEquality.work;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.yfy.app.PEquality.bean.QEHonorRes;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.hander.AssetsApi;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;


/**
 * ------------学生-作业完成老师已审核的详情展示
 */
@SuppressLint("NonConstantResourceId")
public class PEStuWorkEndDetailActivity extends HttpPostActivity implements HttpNetHelpInterface , AssetsGetFileData {
    private static final String TAG = PEStuWorkEndDetailActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_stu_work_end_detail);
        Logger.eLogText(TAG);
        getData();
        initSQToolbar();
    }

    private void getData(){
        /*获取当前月份日期作业发放数据*/
        getAssetsData(AssetsApi.PE_GET_MONTH_WORK_STATE_API);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("作业");


    }













    /**
     * ----------------------------retrofit-----------------------
     */



    public void getHonor(boolean is){




    }



    @Override
    public void success(String api_name, String result) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        QEHonorRes honorRes= gson.fromJson(result, QEHonorRes.class);
        switch (api_name){
            case AssetsApi.GET_CLASS_BEAN_API:
//
//                if (honorRes.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                }else{
//                    ViewTool.showToastShort(mActivity,honorRes.getError_code());
//                }
//                break;
            case ApiUrl.USER_GET_TERM_LIST:
                BaseRes res= gson.fromJson(result, BaseRes.class);
                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){


                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
                break;
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
                case AssetsApi.GET_CLASS_BEAN_API:
                    if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
                        mTask.cancel(true);
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
