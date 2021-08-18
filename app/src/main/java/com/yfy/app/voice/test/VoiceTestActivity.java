package com.yfy.app.voice.test;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yfy.app.voice.AudioRecorderAdapter;
import com.yfy.app.voice.Recorder;
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
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.GreenDaoManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class VoiceTestActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_main);
		PermissionTools.tryVoice(mActivity);

	}


}
