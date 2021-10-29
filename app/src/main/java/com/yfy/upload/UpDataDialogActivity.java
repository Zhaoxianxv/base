package com.yfy.upload;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.FileTools;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.hander.HtmlAsyncTask;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


@SuppressLint("NonConstantResourceId")
public class UpDataDialogActivity extends BaseActivity {

    public RelativeLayout do_layout;
    public AppCompatTextView exit_txt, upData_txt,upData_title,upData_content;
    public ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_data_dialog);
        getData();
        initView();
    }

    public String load_path;
    public void getData(){
        Intent intent=getIntent();
        load_path=intent.getStringExtra(Base.id);
    }
    private void initView() {
        do_layout =  findViewById(R.id.up_data_app_do_layout);
        upData_txt =  findViewById(R.id.up_data_app);
        exit_txt =  findViewById(R.id.up_data_exit);
        progress=findViewById(R.id.up_data_progress);
        upData_title=findViewById(R.id.up_data_app_title);
        upData_content=findViewById(R.id.up_data_app_content);

        upData_title.setText("提示");
        upData_content.setText("发现新的版本请立即更新！");
        exit_txt.setText("取消");
        upData_txt.setText("更新");

        do_layout.setVisibility(View.VISIBLE);
        upData_content.setVisibility(View.VISIBLE);
        upData_title.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);



        upData_txt.setOnClickListener(view);
        exit_txt.setOnClickListener(view);


    }


    public View.OnClickListener view= new NoFastClickListener(){
        @Override
        public void fastClick(View view) {
            switch (view.getId()){
                case R.id.up_data_app:
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        //先判断是否有安装未知来源应用的权限
                        boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                        if(!haveInstallPermission){
                            toInstallPermissionSettingIntent();
                        }else{
                            PermissionTools.tryWRPerm(mActivity);
                        }
                    }else{
                        PermissionTools.tryWRPerm(mActivity);
                    }
                    break;
                case R.id.up_data_exit:
                    finish();
                    break;

            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:"+getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
        startActivityForResult(intent,TagFinal.UI_ADMIN);
    }

//掉应用商店
//    public void launchAppDetail(String appPkg, String marketPkg) {
//        try {
//            if (TextUtils.isEmpty(appPkg)) return;
//
//            Uri uri = Uri.parse("market://details?id=" + appPkg);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            if (!TextUtils.isEmpty(marketPkg)) {
//                intent.setPackage(marketPkg);
//            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /*app打开浏览器*/
    @BindView(R.id.up_data_app_content_to)
    AppCompatTextView tv_updata_llq;
    @OnClick(R.id.up_data_app_content_to)
    void setLlq(){
        Intent intent=new Intent();
        intent.setData(Uri.parse(load_path));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent); //启动浏览器

    }
    /*浏览器打开app*/
    /*在Manifest文件的Activity中加入以下代码*/
    /*在html中加入点击事件*/

    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    public void getApk() {

        mTask=new GetHtmlAsyncTask();
        mTask.execute(load_path);
    }


    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    public void showTipAlbum() {
        ViewTool.showToastShort(mActivity, R.string.permission_fail_album);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }






    /**
     * apk下载安装
     */



    private String saveFileName;

    private void installApk(String saveFileName){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri contentUri = FileProvider.getUriForFile(mActivity, Base.AUTHORITY, apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

        } else {
            Uri contentUri=Uri.fromFile(apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        if (getPackageManager().queryIntentActivities(intent, 0).size() > 0) {


            startActivity(intent);
        }

    }










    public boolean interceptFlag=true;//false 不下载
    public GetHtmlAsyncTask mTask;
    @SuppressLint("StaticFieldLeak")
    class GetHtmlAsyncTask extends HtmlAsyncTask {
        @Override
        public String doIn(String... arg0) {
            try {
                URL url = new URL(arg0[0]);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("conn","Keep-Alive");
                conn.connect();//发送链接
                InputStream is = conn.getInputStream();

                long length = conn.getContentLength();//
                if (length <= 0) {
                    Logger.e(TagFinal.ZXX, "读取文件失败");
                    return "";
                }

                //写入文件
                saveFileName =TagFinal.getAppFile( System.currentTimeMillis() + ".apk");
                FileTools.createFile(saveFileName);
                File ApkFile = new File(saveFileName);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                byte[] buf = new byte[1024];
                int count = 0;
                do{
                    int numread = is.read(buf);
                    count += numread;
                    //通知主线程进度条百分比
                    publishProgress((int)(((float)count / length) * 100));
                    if(numread <= 0){
                        interceptFlag=false;
                        //通知主线程进度条百分比
                        publishProgress(100);
                        break;//do{}while(boolean) 条件为true
                    }else{
                        fos.write(buf,0,numread);
                    }
                }while(interceptFlag);

                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return saveFileName;
        }
        @Override
        public void doUpData(String result) {

            if (StringJudge.isEmpty(result)){
                //读取文件失败
                do_layout.setVisibility(View.GONE);
                upData_content.setVisibility(View.VISIBLE);
                upData_title.setVisibility(View.VISIBLE);
                upData_content.setText("读取文件失败！");
                progress.setVisibility(View.GONE);
            }else{
                progress.setVisibility(View.GONE);
                installApk(result);
            }
        }
        @Override
        public void onPre() {
            do_layout.setVisibility(View.GONE);
            upData_content.setVisibility(View.GONE);
            upData_title.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onProgress(Integer integers) {
            progress.setProgress(integers);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            interceptFlag=false;//结束下载文件（如果正在下载）
            mTask.cancel(true);
        }
    }









    /**
     * 双击退出函数
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit ;
        if (isExit ) {
            finish();
            System.exit(0);
        } else {
            isExit = true;
            ViewTool.showToastShort(mActivity,"再按一次退出更新");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }
    }


}
