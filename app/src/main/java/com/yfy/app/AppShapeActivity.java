package com.yfy.app;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yfy.app.bean.BaseRes;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.hander.HtmlAsyncTask;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.zxing.encoding.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;

public class AppShapeActivity extends BaseActivity {


    @Bind(R.id.app_shape_icon)
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_shape_detail);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initSQToolbar("扫码下载");
        mTask=new GetHtmlAsyncTask();
        mTask.execute();
    }
    public void initSQToolbar(String title){
        assert toolbar!=null;
        toolbar.setTitle(title);

    }


    //生成二维码
    public void make(boolean is,String q_r_text){
        if (q_r_text.equals("")){
            Toast.makeText(mActivity,"输入类容不能为空",Toast.LENGTH_LONG).show();
        }else {
            //createQRCode(q_r_text,500,500,null);
            //1,生成的Sting；2、3、宽和高；4、log要
            //98ji Bitmap bitmap= EncodingUtils.createQRCode(q_r_text,800,800,box.isChecked() ? BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher) : null);
            Bitmap bitmap;
            if (is){
                bitmap= EncodingUtils.createQRCode(q_r_text,800,800, BitmapFactory.decodeResource(getResources(),R.mipmap.logo));
            }else {
                bitmap= EncodingUtils.createQRCode(q_r_text,800,800,null);
            }
            icon.setImageBitmap(bitmap);
        }
    }


    public String lines;
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
            dismissProgressDialog();
            if (StringJudge.isEmpty(list)){
                ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
                finish();
            }else{
                BaseRes res=gson.fromJson(content,BaseRes.class);
                make(true,res.getUrl());
            }
        }
        @Override
        public void onPre() {
            showProgressDialog("");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()==AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }

}
