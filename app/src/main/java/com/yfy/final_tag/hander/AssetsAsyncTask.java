package com.yfy.final_tag.hander;

import android.app.Activity;
import android.os.AsyncTask;

import com.yfy.base.App;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by zxx.
 * Date: 2021/1/19
 */
public class AssetsAsyncTask extends AsyncTask<String, Integer, String> {

    AssetsGetFileData api;

    public AssetsAsyncTask(AssetsGetFileData api) {
        this.api = api;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (isCancelled()) {
            return "";
        }

        return getAssetsFileJson(strings[0]);
    }
    //onPostExecute方法用于在执行完后台任务doInBackground后更新UI,显示结果
    @Override
    protected void onPostExecute(String result) {
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
    }





    public static String getAssetsFileJson(String file_name) {

        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = App.getApp().getApplicationContext().getAssets().open(file_name);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "{result:\"false\"}";
    }


}
