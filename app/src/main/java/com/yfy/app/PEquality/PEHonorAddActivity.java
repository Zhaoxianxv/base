package com.yfy.app.PEquality;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.album.SingePicShowActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmDateWindow;
import com.yfy.view.SQToolBar;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PEHonorAddActivity extends BaseActivity {
    private static final String TAG = PEHonorAddActivity.class.getSimpleName();



    @BindView(R.id.p_e_honor_add_choose_date)
    AppCompatTextView choose_date;
    @BindView(R.id.p_e_honor_add_choose_course)
    AppCompatTextView choose_course;

    @BindView(R.id.public_add_multi)
    MultiPictureView add_multi;

    private DateBean dateBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_honor_add);
        dateBean=new DateBean();
        dateBean.setValue_long(System.currentTimeMillis(),true);
        initAlbumDialog();
        initAbsListView();
        getData();
        initView();
        initSQToolbar();
        initDateDialog();
        initDialogList();

    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"确定");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                finish();
            }
        });

    }

    private void initView(){
        choose_course.setText("未选择");
        choose_course.setTextColor(ColorRgbUtil.getGrayText());
        choose_date.setTextColor(ColorRgbUtil.getGrayText());
        choose_date.setText("未选择");
    }
    private ConfirmDateWindow date_dialog;
    private void initDateDialog(){
        date_dialog = new ConfirmDateWindow(mActivity);
        date_dialog.setOnPopClickListenner(new ConfirmDateWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.set:
                        dateBean.setName(date_dialog.getTimeName());
                        dateBean.setValue(date_dialog.getTimeValue());
                        choose_date.setText(dateBean.getName());
                        choose_date.setTextColor(ColorRgbUtil.getBaseText());
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
        cpwListBeanView.setOnPopClickListenner(new CPWListBeanView.OnPopClickListenner() {
            @Override
            public void onClick(CPWBean cpwBean,String type) {
                choose_course.setText(cpwBean.getName());
                choose_course.setTextColor(ColorRgbUtil.getBaseText());
                cpwListBeanView.dismiss();
            }
        });
    }



    @OnClick(R.id.p_e_honor_add_choose_course)
    void setCourse(){
        setCPWlListBeanData();
        cpwListBeanView.showAtCenter();
    }
    @OnClick(R.id.p_e_honor_add_choose_date)
    void setDate(){
        date_dialog.showAtBottom();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:
                    addMult(FileCamera.photo_camera);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    setMultList(photo_a);

            }
        }
    }
    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_term_list_api(env);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        toastShow(R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }







    private void initAbsListView() {
        add_multi.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(View view) {
                closeKeyWord();
                album_select.showAtBottom();
            }
        });

        add_multi.setClickable(false);
        add_multi.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
            @Override
            public void onDeleted(@NotNull View view, int index) {
                add_multi.removeItem(index,true);
            }
        });
        add_multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
            @Override
            public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
                Intent intent=new Intent(mActivity, SingePicShowActivity.class);
                Bundle b=new Bundle();
                b.putString(TagFinal.ALBUM_SINGE_URI,uris.get(index));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

    ConfirmAlbumWindow album_select;
    private void initAlbumDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setTwo_select(mActivity.getResources().getString(R.string.album));
        album_select.setOne_select(mActivity.getResources().getString(R.string.take_photo));
        album_select.setName(mActivity.getResources().getString(R.string.upload_type));
        album_select.setOnPopClickListenner(new ConfirmAlbumWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {

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


    public void addMult(String uri){
        if (uri==null) return;
        add_multi.addItem(uri);
    }
    public void setMultList(List<Photo> list){
        for (Photo photo:list){
            if (photo==null) continue;
            addMult(photo.getPath());
        }
    }

    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    private void takePhoto() {
        FileCamera camera=new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }
    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    private void photoAlbum() {
        Intent intent;
        intent = new Intent(mActivity, AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, false);
        intent.putExtras(b);
        startActivityForResult(intent,TagFinal.PHOTO_ALBUM);
    }
    @PermissionFail(requestCode = TagFinal.CAMERA)
    private void showCamere() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_camera, Toast.LENGTH_SHORT).show();
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
