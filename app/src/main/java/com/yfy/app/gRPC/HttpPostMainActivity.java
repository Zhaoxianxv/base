package com.yfy.app.gRPC;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.squareup.okhttp.internal.http.HttpConnection;
import com.yfy.app.netHttp.RestClient;
import com.yfy.app.netHttp.ServerApiUrl;
import com.yfy.app.netHttp.service.AccountService;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.Base64Utils;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.upload.UpDataDialogActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;

public class HttpPostMainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloworld);
        Logger.e("http://192.168.50.232:5000/system/getname");
    }




    @OnClick(R.id.send_button)
    void setSendButton(View view){
        thread.start();
    }








//    private String Verification;
//    public void getReultForHttpPost() {
//        //服务器  ：服务器项目  ：servlet名称
//
//
//
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ServerApiUrl.SERVER_ROOT)
//                .build();
//
//
//
//
//        //retrofit.create来生成一个接口实现类
//        AccountService apiService=retrofit.create(AccountService.class);
//        //调用指定方法
//        Call<ResponseBody> gitHubBeanCall=
//                apiService.updateFile(Base64Utils.fileToBase64ByteString("/storage/emulated/0/kugou/lyrics_video/default_res/defaultVideo/1.jpg"));
//        //执行请求
//        gitHubBeanCall.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////                String authorizations_url= response.body().getAuthorizations_url();
////                String team_url= response.body().getTeam_url();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Logger.e("zfq", t.getMessage());
//            }
//        });
//
//    }


    private void httpUrlConnection(){
        try{
            String pathUrl = "http://api.yfyit.com/system/getname";
            //建立连接
            URL url=new URL(pathUrl);
            HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();

            ////设置连接属性
            httpConn.setDoOutput(true);//使用 URL 连接进行输出
            httpConn.setDoInput(true);//使用 URL 连接进行输入
            httpConn.setUseCaches(false);//忽略缓存
            httpConn.setRequestMethod("POST");//设置URL请求方法
            String requestString = Base64Utils.getZipBase64Str("/storage/emulated/0/kugou/lyrics_video/default_res/defaultVideo/1.jpg");

            //设置请求属性
            //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            byte[] requestStringBytes = requestString.getBytes("UTF-8");

//
//            httpConn.setRequestProperty("Content-Type", "application/octet-stream");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");
            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
//            httpConn.addRequestProperty("Content-Disposition", "form-data; filename=" + "1.jpg");//文件名
//            httpConn.setRequestProperty("Content-Disposition","form-data; name"+"1.jpg");

//            String name=URLEncoder.encode("黄武艺","utf-8");
//            httpConn.setRequestProperty("NAME", name);

            //建立输出流，并写入数据
            OutputStream outputStream = httpConn.getOutputStream();
            outputStream.write(requestStringBytes,0,requestStringBytes.length);
            outputStream.close();
            //获得响应状态
            int responseCode = httpConn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode){//连接成功

                //当正确响应时处理数据
                StringBuffer sb = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流，必须与服务器响应流输出的编码一致
                responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                Logger.e(sb.toString());
            }else{
                Logger.e(""+responseCode);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Logger.e("Exception");
        }
    }


    private Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
            httpUrlConnection();

        }
    });


}
