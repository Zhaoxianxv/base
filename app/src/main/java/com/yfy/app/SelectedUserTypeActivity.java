package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.app.duty_evaluate.DutyEvaluateTeaHonorMainActivity;
import com.yfy.app.lottery.LotteryMainActivity;
import com.yfy.app.view.ViewTypeSelectActivity;
import com.yfy.app.voice.VoiceMainActivity;
import com.yfy.base.App;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.greendao.tool.NormalDataSaveTools;
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
        Logger.e(TAG);
        if (App.isServiceRunning(mActivity, UploadDataService.class.getSimpleName())){
            Logger.e(TagFinal.ZXX, "UploadDataService: " );
        }else{
            startService(new Intent(App.getApp().getApplicationContext(),UploadDataService.class));//开启更新
        }

        initSQToolbar();
        initRecycler();
        setAdapterData();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("选择用户类型");



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
            public void startIntentActivity(Intent intent,String type) {


                switch (type){
                    case "VoiceMainActivity":
                        intent.setClass(mActivity, VoiceMainActivity.class);
                        break;
                    case "LotteryMainActivity":
                        intent.setClass(mActivity, LotteryMainActivity.class);
                        break;
                    case "SelectedModeTypeActivity":
                        intent.setClass(mActivity, SelectedModeTypeActivity.class);
                        break;
                    case "ViewTypeSelectActivity":
                        intent.setClass(mActivity, ViewTypeSelectActivity.class);
                        break;
                    case Base.USER_TYPE_STU:
                        intent.setClass(mActivity, DutyEvaluateStuMainActivity.class);
                        break;
                    case Base.USER_TYPE_TEA:
                        intent.setClass(mActivity,SelectedClassActivity.class);
                        intent.putExtra(Base.mode_type,"duty_evaluate");
                        break;
                    case "honor":
                        intent.setClass(mActivity, DutyEvaluateTeaHonorMainActivity.class);
                        intent.putExtra(Base.title,"荣誉比赛");
                        intent.putExtra(Base.term_bean, NormalDataSaveTools.getInstance().getTermBeanToGreenDao());//
                        break;
                    case Base.type:
                        FileCamera.updateFileFromDatabase(mActivity);
                        return;

                }

                startActivity(intent);

            }
        });


    }






    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        keyValue_adapter.clear();

        keyValue_adapter.add(new KeyValue("voice","VoiceMainActivity"));
        keyValue_adapter.add(new KeyValue("ModeType","SelectedModeTypeActivity"));
        keyValue_adapter.add(new KeyValue("彩","LotteryMainActivity"));
        keyValue_adapter.add(new KeyValue("View","ViewTypeSelectActivity"));



        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




}
