package com.yfy.final_tag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yfyandr on 2017/11/7.
 */

public class ZoomImage {

    private static final String TAG = "zxx_zoom";
    /**
     * 采样率压缩
     * @param imagePath
     * @param file
     */
    public static void compressBitmap(String imagePath, File file){
        InputStream inputStream=null;
        try {
            inputStream= new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            Log.e("zxx"," 文件拒绝访问 ");
            e.printStackTrace();
        }
        // 数值越高，图片像素越低
        int inSampleSize = 4;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
//         options.inJustDecodeBounds = false;
        options.inJustDecodeBounds = true;//为true的时候不会真正加载图片，而是得到图片的宽高信息。
        //采样率
        options.inSampleSize = inSampleSize;
        Log.e("zxx"," InputStream  "+inputStream);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
        Log.e("zxx"," bitmap  "+bitmap);
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            if(file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Bitmap ratio(String imgPath, float pixelW) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int h = newOpts.outHeight;
        int w = newOpts.outWidth;
        //        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        //        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        float ww = pixelW;
        int be = 1;//be=1表示不缩放:> 1，请求解码器对原始文件进行子采样

        //      if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
        //            be = (int) (newOpts.outWidth / ww);
        //        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
        //            be = (int) (newOpts.outHeight / hh);
        //        }
        if (w > ww) {
            be = (int) (w / ww);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        return bitmap;
    }

    /**
     * 单图片压缩转base64Str
     */
    public static String fileToBase64Str(String path) {
        int inSampleSize = 7;//采样率设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = ratio(path, 1000f);
//		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);//quality  图片精度
        byte[] bytes = baos.toByteArray();
        //base64 encode
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        return  new String(encode);
    }



    /**
     * 像素压缩
     */

    public static String saveImage(String old){
        String path = Environment.getExternalStorageDirectory().toString() + "/yfy/" +  System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        Bitmap bitMap=getSmallBitmap(old);
        FileOutputStream fos = null;
        ByteArrayOutputStream baos  = null;
        try {
            fos = new FileOutputStream(file);
            //图片允许最大空间 单位：KB
            double maxSize =500.00;
            //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
            baos= new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            //将字节换成KB
            double mid = b.length/1024;
            //判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
            if (mid > maxSize) {
                //获取bitmap大小 是允许最大大小的多少倍
                double i = mid / maxSize;
                //开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
                bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
            }
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File tmpFile = new File(old);
        if (tmpFile.exists())
            tmpFile.delete();//删除原文件


        if (FileTools.fileIsExists(path)) return path;
        return null;

    }
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //避免出现内存溢出的情况，进行相应的属性设置。
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;

        return BitmapFactory.decodeFile(filePath, options);
    }
}
