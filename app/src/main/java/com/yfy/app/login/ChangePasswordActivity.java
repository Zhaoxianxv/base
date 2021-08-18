package com.yfy.app.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserChangePasswordReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity  {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    @BindView(R.id.alter_old_password)
    EditText old;
    @BindView(R.id.alter_new_first_password)
    EditText first;
    @BindView(R.id.alter_new_again_password)
    EditText again;
    public String oldpass,firstpass,againpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_change_password);
        initSQToolbar();
    }


    public void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("修改密码");

        toolbar.addMenuText(1,R.string.ok);

        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position){
                if (isSend()){
                    alterPass();
                }

            }
        });
    }

    public boolean isSend(){
        oldpass=old.getText().toString().trim();
        firstpass=first.getText().toString().trim();
        againpass=again.getText().toString().trim();
        if (StringJudge.isEmpty(oldpass)){
            ViewTool.showToastShort(mActivity,"请输入密码");
            return false;
        }
        if (StringJudge.isEmpty(firstpass)){
            ViewTool.showToastShort(mActivity,"请输入新密码");
            return false;
        }
        if (StringJudge.isEmpty(againpass)){
            ViewTool.showToastShort(mActivity,"请再次输入新密码");
            return false;
        }
        if (!firstpass.equals(againpass)){
            ViewTool.showToastShort(mActivity,"新密码输入不一致");
            return false;
        }
        return true;
    }



    /**
     *-----------------------------retrofit
     */


    private void alterPass() {

        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserChangePasswordReq req = new UserChangePasswordReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());
        req.setNewpassword(firstpass);
        req.setOldpassword(oldpass);

        reqBody.userChangePasswordReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_change_password_api(reqEnv);
        call.enqueue(this);
        ViewTool.showProgressDialog(mActivity,"");
    }



    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        ResEnv respEnvelope = response.body();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userChangePasswordRes !=null){
                String result=b.userChangePasswordRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
//                if (JsonParser.isSuccess(result)){
//                    Base.user =null;
//                    UserPreferences.getInstance().clearUserAll();
//                    GreenDaoManager.getInstance().clearUser();
//                    ViewTool.showToastShort(mActivity,"密码修改成功，请重新登录");
//                    startActivity(new Intent(mActivity,LoginActivity.class));
//                    onPageBack();
//                }
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
            ViewTool.showToastShort(mActivity,StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

}
