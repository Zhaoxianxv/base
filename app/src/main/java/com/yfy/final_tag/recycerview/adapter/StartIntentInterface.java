package com.yfy.final_tag.recycerview.adapter;

import android.content.Intent;

import com.yfy.final_tag.stringtool.Logger;

/**
 * Created by zxx.
 * Date: 2021/3/5
 */
public abstract class StartIntentInterface {


    public void startIntentAdapter(Intent intent){
        if (!isFastClick()){
            return;
        }
        startIntentActivity(intent);
    }
    public void startIntentAdapter(Intent intent, int tag){
        if (!isFastClick()){
            return;
        }
        startIntentActivity(intent,tag);
    }
    public void startIntentAdapter(Intent intent, String type){
        if (!isFastClick()){
            return;
        }
        startIntentActivity(intent,type);
    }







    private long lastClickTime = 0;//上次点击的时间
    //true可以点击（距离上次点击大于500有效）
    public boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick = (currentTime - lastClickTime) >= 1000;      //是否允许点击
        Logger.e(String.format("%1$s:%2$s:%3$s:%4$s",lastClickTime,currentTime,currentTime - lastClickTime,isAllowClick));
        lastClickTime = currentTime;
        return isAllowClick;
    }

    public void startIntentActivity(Intent intent, int tag){

    }
    public void startIntentActivity(Intent intent, String type){

    }
    public void startIntentActivity(Intent intent){

    }

}
