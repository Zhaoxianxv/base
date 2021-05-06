package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.App;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.upload.UploadDataService;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NonConstantResourceId")
public class SelectedUserTypeActivity extends BaseActivity {
    private static final String TAG = SelectedUserTypeActivity.class.getSimpleName();

    public SelectedUserTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);



        if (App.isServiceRunning(mActivity, UploadDataService.class.getSimpleName())){
            Logger.e(TagFinal.ZXX, "UploadDataService: " );
        }else{
            startService(new Intent(App.getApp().getApplicationContext(),UploadDataService.class));//开启更新
        }

        initSQToolbar();
        initRecycler();
        setAdapterData();
        initDialog();
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

        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent) {
                startActivity(intent);
            }
        });


    }

    private ConfirmContentWindow confirmContentWindow;
    private void initDialog(){

        confirmContentWindow = new ConfirmContentWindow(mActivity);
        confirmContentWindow.setPopClickListener(new NoFastClickListener() {
            @Override
            public void popClick(View view) {
                switch (view.getId()){
                    case R.id.pop_dialog_cancel:
                        confirmContentWindow.dismiss();
                        Logger.e("");
                        break;
                    case R.id.pop_dialog_ok:
                        confirmContentWindow.dismiss();
                        break;
                }
            }
        });
    }

    public void setContentShowDialog(String title,String content,String ok,String cancel){
        if (confirmContentWindow==null)return;
        confirmContentWindow.setTitle(title,content,ok,cancel);
        confirmContentWindow.showAtCenter();
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
        KeyValue three=new KeyValue(TagFinal.TYPE_ITEM);
        three.setTitle("更新QQMail图片");
        three.setType(Base.type);
//        keyValue_adapter.add(three);
        KeyValue honor=new KeyValue(TagFinal.TYPE_ITEM);
        honor.setTitle("honor");
        honor.setType("honor");
        keyValue_adapter.add(honor);

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




}
