package com.yfy.app.PEquality.tea;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.yfy.app.PEquality.adapter.PEAttendListAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PEAttendListActivity extends BaseActivity {
    private static final String TAG = PEAttendListActivity.class.getSimpleName();

    private PEAttendListAdapter adapter;


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
        if (StringJudge.isEmpty(type))return;
        if (type.equalsIgnoreCase(TagFinal.FALSE))return;
        toolbar.addMenuText(TagFinal.ONE_INT,"新增");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {

                Intent intent=new Intent(mActivity,PEQualityTeaSuggestActivity.class);
                intent.putExtra(Base.title,"新增请假");
                intent.putExtra(Base.type,title);
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

        adapter=new PEAttendListAdapter(mActivity);
        recyclerView.setAdapter(adapter);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();

        String list_content="感冒,走亲访友,发烧";
        List<String> list=StringUtils.listToStringSplitCharacters(list_content,",");

        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setContent(s);
            one.setTitle("2020.5.21");
            one.setLeft_title("下午第二节课");
            one.setRight("记录人：张丹");
            one.setRes_image(R.mipmap.home_header_1);
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
                    Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    ViewTool.showToastShort(mActivity,"error");
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
