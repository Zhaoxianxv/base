package com.yfy.final_tag.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.yfy.final_tag.FileTools;
import com.yfy.final_tag.data.TagFinal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public final class BitmapLess {




    /**
     * 读取drawable资源成Bitmap
     */
    public static Bitmap $drawable(Context context, int drawableId) {
        return BitmapFactory.decodeResource(context.getResources(), drawableId);
    }
    //改变drawableId图片颜色
    public static Bitmap $drawableColor(Context context, int drawableId,int color) {
        return drawableToBitmap(DrawableLess.$tint(context.getResources().getDrawable(drawableId),color),true);
    }
    // Drawable转换成一个Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable, boolean is) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        if(is){
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());//保证图片大小不变
        }
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 根据reqWidth, reqHeight计算最合适的inSampleSize

     * @return
     */
    public static int $sampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        // raw height and width of image
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;

        // calculate best sample size
        int inSampleSize = 0;
        if (rawHeight > maxHeight || rawWidth > maxWidth) {
            float ratioWidth = (float) rawWidth / maxWidth;
            float ratioHeight = (float) rawHeight / maxHeight;
            inSampleSize = (int) Math.min(ratioHeight, ratioWidth);
        }
        inSampleSize = Math.max(1, inSampleSize);

        return inSampleSize;
    }

    /**
     * 更节省内存的读取raw资源成Bitmap
     * @return
     */
    public static Bitmap $raw(Context context, int rawId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(rawId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 旋转Bitmap(默认回收传进来的原始Bitmap)
     *
     * @param originBitmap
     * @param angle
     * @return
     */
    public static Bitmap $rotate(Bitmap originBitmap, int angle) {
        return $rotate(originBitmap, angle, true);
    }

    /**
     * 旋转Bitmap

     * @param recycleOriginBitmap 是否回收传进来的原始Bitmap
     * @return
     */
    public static Bitmap $rotate(Bitmap originBitmap, int angle, boolean recycleOriginBitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap rotatedBitmap = Bitmap.createBitmap(originBitmap,
                0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);
        if (recycleOriginBitmap && originBitmap != null && !originBitmap.isRecycled()) {
            originBitmap.recycle();
        }
        return rotatedBitmap;
    }

    /**
     * 缩放Bitmap(默认回收传进来的原始Bitmap)
     *
     * @return
     */
    public static Bitmap $scale(Bitmap originBitmap, float scaleX, float scaleY) {
        return $scale(originBitmap, scaleX, scaleY, true);
    }

    /**
     * 缩放Bitmap
     * @param recycleOriginBitmap 是否回收传进来的原始Bitmap
     * @return
     */
    public static Bitmap $scale(Bitmap originBitmap, float scaleX, float scaleY, boolean recycleOriginBitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap scaledBitmap = Bitmap.createBitmap(originBitmap,
                0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);
        if (recycleOriginBitmap && originBitmap != null && !originBitmap.isRecycled()) {
            originBitmap.recycle();
        }
        return scaledBitmap;
    }

    /**
     * 获取缩略图（默认关闭自动旋转）
     * @return
     */
    public static Bitmap $thumbnail(String path, int maxWidth, int maxHeight) {
        return $thumbnail(path, maxWidth, maxHeight, false);
    }

    /**
     * 获取缩略图
     * 1. 支持自动旋转
     */
    public static Bitmap $thumbnail(String path, int maxWidth, int maxHeight, boolean autoRotate) {

        int angle = 0;
        if (autoRotate) {
            angle = ImageLess.$exifRotateAngle(path);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高, 此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int sampleSize = $sampleSize(options, maxWidth, maxHeight);
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = BitmapFactory.decodeFile(path, options);

        if (autoRotate && angle != 0) {
            bitmap = $rotate(bitmap, angle);
        }

        return bitmap;
    }

    /**
     * 保存到本地，默认路径/mnt/sdcard/[package]/save/
     */
    public static String $save(Bitmap bitmap, String image_suffix , int quality) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        Bitmap.CompressFormat format;
        switch (image_suffix){
            case "png":
                format=Bitmap.CompressFormat.PNG;
                break;
            case "":
                format=Bitmap.CompressFormat.WEBP;
                break;
            default:
                format=Bitmap.CompressFormat.JPEG;
                break;
        }
//        File dir = new File(Environment.getExternalStorageDirectory().toString() + "/yfy/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File destFile = new File(dir, UUID.randomUUID().toString());


        String saveFileName= TagFinal.getAppFile( 1,System.currentTimeMillis(),"."+image_suffix);
        FileTools.createFile(saveFileName);
        File dir = new File(saveFileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return $save(bitmap, format, quality, dir);
    }

    /**
     * 保存到本地destFile
     */
    public static String $save(Bitmap bitmap, Bitmap.CompressFormat format, int quality, File destFile) {
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            if (bitmap.compress(format, quality, out)) {
                out.flush();
                out.close();
            }

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
