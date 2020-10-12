package com.yfy.app.PEquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfy.app.PEquality.adapter.PEHonorMainAdapter;
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
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PEHonorMainActivity extends BaseActivity {
    private static final String TAG = PEHonorMainActivity.class.getSimpleName();

    private PEHonorMainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        initSQToolbar();
        setAdapterData();
    }


    private String title,type;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        if (type.equalsIgnoreCase(TagFinal.TRUE))return;
        toolbar.addMenuText(TagFinal.ONE_INT,"添加");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent =new Intent(mActivity,PEHonorAddActivity.class);
                startActivityForResult(intent,TagFinal.UI_ADD);
            }
        });

    }
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new PEHonorMainAdapter(this);
        recyclerView.setAdapter(adapter);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue three=new KeyValue(TagFinal.TYPE_ITEM);
        KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
        KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);



        one.setContent("800米长跑");
        two.setContent("100米短跑");
        three.setContent("单人乒乓球");

        one.setLeft_title("2020.5.21");
        two.setLeft_title("2020.5.21");
        three.setLeft_title("2020.5.21");


        one.setRight_value("20\t分");
        two.setRight_value("0\t分");
        three.setRight_value("0\t分");

        one.setRight("已通过");
        two.setRight("已拒绝");
        three.setRight("待审核");

        one.setTitle("国家级比赛");
        two.setTitle("校级比赛");
        three.setTitle("校级比赛");

        one.setType(type);
        two.setType(type);
        three.setType(type);


        KeyValue all=new KeyValue(TagFinal.TYPE_FLOW_TITLE);
        all.setTitle("");
        List<String> list=StringUtils.listToStringSplitCharacters("校级获奖:3次，区级获奖:2次，市级获奖:1次。,总共得分:",",");
        List<CPWBean> cps=new ArrayList<>();
        int i=0;
        for (String s:list){
            CPWBean bean=new CPWBean();
            bean.setName(s);
            if (i==0){
                bean.setValue("");
            }else{
                bean.setValue("110分");
            }

            bean.setType("");
            cps.add(bean);
            i++;
        }
        all.setCpwBeanArrayList(cps);

        keyValue_adapter.add(all);
        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(three);


        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    setAdapterData();
                    break;
            }
        }
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
