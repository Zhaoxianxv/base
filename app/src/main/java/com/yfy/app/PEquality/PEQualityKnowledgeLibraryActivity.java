package com.yfy.app.PEquality;

import android.graphics.Color;
import android.os.Bundle;


import com.yfy.app.PEquality.adapter.PEKnowledgeLibAdapter;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;



public class PEQualityKnowledgeLibraryActivity extends BaseActivity {
    private static final String TAG = PEQualityKnowledgeLibraryActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
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



    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public PEKnowledgeLibAdapter adapter;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        swipeRefreshLayout =  findViewById(R.id.public_swip);
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                closeSwipeRefresh();
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(TagFinal.LOADING);
                adapter.setLoadState(TagFinal.LOADING_END);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        xlist.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.Gray)));
        adapter=new PEKnowledgeLibAdapter(mActivity);
        recyclerView.setAdapter(adapter);

    }





    public void closeSwipeRefresh(){
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 200);
        }
    }



    public List<KeyValue> keyValue_adapter=new ArrayList<>();

    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue one=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);
        KeyValue two=new KeyValue("奥林匹克运动会","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);
        KeyValue three=new KeyValue("发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);
        KeyValue four=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);

        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);

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
