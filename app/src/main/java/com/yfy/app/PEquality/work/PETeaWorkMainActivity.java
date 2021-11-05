package com.yfy.app.PEquality.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.PEquality.bean.QEHonorRes;
import com.yfy.app.bean.BaseClass;
import com.yfy.app.bean.BaseGrade;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.TermBean;
import com.yfy.app.date_select.DateSelectCard;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.hander.AssetsApi;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class PETeaWorkMainActivity extends HttpPostActivity implements HttpNetHelpInterface , AssetsGetFileData {
    private static final String TAG = PETeaWorkMainActivity.class.getSimpleName();



    @BindView(R.id.date_select_month)
    TextView select_month;
    @OnClick(R.id.date_select_month)
    void selectDate(){

    }


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
        initDialogList();
    }

    public CPWBean class_bean;
    public TermBean select_term;
    private String title;
    private void getData(){
        select_term= NormalDataSaveTools.getInstance().getTermBeanToGreenDao();

        title="作业";
        getAssetsData(AssetsApi.GET_CLASS_BEAN_API);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"统计");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position) {


                setCPWlListBeanData();

            }
        });

    }

    private CPWListBeanView cpwListBeanView;
    public List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            ViewTool.showToastShort(mActivity,"没有获取到班级");
            return;
        }
        cpwListBeanView.setDatas(cpwBeans);
        cpwListBeanView.showAtCenter();
    }

    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {

            @Override
            public void fastPopClick(CPWBean cpwBean, String type) {

                Intent intent=new Intent();

                cpwListBeanView.dismiss();
            }
        });
    }







    public DateBean select_date;
    public DateSelectCard calendarCard;
    private void initDateDate() {

        calendarCard =  findViewById(R.id.calendar_card);
        calendarCard.setmDateSelectClickListener(new DateSelectCard.DateSelectClickListener() {
            @Override
            public void clickDate(DateBean date) {
                select_date.setValue_long(date.getValue_long());
                select_month.setText(StringUtils.stringToGetTextJoint("%1$d年%2$d月",select_date.getSelectYearNameInt(),select_date.getSelectMonthNameInt()));

                Intent intent=new Intent();
                intent.setClass(mActivity, PETeaWorkStuListActivity.class);
                intent.putExtra(Base.title,"title");
                intent.putExtra(Base.data,select_date);
                intent.putExtra(Base.term_bean,select_term);
                intent.putExtra(Base.class_bean,class_bean);

                startActivity(intent);
            }

            @Override
            public void changeDate(DateBean date) {

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



    /*获取当前月份日期作业发放数据*/
    public void getHonor(boolean is){

        getAssetsData("get_class_bean.txt");



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

                    for (TermBean bean:res.getTerm()){
                        CPWBean cpwBean=new CPWBean(StringUtils.stringToGetTextJoint("%1$s",bean.getName()),bean.getId());
                        cpwBeans.add(cpwBean);
                    }
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
            switch (api_name){
                case AssetsApi.GET_CLASS_BEAN_API:
                    dataInitClass(result);
                    if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
                        mTask.cancel(true);
                    }
                    getAssetsData(AssetsApi.PE_GET_MONTH_WORK_STATE_API);
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









    public void dataInitClass(String result){
        BaseRes res=gson.fromJson(result,BaseRes.class);
        if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
            if (StringJudge.isEmpty(res.getGradelist())){
                ViewTool.showToastShort(mActivity,"没有获取到班级信息");
            }else{
                cpwBeans.clear();
                List<BaseGrade> list=res.getGradelist();
                for (BaseGrade s:list){
                    for (BaseClass bean:s.getClasslist()){
                        CPWBean cpwBean=new CPWBean(StringUtils.stringToGetTextJoint("%1$s-%2$s",s.getGradename(),bean.getClassname()),bean.getClassid());

                        class_bean=cpwBean;
                        cpwBeans.add(cpwBean);
                    }
                }

            }
        }else{
            ViewTool.showToastShort(mActivity,res.getError_code());
        }
    }

    
    public void dataInitMonth(String result){}

}
