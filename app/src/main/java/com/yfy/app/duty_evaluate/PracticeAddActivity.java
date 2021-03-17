package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.bean.DateBean;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.greendao.bean.TermBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.camera.CameraActivity;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.dialog.ConfirmDateWindow;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class PracticeAddActivity extends BaseActivity {
    private static final String TAG = PracticeAddActivity.class.getSimpleName();


    @BindView(R.id.p_e_honor_add_choose_date)
    AppCompatTextView select_date_tv;
    @BindView(R.id.p_e_honor_add_choose_course)
    AppCompatTextView select_rank_tv;
    @BindView(R.id.public_type_txt_edit_value)
    EditText honor_name_et;
    @BindView(R.id.practice_add_icon)
    AppCompatImageView honnor_icon_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_practice_add);
        selected_date_bean =new DateBean();
        selected_date_bean.setValue_long(System.currentTimeMillis(),true);
        select_date_tv.setText(selected_date_bean.getName());


        Logger.e(TAG);
        initSQToolbar();

        initDateDialog();
        initDialogList();
        initAlbumDialog();
    }




    //一年级2班
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("上传奖状");
        toolbar.addMenuText(TagFinal.ONE_INT,"提交");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {

                saveHonor();
            }
        });

    }


    private ConfirmDateWindow date_dialog;
    private void initDateDialog(){
        date_dialog = new ConfirmDateWindow(mActivity);
        date_dialog.setOnPopClickListenner(new ConfirmDateWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.set:
                        selected_date_bean.setName(date_dialog.getTimeName());
                        selected_date_bean.setValue(date_dialog.getTimeValue());
                        select_date_tv.setText(selected_date_bean.getName());
                        select_date_tv.setTextColor(ColorRgbUtil.getBaseText());
                        date_dialog.dismiss();
                        break;
                    case R.id.cancel:
                        date_dialog.dismiss();
                        break;
                }

            }
        });
    }




    private CPWListBeanView cpwListBeanView;
    List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            cpwBeans.add(new CPWBean("学校运动会",""));
            cpwBeans.add(new CPWBean("校区联赛",""));
            cpwBeans.add(new CPWBean("县级比赛",""));
            cpwBeans.add(new CPWBean("市级比赛",""));
            cpwBeans.add(new CPWBean("省级比赛",""));
            cpwBeans.add(new CPWBean("国家级比赛",""));
        }
        cpwListBeanView.setDatas(cpwBeans);

    }


    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void onClick(CPWBean cpwBean,String type) {
                selected_rank=cpwBean.getName();
                select_rank_tv.setText(cpwBean.getName());
                select_rank_tv.setTextColor(ColorRgbUtil.getBaseText());
                cpwListBeanView.dismiss();
            }
        });
    }


    @OnClick(R.id.p_e_honor_add_choose_course)
    void setCourse(){
        closeKeyWord();
        setCPWlListBeanData();
        cpwListBeanView.showAtCenter();
    }
    @OnClick(R.id.p_e_honor_add_choose_date)
    void setDate(){
        closeKeyWord();
        date_dialog.showAtBottom();

    }

    @OnClick(R.id.practice_add_icon)
    void setAddPic(){
        closeKeyWord();
        album_select.showAtBottom();

    }






    public TermBean selected_term_bean;
    public DateBean selected_date_bean;
    public String selected_rank;
    public String honor_name_s;
    public String image_path;


    private void saveHonor(){


        if (StringJudge.isEmpty(selected_rank)){
            ViewTool.showToastShort(mActivity,"未选择获奖等级");
            return;
        }
        honor_name_s=honor_name_et.getText().toString().trim();
        if (StringJudge.isEmpty(honor_name_s)){
            ViewTool.showToastShort(mActivity,"获奖项目未填写！");
            return;
        }
        if (StringJudge.isEmpty(image_path)){
            ViewTool.showToastShort(mActivity,"电子奖状未上传！");
            return;
        }



        KeyValueDb keyValue = new KeyValueDb();
        keyValue.setFile_path(image_path);
        keyValue.setName(honor_name_s);
        NormalDataSaveTools.getInstance().saveHonor(keyValue);
    }









    ConfirmAlbumWindow album_select;
    private void initAlbumDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setTwo_select(mActivity.getResources().getString(R.string.album));
        album_select.setOne_select(mActivity.getResources().getString(R.string.take_photo));
        album_select.setName(mActivity.getResources().getString(R.string.upload_type));
        album_select.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {

                switch (view.getId()) {
                    case R.id.popu_select_one:
                        PermissionTools.tryCameraPerm(mActivity);
                        break;
                    case R.id.popu_select_two:
                        PermissionTools.tryWRPerm(mActivity);
                        break;
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:
                    String img=data.getStringExtra(CameraActivity.KEY_IMAGE_PATH);
                    Logger.e(img);
                    GlideTools.chanMult(mActivity,img,honnor_icon_iv,R.drawable.ic_add_black_24dp);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;


            }
        }
    }




    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    public void takePhoto() {
        CameraActivity.startMe(mActivity,TagFinal.CAMERA, CameraActivity.MongolianLayerType.HK_MACAO_TAIWAN_PASSES_NEGATIVE);
    }
    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    public void photoAlbum() {
        Intent intent;
        intent = new Intent(mActivity, AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, false);
        intent.putExtras(b);
        startActivityForResult(intent,TagFinal.PHOTO_ALBUM);
    }
    @PermissionFail(requestCode = TagFinal.CAMERA)
    public void showCamere() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_camera, Toast.LENGTH_SHORT).show();
    }
    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    public void showTip1() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
