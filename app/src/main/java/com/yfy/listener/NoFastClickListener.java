package com.yfy.listener;

import android.view.View;

public abstract class NoFastClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        if (isFastClick()){
            fastClick(view);
        }

    }
    private long lastClickTime = 0;//上次点击的时间
    private boolean isFastClick() {
        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick = currentTime - lastClickTime >= 500;      //是否允许点击
        lastClickTime = currentTime;
        return isAllowClick;
    }
    public abstract void fastClick(View view);
}
