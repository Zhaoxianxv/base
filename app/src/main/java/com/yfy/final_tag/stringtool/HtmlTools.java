package com.yfy.final_tag.stringtool;

import android.text.Html;

/**
 * Created by yfy on 2017/12/21.
 */

public class HtmlTools {
    private static final String TAG = "zxx";

    private static String base_bg="#999999", text_color="#444444";


    public static CharSequence  getHtmlFont(String content1,String content2){
        StringBuilder builder=new StringBuilder();
        builder.append("<font size= 16px>").append(content1).append("</font>")
                .append("<font size=>").append(content2).append("</font>");
        String msg=builder.toString();
        CharSequence charSequence;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(msg,Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(msg);
        }
        return charSequence;
    }

    /**
     * 其中的flags表示：
     * FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
     * FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
     */
    public static CharSequence  getHtmlString(String content1,String content2){
        String html_txt= StringUtils.stringToGetTextJoint("<font color=#999999>%1$s</font><font color=#444444>%2$s</font>",content1,content2);
        CharSequence charSequence;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(html_txt,Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(html_txt);
        }
        return charSequence;
    }


    public static CharSequence  getHtmlString(String util_color,String util,String content_color,String content){
        StringBuilder builder=new StringBuilder();
        builder.append("<font color=").append(util_color).append(">").append(util).append("</font>")
                .append("<font color=").append(content_color).append(">").append(content).append("</font>");
        String msg=builder.toString();
        CharSequence charSequence;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(msg,Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(msg);
        }
        return charSequence;
    }
}
