package com.yfy.app.httppost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.httppost.netHttp.ApiUrl;
import com.yfy.app.httppost.netHttp.service.AccountApi;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.Base64Utils;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class HttpPostMainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_main);
        initAlbumDialog();
    }




    @OnClick(R.id.send_button)
    void setSendButton(View view){
        album_select.showAtBottom();
    }

    @OnClick(R.id.retrofit_param)
    void setParam(View view){
        retrofitPostParam();
    }



    @OnClick(R.id.retrofit_image)
    void setImage(View view){
        retrofitPostImage();
    }













    private void retrofitPostImage(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Logger.e("retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .build();






        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiUrl.SERVER_ROOT)
                .addConverterFactory(GsonConverterFactory.create()) //设置 Json 转换器
                .build();


        //拼接地址
        AccountApi accountApi = retrofit.create(AccountApi.class);

        Call<ResponseBody> bodyCall = accountApi.getGetNameApi();

//               .addHeader("Content-Type", "multipart/form-data")
//                .addHeader("Content-Disposition", "form-data; boundary="+"");
        Request request =bodyCall.request();


        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body()!=null;
                    String string = response.body().string();
                    Logger.e(string);
                    Toast.makeText(mActivity, string, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("onFailure  :"+call.request().headers().toString());
            }
        });


    }




    public void retrofitPostParam() {
        //服务器  ：服务器项目  ：servlet名称
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Logger.e("retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiUrl.SERVER_ROOT)
                .addConverterFactory(GsonConverterFactory.create()) //设置 Json 转换器
                .build();

        //拼接地址
        AccountApi accountApi = retrofit.create(AccountApi.class);




        HashMap<String, Object> map = new HashMap<>();
        map.put("gradeid", "1");
        map.put("fxid", "11");
        Call<ResponseBody> bodyCall = accountApi.getCodeApi(map);


        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body()!=null;
                    String string = response.body().string();
                    Logger.e(string);
                    Toast.makeText(mActivity, string, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("onFailure  :"+call.request().headers().toString());
            }
        });



    }




    private Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
            httpUrlConnection();
        }
    });


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
            String requestString = Base64Utils.fileToBase64Str(image_path);


            //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            byte[] requestStringBytes = requestString.getBytes("UTF-8");


            //设置请求属性
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");
//            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
//            httpConn.addRequestProperty("Content-Disposition", "form-data; filename=" + "1.jpg");//文件名
            //            httpConn.setRequestProperty("Content-Type", "application/octet-stream");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data");
            httpConn.setRequestProperty("Content-Disposition","form-data; boundary=" +requestStringBytes);
            httpConn.addRequestProperty("Content-Disposition", "form-data; filename=" + StringUtils.stringToGetTextJoint("%1$s.jpg",System.currentTimeMillis()));//文件名
//            String name=URLEncoder.encode("黄武艺","utf-8");
//            httpConn.setRequestProperty("NAME", name);

            //建立输出流，并写入数据
//            OutputStream outputStream = httpConn.getOutputStream();
//            // httpConn.getOutputStream(); 获得输出流对象（其实通过这个就可以往这个请求里面写数据，这样网站那就可以获得数据了）
//            outputStream.write(requestStringBytes,0,requestStringBytes.length);
//            outputStream.close();


//            formData.append(name, file, filename);

//            PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
//            pw.print(requestString);
//            pw.flush();
//            pw.close();

            //获得响应状态
//            Logger.e(httpConn.hea());
            int responseCode = httpConn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode){//连接成功

                //当正确响应时处理数据
                StringBuffer sb = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流，必须与服务器响应流输出的编码一致
                //httpConn.getInputStream()//获得输入流对象（其实就是最后网站返回过来的数据）
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
            Logger.e("Exception"+ex.toString());
        }
    }











    ConfirmAlbumWindow album_select;
    private void initAlbumDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setTwo_select(mActivity.getResources().getString(R.string.album));
        album_select.setOne_select(mActivity.getResources().getString(R.string.take_photo));
        album_select.setName(mActivity.getResources().getString(R.string.upload_type));
        album_select.setOnPopClickListenner(new ConfirmAlbumWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.popu_select_one:
                        PermissionTools.tryCameraPerm(mActivity);
                        break;
                    case R.id.popu_select_two:
                        PermissionTools.tryWRPerm(mActivity);
                        break;
                }
            }
        });
    }










    public String image_path="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:
                    image_path=FileCamera.photo_camera;
                    thread.start();
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    image_path=photo_a.get(0).getPath();
                    thread.start();
                    break;
            }
        }
    }


    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    public void takePhoto() {
        FileCamera camera=new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }
    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    public void photoAlbum() {
        Intent intent;
        intent = new Intent(mActivity, AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, false);
        intent.putExtras(b);
        startActivityForResult(intent,TagFinal.PHOTO_ALBUM);
    }
    @PermissionFail(requestCode = TagFinal.CAMERA)
    public void showCamere() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_camere, Toast.LENGTH_SHORT).show();
    }
    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    public void showTip1() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }



}