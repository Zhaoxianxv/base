package com.yfy.final_tag.stringtool;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import com.yfy.base.R;


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
        spannableString.setSpan(new ForegroundColorSpan(color), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
