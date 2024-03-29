package com.yfy.app.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.login.bean.UserRes;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserResetPasswordReq;
import com.yfy.app.net.login.ResetPasswordGetCodeReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.RegexUtils;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.rsa.AES;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends BaseActivity implements Callback<ResEnv> {
    private static final String TAG = ResetPasswordActivity.class.getSimpleName();

    RadioGroup group;
    RadioButton type_one;
    RadioButton type_two;
    @BindView(R.id.reset_edit_phone)
    EditText edit_phone;
    @BindView(R.id.reset_edit_code)
    EditText edit_code;
    @BindView(R.id.send_code)
    TextView send_code;
    @BindView(R.id.reset_edit_new_password)
    EditText edit_new_pass;
    @BindView(R.id.reset_alter_edit_new_password)
    EditText alter_edit_pass;
    MyCountDownTimer mycount;
    private String code,user_id;
    private String user_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_password);
        mycount = new MyCountDownTimer(60000, 1000);
        initSQToolbar();
        group=  findViewById(R.id.reset_password_radio_group);
        type_one =findViewById(R.id.reset_password_one);
        type_two =  findViewById(R.id.reset_password_two);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.reset_password_one:
                        user_type="stu";
                        type_two.setTextColor(ColorRgbUtil.getBaseColor());
                        type_one.setTextColor(ColorRgbUtil.getWhite());
                        type_one.setBackgroundResource(R.drawable.radius_right_bottom4_top4);
                        type_two.setBackgroundColor(Color.TRANSPARENT);

                        break;
                    case R.id.reset_password_two:
                        user_type="tea";
                        type_two.setTextColor(ColorRgbUtil.getWhite());
                        type_one.setTextColor(ColorRgbUtil.getBaseColor());
                        type_two.setBackgroundResource(R.drawable.radius_left_bottom4_top4);
                        type_one.setBackgroundColor(Color.TRANSPARENT);

                        break;
                }
            }
        });
    }




    @Override
    protected void onDestroy() {
        mycount.cancel();
        super.onDestroy();
        mycount = null;
    }

    private void initSQToolbar(){
        assert toolbar!=null;
        toolbar.setTitle("重置密码");
    }
    @OnClick(R.id.send_code)
    void setSendcode(){
        String tell=edit_phone.getText().toString().trim();

        if (StringJudge.isEmpty(tell)){
            ViewTool.showToastShort(mActivity,R.string.please_edit_phone);
            return;
        }

        if (StringJudge.isEmpty(user_type)){
            ViewTool.showToastShort(mActivity,"请选择用户类型");
            return;
        }
        if (RegexUtils.isMobilePhoneNumber(tell)){
            resetGetCode(true,tell,user_type);
        }else{
            ViewTool.showToastShort(mActivity,"支持：13，14，15，17，18，19 .手机号段");
        }

    }

    private String first_password;
    @OnClick(R.id.forget_commit_btn)
    void setBtn(){
        String e_code=edit_code.getText().toString().trim();
        if (StringJudge.isEmpty(e_code)){
            ViewTool.showToastShort(mActivity,R.string.please_edit_code);
            return;
        }
        if (!code.equals(e_code)){
            ViewTool.showToastShort(mActivity,"验证码输入错误！");
            return;
        }

        first_password = edit_new_pass.getText().toString().trim();
        String alter_password=alter_edit_pass.getText().toString().trim();
        if (StringJudge.isEmpty(first_password)){
            ViewTool.showToastShort(mActivity,R.string.please_edit_password);
            return;
        }
        if(!RegexUtils.isCharAndNumbar(first_password)){
            ViewTool.showToastShort(mActivity,"密码必须是六位以上且包含字母、数字");
            return;
        }

        if (StringJudge.isEmpty(alter_password)){
            ViewTool.showToastShort(mActivity,"请再次输入新密码");
            return;
        }

        if (first_password.equals(alter_password)){
            resetPassWord();
        }else{
            ViewTool.showToastShort(mActivity,"新密码输入不一致");
        }
    }

    /**
     * 倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (send_code==null)return;
            send_code.setClickable(true);
            send_code.setBackgroundResource(R.drawable.send_code_reg);
            send_code.setTextColor(ColorRgbUtil.getBaseColor());
            send_code.setText("重新发送");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (send_code==null)return;
            send_code.setBackgroundResource(R.drawable.radius4_graybg_grayline);
            send_code.setTextColor(ColorRgbUtil.getBaseColor());
            send_code.setClickable(false);
            send_code.setText(StringUtils.getTextJoint("(%1$d)重试", millisUntilFinished / 1000 ));
        }
    }








    /**
     * ----------------------------retrofit-----------------------
     */

    public void resetGetCode(boolean is, String phone_num_s,String user_type)  {
        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        ResetPasswordGetCodeReq req = new ResetPasswordGetCodeReq();
        //获取参数
        req.setPhone(phone_num_s);
        req.setUsertype(user_type);
        reqBody.resetPasswordGetCodeReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_reset_password_get_code(reqEnv);
        call.enqueue(this);
        Logger.e(reqEnv.toString());
        if (is) ViewTool.showProgressDialog(mActivity,"");

    }


    private void resetPassWord() {
        //登陆时传的Constants.APP_ID：

        if (StringJudge.isEmpty(user_id)){
            ViewTool.showToastShort(mActivity,"请重新获取验证码");
            return;
        }

        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserResetPasswordReq req = new UserResetPasswordReq();
        //获取参数
        req.setUserinfo(AES.decrypTToString(user_type+"_"+user_id+"_"+first_password));
        reqBody.userResetPasswordReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_reset_password(reqEnv);
        call.enqueue(this);
        Logger.e(reqEnv.toString());
        ViewTool.showProgressDialog(mActivity,"");
    }



    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {

        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.resetPasswordGetCodeRes !=null){
                String result=b.resetPasswordGetCodeRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals(TagFinal.TRUE)){
                    mycount.start();
                    code=res.getVerification_Code();
                    user_id=res.getUserid();
                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
            }
            if (b.userResetPasswordRes !=null){
                String result=b.userResetPasswordRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                UserRes res= gson.fromJson(result,UserRes.class);
                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                    ViewTool.showToastShort(mActivity,R.string.success_do);
                    finish();
                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
            }
        }else{
            Logger.e(StringUtils.getTextJoint("%1$s:%2$d",name,response.code()));
            ViewTool.showToastShort(mActivity,StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (isActivity())return;
        ViewTool.dismissProgressDialog();
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);

    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

}
