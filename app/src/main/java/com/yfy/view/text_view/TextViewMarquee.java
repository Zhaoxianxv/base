package com.yfy.view.text_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yfy on 2017/9/15.
 */

public class TextViewMarquee extends TextView {
    public TextViewMarquee(Context context) {
        super(context);
    }

    public TextViewMarquee(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewMarquee(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
