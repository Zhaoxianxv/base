package com.yfy.app.PEquality.tea;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.yfy.app.PEquality.adapter.PETeaStandardListAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PETeaStandardListActivity extends BaseActivity {
    private static final String TAG = PETeaStandardListActivity.class.getSimpleName();

    private PETeaStandardListAdapter adapter_attitude;



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
        toolbar.addMenuText(TagFinal.ONE_INT,"添加");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position){

                Intent intent=new Intent(mActivity,PETeaAddScoreActivity.class);
                intent.putExtra(Base.title,title);
                intent.putExtra(Base.type,type);
                startActivity(intent);



            }
        });


    }
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter_attitude=new PETeaStandardListAdapter(mActivity);
        recyclerView.setAdapter(adapter_attitude);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue one=new KeyValue("","",TagFinal.TYPE_ITEM);
        one.setTitle(title);
        one.setRight("赵一");
        one.setValue("18.24310926");

        KeyValue three=new KeyValue("","",TagFinal.TYPE_ITEM);
        three.setTitle(title);
        three.setRight("李三");
        three.setValue("13.3");

        KeyValue two=new KeyValue("","",TagFinal.TYPE_ITEM);
        two.setTitle(title);
        two.setRight("王八");
        two.setValue("124");
        KeyValue four=new KeyValue("","",TagFinal.TYPE_ITEM);
        four.setTitle(title);
        four.setRight("钱二");
        four.setValue("");

        keyValue_adapter.add(one);
        keyValue_adapter.add(three);
        keyValue_adapter.add(two);
        keyValue_adapter.add(four);
        keyValue_adapter.add(two);

        adapter_attitude.setDataList(keyValue_adapter);
        adapter_attitude.setLoadState(TagFinal.LOADING_END);
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
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e("");
                }else{
                    ViewTool.showToastShort(mActivity,"error");
                }
            }

        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            ViewTool.showToastShort(mActivity,StringUtils.stringToGetTextJoint("数据错误:%1$d",response.code()));
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
