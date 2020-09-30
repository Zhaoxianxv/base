package com.yfy.final_tag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentAction {
    //    打电话（权限）
    private void callDirectly(Context mContext, String mobile){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        mContext.startActivity(intent);
    }

    public static void rating(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + AppLess.$appname()));
            activity.startActivity(intent);
        } catch (Throwable t) {
//            toast("您的手机上没有安装Android应用市场");
        }
    }
}
