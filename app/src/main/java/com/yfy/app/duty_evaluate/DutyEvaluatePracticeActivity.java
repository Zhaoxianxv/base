package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.TermBean;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateRecodeAdapter;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.view.SQToolBar;

import butterknife.Bind;
import butterknife.OnClick;

public class DutyEvaluatePracticeActivity extends BaseActivity {
    private static final String TAG = DutyEvaluatePracticeActivity.class.getSimpleName();


    @Bind(R.id.public_recycler_del)
    Button bottom_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_del_view);
        Logger.e(TAG);
        getData();
        initSQToolbar();
        bottom_bt.setText("添加");
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
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mActivity,SelectedTermActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);
            }
        });
        if (StringJudge.isEmpty(selected_termBean.getId())){
            menu_one.setText("选择学期");
            bottom_bt.setVisibility(View.GONE);
        }else{
            menu_one.setText(selected_termBean.getName());
            if (selected_termBean.getIsnow().equalsIgnoreCase("1")){
                bottom_bt.setVisibility(View.VISIBLE);
            }else{
                bottom_bt.setVisibility(View.GONE);
            }
        }

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
//        adapter=new DutyEvaluateRecodeAdapter(mActivity);
//        recyclerView.setAdapter(adapter);


    }




    @OnClick(R.id.public_recycler_del)
    void setOnClick(){
        Intent intent=new Intent(mActivity,PracticeAddActivity.class);
        startActivityForResult(intent,TagFinal.UI_ADD);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_TAG:
                    assert data!=null;
                    selected_termBean=data.getParcelableExtra(Base.data);
                    menu_one.setText(selected_termBean.getName());
                    if (selected_termBean.getIsnow().equalsIgnoreCase("1")){
                        bottom_bt.setVisibility(View.VISIBLE);
                    }else{
                        bottom_bt.setVisibility(View.GONE);
                    }
                    break;
                case TagFinal.UI_ADD:

                        break;

            }
        }
    }


}
