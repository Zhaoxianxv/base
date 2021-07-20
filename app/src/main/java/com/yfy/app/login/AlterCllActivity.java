package com.yfy.app.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserResetCallReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.RegexUtils;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterCllActivity extends BaseActivity implements Callback<ResEnv> {


    private static final String TAG = AlterCllActivity.class.getSimpleName();
    @BindView(R.id.call_edit_first)
    EditText first;
    @BindView(R.id.call_edit_again)
    EditText again;
    private String first_editor,again_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_call);
        initSQToolbar();
        if (StringJudge.isEmpty(UserPreferences.getInstance().getTell())){
        }else{
            first.setText(UserPreferences.getInstance().getTell());
        }
    }



    public void initSQToolbar(){
        assert toolbar!=null;
        TextView titlebar=toolbar.setTitle("联系电话");
        TextView menuOne=toolbar.addMenuText(1,R.string.ok);
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                closeKeyWord();
                isSend();

            }
        });
    }

    public void  isSend(){
        first_editor=first.getText().toString().trim();
        again_editor=again.getText().toString().trim();
        if (StringJudge.isEmpty(first_editor)){
            ViewTool.showToastShort(mActivity,"请输入电话号码");
            return ;
        }
        if (StringJudge.isEmpty(first_editor)){
            ViewTool.showToastShort(mActivity,"请再次输入电话号码");
            return ;
        }
        if (first_editor.equals(again_editor)){
            alterpass(first_editor);
        }else{
            ViewTool.showToastShort(mActivity,"确认号码是否一致！");
        }
    }





    /**
     *-----------------------------retrofit
     */

    private void alterpass(String no) {

        if (RegexUtils.isMobilePhoneNumber(no)){

        }else{
            ViewTool.showToastShort(mActivity,"支持：13，14，15，17，18，19 .手机号段");
            return;
        }

        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserResetCallReq req = new UserResetCallReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());
        req.setNo(no);
        reqBody.userResetCallReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_reset_call(reqEnv);
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
            if (b.userResetCallRes !=null){
                String result=b.userResetCallRes.result;
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
//                if (JsonParser.isSuccess(result)){
//                    ViewTool.showToastShort(mActivity,"联系号码设置成功！");
//                    setResult(RESULT_OK);
//                    onPageBack();
//                }
            }
        }else{
            Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d",name,response.code()));
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
