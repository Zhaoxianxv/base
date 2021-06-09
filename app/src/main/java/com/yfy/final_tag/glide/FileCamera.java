package com.yfy.final_tag.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;


import com.yfy.base.Base;
import com.yfy.final_tag.FileTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.content.FileProvider;

/**
 * Created by yfy on 2017/9/12.
 */

public class FileCamera {


    public Activity mActivity;
    public static String photo_camera;
    public FileCamera(Activity context) {
        this.mActivity = context;
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
            mOutPutFileUri = FileProvider.getUriForFile(mActivity,Base.AUTHORITY, file);
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

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
    }

    /**
     *   提示本地相册更新
     *
     *
     *   通过操作 MediaStore 类。
     * 发送广播更新 MediaStore。
     * 通过操作 MediaScannerConnection 类。
     *  https://blog.csdn.net/iblue007/article/details/111835449
     */


    //提示更新文件夹
    //图片文件更新
    @SuppressLint("ObsoleteSdkInt")
    public void scanMediaSelectFile(String file_path) {
        Logger.e(file_path);
        List<String> path_list=new ArrayList<>();
        path_list.add(file_path);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //通过操作 MediaScannerConnection 类
            MediaScannerConnection.scanFile(
                    mActivity,
                    StringUtils.arraysToListString(path_list),
//                    mimeTypes,
                    null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
//                            Logger.e("path",path);
//                            Logger.e("uri",uri.getPath());

//                            Intent mediaScanIntent = new Intent();
//                            mediaScanIntent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                            Uri contentUri ;
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                File file = new File(path);
//                                contentUri = FileProvider.getUriForFile(mActivity, Base.AUTHORITY, file);
//                            } else {
//                                contentUri=Uri.parse(path);
//                            }
//                            mediaScanIntent.setData(contentUri);
//                            mActivity.sendBroadcast(mediaScanIntent);

                        }
                    });
        }
    }




    public static String[] mimeTypes=new String[] {"video/quicktime"};
    //删除文件后更新数据库  通知媒体库更新文件夹
    public static void updateFileFromDatabase(Context context){

        List<String> path_list=getAllFile(Environment.getExternalStorageDirectory().toString() + "/yfy/");
        if (StringJudge.isEmpty(path_list)){
            ViewTool.showToastShort(context,"没有获取到路径：yfy");
            return;
        }

//        intent .setType("*/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //通过操作 MediaScannerConnection 类
            MediaScannerConnection.scanFile(
                    context,
                    StringUtils.arraysToListString(path_list),
                    mimeTypes,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Logger.e("path",path);
                            Logger.e("uri",uri.getPath());

                        }
                    });
        } else  {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            Uri contentUri ;
            contentUri=Uri.parse("file://" + Environment.getExternalStorageDirectory());
            intent.setData(contentUri);
            context.sendBroadcast(intent);
        }
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



        List<File> list = Arrays.asList(files);
        List<File> fileList=new ArrayList<>(list);


        for (File value : fileList) {
            if (value.isDirectory()) {
                //file为目录
               continue;
            }else{
                allFilePath.add(value.toString());
            }
            Logger.e(value.toString());
        }
        return allFilePath;
    }


}
