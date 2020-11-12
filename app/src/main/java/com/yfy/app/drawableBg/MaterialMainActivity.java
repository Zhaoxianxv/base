package com.yfy.app.drawableBg;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;


public class MaterialMainActivity extends BaseActivity {

    public DrawerLayout mDrawerLayout;
    public Button toggle;
    public ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_materail_main);
        initView();

    }


    public void initView(){
        toggle=  findViewById(R.id.toggle);
        mDrawerLayout=  findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        //初始化状态
        mDrawerToggle.syncState();

//        mDrawerLayout.openDrawer(Gravity.START);      //      打开左边
//        mDrawerLayout.openDrawer(Gravity.END);        //      打开右边
//        mDrawerLayout.closeDrawer(Gravity.START);     //      关闭一边
//        mDrawerLayout.closeDrawers();                 //      关闭所有

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
    }

}
