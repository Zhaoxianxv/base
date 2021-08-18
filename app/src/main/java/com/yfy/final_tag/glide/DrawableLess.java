package com.yfy.final_tag.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.core.graphics.drawable.DrawableCompat;

public final class DrawableLess {




    //获取Drawable  （idDrawable）
    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getResourceDrawable(Context mActivity, int idDrawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mActivity.getResources().getDrawable(idDrawable, null);
        } else {
            return mActivity.getResources().getDrawable(idDrawable);
        }

    }


    /**
     * ========================================================
     * tint drawable with Color or ColorStateList using the DrawableCompat in support v4 library
     * ========================================================
     */
    //改变drawable图片颜色
    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable $tint(Drawable originDrawable, int color) {
        return $tint(originDrawable, ColorStateList.valueOf(color));
    }

    public static Drawable $tint(Drawable originDrawable, int color, PorterDuff.Mode tintMode) {
        return $tint(originDrawable, ColorStateList.valueOf(color), tintMode);
    }

    public static Drawable $tint(Drawable originDrawable, ColorStateList colorStateList) {
        return $tint(originDrawable, colorStateList, null);
    }

    public static Drawable $tint(Drawable originDrawable, ColorStateList colorStateList, PorterDuff.Mode tintMode) {
        Drawable tintDrawable = DrawableCompat.wrap(originDrawable);
        if (tintMode != null) {
            DrawableCompat.setTintMode(tintDrawable, tintMode);
        }
        DrawableCompat.setTintList(tintDrawable, colorStateList);
        return tintDrawable;
    }


}
