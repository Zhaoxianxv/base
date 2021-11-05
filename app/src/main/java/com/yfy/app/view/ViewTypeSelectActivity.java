package com.yfy.app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.date_select.DateSelectMainActivity;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("NonConstantResourceId")
public class ViewTypeSelectActivity extends BaseActivity {
    private static final String TAG = ViewTypeSelectActivity.class.getSimpleName();

    public ViewTypeSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);

        initSQToolbar();
        initRecycler();
        setAdapterData();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("VIEW");


    }

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
                ColorRgbUtil.getGainsboro()));
        adapter=new ViewTypeSelectAdapter(mActivity);
        recyclerView.setAdapter(adapter);

        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent,String type) {


                switch (type){
                    case "date_select":
                        intent.setClass(mActivity, DateSelectMainActivity.class);

                        break;

                }

                startActivity(intent);

            }
        });


    }




    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue view_mode=new KeyValue(TagFinal.TYPE_ITEM);
        view_mode.setTitle("date_select");
        view_mode.setType("date_select");
        keyValue_adapter.add(view_mode);



        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




}
