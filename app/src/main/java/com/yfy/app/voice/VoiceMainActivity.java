package com.yfy.app.voice;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.yfy.app.voice.recorder.MediaManage;
import com.yfy.app.voice.unite.CurrentTime;
import com.yfy.app.voice.view.AudioRecorderButton;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;

import java.util.ArrayList;
import java.util.List;




public class VoiceMainActivity extends Activity {

	private ListView mListView;
	private ArrayAdapter<Recorder> mAdapter;
	private List<Recorder> mDatas = new ArrayList<>();
	

	private AudioRecorderButton mAudioRecorderButton;
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
		mListView = (ListView) findViewById(R.id.voiceNotesListView);
		mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.recorderButton);
		
		mAudioRecorderButton.setAudioFinishRecorderListenter(new AudioRecorderButton.AudioFinishRecorderListenter() {

					@Override
					public void onFinish(float seconds, String FilePath) {
						
						if ( !flag ) {
							topView.setVisibility(View.VISIBLE);
							flag = true;
						}
						
						currentTimeString=CurrentTime.getCurrentTime();
						
						Recorder recorder = new Recorder(seconds, FilePath, currentTimeString);
						mDatas.add(recorder);
						mAdapter.notifyDataSetChanged();
						mListView.setSelection(mDatas.size() - 1);
					}
				});

		mAdapter = new AudioRecorderAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);

		//mListView.getChildAt(index)
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
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
		
		
		

		// TODO
		
		
	}

	/**
	 * @author songshi
	 *
	 */

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MediaManage.pause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MediaManage.resume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MediaManage.release();
	}






	@PermissionSuccess(requestCode = TagFinal.VOICE_RECORD)
	private void voiceRecord() {

	}

	@PermissionFail(requestCode = TagFinal.CAMERA)
	private void showCamere() {
		Toast.makeText(getApplicationContext(), R.string.permission_fail_camere, Toast.LENGTH_SHORT).show();
	}

	@PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
	private void showTip1() {
		Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}
}
