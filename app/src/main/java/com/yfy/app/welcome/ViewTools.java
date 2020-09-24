package com.yfy.app.welcome;


import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.widget.ImageView;

public class ViewTools {



    public static void releaseImageView(ImageView iv) {
        try {
            if (iv != null) {
                iv.setImageResource(0);
            }
        } catch (Throwable t) {
        }
    }

    public static void releaseBitmap(Bitmap bitmap) {
        try {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Throwable t) {
        }
    }

    public static void setImageResource(ImageView iv, int res) {
        try {
            if (iv != null) {
                iv.setImageResource(res);
            }
        } catch (Throwable t) {
        }
    }

    public static void setImageBitmap(ImageView iv, Bitmap bitmap) {
        try {
            if (iv != null && bitmap != null) {
                iv.setImageBitmap(bitmap);
            }
        } catch (Throwable t) {
        }
    }



    /**
     * 首页在线直播Tag的背景
     */
    public static Drawable setRandomBackground(int bgColor) {
        // 外部矩形弧度
        float[] outerR = new float[]{0, 0, 0, 0, 0, 0, 10, 10};
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        //指定填充颜色
        drawable.getPaint().setColor(bgColor);
        // 指定填充模式
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }


}
