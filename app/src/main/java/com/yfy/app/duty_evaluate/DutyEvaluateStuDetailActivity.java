package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.view.SQToolBar;

public class DutyEvaluateStuDetailActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateStuDetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_stu_detail);
        Logger.e(TAG);
        getData();
    }
    public KeyValue stu_bean;
    private void getData(){
        stu_bean=getIntent().getParcelableExtra(Base.stu_bean);
        initSQToolbar(stu_bean.getName());
    }
    public void initSQToolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);
        if (Base.user.getUsertype().equalsIgnoreCase(Base.USER_TYPE_STU)){
            toolbar.addMenuText(TagFinal.ONE_INT,"添加");
            toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent=new Intent(mActivity,DutyEvaluateStuMainActivity.class);
                    startActivityForResult(intent,TagFinal.UI_ADD);
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    break;

            }
        }
    }
}
