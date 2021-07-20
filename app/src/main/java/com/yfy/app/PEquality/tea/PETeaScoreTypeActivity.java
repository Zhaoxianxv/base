package com.yfy.app.PEquality.tea;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.PEquality.adapter.PETeaMainAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PETeaScoreTypeActivity extends BaseActivity {
    private static final String TAG = PETeaScoreTypeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();

        initSQToolbar();
        initRecycler();
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

                Intent intent=new Intent(mActivity,PETeaStandardListActivity.class  );
                intent.putExtra(Base.title,bean.getTitle());
                intent.putExtra(Base.type,type);
                startActivity(intent);


            }
        });
    }



    private void setAdapterData(){
        keyValue_adapter.clear();


        int[] res_drawables=new int[]{
                R.mipmap.schedule,
                R.mipmap.attend_leave,
                R.mipmap.goods_seven,
                R.mipmap.goods_two,
                R.mipmap.deyu,
                R.mipmap.icon_score,
                R.mipmap.suggestion,
                R.mipmap.recipe};
        int i=0;
        List<String> list=StringUtils.listToStringSplitCharacters("体重指数,肺活量,50米,坐位体前屈,体能,田径,民传,新兴体育,球类,体操类,速度类,力量类,灵敏类,柔韧类,耐力类",",");
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setTitle(s);
            one.setRes_image(res_drawables[i%res_drawables.length]);
            keyValue_adapter.add(one);
            i++;
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
