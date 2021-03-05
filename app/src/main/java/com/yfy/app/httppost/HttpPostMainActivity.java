package com.yfy.app.httppost;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.app.netHttp.RestHelper;
import com.yfy.base.R;
import com.yfy.final_tag.Base64Utils;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;


@SuppressLint("NonConstantResourceId")
public class HttpPostMainActivity extends HttpPostActivity implements HttpNetHelpInterface {
    private static final String TAG = HttpPostMainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_main);
        Logger.e(TAG);
        initAlbumDialog();
    }




    @OnClick(R.id.send_button)
    void setSendButton(View view){
        album_select.showAtBottom();
    }

    @OnClick(R.id.retrofit_param)
    void setParam(View view){
        retrofitPostParamToString();
    }



    @OnClick(R.id.retrofit_image)
    void setImage(View view){
       album_select.showAtCenter();
    }











    private void retrofitPostImage(String fileUrl){
        Logger.e(fileUrl);
        //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
        byte[] requestStringBytes = Base64Utils.fileToBase64ByteString(fileUrl);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), requestStringBytes);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "file",
                StringUtils.stringToGetTextJoint("%1$s.%2$s",System.currentTimeMillis(),"jpg"),
                requestFile);
        //执行请求
        RestClient.instance.getAccountApi().getGetNameApi(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }








    public void retrofitPostParamToString() {

        Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().school_get_news_menu_api_string("02","13");
        setNetHelper(this,bodyCall,true,ApiUrl.SCHOOL_GET_NEWS_MENU,false);

    }


    @Override
    public void success(String api_name, String result) {
        ViewTool.dismissProgressDialog();
        switch (api_name){
            case ApiUrl.SCHOOL_GET_NEWS_MENU:

                break;

            default:
                break;
        }
    }


    @Override
    public void fail(String api_name) {

    }





    public void retrofitPostParamToObservable() {
        ViewTool.showProgressDialog(mActivity,"");
        Observable<String> bodyCall = RestClient.instance.getAccountApi().school_get_news_menu_api("02","13");
        RestHelper.subscribeResult(mActivity, bodyCall, new Action1<String>() {
            @Override
            public void call(String code) {
                ViewTool.dismissProgressDialog();
                Logger.e(code);

            }
        }, networkThrowable);
    }

    Action1<Throwable> networkThrowable = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            ViewTool.dismissProgressDialog();
        }
    };



    private Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
            httpUrlConnection();
        }
    });


    private void httpUrlConnection(){
        try{
            String pathUrl = "http://api.yfyit.com/" +
                    "system/getname";
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
            httpConn.setRequestProperty("Content-Type", "multipart/form-data");
            httpConn.setRequestProperty("Content-Disposition","form-data; boundary=" +requestStringBytes);
            httpConn.addRequestProperty("Content-Disposition", "form-data; filename=" + StringUtils.stringToGetTextJoint("%1$s.jpg",System.currentTimeMillis()));//文件名
            loggerHttpHead(httpConn);
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
//            Logger.e(httpConn.get());

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






    private void loggerHttpHead(HttpURLConnection httpConn){

        Logger.e(""+httpConn.getRequestMethod());
        Logger.e("Host"+httpConn.getRequestProperty("Host"));
        Logger.e("Connection"+httpConn.getRequestProperty("Connection"));
        Logger.e("Accept"+httpConn.getRequestProperty("Accept"));
        Logger.e("User-Agent"+httpConn.getRequestProperty("User-Agent"));
        Logger.e("Accept-Encoding"+httpConn.getRequestProperty("Accept-Encoding"));
        Logger.e("Content-Disposition"+httpConn.getRequestProperty("Content-Disposition"));
        System.out.print("Content-Disposition"+httpConn.getRequestProperty("Content-Disposition"));
    }




    ConfirmAlbumWindow album_select;
    private void initAlbumDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setTwo_select(mActivity.getResources().getString(R.string.album));
        album_select.setOne_select(mActivity.getResources().getString(R.string.take_photo));
        album_select.setName(mActivity.getResources().getString(R.string.upload_type));
        album_select.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {

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
//                    thread.start();
                    retrofitPostImage(image_path);
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
        Toast.makeText(getApplicationContext(), R.string.permission_fail_camera, Toast.LENGTH_SHORT).show();
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
