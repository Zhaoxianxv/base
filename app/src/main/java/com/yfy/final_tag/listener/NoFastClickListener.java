package com.yfy.final_tag.listener;

import android.view.View;

import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.PopClickListener;
import com.yfy.view.OnMenuClickListener;

public abstract class NoFastClickListener implements View.OnClickListener , PopClickListener, OnMenuClickListener {
    @Override
    public void onClick(View view) {
        if (isFastClick()){
            fastClick(view);
        }

    }

    @Override
    public void popClick(View view) {
        if (isFastClick()){
            fastPopClick(view);
        }
    }
    @Override
    public void popClick(View view,String type) {
        if (isFastClick()){
            fastPopClick(view,type);
        }
    }
    @Override
    public void popClick(CPWBean cpwBean, String type) {
        if (isFastClick()){
            fastPopClick(cpwBean, type);
        }
    }



    @Override
    public void onMenuClick(View view, int position) {
        if (isFastClick()){
            fastMenuClick(view,position);
        }
    }

    private long lastClickTime = 0;//上次点击的时间
    //true可以点击（距离上次点击大于500有效）
    private boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick = (currentTime - lastClickTime) >= 1000;      //是否允许点击
//        Logger.e(String.format("%1$s:%2$s:%3$s:",lastClickTime,currentTime,currentTime - lastClickTime));
        lastClickTime = currentTime;
        return isAllowClick;
    }
    /**
     *     --------------------------------------------------------------------------------
     */

    public  void fastClick(View view){

    }

    public void fastMenuClick(View view, int position) {

    }
    public void fastPopClick(View view){


    }
    public void fastPopClick(View view,String type) {

    }
    public void fastPopClick(CPWBean cpwBean, String type){


    }
}
