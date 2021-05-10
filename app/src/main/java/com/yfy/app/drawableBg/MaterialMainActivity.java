package com.yfy.app.drawableBg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.stringtool.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

@SuppressLint("WrongConstant")
public class MaterialMainActivity extends BaseActivity {

    public DrawerLayout mDrawerLayout;
    public Button toggle;
    public ListView listView;
    public ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_materail_main);
        initView();

    }


    public void initView(){
        listView =findViewById(R.id.left_drawer);

        toggle=  findViewById(R.id.toggle);
        toggle.setText("drawer_open");
        mDrawerLayout=  findViewById(R.id.drawer_layout);

        //关联Toolbar
        //比如上图中的toolbar左边有三道杠，点击即可弹出DrawerLayout
        mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        //初始化状态
        mDrawerToggle.syncState();

//        mDrawerLayout.openDrawer(Gravity.START);      //      打开左边
//        mDrawerLayout.openDrawer(Gravity.END);        //      打开右边
//        mDrawerLayout.closeDrawer(Gravity.START);     //      关闭一边
//        mDrawerLayout.closeDrawers();       //      关闭所有

        toggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mDrawerLayout.isDrawerOpen(mDrawerToggle.);
                mDrawerLayout.openDrawer(Gravity.START);
//                mDrawerLayout.closeDrawers();
//                mDrawerLayout.openDrawer(listView);
            }
        });




        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Logger.e("---", "滑动中");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Logger.e("---", "打开");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Logger.e("---", "关闭");
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Logger.e("---", "状态改变");
            }
        });
    }

}
