package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.SelectedClassAdapter;
import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.BaseClass;
import com.yfy.app.bean.BaseGrade;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.bean.TermBean;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetClassListReq;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
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

    public SelectedClassAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        getClassList();
        getTerm();
    }

    public String class_id;
    public KeyValue classBean;
    private void getData(){
        classBean=getIntent().getParcelableExtra(Base.class_bean);
        class_id=classBean.getValue();
        initSQToolbar(classBean.getTitle());
    }


    public TermBean select_term;
    private void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);


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
        adapter=new SelectedClassAdapter(this);
        recyclerView.setAdapter(adapter);

    }


    /**
     * ----------------------------retrofit-----------------------
     */

    private  List<KeyValue> adapter_data_list=new ArrayList<>();
    public void getClassList() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetClassListReq req = new UserGetClassListReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());
        reqBody.userGetClassListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_class_list_api(env);
        call.enqueue(this);
    }
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());

        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_term_list_api(env);
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
            if (b.userGetClassListRes !=null){
                String result=b.userGetClassListRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    adapter_data_list.clear();
                    List<BaseGrade> list=res.getGradelist();
                    for (BaseGrade s:list){
                        for (BaseClass bean:s.getClasslist()){
                            KeyValue keyValue=new KeyValue(StringUtils.stringToGetTextJoint("%1$s-%2$s",s.getGradename(),bean.getClassname()),bean.getClassid());
                            adapter_data_list.add(keyValue);
                        }
                    }
                    adapter.setDataList(adapter_data_list);
                    adapter.setLoadState(TagFinal.LOADING_END);
                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
            }
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    for (TermBean bean:res.getTerm()){
                        if (bean.getIsnow().equalsIgnoreCase("1")){
                            UserPreferences.getInstance().saveTermId(bean.getId());
                            UserPreferences.getInstance().saveTermName(bean.getName());
                            select_term.setId(bean.getId());
                            select_term.setName(bean.getName());
                        }
                    }
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
