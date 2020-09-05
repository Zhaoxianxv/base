package com.yfy.final_tag;

import android.net.Uri;

public class UriTools {
    public static Uri getTell(String numbar){
        return Uri.parse(StringUtils.getTextJoint("tel:%1$s",numbar));
    }
}
