package com.yfy.app.httppost.netHttp.action;


import com.yfy.final_tag.stringtool.Logger;

import rx.functions.Action1;

/**
 * Created by Aj
 */
public class EmptyAction<T> implements Action1<T> {
    @Override
    public void call(T o) {
        if (o instanceof Throwable) {
            Logger.e("EmptyAction:fail");
        } else {
            Logger.e("EmptyAction:success");
        }
    }
}
