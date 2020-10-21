package com.yfy.app.Record;

import android.media.MediaRecorder;
import android.os.Environment;

import com.yfy.final_tag.FileTools;

import java.io.File;
import java.io.IOException;

public class Record {
    MediaRecorder mMediaRecorder;
    File mAudioFile;
    long mStartRecordTime;
    private boolean doStartRecord() {
        try {
            mMediaRecorder = new MediaRecorder();
            mAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yfy/" + System.currentTimeMillis() + ".m4a");
            FileTools.createFile(mAudioFile);


            //设置从麦克风采集声音
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //保存文件为mp4的格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            //设置所有android系统都支持的采样频率
            mMediaRecorder.setAudioSamplingRate(44100);

            //设置acc的编码方式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            //设置比较好的音质
            mMediaRecorder.setAudioEncodingBitRate(96000);

            mMediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());

            mMediaRecorder.prepare();
            mMediaRecorder.start();

            mStartRecordTime = System.currentTimeMillis();

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return false;
        }finally {
//            if(mAudioRecord != null){
//                mAudioRecord.release();
//            }
        }

        return true;
    }
    public void releaseRecord(){

        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

}
