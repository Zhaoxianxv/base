package com.yfy.upload;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
    public String name,oldname,packagename;
    public int oldcode;
    String lines;


    @Override
    public void onCreate() {
        super.onCreate();
        getVersion();

    }


    public String getVersion() {
        try {
            PackageManager manager = App.getApp().getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getApp().getApplicationContext().getPackageName(), 0);
            oldname = info.versionName;
            oldcode = info.versionCode;
            packagename = info.packageName;
            return oldname;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

  
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }








    public GetHtmlAsyncTask mTask;
    @SuppressLint("StaticFieldLeak")
    class GetHtmlAsyncTask extends HtmlAsyncTask {
        @Override
        public String doIn(String... arg0) {
            StringBuilder sb = new StringBuilder();

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
        public void doUpData(String list) {
            if (StringJudge.isEmpty(list)){
                ViewTool.showToastShort(getApplicationContext(),"没有数据，请从新尝试");
            }else{
                Gson gson=new Gson();
                BaseRes res=gson.fromJson(content, BaseRes.class);
                if (StringJudge.isEmpty(res.getPackagename())){

                    ViewTool.showToastShort(getApplicationContext(),"没有获取到应用");
                }else{
                    if (res.getPackagename().equalsIgnoreCase(packagename)){
                        if (res.getVersionCode()>oldcode){
                            Intent i=new Intent();
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setClass(getApplicationContext(),UpDataDialogActivity.class);
                            startActivity(i);
                        }
                    }

                }


            }
        }
        @Override
        public void onPre() {

        }
    }



}
