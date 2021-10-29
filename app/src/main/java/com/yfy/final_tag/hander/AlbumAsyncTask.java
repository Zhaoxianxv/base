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


        return all_photo_list;
    }











}
