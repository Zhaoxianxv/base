package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
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
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DutyEvaluateTeaDoActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateTeaDoActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_tea_do);
        getData();
    }

    public String class_id;
    public KeyValue classBean;
    private void getData(){
        classBean=getIntent().getParcelableExtra(Base.class_bean);
        class_id=classBean.getValue();
        initSQToolbar("五育评价班评");

        getClassAllStu();
    }


    private void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"记录");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent=new Intent(mActivity,DutyEvaluateRecordActivity.class);
                startActivity(intent);
            }
        });


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
        req.setClassid(ConvertObjtect.getInstance().getInt(class_id));
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
