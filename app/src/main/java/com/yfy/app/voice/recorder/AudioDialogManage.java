package com.yfy.app.voice.recorder;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yfy.base.R;


/**
 * @author songshi
 *
 */
public class AudioDialogManage {
	private Dialog mDialog;

	private ImageView mIcon;
	private ImageView mVoice;

	private TextView mLabel;

	private Context mContext;

	public AudioDialogManage(Context context) {
		this.mContext = context;
	}

	/**

	 */
	public void showRecorderingDialog() {
		mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.voicenotes_recorder_dialog, null);
		mDialog.setContentView(view);

		mIcon = (ImageView) mDialog.findViewById(R.id.recorder_dialog_icon);
		mVoice = (ImageView) mDialog.findViewById(R.id.recorder_dialog_voice);
		mLabel = (TextView) mDialog.findViewById(R.id.recorder_dialog_label);

		mDialog.show();
	}

	/**
	 */
	public void recording() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.VISIBLE);
			mLabel.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.recorder);
			mLabel.setText("��ָ������ȡ��¼��");
		}
	}

	/**
	 */
	public void wantToCancel() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);

			mIcon.setImageResource(R.drawable.ic_cancel_circle);
			mLabel.setText("�ɿ���ָ��ȡ��¼��");
		}
	}

	/**
	 */
	public void tooShort() {
		if (mDialog != null && mDialog.isShowing()) {
			
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);
			
			mIcon.setImageResource(R.drawable.voice_to_short);
			mLabel.setText("¼��ʱ�����");
		}
	}

	/**
	 * mDialog.dismiss();
	 */
	public void dimissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}	
	}

	/**
	 * @param level
	 */
	public void updateVoiceLevel(int level) {
		if (mDialog != null && mDialog.isShowing()) {
			
			int voiceResId=mContext.getResources().getIdentifier("v"+level, "drawable", mContext.getPackageName());  //getIdentifier()��ȡӦ�ð���ָ����Դ��ID
			mVoice.setImageResource(voiceResId);
		}
	}
}
