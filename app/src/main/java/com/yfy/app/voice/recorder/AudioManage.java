package com.yfy.app.voice.recorder;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioManage {

	private MediaRecorder mMediaRecorder;
	private String mDir;
	private String mCurrentFilePath;

	private static AudioManage mInstance;

	private boolean isPrepared;

	private AudioManage(String dir) {
		mDir = dir;
	}

	/**

	 * @author songshi
	 *
	 */
	public interface AudioStateListenter {
		void wellPrepared();
	}

	public AudioStateListenter mListenter;

	public void setOnAudioStateListenter(AudioStateListenter audioStateListenter) {
		mListenter = audioStateListenter;
	}
	
	/**

	 * @param dir
	 * @return
	 */
	public static AudioManage getInstance(String dir) {
		if (mInstance == null) {
			synchronized (AudioManage.class) {
				if (mInstance == null) {
					mInstance = new AudioManage(dir);
				}
			}
		}

		return mInstance;
	}

	/**

	 */
	public void prepareAudio() {

		try {
			isPrepared = false;

			File dir = new File(mDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = GenerateFileName();
			File file = new File(dir, fileName);


			mMediaRecorder = new MediaRecorder();
			mCurrentFilePath = file.getAbsolutePath();
			mMediaRecorder.setOutputFile(file.getAbsolutePath());
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			mMediaRecorder.prepare();

			mMediaRecorder.start();
			
			isPrepared = true;

			if (mListenter != null) {
				mListenter.wellPrepared();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return
	 */
	private String GenerateFileName() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString() + ".amr";
	}

	/**
	 * @param maxLevel
	 * @return
	 */
	public int getVoiceLevel(int maxLevel) {
		if (isPrepared) {
			try {
				return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return 1;
	}

	/**
	 */
	public void release() {
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}

	/**
	 */
	public void cancel() {

		release();

		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath = null;
		}
	}

	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return mCurrentFilePath;
	}
}
