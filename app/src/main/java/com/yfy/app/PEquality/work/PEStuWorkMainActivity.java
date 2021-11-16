package com.yfy.app.PEquality.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.yfy.app.PEquality.bean.QEHonorRes;
import com.yfy.app.PEquality.bean.WorkDayPE;
import com.yfy.app.PEquality.bean.WorkResPE;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.date_select.DateSelectStateCard;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.hander.AssetsApi;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class PEStuWorkMainActivity extends HttpPostActivity implements HttpNetHelpInterface , AssetsGetFileData {
    private static final String TAG = PEStuWorkMainActivity.class.getSimpleName();



    @BindView(R.id.date_select_month)
    TextView select_month;


    @OnClick(R.id.date_select_btn_PreMonth)
    void setPreMonth(){
        initDate(false);
    }
    @OnClick(R.id.date_select_btn_btnNextMonth)
    void setNextMonth(){
        initDate(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_work_main);
        Logger.eLogText(TAG);
        select_date=new DateBean();
        select_date.setValue_long(System.currentTimeMillis(),true);
        select_month.setText(StringUtils.stringToGetTextJoint("%1$d年%2$d月",select_date.getSelectYearNameInt(),select_date.getSelectMonthNameInt()));
        getData();
        initSQToolbar();
        initDateDate();
    }

    private void getData(){
        /*获取当前月份日期作业发放数据*/
        getAssetsData(AssetsApi.PE_GET_MONTH_WORK_STATE_API);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("作业");


    }









    public DateBean select_date;
    public DateSelectStateCard calendarCard;
    private void initDateDate() {

        calendarCard =  findViewById(R.id.calendar_card_pe);
        calendarCard.setDateSelectClickListener(new DateSelectStateCard.DateSelectClickListener() {
            @Override
            public void clickDate(DateBean date,String state) {
                select_date.setValue_long(date.getValue_long());
                select_month.setText(StringUtils.stringToGetTextJoint("%1$d年%2$d月",select_date.getSelectYearNameInt(),select_date.getSelectMonthNameInt()));


                Intent intent=new Intent();
                Logger.eLogText(state);

                switch (state){
                    /*选中日期已布置作业-未审核*/
                    case "5":
                        intent.setClass(mActivity, PEStuWorkIngDetailActivity.class);
                        startActivity(intent);
                        break;
                    /*选中日期已布置作业-已审核*/
                    case "7":
                        intent.setClass(mActivity, PEStuWorkEndDetailActivity.class);
                        startActivity(intent);
                        break;
                        /*选中日期未布置作业*/
                    case "":
                        break;
                }
            }
        });
    }









    private void initDate(boolean  is) {



        int yearNameInt=select_date.getSelectYearNameInt();
        int monthNameInt=select_date.getSelectMonthNameInt();

        int element_month=is?monthNameInt+1:monthNameInt-1;
        DateBean customDate = new DateBean();
        customDate.setDateYMD(getYearOrMonth(yearNameInt,element_month,true), getYearOrMonth(yearNameInt,element_month,false), 1);

        calendarCard.update(customDate);

        select_date.setValue_long(customDate.getValue_long());
        select_month.setText(StringUtils.stringToGetTextJoint("%1$d年%2$d月",select_date.getSelectYearNameInt(),select_date.getSelectMonthNameInt()));

        Logger.eLogText(select_date.getName());

    }


    private int getYearOrMonth(int year, int month, boolean is) {
        if (month > 12) {
            year += month / 12;
            month = month % 12;
        } else if (month == 0) {
            year -= 1;
            month = 12;
        } else if (month < 0) {
            year -= Math.abs(month) / 12 + 1;
            month = 12 - Math.abs(month) % 12;
        }
        if (is) {
            return year;
        } else {
            return month;
        }

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
                    dataInitMonth(result);
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









    public List<DateBean> dateBeanList=new ArrayList<>();
    public void dataInitMonth(String result){
        dateBeanList.clear();
        WorkResPE res=gson.fromJson(result, WorkResPE.class);
        if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
            if (StringJudge.isEmpty(res.getMonth_day_list())){
                ViewTool.showToastShort(mActivity,"没有获取到信息");
            }else{
                for (WorkDayPE workDayPE:res.getMonth_day_list()){
                    DateBean bean=new DateBean();
                    bean.setDateYMD(
                            MathTool.stringToInt(workDayPE.getName_year()),
                            MathTool.stringToInt(workDayPE.getName_month()),
                            MathTool.stringToInt(workDayPE.getName_day())
                            );
                    bean.setState_color(workDayPE.getState());
                    dateBeanList.add(bean);
                }

                calendarCard.update(select_date,dateBeanList);
            }
        }else{
            ViewTool.showToastShort(mActivity,res.getError_code());
        }
    }

}
