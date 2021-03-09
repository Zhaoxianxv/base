package com.yfy.final_tag.dialog;

import android.view.View;

/**
 * Created by zxx.
 * Date: 2021/2/5
 */
public interface PopClickListener {
    void popClick(View view);
    void popClick(CPWBean cpwBean, String type);
}
