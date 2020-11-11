package com.yfy.app.voice.view;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import com.yfy.app.voice.recorder.AudioDialogManage;
import com.yfy.app.voice.recorder.AudioManage;
import com.yfy.app.voice.recorder.AudioManage.AudioStateListenter;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;


/**
 */
public class AudioRecorderButton extends Button implements AudioStateListenter {

	/**

	 */
	private static final int STATE_NORMAL = 1;
	private static final int STATE_RECORDERING = 2;
	private static final int STATE_WANT_TO_CALCEL = 3;

	private int mCurState = STATE_NORMAL;
	private boolean isRecordering = false;
	private boolean mReady;

	private static final int DISTANCE_Y_CANCEL = 50;

	private AudioDialogManage audioDialogManage;

	private AudioManage mAudioManage;
	
	/**

	 * @author songshi
	 *
	 */
	public interface AudioFinishRecorderListenter{
		void onFinish(float seconds, String FilePath);
	}
	
	private AudioFinishRecorderListenter mListenter;
	
	public void setAudioFinishRecorderListenter(AudioFinishRecorderListenter listenter){
		this.mListenter=listenter;
	}
	

	public AudioRecorderButton(Context context) {
		super(context, null);
		// TODO Auto-generated constructor stub
	}
	public AudioRecorderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		audioDialogManage = new AudioDialogManage(getContext());

		String dir = TagFinal.getAppDir("voice");
		mAudioManage = AudioManage.getInstance(dir);
		mAudioManage.setOnAudioStateListenter(this);

		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				mReady = true;
				mAudioManage.prepareAudio();
				//return true;
				return false;
			}
		});
		// TODO Auto-generated constructor stub
	}

	/* 
	 * @see android.widget.TextView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		
		case MotionEvent.ACTION_DOWN:
			changeState(STATE_RECORDERING);
			break;

		case MotionEvent.ACTION_MOVE:

			if (isRecordering) {
				if (wantToCancel(x, y)) {
					changeState(STATE_WANT_TO_CALCEL);
				} else {
					changeState(STATE_RECORDERING);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			if (!mReady) {
				reset();
				return super.onTouchEvent(event);
			}

			if (!isRecordering || mTime < 0.7f) {
				audioDialogManage.tooShort();
				mAudioManage.cancel();
				mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
			} 
			
			else if (mCurState == STATE_RECORDERING) {
				audioDialogManage.dimissDialog();
				// release
				mAudioManage.release();
				// callbackToAct
				if(mListenter!=null){
					mListenter.onFinish(mTime, mAudioManage.getCurrentFilePath());
				}				
				
			} else if (mCurState == STATE_WANT_TO_CALCEL) {
				audioDialogManage.dimissDialog();
				mAudioManage.cancel();
			}

			reset();
			break;
		}
		return super.onTouchEvent(event);
	}

	/**

	 */
	private void reset() {
		isRecordering = false;
		mReady = false;
		mTime = 0;
		changeState(STATE_NORMAL);
	}

	private boolean wantToCancel(int x, int y) {

		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}
		return false;
	}

	/**
	 * @param state
	 */
	private void changeState(int state) {
		if (mCurState != state) {
			mCurState = state;
			switch (state) {
			case STATE_NORMAL:
				setBackgroundResource(R.drawable.voice_recorder_normal);
				setText(R.string.str_recorder_normal);
				break;

			case STATE_RECORDERING:
				setBackgroundResource(R.drawable.voice_recorder_recordering);
				setText(R.string.str_recorder_recording);
				if (isRecordering) {
					audioDialogManage.recording();
				}
				break;

			case STATE_WANT_TO_CALCEL:
				setBackgroundResource(R.drawable.voice_recorder_recordering);
				setText(R.string.str_recorder_want_cancel);
				audioDialogManage.wantToCancel();
				break;
			}
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see songshi.voicenotes.recorder.AudioManage.AudioStateListenter#wellPrepared()
	 */
	@Override
	public void wellPrepared() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
	}
	
	private static final int MSG_AUDIO_PREPARED = 0x110;
	private static final int MSG_VOICE_CHANGE = 0x111;
	private static final int MSG_DIALOG_DIMISS = 0x112;
	
	/**
	 */
	private Handler mHandler = new Handler() {
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_AUDIO_PREPARED:        //216:mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
				audioDialogManage.showRecorderingDialog();
				isRecordering = true;
				new Thread(mGetVoiceLevelRunnable).start();
				break;

			case MSG_VOICE_CHANGE:          //265:mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
				audioDialogManage.updateVoiceLevel(mAudioManage
						.getVoiceLevel(7));
				break;


			case MSG_DIALOG_DIMISS:         //125:mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
				audioDialogManage.dimissDialog();
				break;
			}
		};
	};
	
	private float mTime;
	/**
	 */
	private Runnable mGetVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			
			while (isRecordering) {
				
				try {
					Thread.sleep(100);
					mTime += 0.1f;
					mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	};
	
}
