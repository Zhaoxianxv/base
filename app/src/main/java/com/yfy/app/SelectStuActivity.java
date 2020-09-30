package com.yfy.app;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectStuActivity extends BaseActivity implements Callback<ResEnv> {

    private static final String TAG = SelectStuActivity.class.getSimpleName();


    private SelectStuAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        setAdapterData();
    }
    public String title,type;
    public int index;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
        index=getIntent().getIntExtra(Base.index,0);
        initSQtoolbar(title);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initSQtoolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.setOnNaviClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,4, LinearLayoutManager.VERTICAL,false);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return select_stu.get(position).getSpan_size();
//            }
//        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new SelectStuAdapter(mActivity);
        recyclerView.setAdapter(adapter);


        adapter.setIndex(index);

    }



    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        keyValue_adapter.clear();
        List<String> list=StringUtils.listToStringSplitCharacters("张三,李四,王五",",");
        for (String s:list){
            KeyValue one=new KeyValue(s,Base.user.getHeadPic(),TagFinal.TYPE_ITEM);
            one.setType(type);
            switch (type){
                case "运动技能":
                    one.setRight_value("总分：88");
                    break;
                case "请假":
                    one.setRight_value("请假次数：8");
                    break;
                case "课后作业":
                    one.setRight_value("总分：88");
                    break;
                case "体能":
                    one.setRight_value("总分：88");
                    break;
                case "国家体质标准":
                    one.setRight_value("总分：88");
                    break;
                case "学习态度采集":
                    one.setRight_value("总分：88");
                    break;
                case "膳食建议":
                    one.setRight_value("膳食建议:无");
                    break;
                case "运动处方":
                    one.setRight_value("运动处方:无");
                    break;
                case "课堂表现":
                    one.setRight_value("得分：88");
                    break;
                case "体育比赛成绩":
                    one.setRight_value("体育比赛成绩:5条");
                    break;
                case "体育荣誉证书":
                    one.setRight_value("体育荣誉:5次");
                    break;
            }
            keyValue_adapter.add(one);
        }

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }

    /**
     * ----------------------------retrofit-----------------------
     */




    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);

        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;

            if (b.checkGetStuRes!=null) {
                String result = b.checkGetStuRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));

            }
            if (b.checkSubimtYesRes!=null){
                String result = b.checkSubimtYesRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));

            }
        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e(TagFinal.ZXX, "onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }
    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  "+call.request().headers().toString() );
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
