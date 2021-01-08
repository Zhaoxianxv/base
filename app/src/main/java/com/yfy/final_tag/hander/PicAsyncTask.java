package com.yfy.final_tag.hander;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;


public abstract class PicAsyncTask extends AsyncTask<String, Integer, Void>{

    public List<String> content=new ArrayList<>();
    @Override
    protected Void doInBackground(String... strings) {
        if (isCancelled()) {
            return null;
        }
        content=doIn(strings);
        return null;
    }
    //onPostExecute方法用于在执行完后台任务doInBackground后更新UI,显示结果
    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        doUpData(content);
    }

    //onProgressUpdate方法用于更新进度信息
    protected void onProgressUpdate(Integer... integers) {
        super.onProgressUpdate(integers);

    }
    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPre();
    }



    public abstract List<String> doIn(String... arg0);
    public abstract void doUpData(List<String> list);
    public abstract void onPre();

}
