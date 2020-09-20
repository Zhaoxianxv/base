package com.yfy.app.PEquality;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfy.app.PEquality.adapter.PEHonorMainAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.Logger;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmDateWindow;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PEHonorAddActivity extends BaseActivity {
    private static final String TAG = PEHonorAddActivity.class.getSimpleName();



    @Bind(R.id.p_e_honor_add_choose_date)
    AppCompatTextView choose_date;
    @Bind(R.id.p_e_honor_add_choose_course)
    AppCompatTextView choose_course;


    private DateBean dateBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_honor_add);
        dateBean=new DateBean();
        dateBean.setValue_long(System.currentTimeMillis(),true);
        getData();
        initView();
        initSQToolbar();
        initDateDialog();
        initDialogList();

    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"确定");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                finish();
            }
        });

    }

    private void initView(){
        choose_course.setText("未选择");
        choose_course.setTextColor(ColorRgbUtil.getGrayText());
        choose_date.setTextColor(ColorRgbUtil.getGrayText());
        choose_date.setText("未选择");
    }
    private ConfirmDateWindow date_dialog;
    private void initDateDialog(){
        date_dialog = new ConfirmDateWindow(mActivity);
        date_dialog.setOnPopClickListenner(new ConfirmDateWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.set:
                        dateBean.setName(date_dialog.getTimeName());
                        dateBean.setValue(date_dialog.getTimeValue());
                        choose_date.setText(dateBean.getName());
                        choose_date.setTextColor(ColorRgbUtil.getBaseText());
                        date_dialog.dismiss();
                        break;
                    case R.id.cancel:
                        date_dialog.dismiss();
                        break;
                }

            }
        });
    }






    private CPWListBeanView cpwListBeanView;
    List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            cpwBeans.add(new CPWBean("上午·第1节",""));
            cpwBeans.add(new CPWBean("上午·第2节",""));
            cpwBeans.add(new CPWBean("上午·第3节",""));
            cpwBeans.add(new CPWBean("上午·第4节",""));
            cpwBeans.add(new CPWBean("下午·第1节",""));
            cpwBeans.add(new CPWBean("下午·第2节",""));
            cpwBeans.add(new CPWBean("下午·第3节",""));
        }
        cpwListBeanView.setDatas(cpwBeans);

    }


    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListenner(new CPWListBeanView.OnPopClickListenner() {
            @Override
            public void onClick(CPWBean cpwBean,String type) {
                choose_course.setText(cpwBean.getName());
                choose_course.setTextColor(ColorRgbUtil.getBaseText());
                cpwListBeanView.dismiss();
            }
        });
    }



    @OnClick(R.id.p_e_honor_add_choose_course)
    void setCourse(){
        setCPWlListBeanData();
        cpwListBeanView.showAtCenter();
    }
    @OnClick(R.id.p_e_honor_add_choose_date)
    void setDate(){
        date_dialog.showAtBottom();

    }
    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_term_list(env);
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
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
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
