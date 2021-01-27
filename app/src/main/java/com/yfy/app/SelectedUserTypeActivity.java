package com.yfy.app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.app.login.bean.UserRes;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.login.UserLoginReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.rsa.AES;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.User;
import com.yfy.greendao.tool.GreenDaoManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SelectedUserTypeActivity extends BaseActivity {
    private static final String TAG = SelectedUserTypeActivity.class.getSimpleName();

    public SelectedUserTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        initSQToolbar();
        initRecycler();
        setAdapterData();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("选择用户类型");


    }

    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                ColorRgbUtil.getGainsboro()));
        adapter=new SelectedUserTypeAdapter(mActivity);
        recyclerView.setAdapter(adapter);

        adapter.setModeItem(new SelectedUserTypeAdapter.UserTypeItem() {
            @Override
            public void modeItem(KeyValue bean) {
//                switch (bean.getType()) {
//                    case Base.USER_TYPE_TEA:
//                        Intent intent=new Intent(mActivity,PETeaMainActivity.class);
//                        intent.putExtra(Base.title,"体育素质评价");
//                        startActivity(intent);
//                        break;
//                    case Base.USER_TYPE_STU:
//                        startActivity(new Intent(mActivity,PEQualityMainTestActivity.class));
//                        break;
//                }
                switch (bean.getType()) {

                    case Base.USER_TYPE_STU:
                        edit_name="测试";
                        edit_password="123yfy";
                        role_id="1";
                        break;
                    case Base.USER_TYPE_TEA:
                        edit_name="yfy";
                        edit_password="yfy123";
                        role_id="2";
                        break;
                }
                ViewTool.showProgressDialog(mActivity,"");
                getToken("login");
            }
        });


    }






    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
        one.setTitle("学生");
        one.setType(Base.USER_TYPE_STU);
        keyValue_adapter.add(one);




        KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
        two.setTitle("教师");
        two.setType(Base.USER_TYPE_TEA);
        keyValue_adapter.add(two);

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    /**
     * ----------------------------retrofit-----------------------
     */
    private String edit_name,edit_password,role_id;
    private void login_(String token) {
        //登陆时传的Constants.APP_ID：
//		String apikey=JPushInterface.getRegistrationID(mActivity);

        String user_name,password_name;
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);

        user_name=AES.decrypTToString(edit_name);
        password_name=AES.decrypTToString(edit_password);

        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserLoginReq req = new UserLoginReq();
        //获取参数
        req.setUsername(user_name);
        req.setPassword(password_name);
        req.setRole_id(role_id);
        req.setAppid(ANDROID_ID);
        req.setFirsttoken(token);

        reqBody.userLoginReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_login(reqEnv);
        call.enqueue(this);
        Logger.e(reqEnv.toString());

    }
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userLoginRes !=null){
                ViewTool.dismissProgressDialog();
                String result=b.userLoginRes.result;
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                UserRes res= gson.fromJson(result,UserRes.class);

                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)) {
                    ViewTool.dismissProgressDialog();
                    saveUser(res,TagFinal.FALSE);
                } else {

                    toastShow(res.getError_code());

                }
            }
            if (b.baseGetTokenRes !=null){
                String result=b.baseGetTokenRes.result;
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result,BaseRes.class);
                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                    if (token.equalsIgnoreCase("login")){
                        login_(res.getTooken());
                    }
                }else{
                    toastShow(res.getError_code());
                }
            }
        }else{
            ViewTool.dismissProgressDialog();
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
        ViewTool.dismissProgressDialog();
        Logger.e("onFailure  :"+call.request().headers().toString());
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




    private void saveUser(UserRes res, String login_type){
//        ViewTool.showToastShort(mActivity,"登录成功");
        GreenDaoManager.getInstance().clearUser();
        User user=new User();
        user.setSession_key(res.getSession_key());
        user.setHeadPic(res.getHeadPic());
        user.setFxid(res.getFxid());
        user.setName(res.getName());
        user.setPwd(edit_password);
        user.setUsertype(role_id.equals("1")? Base.USER_TYPE_STU : Base.USER_TYPE_TEA);
        user.setIdU(res.getId());
        user.setClassid(res.getClassid());
        user.setUsername(res.getUsername());
        user.setRightlist(res.getRightlist());
        user.setIsDuplication(login_type);
        Base.user=user;
        UserPreferences.getInstance().saveUserID(user.getIdU());
        GreenDaoManager.getInstance().saveUser(user);

        //--------------体育素质----------
//        switch (user.getUsertype()) {
//            case Base.USER_TYPE_TEA:
//                Intent intent=new Intent(mActivity,PETeaMainActivity.class);
//                intent.putExtra(Base.title,"体育素质评价");
//                startActivity(intent);
//                break;
//            case Base.USER_TYPE_STU:
//                startActivity(new Intent(mActivity,PEQualityMainTestActivity.class));
//                break;
//        }
        //--------------德育评价----------
        Intent intent;
        switch (user.getUsertype()) {
            case Base.USER_TYPE_STU:
                intent=new Intent(mActivity,DutyEvaluateStuMainActivity.class);

                startActivity(intent);
                break;
            case Base.USER_TYPE_TEA:
                intent=new Intent(mActivity,SelectedClassActivity.class);
                intent.putExtra(Base.mode_type,"duty_evaluate");
                startActivity(intent);
                break;
        }
        finish();
    }


}
