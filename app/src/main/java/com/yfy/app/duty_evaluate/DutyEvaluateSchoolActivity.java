package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yfy.app.SelectedTermActivity;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.greendao.bean.TermBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.view.SQToolBar;

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


    @OnClick(R.id.public_recycler_del)
    void setAdd(){
        Intent intent=new Intent();
        intent.setClass(mActivity,PracticeAddActivity.class);
        startActivity(intent);
    }
}
