package com.yfy.final_tag;

import android.net.Uri;

import com.yfy.final_tag.stringtool.StringUtils;

public class UriTools {
    public static Uri getTell(String numbar){
        return Uri.parse(StringUtils.getTextJoint("tel:%1$s",numbar));
    }
}
