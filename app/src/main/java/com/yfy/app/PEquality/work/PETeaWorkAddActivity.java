package com.yfy.app.PEquality.work;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.yfy.app.album.AlbumMainActivity;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.SaveImgReq;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.glide.ZoomImage;
import com.yfy.final_tag.hander.PicAsyncTask;
import com.yfy.final_tag.hander.SaveImageAsync;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;


public class PETeaWorkAddActivity extends HttpPostActivity implements HttpNetHelpInterface , SaveImageAsync {
    private static final String TAG = PETeaWorkAddActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        Logger.eLogText(TAG);
        getData();
        initSQToolbar();
        initRecycler();
        setWorkData();
    }



    public CPWBean classCPWBean;
    public DateBean dateBean;
    private void getData(){
        classCPWBean=getIntent().getParcelableExtra(Base.class_bean);
        dateBean=getIntent().getParcelableExtra(Base.date);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("添加作业");
    }
    private KeyValue multi_bean;
    private int position_index;

    public List<KeyValue> keyValueAdapterData =new ArrayList<>();
    public PETeaWorkAddAdapter adapter;
    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new PETeaWorkAddAdapter(PETeaWorkAddActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setSealChoice(new PETeaWorkAddAdapter.SealChoice() {
            @Override
            public void refresh(KeyValue bean, int pos_index) {
                position_index=pos_index;
                multi_bean=bean;
            }
        });

    }


    private void setWorkData(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_DATE);
        one.setTitle("日期");
        one.setRight_value(dateBean.getValue());
        one.setRight_name(dateBean.getName());
        one.setRight_key("未选择");
        one.setType("date");
        one.setId("");



        KeyValue three=new KeyValue(TagFinal.TYPE_LONG_TXT_EDIT);
        three.setTitle("作业文字描述");
        three.setKey("点击输入作业描述");
        three.setValue("");
        three.setType("edit_long");


        KeyValue four=new KeyValue(TagFinal.TYPE_IMAGE);
        four.setTitle("图片");
        four.setListValue(new ArrayList<>());
        four.setType("image");



        keyValueAdapterData.add(one);
        keyValueAdapterData.add(three);
        keyValueAdapterData.add(four);


        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:
                    mTask=new PicAsyncTask(this);
                    mTask.execute(FileCamera.photo_camera);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    List<String> photo_list=new ArrayList<>();
                    for (Photo photo:photo_a){
                        photo_list.add(photo.getPath());
                    }
                    mTask=new PicAsyncTask(this);
                    mTask.execute(StringUtils.arraysToListString(photo_list));
                    break;

            }
        }
    }


    /**
     * ----------------------------retrofit-----------------------
     */

    private void saveImg(String file_string){
        SaveImgReq req = new SaveImgReq();
        //获取参数

        req.setImage_file(file_string);
        req.setFileext("jpg");


        Call<ResponseBody> bodyCall= RestClient.instance.getAccountApi().base_save_img_api(
                req.getSession_key(),
                req.getFileext(),
                req.getImage_file()
        );
        setNetHelper(this,bodyCall,false, ApiUrl.BASE_SAVE_IMG);

    }



    @Override
    public void success(String api_name, String result) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
//        if (api_name.equalsIgnoreCase(ApiUrl.QUALITY_TEA_GET_SCORE_ITEM_LIST)){
//            QualityRes res=gson.fromJson(result, QualityRes.class);
//            Logger.eLogText(StringUtils.getTextJoint("%1$s:%2$s",api_name,result));
//            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                List<QualityStu> list=res.getStulist();
//            }else{
//                ViewTool.showToastShort(mActivity,res.getError_code());
//            }
//        }
    }


    @Override
    public void fail(String api_name) {


    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }














    /*PicAsyncTask*/

    public int num=0;
    public PicAsyncTask mTask;

    @Override
    public List<String> doIn(String... arg0) {
        List<String> list=new ArrayList<>(Arrays.asList(arg0));
        List<String> base64_list=new ArrayList<>();
        num=0;
        for (String s:list){
            base64_list.add(ZoomImage.fileToBase64Str(s));
            num++;
        }
        return base64_list;
    }
    @Override
    public void doUpData(List<String> list) {
        if (StringJudge.isEmpty(list)){
            ViewTool.showToastShort(mActivity,"未选择图片");
            ViewTool.dismissProgressDialog();
        }else{
            for (String s:list){
                saveImg(s);
            }
        }
    }
    @Override
    public void onPre() {
        ViewTool.showProgressDialog(mActivity,"");
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }














    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    public void takePhoto() {
        FileCamera camera = new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }

    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    public void photoAlbum() {
        Intent intent = new Intent();
        intent.setClass(mActivity, AlbumMainActivity.class);
        intent.putExtra(TagFinal.ALBUM_SINGLE,false);
        intent.putExtra(TagFinal.ALBUM_LIST_INDEX,-1);
        startActivityForResult(intent, TagFinal.PHOTO_ALBUM);

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
