package com.yfy.app.album;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.BitmapLess;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.image.PinchImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

@SuppressLint("NonConstantResourceId")
public class SingePicShowActivity extends BaseActivity {


    private String url,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_singe_pic_show);
        getData();
        initSQToolbar();
    }


    private void initSQToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.show_pic_one_title_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (title!=null){
            toolbar.setTitle(title);
        }else{
            toolbar.setTitle("返回");
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_nav);
        toolbar.setNavigationOnClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View v) {
                onBackPressed();
            }
        });
    }


    public void getData(){
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey(TagFinal.ALBUM_SINGE_URI)) {
                url = b.getString(TagFinal.ALBUM_SINGE_URI);
            }
            if (b.containsKey("title")) {
                title = b.getString("title");
            }
        }
        initView();
    }
    public void initView(){
        PinchImageView imageView= (PinchImageView) findViewById(R.id.big_url_pic);
        GlideTools.loadImage(mActivity,url,imageView);
    }






    //    菜单 返回true(显示) false（）
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_down, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_down_title:
                if (StringJudge.isEmpty(url)){
                    ViewTool.showToastShort(mActivity,"无法下载本张图片");
                }else{
                    String title=url.substring(0, 4);
                    if (title.equalsIgnoreCase("http")){
                        PermissionTools.tryWRPerm(mActivity);
                    }else{
                        ViewTool.showToastShort(mActivity,"无法下载本张图片");
                    }
                }
                break;

            case -1:
                break;
        }
        return true;

    }









    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    public void photoAlbum() {
        Logger.e(url);
        Glide.with(mActivity)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        String suffix= StringUtils.getFileSuffixName(url).substring(1);
                        String file= BitmapLess.$save(resource,suffix,100);
                        if (StringJudge.isEmpty(file)){
                            //读取文件失败
                            ViewTool.showToastShort(mActivity,"保存失败");
                        }else{
                            ViewTool.showToastShort(mActivity,"已保存到本地相册");
                            FileCamera fileCamera=new FileCamera(mActivity);
                            fileCamera.scanMediaSelectFile(file);
                        }
                    }
                });
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
