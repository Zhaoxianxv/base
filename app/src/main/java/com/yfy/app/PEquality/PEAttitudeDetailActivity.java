package com.yfy.app.PEquality;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.PEquality.adapter.PEQualityAttitudeDetailAdapter;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;


public class PEAttitudeDetailActivity extends BaseActivity {
    private static final String TAG = PEAttitudeDetailActivity.class.getSimpleName();

    private PEQualityAttitudeDetailAdapter adapter;


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
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));
        adapter=new PEQualityAttitudeDetailAdapter(mActivity);
        recyclerView.setAdapter(adapter);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();



        keyValue_adapter.add(new KeyValue("扣分:","-6","",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("原因:","test","",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("时间:","2020.2.25","",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("节数:","下午·第一节","",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("记录人:","张三","",TagFinal.TYPE_ITEM));

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    /**
     * ----------------------------retrofit-----------------------
     */

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
