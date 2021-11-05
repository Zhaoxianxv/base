package com.yfy.final_tag.hander;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.yfy.final_tag.glide.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by zxx.
 * Date: 2021/1/19
 */
public class AlbumAsyncTask extends AsyncTask<Object, Object, List<Photo>> {

    public AlbumGetFileData api;



    public AlbumAsyncTask(AlbumGetFileData api) {
        this.api = api;
    }

    @Override
    protected List<Photo> doInBackground(Object... params) {
        if (isCancelled()) {
            return null;
        }

        return getAllAlbum();

    }
    //onPostExecute方法用于在执行完后台任务doInBackground后更新UI,显示结果
    @Override
    protected void onPostExecute(List<Photo> list) {
        // TODO Auto-generated method stub
        super.onPostExecute(list);
        if (api!=null){
            api.OnEnd(list);
        }

    }

    //onProgressUpdate方法用于更新进度信息
//    protected void onProgressUpdate(Integer... integers) {
//        super.onProgressUpdate(integers);
//
//    }
    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }













    /*------------------媒体库-----------------------*/
    public ContentResolver resolver;
    public List<Photo> all_photo_list=new ArrayList<>();

    public void setResolver(Activity mActivity) {
        this.resolver = mActivity.getContentResolver();

    }



    /*获取所有图片*/
    public List<Photo>  getAllAlbum(){
        all_photo_list.clear();
        HashMap<String,List<Photo>> all_photo_Temp = new HashMap<>();//所有照片
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = resolver.query(mImageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

        if(mCursor!=null){
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                /*图片大小*/
                int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                /*图片备注*/
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //用于展示相册初始化界面
                all_photo_list.add(new Photo(path,size,displayName,false));
                // 获取该图片的父路径名
                String dirPath = new File(path).getParentFile().getAbsolutePath();
                //存储对应关系
                if (all_photo_Temp.containsKey(dirPath)) {
                    List<Photo> data = all_photo_Temp.get(dirPath);
                    data.add(new Photo(path,size,displayName,false));
                    continue;
                } else {
                    List<Photo> data = new ArrayList<>();
                    data.add(new Photo(path,size,displayName,false));
                    all_photo_Temp.put(dirPath,data);
                }
            }
            mCursor.close();
        }

        return all_photo_list;
    }











}
