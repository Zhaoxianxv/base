package com.yfy.final_tag.glide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;


import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.FileTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.FileProvider;

/**
 * Created by yfy on 2017/9/12.
 */

public class FileCamera {


    public Activity context;
    public static String photo_camera;
    public FileCamera(Activity context) {
        this.context = context;
    }


    public Intent takeCamera(){
        photo_camera= Environment.getExternalStorageDirectory().toString() + "/yfy/" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        FileTools.createFile(photo_camera);
        File file = new File(photo_camera);
        Uri mOutPutFileUri ;
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mOutPutFileUri = FileProvider.getUriForFile(context,Base.AUTHORITY, file);
        }else{
            mOutPutFileUri = Uri.fromFile(file);
        }
        //这里进行替换uri的获得方式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag

        scanMediaFile(file);
        return intent;
    }






    //保存bitmap到photo
    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
        scanMediaFile(photo);
    }
//
//    public boolean addJpgSignatureToGallery(Bitmap signature) {
//        boolean result = false;
//        try {
//            sign_path = TagFinal.getAppFile() +  System.currentTimeMillis() + ".png";
//            File file= FileTools.getFile(sign_path);
//            saveBitmapToJPG(Base64Utils.rotaingImageView(270, signature), file);
//            scanMediaFile(file);
//            result = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public boolean addJpg(Bitmap signature) {
//        boolean result = false;
//        try {
//            path = TagFinal.getAppFile() +  System.currentTimeMillis() + ".png";
//            File file= FileTools.getFile(path);
//            saveBitmapToJPG(signature, file);
//            scanMediaFile(file);
//            result = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    /**
     *   提示本地相册更新
     */

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
    //提示本地相册更新
    public static void scanMediaAllFile(Activity context) {
//        Logger.e(Environment.getExternalStorageDirectory().toString());
//        Logger.e(Environment.getDataDirectory().toString());
//        Logger.e(Environment.getDownloadCacheDirectory().toString());
//        Logger.e(Environment.getRootDirectory().toString());
//        Logger.e(Environment.getExternalStoragePublicDirectory().toString());
        List<String> path_list=getAllFile(Environment.getExternalStorageDirectory().toString() + "/Download/QQMail/");
        if (StringJudge.isEmpty(path_list)){
            ViewTool.showToastShort(context,"没有获取到路径：/Download/QQMail/");
            return;
        }
        int i=0;
        for (String path:path_list){
            File file = new File(path);
            scanMediaAllFile(context,file);
            Logger.e(String.valueOf(i++));
        }

    }
    public static void scanMediaAllFile(Activity context,File photo) {
        Logger.e("scanMediaAllFile");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    /**
     * 获取某个目录下所有的文件的全路径名的集合；
     */
    public static List<String> getAllFile(String mulu) {
        List<String> allFilePath = new ArrayList<>();
        File file = new File(mulu);
        File[] files = file.listFiles();

        if (files==null){
            return allFilePath;
        }else{
            if (files.length==0){
                return allFilePath;
            }
        }
        for (File value : files) {
            if (value.isDirectory()) {
                allFilePath.add(value.toString());
            }
        }
        return allFilePath;
    }


}
