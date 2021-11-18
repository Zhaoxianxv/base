package com.yfy.final_tag.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yfy.app.WebActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.NoLineClickableSpan;
import com.yfy.final_tag.stringtool.StringUtils;

import androidx.appcompat.widget.AppCompatTextView;

public class ProtocolPopupWindow extends PopupWindow {

	public Activity mActivity;
	public AppCompatTextView tv_protocol_title,tv_protocol_content;




	public ProtocolPopupWindow(Activity mActivity) {
		super(mActivity);
		this.mActivity = mActivity;
		initAlize();
	}


	private void initAlize() {
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		View view = inflater.inflate(R.layout.protocol_main, null);
		AppCompatTextView pass = view.findViewById(R.id.tv_pass_app_protocol);//
		AppCompatTextView exit = view.findViewById(R.id.tv_exit_app_protocol);//
		exit.setText("退出应用");
		pass.setText("同意");
		pass.setOnClickListener(onClickListener);
		exit.setOnClickListener(onClickListener);

		tv_protocol_title = view.findViewById(R.id.tv_title_protocol);//
		tv_protocol_content = view.findViewById(R.id.tv_content_protocol);//
		tv_protocol_title.setText(StringUtils.stringToGetTextJoint("欢迎使用%1$s", StringUtils.getResourceString(mActivity,R.string.app_name)));

		initView(tv_protocol_content);


		setContentView(view);
		initWindow();
	}

	private void initWindow() {
		DisplayMetrics d = mActivity.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 0.8));//width比例
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(false);//没有焦点
		this.setOutsideTouchable(true);//
		this.update();
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha(mActivity, 1.0f);//0.0-1.0


		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha(mActivity, 1f);//
			}
		});
	}

	//设置添加屏幕的背景透明度
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}


	public void showAtCenter() {
		backgroundAlpha(mActivity, 0.1f);//0.0-1.0
		showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}



	public View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			if (listener != null) {
				listener.popClick(v);
			}

		}
	};

	private PopClickListener listener = null;

	public void setOnPopClickListener(PopClickListener listener) {
		this.listener = listener;
	}








	public String protocol_title="《用户协议》";
	public String private_title="《隐私声明》";
	public String one="已阅读";
	public String three="内容，并了解我们如何收集、处理个人信息。";
	public String two="\n\n系统权限：在使用时可能需要联网、拨打电话、使用摄像头，我们可能会申请系统设备权限收集设备信息用于推送,设备储存权限用于保存数据,以及拨打电话、使用摄像头权限";
	public String four="\n\n集成：在使用时，我们可能会申请系统设备权限收集设备信息用于推送,设备储存权限用于保存数据,以及拨打电话、使用摄像头权限";
	public void initView(AppCompatTextView tv_content){
		tv_content.setMovementMethod(new ScrollingMovementMethod());

		tv_content.setMovementMethod(LinkMovementMethod.getInstance());

		//protocol
		SpannableString protocol_span = new SpannableString(protocol_title);

		NoLineClickableSpan span_pro = new NoLineClickableSpan();
		span_pro.setOnNoLineTextClick(new NoLineClickableSpan.IOnNoLineTextClick() {

			@Override
			public void onClick() {
				Intent intent = new Intent(mActivity, WebActivity.class);
				Bundle b = new Bundle();
				b.putString(TagFinal.URI_TAG, Base.HUA_WEI_AGREEMENT);
				b.putString(TagFinal.TITLE_TAG, "用户协议");
				intent.putExtras(b);
				mActivity.startActivity(intent);
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
		span.setOnNoLineTextClick(new NoLineClickableSpan.IOnNoLineTextClick() {

			@Override
			public void onClick() {
				Intent intent = new Intent(mActivity, WebActivity.class);
				Bundle b = new Bundle();
				b.putString(TagFinal.URI_TAG, Base.HUA_WEI_PRIVATE);
				b.putString(TagFinal.TITLE_TAG, "隐私声明");
				intent.putExtras(b);
				mActivity.startActivity(intent);
			}
		});
		private_span.setSpan(span, 0, private_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		SpannableStringBuilder sb=new SpannableStringBuilder();
		sb.append(one).append(protocol_span).append("与").append(private_span).append(three).append(two);

		tv_content.setText(sb);

	}




}
