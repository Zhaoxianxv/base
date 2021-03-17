package com.yfy.app;

import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.bean.KeyValue;
import com.yfy.greendao.bean.TermBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SelectedTermActivity extends BaseActivity {
    private static final String TAG = SelectedTermActivity.class.getSimpleName();

    private SelectedTermAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        initRecycler();
        initSQToolbar();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("选择学期");


    }





    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
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


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.Gray)));
        adapter=new SelectedTermAdapter(mActivity);
        recyclerView.setAdapter(adapter);


        setAdapterData();
    }




    public List<KeyValue> keyValue_adapter=new ArrayList<>();

    private void setAdapterData(){
        List<TermBean> class_list=new ArrayList<>();
        class_list.add(NormalDataSaveTools.getInstance().getTermBeanToGreenDao());


        adapter.setDataList(class_list);
        adapter.setLoadState(TagFinal.LOADING_END);
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



    /**
     * ----------------------------retrofit-----------------------
     */

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
