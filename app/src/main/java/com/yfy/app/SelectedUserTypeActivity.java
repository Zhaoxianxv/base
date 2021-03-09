package com.yfy.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.PEquality.PEQualityMainTestActivity;
import com.yfy.app.PEquality.tea.PETeaMainActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;

import java.util.ArrayList;
import java.util.List;


public class SelectedUserTypeActivity extends BaseActivity {
    private static final String TAG = SelectedUserTypeActivity.class.getSimpleName();

    public SelectedUserTypeAdapter adapter;

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
        toolbar.setTitle("选择用户类型");
        toolbar.addMenuText(TagFinal.ONE_INT,"zxx");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                startActivity(new Intent(SelectedUserTypeActivity.this, SelectedModeTypeActivity.class));
            }
        });


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
        adapter=new SelectedUserTypeAdapter(mActivity);
        recyclerView.setAdapter(adapter);

        adapter.setModeItem(new SelectedUserTypeAdapter.UserTypeItem() {
            @Override
            public void modeItem(KeyValue bean) {
                Intent intent;

//                --------------德育评价----------
                switch (bean.getType()) {
                    case Base.USER_TYPE_STU:
                        intent=new Intent(mActivity,DutyEvaluateStuMainActivity.class);
                        startActivity(intent);
                        break;
                    case Base.USER_TYPE_TEA:
                        intent=new Intent(mActivity,SelectedClassActivity.class);
                        intent.putExtra(Base.mode_type,"duty_evaluate");
                        startActivity(intent);
                        break;
                }

            }
        });


    }






    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        keyValue_adapter.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
        one.setTitle("学生");
        one.setType(Base.USER_TYPE_STU);
        keyValue_adapter.add(one);




        KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
        two.setTitle("教师");
        two.setType(Base.USER_TYPE_TEA);
        keyValue_adapter.add(two);

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




}
