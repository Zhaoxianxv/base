package com.yfy.final_tag.hander;

import android.os.AsyncTask;

import java.util.List;


public class PicAsyncTask extends AsyncTask<String, Integer, List<String>>{


    SaveImageAsync api;

    public PicAsyncTask(SaveImageAsync api) {
        this.api = api;
    }

    @Override
    protected List<String>  doInBackground(String... strings) {
        if (isCancelled()) {
            return null;
        }
        if (api!=null){
            return api.doIn(strings);
        }else{
            return null;
        }

    }
    //onPostExecute方法用于在执行完后台任务doInBackground后更新UI,显示结果
    @Override
    protected void onPostExecute(List<String> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (api!=null){
            api.doUpData(result);
        }

    }

    //onProgressUpdate方法用于更新进度信息
    protected void onProgressUpdate(Integer... integers) {
        super.onProgressUpdate(integers);

    }
    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (api!=null){
            api.onPre();
        }
    }





}
