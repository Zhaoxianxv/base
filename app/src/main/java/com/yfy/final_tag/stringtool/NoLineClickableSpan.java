package com.yfy.final_tag.stringtool;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by zxx.
 * Date: 2021/10/12
 */
public class NoLineClickableSpan extends ClickableSpan {

    private IOnNoLineTextClick iOnNoLineTextClick;

    public NoLineClickableSpan() {
        super();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        iOnNoLineTextClick.onClick();
    }

    public void setOnNoLineTextClick(IOnNoLineTextClick i){
        iOnNoLineTextClick = i;
    }
    public interface IOnNoLineTextClick{
        public void onClick();
    }
}
