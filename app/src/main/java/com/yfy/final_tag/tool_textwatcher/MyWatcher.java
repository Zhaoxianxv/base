package com.yfy.final_tag.tool_textwatcher;

import android.text.TextWatcher;

/**
 * Created by yfy on 2017/10/16.
 */

public abstract class MyWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
