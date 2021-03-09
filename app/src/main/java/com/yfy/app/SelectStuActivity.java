package com.yfy.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

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
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.greendao.bean.StuBean;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class SelectStuActivity extends BaseActivity {

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
        if (type.equalsIgnoreCase("select_stu")){
            toolbar.addMenuText(TagFinal.ONE_INT,"确定");
            toolbar.setOnMenuClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {

                    List<String> nameList=new ArrayList<>();
                    List<KeyValue> stuList=adapter.getDataList();
                    for (KeyValue stu:stuList){
                        if (stu.isIs_selected())nameList.add(stu.getName());
                    }
                    Intent intent=new Intent();
                    intent.putExtra(Base.data,StringUtils.stringToArraysGetString(nameList,","));
                    intent.putExtra(Base.index,index);
                    intent.putParcelableArrayListExtra("all", (ArrayList<? extends Parcelable>) stuList);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
    }

    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,4, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new SelectStuAdapter(mActivity);
        recyclerView.setAdapter(adapter);


        adapter.setIndex(index);

    }



    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        List<StuBean> stu_list= NormalDataSaveTools.getInstance().getStuBeanToGreenDao();
        if (StringJudge.isEmpty(stu_list)){
            stu_list=new ArrayList<>();
        }
        Logger.e(""+stu_list.size());
        keyValue_adapter.clear();
        for (StuBean s:stu_list){
            KeyValue one=new KeyValue(s.getStuname(),"",TagFinal.TYPE_ITEM);
            one.setId(s.getStuid());
            one.setType(type);
            one.setRight_value("");

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

//            if (b.checkGetStuRes!=null) {
//                String result = b.checkGetStuRes.result;
//                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
//
//            }

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
