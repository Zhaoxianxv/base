package com.yfy.final_tag.hander;

import android.os.AsyncTask;


public abstract class HtmlAsyncTask extends AsyncTask<String, Integer, String>{


    @Override
    protected String doInBackground(String... strings) {
        if (isCancelled()) {
            return null;
        }
        return doIn(strings);
    }
    //onPostExecute方法用于在执行完后台任务doInBackground后更新UI,显示结果
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        doUpData(result);
    }

    //onProgressUpdate方法用于更新进度信息
    protected void onProgressUpdate(Integer... integers) {
        super.onProgressUpdate(integers);
        onProgress(integers[0]);
    }
    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPre();
    }



    public abstract String doIn(String... arg0);
    public abstract void doUpData(String content);
    public abstract void onPre();
    public abstract void onProgress(Integer integers);

}
