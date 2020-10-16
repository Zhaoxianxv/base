package com.yfy.final_tag.viewtools;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.Selection;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ViewTool {


    public static void alterVectorDrawableColor(View view, int color) {
        VectorDrawable one = (VectorDrawable) view.getBackground();
        one.setTint(color);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void alterGradientDrawableColor(View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground();
        one.setTint(color);
    }
//   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public static void alterButtonColor(Button view, int color) {
//       int[] colors = new int[]{0xfff8513f, 0xffe43d2b};
//       int[][] states = new int[2][];
//       states[0] = new int[]{android.R.attr.state_pressed};
//       states[1] = new int[]{android.R.attr.state_enabled};
//       view.setBackgroundTintList(new ColorStateList(states, colors));
//       view.setBackgroundTintMode(mode[i - 1]);
//    }


    public static void alterGradientDrawableStrokeColor(Context context,View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground();
        one.setStroke(px2dip(context,1),color);
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * @param pxValue （DisplayMetrics类中属性density）
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics());
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dipValue, context.getResources().getDisplayMetrics());
    }

    //-
    public static void setRelativeLayoutDisplayMetrics(View view,int height,int width){
        //ViewGroup.LayoutParams.MATCH_PARENT
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(height,width);
        view.setLayoutParams(params);
    }

    /**
     * ---------------ScreenWidth--------------
     */
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    /**
     * 设置输入框的光标到末尾
     */
    public static void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }

    /**
     * 設置SwipeRefreshLayout颜色
     */
    public static void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout,@ColorRes int... colorResIds) {
        swipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    /**
     * 显示Toast
     */
    public static void showToastLong(Context mContext,String content){
        Toast.makeText(mContext,content,Toast.LENGTH_LONG).show();
    }
    public static void showToastShort(Context mContext,String content){
        Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
    }
    public static void showToastShort(Context mContext,@StringRes int text) {
        showToastShort(mContext,mContext.getString(text));
    }
}
