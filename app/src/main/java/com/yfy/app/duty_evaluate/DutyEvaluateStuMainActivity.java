package com.yfy.app.duty_evaluate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.StuBean;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetClassAllStuReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.SQToolBar;
import com.yfy.view.time.CustomDatePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Response;

public class DutyEvaluateStuMainActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateStuMainActivity.class.getSimpleName();


    @Bind(R.id.confirm_tab_layout_one)
    TabLayout one;
    @Bind(R.id.confirm_tab_layout_two)
    TabLayout two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_stu_main);
        initSQToolbar("五育评价");
        initView();
    }



    public void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"选择周");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });


    }



    private String year_s,month_s;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
//        time_text=now.split(" ")[0];
//        String data=now.split(" ")[0].substring(0,now.split(" ")[0].lastIndexOf("-"));
//        year_s=data.split("-")[0];
//        month_s=data.split("-")[1];
//        munetv.setText(data);
//        refresh();
//        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
////				currentDate.setText(time.split(" ")[0]);
//                time_text=time.split(" ")[0];
//                String data=time.split(" ")[0].substring(0,time.split(" ")[0].lastIndexOf("-"));
//                year_s=data.split("-")[0];
//                month_s=data.split("-")[1];
//                munetv.setText(data);
//                refresh();
////                getTerm(Variables.userInfo.getSession_key(),year_s+"/"+month_s);
//            }
//        }, "2000-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }



















    private void initView(){
        one.setTabTextColors(Color.GRAY,ColorRgbUtil.getBlue());
        two.setTabTextColors(Color.GRAY,ColorRgbUtil.getBlue());
        initCreatOne();
        initCreatTwo();
        one.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
//                if (index==0){
//                    two.removeAllTabs();
//                    two.setVisibility(View.GONE);
//                    if (listenner != null) {
//                        listenner.onClick(tab.getText().toString(),"0");
//                    }
//                    dismiss();
//                }else{
//                    two.setVisibility(View.VISIBLE);
//                    initCreatTwo(groups.get(index-1).getGradeid());
//                }


                two.setVisibility(View.VISIBLE);
                initCreatTwo();
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

//                ClassGrade class_bean=twoclass.get(index);
//                Logger.e(""+index);
//                if (listenner != null) {
//                    listenner.onClick(StringUtils.stringToGetTextJoint("%1$s-%2$s",class_bean.getGradename(),class_bean.getClassname()),class_bean.getClassid());
//                }
//                dismiss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void initCreatOne(){
        List<String> list=StringUtils.listToStringSplitCharacters("遵守纪律,热爱学习,强健体魄,表率文雅,勤于劳动",",");
        for (String s:list){

            one.addTab(one.newTab().setText(s),s.equalsIgnoreCase("遵守纪律")?true:false);
        }
    }


    private void initCreatTwo(){
        List<String> list=StringUtils.listToStringSplitCharacters("守时守信,遵纪尊规,广播体操,会值日,会服务",",");

        two.removeAllTabs();
        for (String bean:list){
            two.addTab(two.newTab().setText(bean),bean.equalsIgnoreCase("守时守信")?true:false);
        }

    }



    /**
     * ----------------------------retrofit-----------------------
     */

    public List<StuBean> stuBeanList=new ArrayList<>();
    public void getClassAllStu() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetClassAllStuReq req = new UserGetClassAllStuReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());
        req.setTermid(ConvertObjtect.getInstance().getInt(UserPreferences.getInstance().getTermId()));


        reqBody.userGetClassAllStuReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_class_all_stu_api(env);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetClassAllStuRes !=null){
                String result=b.userGetClassAllStuRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    stuBeanList=res.getIncompletestu();
                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
            }
        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        toastShow(R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}




