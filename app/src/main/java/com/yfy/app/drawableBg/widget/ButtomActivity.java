package com.yfy.app.drawableBg.widget;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;

import butterknife.Bind;

public class ButtomActivity extends BaseActivity {


    @Bind(R.id.buttom_toggebuttom)
    ToggleButton toggle;
    @Bind(R.id.buttom_toggebuttom_text)
    TextView toggle_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_buttom_main);
        initView();
    }

    private void initView() {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    toggle_text.setText("女");
                }else{
                    toggle_text.setText("男");
                }
            }
        });
    }


}
