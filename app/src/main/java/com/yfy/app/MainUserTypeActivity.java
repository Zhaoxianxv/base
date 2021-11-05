package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.PEquality.work.PETeaWorkMainActivity;
import com.yfy.app.album.AlbumMainActivity;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.app.duty_evaluate.DutyEvaluateTeaHonorMainActivity;
import com.yfy.app.login.LoginActivity;
import com.yfy.app.lottery.LotteryMainActivity;
import com.yfy.app.view.ViewTypeSelectActivity;
import com.yfy.app.voice.VoiceMainActivity;
import com.yfy.base.App;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.upload.UploadDataService;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NonConstantResourceId")
public class MainUserTypeActivity extends BaseActivity {
    private static final String TAG = MainUserTypeActivity.class.getSimpleName();

    public MainUserTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        Logger.e(TAG);

        initProtocolDialog();
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
        adapter=new MainUserTypeAdapter(mActivity);
        recyclerView.setAdapter(adapter);

        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent,String type) {


                switch (type){
                    case "PETeaWorkMainActivity":
                        intent.setClass(mActivity, PETeaWorkMainActivity.class);
                        break;
                    case "AlbumMainActivity":
                        PermissionTools.tryWRPerm(mActivity);
                        return;
                    case "LoginActivity":
                        intent.setClass(mActivity, LoginActivity.class);
                        break;
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

//        keyValue_adapter.add(new KeyValue("p e 作业","PETeaWorkMainActivity"));
//        keyValue_adapter.add(new KeyValue("voice","VoiceMainActivity"));
//        keyValue_adapter.add(new KeyValue("ModeType","SelectedModeTypeActivity"));
//        keyValue_adapter.add(new KeyValue("彩","LotteryMainActivity"));
//        keyValue_adapter.add(new KeyValue("View","ViewTypeSelectActivity"));
//        keyValue_adapter.add(new KeyValue("login","LoginActivity"));
        keyValue_adapter.add(new KeyValue("album","AlbumMainActivity"));



        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }


    /**
     * ---------------------protocol
     */
    public void initProtocolDialog(){
//        UserPreferences.getInstance().saveFirstTimeOpen(false);

        if (UserPreferences.getInstance().getIsFirstTimeOpen()){
            Intent intent=new Intent();
            intent.setClass(mActivity,ProtocolDialogActivity.class);
            startActivity(intent);
        }else{
            if (App.isServiceRunning(mActivity, UploadDataService.class.getSimpleName())){
                Logger.e(TagFinal.ZXX, "UploadDataService: " );
            }else{
                startService(new Intent(App.getApp().getApplicationContext(),UploadDataService.class));//开启更新
            }
        }
    }









    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    private void photoAlbum() {
        Intent intent = new Intent();
        intent.setClass(mActivity, AlbumMainActivity.class);
        intent.putExtra(TagFinal.ALBUM_SINGLE,false);
        intent.putExtra(TagFinal.ALBUM_LIST_INDEX,-1);
        startActivity(intent);
    }

    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    private void showTip1() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

}
