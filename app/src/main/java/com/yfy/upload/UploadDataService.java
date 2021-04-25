package com.yfy.upload;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;

import com.google.gson.Gson;
import com.yfy.app.bean.BaseRes;
import com.yfy.base.App;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.hander.HtmlAsyncTask;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yfy1 on 2016/9/20.
 */
public class UploadDataService extends Service{

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        getVersion();

    }

    /**
     *  绑定服务时才会调用
     *  此方法必须重写，但在启动状态的情况下直接返回 null
     */
  
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 每次通过startService()方法启动Service时都会被回调。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand invoke");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 服务销毁时的回调
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }





    private PackageInfo info;
    public void getVersion() {
        try {
            PackageManager manager = App.getApp().getApplicationContext().getPackageManager();
            info = manager.getPackageInfo(App.getApp().getApplicationContext().getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (info==null)return;
        mTask=new GetHtmlAsyncTask();
        mTask.execute();
    }





    public GetHtmlAsyncTask mTask;
    @SuppressLint("StaticFieldLeak")
    class GetHtmlAsyncTask extends HtmlAsyncTask {
        @Override
        public String doIn(String... arg0) {
            StringBuilder sb = new StringBuilder();
            String lines;
            try {
                HttpURLConnection conn;
                URL url = new URL(Base.AUTO_UPDATE_URI);
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("conn","Keep-Alive");
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((lines = reader.readLine()) != null){
                    sb.append(lines);
                }
                reader.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sb.toString().length() != 0) {
                return sb.toString();
            }else {
                return "";
            }
        }
        @Override
        public void doUpData(String result) {
            if (StringJudge.isEmpty(result)){
                ViewTool.showToastShort(getApplicationContext(),"没有数据");
            }else{
                Gson gson=new Gson();
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (StringJudge.isEmpty(res.getPackagename())){
                    ViewTool.showToastShort(getApplicationContext(),"没有获取到应用");
                }else{
                    if (res.getPackagename().equalsIgnoreCase(info.packageName)){
                        if (res.getVersionCode()>info.versionCode){
                            Intent intent=new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            intent.setClass(getApplicationContext(),UpDataDialogActivity.class);
                            intent.putExtra(Base.id,res.getUrl());
                            startActivity(intent);
                        }
                    }

                }
            }
            stopService(new Intent(getApplicationContext(),UploadDataService.class));
        }
        @Override
        public void onPre() {

        }

        @Override
        public void onProgress(Integer integers) {

        }
    }



}
