package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class VersionDetailActivity extends BaseActivity {


    @BindView(R.id.version)
    TextView version;

    @BindView(R.id.version_school_weight)
    TextView version_weiht;
    @BindView(R.id.version_school_name)
    TextView version_name;
    @BindView(R.id.version_school_site)
    TextView version_site;
    @BindView(R.id.version_school_tell)
    TextView version_tell;
    @BindView(R.id.version_admin)
    TextView version_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_detail);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initSQtoolbar("关于我们");
    }
    private void initSQtoolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);
//        toolbar.setNavi(R.drawable.ic_back);

        version_name.setText(R.string.app_school_name);
        version_site.setText("地址：成都市青羊区青森小区南侧");
        version_weiht.setText(StringUtils.stringToGetTextJoint("版权所有 © %1$s",
                mActivity.getResources().getString(R.string.app_school_name)));
        version.setText(StringUtils.stringToGetTextJoint("(Version\t\t%1$s)",AppLess.$vername()));
        version_admin.setText(StringUtils.stringToGetTextJoint("Copyright(c) %1$s.All rights reserved.",
                mActivity.getResources().getString(R.string.app_school_name)));
//        version_admin.setText("Copyright(C) 2013 JinCheng Primary School of Chengdu Hi-Tech Zone");
        version_tell.setVisibility(View.GONE);
        version_tell.setText(StringUtils.stringToGetTextJoint("联系电话：%1$s","028-85311847"));
    }



    @OnClick(R.id.version_private)
    void setPrivate(){

        Intent intent = new Intent(mActivity, WebActivity.class);
        Bundle b = new Bundle();
        b.putString(TagFinal.URI_TAG, Base.HUA_WEI_PRIVATE);
        b.putString(TagFinal.TITLE_TAG, "隐私声明");
        intent.putExtras(b);
        startActivity(intent);
    }
    @OnClick(R.id.version_agreement)
    void setAgreement(){

        Intent intent = new Intent(mActivity, WebActivity.class);
        Bundle b = new Bundle();
        b.putString(TagFinal.URI_TAG, Base.HUA_WEI_AGREEMENT);
        b.putString(TagFinal.TITLE_TAG, "用户协议");
        intent.putExtras(b);
        startActivity(intent);
    }
}
