package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateSchoolAdapter;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.greendao.bean.TermBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class DutyEvaluateSchoolActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateSchoolActivity.class.getSimpleName();

    @BindView(R.id.public_recycler_del)
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_del_view);
        Logger.e(TAG);
        getData();
        initView();
        initSQToolbar();
        initRecycler();

    }

    private String barTitle="";
    private void getData(){
        barTitle=getIntent().getStringExtra(Base.title);
        selected_termBean=getIntent().getParcelableExtra(Base.term_bean);
    }

    public TermBean selected_termBean;
    public TextView menu_one;
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(barTitle);
        menu_one =toolbar.addMenuText(TagFinal.ONE_INT,"");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                Intent intent=new Intent(mActivity,SelectedTermActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);
            }
        });
        if (StringJudge.isEmpty(selected_termBean.getId())){
            menu_one.setText("选择学期");
        }else{
            menu_one.setText(selected_termBean.getName());
        }

    }

    public void initView(){
        button.setTextColor(ColorRgbUtil.getResourceColor(mActivity,R.color.white));
        button.setText("添加");



    }

    public DutyEvaluateSchoolAdapter adapter;
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new DutyEvaluateSchoolAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        setAdapterData();
    }




    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue three=new KeyValue(TagFinal.TYPE_ITEM);
        KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
        KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);



        one.setContent("800米长跑");
        two.setContent("100米短跑");
        three.setContent("单人乒乓球");

        one.setLeft_title("2020.5.21");
        two.setLeft_title("2020.5.21");
        three.setLeft_title("2020.5.21");


        one.setRight_value("20\t分");
        two.setRight_value("0\t分");
        three.setRight_value("0\t分");

        one.setRight("已通过");
        two.setRight("已拒绝");
        three.setRight("待审核");

        one.setTitle("国家级比赛");
        two.setTitle("校级比赛");
        three.setTitle("校级比赛");



        KeyValue all=new KeyValue(TagFinal.TYPE_FLOW_TITLE);
        all.setTitle("");
        List<String> list= StringUtils.listToStringSplitCharacters("校级获奖:3次，区级获奖:2次，市级获奖:1次。,总共得分:",",");
        List<CPWBean> cps=new ArrayList<>();
        int i=0;
        for (String s:list){
            CPWBean bean=new CPWBean();
            bean.setName(s);
            if (i==0){
                bean.setValue("");
            }else{
                bean.setValue("110分");
            }

            bean.setType("");
            cps.add(bean);
            i++;
        }
        all.setCpwBeanArrayList(cps);



        keyValue_adapter.add(three);
        keyValue_adapter.add(one);
        keyValue_adapter.add(two);



        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @OnClick(R.id.public_recycler_del)
    void setAdd(){
        Intent intent=new Intent();
        intent.setClass(mActivity, DutyEvaluatePracticeAddActivity.class);
        startActivity(intent);
    }
}
