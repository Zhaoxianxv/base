package com.yfy.app.PEquality.tea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yfy.app.PEquality.adapter.PETeaMainAdapter;
import com.yfy.app.SelectedClassActivity;
import com.yfy.app.bean.BaseRes;
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
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class PETeaMainActivity extends BaseActivity {
    private static final String TAG = PETeaMainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();

        initSQToolbar();
        initRecycler();
        setAdapterData();


    }

    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }

    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
    }




    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public PETeaMainAdapter adapter;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new PETeaMainAdapter(mActivity);
        recyclerView.setAdapter(adapter);


        adapter.setItemOnc(new PETeaMainAdapter.ItemOnc() {
            @Override
            public void onc(KeyValue bean) {
                String title=bean.getTitle();
                switch (title){


                    case "运动技能":
                        break;
                    case "请假":
                        break;
                    case "课后作业":
                        break;
                    case "体能":
                        break;
                    case "国家体质标准":
                        break;
                    case "学习态度采集":
                        break;

                }
//                Intent intent=new Intent(mActivity,PEQualityTeaSuggestActivity.class);
//                intent.putExtra(Base.title,title);
//                intent.putExtra(Base.type,title);
//                startActivity(intent);
                Intent intent=new Intent(mActivity,SelectedClassActivity.class);
                intent.putExtra(Base.title,title);
                intent.putExtra(Base.type,bean.getTitle());
                startActivity(intent);
            }
        });
    }



    private void setAdapterData(){
        keyValue_adapter.clear();



        List<String> list=StringUtils.listToStringSplitCharacters("学习态度采集,请假,运动技能,体能,课后作业,国家体质标准,体育荣誉证书,体育比赛成绩,课堂表现,膳食建议,运动处方",",");
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setTitle(s);
            keyValue_adapter.add(one);
        }
        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }




    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        if (Base.user==null)return;
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        req.setSession_key(Base.user.getSession_key());
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
