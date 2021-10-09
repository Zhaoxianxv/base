package com.yfy.app.voice;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.yfy.app.voice.recorder.MediaManage;
import com.yfy.app.voice.view.AudioRecorderButton;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.DateUtils;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.GreenDaoManager;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;


public class VoiceMainActivity extends BaseActivity {

	private ListView mListView;
	private ArrayAdapter<Recorder> mAdapter;
	public List<Recorder> mDatas = new ArrayList<>();


	public AudioRecorderButton mAudioRecorderButton;
	private View mAnimView;
	
	private View topView;
	private boolean flag = false;

	public static String currentTimeString = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_main);

		PermissionTools.tryVoice(VoiceMainActivity.this);
		topView=findViewById(R.id.topView);
		mListView =  findViewById(R.id.voiceNotesListView);
		mAudioRecorderButton =  findViewById(R.id.recorderButton);
		
		mAudioRecorderButton.setAudioFinishRecorderListenter(new AudioRecorderButton.AudioFinishRecorderListenter() {

					@Override
					public void onFinish(float seconds, String FilePath) {
						
						if ( !flag ) {
							topView.setVisibility(View.VISIBLE);
							flag = true;
						}
						
						currentTimeString=DateUtils.getCurrentTimeDefault();
						
						Recorder recorder = new Recorder(seconds, FilePath, currentTimeString);


						saveRecorder(recorder);
						mDatas.add(recorder);
						mAdapter.notifyDataSetChanged();
						mListView.setSelection(mDatas.size() - 1);
					}
				});

		mAdapter = new AudioRecorderAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);


		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				if (mAnimView != null) {
					mAnimView.setBackgroundResource(R.drawable.voice_right3);
					mAnimView = null;
				}
				

				mAnimView = view.findViewById(R.id.recorder_anim);
				mAnimView.setBackgroundResource(R.drawable.audio_play_anim);
				AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
				anim.start();
				

				MediaManage.playSound(mDatas.get(position).filePath,
						new MediaPlayer.OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {

								mAnimView.setBackgroundResource(R.drawable.voice_right3);
							}
						});
			
			}

		});


		List<KeyValueDb> db_index=GreenDaoManager.getInstance().getKeyValueDbList("where type = \"voice\"");

		if (StringJudge.isNotEmpty(db_index)) {


			for (KeyValueDb db:db_index){
				Recorder recorder=new Recorder();
				recorder.setFilePath(db.getFile_path());
				recorder.setmCurrentTime(db.getTime());
				recorder.setTime(db.getTime_duration());//语音时间长度
				mDatas.add(recorder);
			}
		}

		mAdapter.notifyDataSetChanged();

	}




    private void saveRecorder(Recorder recorder){
        KeyValueDb keyValue=new KeyValueDb();
        keyValue.setTime(recorder.getmCurrentTime());
        keyValue.setTime_duration(recorder.getTime() );
        keyValue.setType("voice");
        keyValue.setFile_path(recorder.getFilePath());
        GreenDaoManager.getInstance().saveKeyValueDb(keyValue);
    }
	/**
	 * @author songshi
	 */

	
	@Override
	public void onPause() {
		super.onPause();
		MediaManage.pause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MediaManage.resume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MediaManage.release();
	}






	@PermissionSuccess(requestCode = TagFinal.VOICE_RECORD)
	public void voiceRecord() {
		ViewTool.showToastShort(mActivity,"没有获取到麦克风权限！");
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}
}
