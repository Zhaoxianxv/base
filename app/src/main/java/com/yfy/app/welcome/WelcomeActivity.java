package com.yfy.app.welcome;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.yfy.app.PEquality.PEQualityMainTestActivity;
import com.yfy.app.PEquality.tea.PETeaMainActivity;
import com.yfy.app.SelectedUserTypeActivity;
import com.yfy.app.login.LoginActivity;
import com.yfy.app.welcome.adapter.GuidePagerAdapter;
import com.yfy.app.welcome.utils.v4.FragmentPagerItem;
import com.yfy.app.welcome.utils.v4.FragmentPagerItemAdapter;
import com.yfy.app.welcome.utils.v4.FragmentPagerItems;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.glide.BitmapLess;
import com.yfy.jpush.ExampleUtil;
import com.yfy.jpush.LocalBroadcastManager;


public class WelcomeActivity extends BaseActivity {

    ImageView iv_splash;
    ViewPager vp_guide;
    SmartTabLayout tab_indicator;
    TextView btn_done;
    GuidePagerAdapter guidePagerAdapter;
    Bitmap splashBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_welcome);
        registerMessageReceiver();
        initView();
    }

    private void initView() {
//        splashBitmap = BitmapLess.$drawableColor(this,R.mipmap.icon_launcher,ColorRgbUtil.getBaseColor());
        splashBitmap = BitmapLess.$drawable(this,R.mipmap.icon_launcher);
        if (UserPreferences.getInstance().getIsFirstTimeOpen()) {
//        if (false) {
            showGuide();//显示引导内容
        } else {
            showSplash();//显示闪屏图片
        }
    }

    private void showGuide() {
        vp_guide =  findViewById(R.id.splash_vp_guide);
        tab_indicator =  findViewById(R.id.splash_tab_guide);
        btn_done =  findViewById(R.id.guide_btn_done);
        vp_guide.setVisibility(View.VISIBLE);
        tab_indicator.setVisibility(View.VISIBLE);
        //adapter
        FragmentPagerItemAdapter adapter = createFragmentPagerItemAdapter();
        vp_guide.setAdapter(adapter);
        vp_guide.setOffscreenPageLimit(3);
        vp_guide.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tab_indicator.setVisibility(View.GONE);
                    btn_done.setVisibility(View.VISIBLE);
                } else {
                    tab_indicator.setVisibility(View.VISIBLE);
                    btn_done.setVisibility(View.GONE);
                }
            }
        });
        //关联
        tab_indicator.setViewPager(vp_guide);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_guide.setVisibility(View.GONE);
                tab_indicator.setVisibility(View.GONE);
                btn_done.setVisibility(View.GONE);
                showSplash();
                UserPreferences.getInstance().saveFirstTimeOpen(false);
            }
        });
    }

    private void showSplash() {
        iv_splash =  findViewById(R.id.splash_iv_splash);
        iv_splash.setVisibility(View.VISIBLE);
        ViewTools.setImageBitmap(iv_splash, splashBitmap);
        iv_splash.setBackgroundColor(Color.WHITE);
        iv_splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                //初始化登录信息
                startActivity(new Intent(WelcomeActivity.this,SelectedUserTypeActivity.class));
//                startActivity(new Intent(WelcomeActivity.this,SelectedModeTypeActivity.class));
                finish();






//                if (Base.user==null){
//                    Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
//                    startActivityForResult(intent,TagFinal.UI_ADD);
//                }else{
//                    album_select.showAtCenter();
//                }
            }

        }, 1000);
    }


    private FragmentPagerItemAdapter createFragmentPagerItemAdapter() {
        FragmentPagerItems pages = new FragmentPagerItems(WelcomeActivity.this);
        for (int i = 0; i < 3; i++) {
            pages.add(FragmentPagerItem.of("", GuideImageFragment.class));
        }
        return new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vp_guide = null;
        tab_indicator = null;
        ViewTools.releaseImageView(iv_splash);
        iv_splash = null;
        ViewTools.releaseBitmap(splashBitmap);
        splashBitmap = null;
        guidePagerAdapter = null;
        btn_done = null;
    }








    public MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
//    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE).append(" : " ).append( messge ).append("\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_MESSAGE).append(" : " ).append( extras ).append("\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }

        private void setCostomMsg(String string) {
            // TODO Auto-generated method stub

        }
    }
}
