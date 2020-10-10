package com.yfy.app.PEquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.yfy.app.PEquality.adapter.PEQualityAttitudeAdapter;
import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
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
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PEQualityAttitudeActivity extends BaseActivity {
    private static final String TAG = PEQualityAttitudeActivity.class.getSimpleName();

    private PEQualityAttitudeAdapter adapter;

    @Bind(R.id.public_recycler_del)
    Button del_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_del_view);
        getData();
        initRecycler();

        initView();
        setAdapterData();
    }


    private String title,type;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
        initSQToolbar();
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        if (type.equalsIgnoreCase(TagFinal.FALSE))return;
        toolbar.addMenuText(TagFinal.ONE_INT,"添加");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,PEQualityTeaSuggestActivity.class);
                intent.putExtra(Base.title,title);
                intent.putExtra(Base.type,title);
                startActivity(intent);
            }
        });

    }


    private void initView(){
        if (type.equalsIgnoreCase(TagFinal.TRUE)){
            del_button.setVisibility(View.GONE);
        }else{
            del_button.setText("请假记录");
        }

    }

    @OnClick(R.id.public_recycler_del)
    void setDel(){
        Intent intent=new Intent(mActivity,PEQualityAttenListActivity.class);
        intent.putExtra(Base.title,"请假记录");
        intent.putExtra(Base.type,TagFinal.FALSE);
        startActivity(intent);
    }
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
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
                getResources().getColor(R.color.gray)));
        adapter=new PEQualityAttitudeAdapter(mActivity);
        recyclerView.setAdapter(adapter);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue two=new KeyValue("","",TagFinal.TYPE_ITEM);
        KeyValue detail=new KeyValue("本期满分100分，已扣除12分当前88分","",TagFinal.TYPE_DETAIL);
        KeyValue one=new KeyValue("","",TagFinal.TYPE_ITEM);
        one.setTitle("2020.5.21");
        one.setLeft_title("学生1");
        one.setContent("旷课");
        one.setRight("张丹");

        two.setTitle("2020.5.21");
        two.setLeft_title("学生2");
        two.setContent("大课间体育活动违纪或缺席");
        two.setRight("张丹");

        if (type.equalsIgnoreCase(TagFinal.FALSE))
        keyValue_adapter.add(detail);
        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(one);



        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);


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
