package com.yfy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.glide.DrawableLess;
import com.yfy.final_tag.tool_textwatcher.MyWatcher;
import com.yfy.final_tag.viewtools.ViewTool;


/**
 *
 */
@SuppressLint("AppCompatCustomView")
public class ClearableEditText extends EditText {
    private TextWatcherCallBack mCallback;
    //右侧删除图标
    private Drawable rigthDrawable;
    public ClearableEditText(Context context) {
        super(context);
        init(context);
    }
    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void init(Context context) {
        rigthDrawable = DrawableLess.$tint(context.getResources().getDrawable(R.drawable.ic_delete_red),ColorRgbUtil.getGray());
        mCallback = null;
        //重写了TextWatcher，在具体实现时就不用每个方法都实现，减少代码量
        TextWatcher textWatcher = new MyWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(), true);
                //如果有在activity中设置回调，则此处可以触发
                if(mCallback != null)
                    mCallback.handleMoreTextChanged();
            }
        };
        this.addTextChangedListener(textWatcher);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(), hasFocus);
            }
        });
    }
    //当内容不为空，而且获得焦点，才显示右侧删除按钮
    public void updateCleanable(int length, boolean hasFocus){
        if(length() > 0 && hasFocus)
            setCompoundDrawablesWithIntrinsicBounds(null, null, rigthDrawable, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
        Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            //检查点击的位置是否是右侧的删除图标
            //注意，使用getRawX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
            int leftEdgeOfRightDrawable = getRight() - getPaddingRight() - rightIcon.getBounds().width();
            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void finalize() throws Throwable {
        rigthDrawable = null;
        super.finalize();
    }
    interface TextWatcherCallBack {
        public void handleMoreTextChanged();
    }

}
