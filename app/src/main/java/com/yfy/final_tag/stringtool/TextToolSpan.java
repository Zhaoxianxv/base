package com.yfy.final_tag.stringtool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.glide.BitmapLess;
import com.yfy.final_tag.glide.DrawableLess;
import com.yfy.final_tag.permission.PermissionTools;

/**
 * ImageSpan            图文混排
 * SpannableString
 * ForegroundColorSpan  单独设置字体颜色
 * BackgroundColorSpan  设置文字背景色
 * AbsoluteSizeSpan     设置字体大小
 * StyleSpan            字体设置：粗体、斜体等
 * StrikethroughSpan    设置删除线（中划线）
 * UnderlineSpan        设置下划线
 * UrlSpan              设置文本超链接
 * ClickableSpan        单独设置点击事件    ->      tv.setMovementMethod(LinkMovementMethod.getInstance());// 必须加
 * MaskFilterSpan       修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
 * MetricAffectingSpan  父类，一般不用
 * RasterizerSpan       光栅效果
 * SuggestionSpan       相当于占位符
 * DynamicDrawableSpan  设置图片，基于文本基线或底部对齐
 * ScaleXSpan           基于x轴缩放
 * SubscriptSpan        下标（数学公式会用到）
 * SuperscriptSpan      上标（数学公式会用到）
 * TextAppearanceSpan   文本外貌（包括字体、大小、样式和颜色）
 * TypefaceSpan         文本字体
 *
 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
 * Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
 * Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
 */


public class TextToolSpan {

    //start、end 用图片替换字符串[star，end) 的位置
    public static SpannableString stringToByteArrayGetString(String content, Context context, int resourceId){
        SpannableString spannableString = new SpannableString(content);
        ImageSpan imgSpan = new ImageSpan(context,resourceId);
        spannableString.setSpan(imgSpan, 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    public static SpannableString stringToByteArrayGetString(Context context){
        SpannableString spannableString = new SpannableString("");
        ImageSpan imgSpan = new ImageSpan(context, R.drawable.radius_oval_gray);
        ImageSpan two = new ImageSpan(context, R.drawable.radius_oval_gray);
        SpannableStringBuilder sb=new SpannableStringBuilder();
        SpannableString sb_one = new SpannableString("013\t我的成绩");
        SpannableString sb_two = new SpannableString("012\t班级平均成绩");
        sb_one.setSpan(imgSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb_two.setSpan(two, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append(sb_one).append("\t\t").append(sb_two);
        return spannableString;
    }

    //修改字体颜色
    public static void  $spannableStringColor(SpannableString spannableString, int color){
        spannableString.setSpan(
                new ForegroundColorSpan(color),
                0,
                spannableString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }

    /**
     * 图片与文字的对齐方式
     * ImageSpan.ALIGN_BASELINE
     * ImageSpan.ALIGN_BOTTOM
     * ImageSpan.ALIGN_CENTER
     */
    //添加一个图片
    public static void  $spannableAddIconColor(Context context, TextView textView,String string, int resourceId, int color){
        SpannableString sb = new SpannableString(string);

        sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Drawable drawable=DrawableLess.$tint(context.getResources().getDrawable(resourceId),color);
        drawable.setBounds(0, 0, textView.getLineHeight(),textView.getLineHeight());//让图片与文字对齐

        ImageSpan imgSpan = new ImageSpan(drawable,ImageSpan.ALIGN_CENTER);
        sb.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sb);
    }



    //textView文字添加链接点击转webView
    public static void  setSpannableStringColor(TextView tv,String startString ,String  endString,String linkString, int color,String link){
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableStringBuilder sb=new SpannableStringBuilder();
        SpannableString sb_s = new SpannableString(linkString);

        sb_s.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //响应
            }
        }, 0, linkString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb_s.setSpan(
                new ForegroundColorSpan(color),
                0,
                sb_s.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        sb.append(startString).append(sb_s).append(endString);

        tv.setText(sb);
    }
}
