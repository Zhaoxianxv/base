package com.yfy.app.drawableBg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.yfy.app.SelectedModeTypeAdapter;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.drawableBg.widget.ButtomActivity;
import com.yfy.app.drawableBg.widget.EditTextActivity;
import com.yfy.app.drawableBg.widget.ImageViewActivity;
import com.yfy.app.drawableBg.widget.ListViewActivity;
import com.yfy.app.drawableBg.widget.OrthogonActivity;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class DrawableBgActivity extends BaseActivity {
    private SelectedModeTypeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        initRecycler();
        initSQToolbar();
        setAdapterData();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("Drawable");


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
        adapter=new SelectedModeTypeAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setModeItem(new SelectedModeTypeAdapter.ModeItem() {
            @Override
            public void modeItem(String name) {
                switch (name){
                    case "OrthogonActivity":
                        startActivity(new Intent(mActivity,OrthogonActivity.class));
                        break;
                    case "EditTextActivity":
                        startActivity(new Intent(mActivity,EditTextActivity.class));
                        break;
                    case "ButtomActivity":
                        startActivity(new Intent(mActivity,ButtomActivity.class));
                        break;
                    case "ImageViewActivity":
                        startActivity(new Intent(mActivity,ImageViewActivity.class));
                        break;
                    case "ListViewActivity":
                        startActivity(new Intent(mActivity,ListViewActivity.class));
                        break;
                    case "TableLayoutActivity":
                        startActivity(new Intent(mActivity,TableLayoutActivity.class));
                        break;
                    case "MaterialMainActivity":
                        startActivity(new Intent(mActivity,MaterialMainActivity.class));
                        break;
                    case "SingleActivity":
                        startActivity(new Intent(mActivity,SingleActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

    }
    private List<String> list=StringUtils.listToStringSplitCharacters("OrthogonActivity,EditTextActivity,ButtomActivity,ImageViewActivity,ListViewActivity",",");
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        keyValue_adapter.clear();
        list.add("TableLayoutActivity");
        list.add("MaterialMainActivity");
        list.add("SingleActivity");
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setTitle(s);
            keyValue_adapter.add(one);
        }

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



}
