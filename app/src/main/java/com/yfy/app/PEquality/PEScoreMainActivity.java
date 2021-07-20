package com.yfy.app.PEquality;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.PEquality.adapter.PEScoreMainAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class PEScoreMainActivity extends BaseActivity {
    private static final String TAG = PEScoreMainActivity.class.getSimpleName();

//    private PEScoreAlterMainAdapter adapter;
    private PEScoreMainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        initSQToolbar();
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
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter=new PEScoreAlterMainAdapter(mActivity);
        adapter=new PEScoreMainAdapter(mActivity);
        recyclerView.setAdapter(adapter);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();


        KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
        one.setTitle("学习态度");
        one.setRight_name("90");
        one.setRight_value("30%");
        one.setLeft_title("最终得分：27");


        KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
        two.setTitle("健康教育知识");
        two.setRight_name("90");
        two.setRight_value("20%");
        two.setLeft_title("最终得分：18");

        KeyValue three=new KeyValue(TagFinal.TYPE_ITEM);
        three.setTitle("运动技能");
        three.setRight_name("95");
        three.setRight_value("30%");
        three.setLeft_title("最终得分：28.5");


        KeyValue four=new KeyValue(TagFinal.TYPE_ITEM);
        four.setTitle("体育课后作业");
        four.setRight_name("100");
        four.setRight_value("10%");
        four.setLeft_title("最终得分：10");

        KeyValue f=new KeyValue(TagFinal.TYPE_ITEM);
        f.setTitle("体能");
        f.setRight_name("90");
        f.setRight_value("10%");
        f.setLeft_title("最终得分：9");



        KeyValue chart=new KeyValue(TagFinal.TYPE_SELECT_GROUP);
        chart.setTitle("各学期分数");
        keyValue_adapter.add(chart);


        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(three);
        keyValue_adapter.add(four);
        keyValue_adapter.add(f);



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
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
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
                    ViewTool.showToastShort(mActivity,"true");
                }else{
                    ViewTool.showToastShort(mActivity,"error");
                }
            }

        }else{
            try {
                assert response.errorBody() != null;
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
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
