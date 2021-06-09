package com.yfy.final_tag.viewtools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yfy.final_tag.tool_textwatcher.MyWatcher;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ViewTool {


    //修改 渐变色 gradient 中属性值
    public static void alterGradientStartEndColor(View view, int startColor, int endColor){
        GradientDrawable one = (GradientDrawable) view.getBackground();
        one.setColors(new int[]{startColor, endColor});


    }



    //button改变按钮颜色
    public static void setButtonBgColor(Button button , int color){
        button.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
    }
    public static void setButtonBgColor(Button button ,int color,int text_color){
        button.setTextColor(text_color);
        button.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
    }



    //改变 progressbar 的 progress 图层颜色
    @TargetApi(Build.VERSION_CODES.M)
    public static void alterProgressBarFirstBackgroundColor(ProgressBar progressBar, int progressColor,int backgroundColor) {
        //progress
        LayerDrawable layerDrawable = (LayerDrawable) progressBar.getProgressDrawable();
        ClipDrawable clipDrawable_progress = (ClipDrawable) layerDrawable.findDrawableByLayerId(android.R.id.progress);
        GradientDrawable gradientDrawable_progress= new GradientDrawable();
        gradientDrawable_progress.setColor(progressColor);
        clipDrawable_progress.setDrawable(gradientDrawable_progress);
        layerDrawable.setDrawableByLayerId(android.R.id.progress, clipDrawable_progress);

        //background
        GradientDrawable clipDrawable_background = (GradientDrawable) layerDrawable.findDrawableByLayerId(android.R.id.background);
        clipDrawable_background.setTint(backgroundColor);
        layerDrawable.setDrawableByLayerId(android.R.id.background, clipDrawable_background);
    }
    //改变 progressbar 的 progress Secondary 图层颜色
    @TargetApi(Build.VERSION_CODES.M)
    public static void alterProgressSecondaryBackgroundColor(ProgressBar progressBar, int secondaryProgress,int progressColor,int backgroundColor) {

        LayerDrawable layerDrawable = (LayerDrawable) progressBar.getProgressDrawable();
        //progressColor
        ClipDrawable clipDrawable_progress = (ClipDrawable) layerDrawable.findDrawableByLayerId(android.R.id.progress);
        GradientDrawable gradientDrawable_progress= new GradientDrawable();
        gradientDrawable_progress.setColor(progressColor);
        clipDrawable_progress.setDrawable(gradientDrawable_progress);
        layerDrawable.setDrawableByLayerId(android.R.id.progress, clipDrawable_progress);

        //secondaryProgress
        ClipDrawable clipDrawable_secondaryProgress = (ClipDrawable) layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);
        GradientDrawable gradientDrawable_secondaryProgress= new GradientDrawable();
        gradientDrawable_secondaryProgress.setColor(secondaryProgress);
        clipDrawable_secondaryProgress.setDrawable(gradientDrawable_secondaryProgress);
        layerDrawable.setDrawableByLayerId(android.R.id.secondaryProgress, clipDrawable_secondaryProgress);

        //background
        GradientDrawable clipDrawable_background = (GradientDrawable) layerDrawable.findDrawableByLayerId(android.R.id.background);
        clipDrawable_background.setTint(backgroundColor);
        layerDrawable.setDrawableByLayerId(android.R.id.background, clipDrawable_background);
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


    //改变Vector 图片颜色
    public static void alterVectorDrawableColor(View view, int color) {
        VectorDrawable one = (VectorDrawable) view.getBackground();
        one.setTint(color);
    }
    //改变 Drawable(除开层叠Drawable) 颜色
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void alterGradientDrawableColor(View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground();
        one.setTint(color);
    }

    //改变 shape  <stroke android:width="1dp" android:color="#D4D4D4"/>
    public static void alterGradientDrawableStrokeColor(Context context,View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground();
        one.setStroke(pxPointDp(context,1),color);
    }
    //改变 Background Shape Drawable Solid Color <solid android:color="@color/white"/>
    public static void alterBSDSColor(View view, int color) {
        GradientDrawable one = (GradientDrawable) view.getBackground();
        one.setTint(color);
    }
    //改变 AppCompatImageView  Shape Drawable Solid Color <solid android:color="@color/white"/>
    public static void alterISDSColor(Context context,View view, int color) {
//        GradientDrawable one = (GradientDrawable) view.getr();
//        one.setTint(color);
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *（DisplayMetrics类中属性density）
     * 控件1dp=2dip
     */
    public static int pxPointDp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics());
    }

    public static int dpPointPx(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dipValue, context.getResources().getDisplayMetrics());
    }
    public static int dpPointPx(Activity context, float dipValue) {
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
    public static int getScreenWidth(Activity context){
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
    public static void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout, @ColorRes int... colorResIds) {
        swipeRefreshLayout.setColorSchemeResources(colorResIds);
    }



    //截取控件生产图片
    public static Bitmap createImage(Activity activity, View view) {

        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。

        //View.MeasureSpec.UNSPECIFIED不指定大小
//        View.MeasureSpec.EXACTLY//屏幕大小
//        View.MeasureSpec.AT_MOST
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.AT_MOST);
        /* 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.requestLayout();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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


    /**
     * 显示一个ProgressDialog
     */
    public static ProgressDialog dialog;
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;

    public static void  showProgressDialog(Context context,String message) {
        mContext=context;
        showProgressDialog(mContext,null, message);
        getInstance();
        mycount.start();
    }
    public static void showProgressDialog(Context context,long time,String message) {
        mContext=context;
        showProgressDialog(mContext,null, message);
        getInstance(time);
        mycount.start();
    }
    public static void dismissProgressDialog() {
        if ( mycount != null){
            mycount.cancel();
            mycount = null;
        }
        if (dialog != null) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
            dialog=null;
        }

    }
    public static void showProgressDialog(Context context,String title, String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
        }
        dialog.setCancelable(false);
        if (title != null && !title.equals("")) {
            dialog.setTitle(title);
        }
        if (message != null && !message.equals("")) {
            dialog.setMessage(message);
        }
        if (!dialog.isShowing()){
            dialog.show();
        }

    }




















    /**
     * 倒计时
     */
    private static MyCountDownTimer mycount;

    public static MyCountDownTimer getInstance() {
        if (mycount == null) {
            mycount = new MyCountDownTimer(20000,10000);
        }
        return mycount;
    }
    public static MyCountDownTimer getInstance(long time) {
        if (mycount == null) {
            mycount = new MyCountDownTimer(time,1000);
        }
        return mycount;
    }


    private static class MyCountDownTimer extends CountDownTimer {
        private MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时器结束
        @Override
        public void onFinish() {
            ViewTool.showToastShort(mContext,"网络超时");
            dismissProgressDialog();

        }

        //计时器开始
        @Override
        public void onTick(long millisUntilFinished) {


        }
    }







    /**
     * ------------------------edit 控件输入限制--------------------------
     */
    ////输入类型为没有指定明确的类型的特殊内容类型
    //editText.setInputType(InputType.TYPE_NULL);
    //
    ////输入类型为普通文本
    //editText.setInputType(InputType.TYPE_CLASS_TEXT);
    //
    ////输入类型为数字文本
    //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    //
    ////输入类型为电话号码
    //editText.setInputType(InputType.TYPE_CLASS_PHONE);
    //
    ////输入类型为日期和时间
    //editText.setInputType(InputType.TYPE_CLASS_DATETIME);
    //
    ////输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，允许输入日期和时间。
    //editText.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
    //
    ////输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个日期。
    //editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
    //
    ////输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个时间。
    //editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
    //
    ////输入类型为决定所给文本整体类的位掩码
    //editText.setInputType(InputType.TYPE_MASK_CLASS);
    //
    ////输入类型为提供附加标志位选项的位掩码
    //editText.setInputType(InputType.TYPE_MASK_FLAGS);
    //
    ////输入类型为决定基类内容变化的位掩码
    //editText.setInputType(InputType.TYPE_MASK_VARIATION);
    //
    ////输入类型为小数数字，允许十进制小数点提供分数值。
    //editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    ////输入类型为数字是带符号的，允许在开头带正号或者负号
    //editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
    //
    ////输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为纯普通数字文本
    //editText.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
    //
    ////输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为数字密码
    //editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    //
    ////输入类型为自动完成文本类型
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
    //
    ////输入类型为自动纠正文本类型
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
    //
    ////输入类型为所有字符大写
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    //
    ////输入类型为每句的第一个字符大写
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    //
    ////输入类型为每个单词的第一个字母大写
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    //
    ////输入多行文本
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
    //
    ////进行输入时，输入法无提示
    //editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    //
    ////输入一个短的，可能是非正式的消息，如即时消息或短信。
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
    //
    ////输入长内容，可能是正式的消息内容，比如电子邮件的主体
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
    //
    ////输入文本以过滤列表等内容
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
    //
    ////输入一个电子邮件地址
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    //
    ////输入电子邮件主题行
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
    //
    ////输入一个密码
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    //
    ////输入老式的普通文本
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
    //
    ////输入人名
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
    //
    ////输入邮寄地址
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
    //
    ////输入语音发音输入文本，如联系人拼音名称字段
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);
    //
    ////输入URI
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
    //
    ////输入对用户可见的密码
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    //
    ////输入网页表单中的文本
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
    //
    ////输入网页表单中的邮件地址
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
    //
    ////输入网页表单中的密码
    //editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);

    public static void editControlInputType(EditText edit,String type){

        switch (type){
            case "decimal":
                edit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
                break;
            case "number":
                edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "numberSigned":
                edit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);//可以输入正负数字
                break;
            case "numberSigned|numberDecimal":
                edit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED|InputType.TYPE_NUMBER_FLAG_DECIMAL);//正负带小数点的数字
                break;
        }
    }
    //限制num位小数.
    public static void editControlDecimalLength(EditText edit,int num){
        edit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
        edit.addTextChangedListener(new MyWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String edit_String=s.toString();
                int posDot=edit_String.indexOf(".");
                if (posDot<0){
                    return;
                }
                if (posDot==0){
                    s.delete(0,edit_String.length());
                    return;
                }
                if (edit_String.length()-posDot-1>num)
                    s.delete(posDot+num+1,edit_String.length());
            }
        });


    }



}
