package com.yfy.app.voice;

public class Recorder {
    float time;
    String filePath;
    String mCurrentTime;

    public Recorder(float time, String filePath ,String currentTime) {
        super();
        this.time = time;
        this.filePath = filePath;
        this.mCurrentTime = currentTime;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getmCurrentTime() {
        return mCurrentTime;
    }

    public void setmCurrentTime(String mCurrentTime) {
        this.mCurrentTime = mCurrentTime;
    }
}
