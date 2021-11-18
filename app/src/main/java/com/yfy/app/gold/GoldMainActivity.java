package com.yfy.app.gold;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.SelectedTermAdapter;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.bean.TermBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GoldMainActivity extends BaseActivity {
    private static final String TAG = GoldMainActivity.class.getSimpleName();

    private GoldMainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        list_title= StringUtils.listToArrays(StringUtils.getResourceStringArray(mActivity,R.array.gold_type));
        select_gold_type=list_title.get(0);
        keyValue_adapter=NormalDataSaveTools.getInstance().getGoldToGreenDao(list_title.get(0));
        initDialogList();
        setCPWlListBeanData();
        initRecycler();
        initSQToolbar();

    }



    public List<String> list_title=new ArrayList<>();
    public String select_gold_type;
    public TextView tv_top_title;
    private void initSQToolbar() {
        assert toolbar!=null;
        tv_top_title=toolbar.setTitle(select_gold_type);
        toolbar.addMenuText(TagFinal.ONE_INT,"切换");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position) {

                cpwListBeanView.showAtCenter();
            }
        });


    }

    public CPWListBeanView cpwListBeanView;
    public List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            for(String s:list_title){
                CPWBean cpwBean =new CPWBean();
                cpwBean.setName(s);
                cpwBean.setId(s);
                cpwBeans.add(cpwBean);
            }
        }
        cpwListBeanView.setDatas(cpwBeans);
    }
    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastPopClick(CPWBean cpwBean, String type) {
                cpwListBeanView.dismiss();
                keyValue_adapter.clear();
                keyValue_adapter=NormalDataSaveTools.getInstance().getGoldToGreenDao(cpwBean.getId());
                adapter.setDataList(keyValue_adapter);
                adapter.setLoadState(TagFinal.LOADING_END);
                tv_top_title.setText(cpwBean.getId());
            }
        });
    }



    public List<KeyValueDb> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.Gray)));
        adapter=new GoldMainAdapter(mActivity);
        recyclerView.setAdapter(adapter);
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
