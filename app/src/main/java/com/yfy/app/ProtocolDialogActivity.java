package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;

import com.yfy.base.App;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.NoLineClickableSpan;
import com.yfy.final_tag.stringtool.NoLineClickableSpan.IOnNoLineTextClick;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("NonConstantResourceId")
public class ProtocolDialogActivity extends BaseActivity {


    @BindView(R.id.tv_content_protocol)
    AppCompatTextView tv_content;

    @BindView(R.id.tv_title_protocol)
    AppCompatTextView tv_title_top;

    @BindView(R.id.tv_exit_app_protocol)
    AppCompatTextView tv_exit_app;
    @OnClick(R.id.tv_exit_app_protocol)
    void setExit(){
        App.getApp().onTerminate();
    }

    @BindView(R.id.tv_pass_app_protocol)
    AppCompatTextView tv_pass_protocol;
    @OnClick(R.id.tv_pass_app_protocol)
    void setPass(){

        UserPreferences.getInstance().saveFirstTimeOpen(false);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocol_main);
        getData();
        initView();
    }

    public String load_path;
    public void getData(){
        Intent intent=getIntent();
        load_path=intent.getStringExtra(Base.id);
    }




    public String protocol_title="《用户协议》";
    public String private_title="《隐私声明》";
    public String one="已阅读";
    public String three="内容，并了解我们如何收集、处理个人信息。";
    public String two="\n\n系统权限：在使用时，我们可能会申请系统设备权限收集设备信息用于推送,设备储存权限用于保存数据,以及拨打电话、使用摄像头权限";
    public void initView(){
        tv_title_top.setText(StringUtils.stringToGetTextJoint("欢迎使用%1$s",StringUtils.getResourceString(mActivity,R.string.app_name)));
        tv_exit_app.setText("退出应用");
        tv_pass_protocol.setText("同意");
        tv_content.setMovementMethod(new ScrollingMovementMethod());

        tv_content.setMovementMethod(LinkMovementMethod.getInstance());

        //protocol
        SpannableString protocol_span = new SpannableString(protocol_title);

        NoLineClickableSpan span_pro = new NoLineClickableSpan();
        span_pro.setOnNoLineTextClick(new IOnNoLineTextClick() {

            @Override
            public void onClick() {
//                ViewTool.showToastShort(mActivity,"protocol");
                Intent intent = new Intent(mActivity, WebActivity.class);
                Bundle b = new Bundle();
                b.putString(TagFinal.URI_TAG, Base.HUA_WEI_AGREEMENT);
                b.putString(TagFinal.TITLE_TAG, "用户协议");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        protocol_span.setSpan(span_pro, 0, protocol_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        //private
        SpannableString private_span = new SpannableString(private_title);


        private_span.setSpan(
                new ForegroundColorSpan(ColorRgbUtil.getResourceColor(mActivity,R.color.OrangeRed)),
                0,
                private_title.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        NoLineClickableSpan span = new NoLineClickableSpan();
        span.setOnNoLineTextClick(new IOnNoLineTextClick() {

            @Override
            public void onClick() {
//                ViewTool.showToastShort(mActivity,"private");
                Intent intent = new Intent(mActivity, WebActivity.class);
                Bundle b = new Bundle();
                b.putString(TagFinal.URI_TAG, Base.HUA_WEI_PRIVATE);
                b.putString(TagFinal.TITLE_TAG, "隐私声明");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        private_span.setSpan(span, 0, private_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(one).append(protocol_span).append("与").append(private_span).append(three).append(two);

        tv_content.setText(sb);

    }







    /**
     * 双击退出函数
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit ;
        if (isExit ) {
            App.getApp().onTerminate();
        } else {
            isExit = true;
            ViewTool.showToastShort(mActivity,"再按一次退出更新");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }
    }


}
