package com.yfy.app.httppost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.yfy.app.album.AlbumOneActivity;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.Logger;

import java.util.ArrayList;

import butterknife.OnClick;
import rx.functions.Action1;

public class HttpPostMainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloworld);
        Logger.e("http://192.168.50.232:5000/system/getname");
        initAlbumDialog();
    }




    @OnClick(R.id.send_button)
    void setSendButton(View view){
//        album_select.showAtBottom();
        getReultForHttpPost();
    }








    public void getReultForHttpPost() {
        //服务器  ：服务器项目  ：servlet名称






//        //retrofit.create来生成一个接口实现类
//        AccountService apiService=RestClient.instance.getAccountService().updateFile();
//        //调用指定方法
//        Observable<BaseResResult> gitHubBeanCall= apiService.updateFile();
//        //执行请求
//        RestHelpter.subscribeResult(mActivity, gitHubBeanCall, new Action1<List<BaseRes>>() {
//            @Override
//            public void call(List<BaseRes> newDataItems) {
//            }
//        }, networkThrowable);

    }



    Action1<Throwable> networkThrowable = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            dismissProgressDialog();
            Logger.e("网络错误");
        }
    };




//
//    private void httpUrlConnection(){
//        try{
//            String pathUrl = "http://api.yfyit.com/system/getname";
//            //建立连接
//            URL url=new URL(pathUrl);
//            HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
//
//            ////设置连接属性
//            httpConn.setDoOutput(true);//使用 URL 连接进行输出
//            httpConn.setDoInput(true);//使用 URL 连接进行输入
//            httpConn.setUseCaches(false);//忽略缓存
//            httpConn.setRequestMethod("POST");//设置URL请求方法
//            String requestString = Base64Utils.fileToBase64Str(image_path);
//
//
//            //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
//            byte[] requestStringBytes = requestString.getBytes("UTF-8");
//
//
//            //设置请求属性
//            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
//            httpConn.setRequestProperty("Charset", "UTF-8");
////            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
////            httpConn.addRequestProperty("Content-Disposition", "form-data; filename=" + "1.jpg");//文件名
//            //            httpConn.setRequestProperty("Content-Type", "application/octet-stream");
//            httpConn.setRequestProperty("Content-Type", "multipart/form-data");
//            httpConn.setRequestProperty("Content-Disposition","form-data; boundary=" +requestStringBytes);
//            httpConn.addRequestProperty("Content-Disposition", "form-data; filename=" + StringUtils.stringToGetTextJoint("%1$s.jpg",System.currentTimeMillis()));//文件名
////            String name=URLEncoder.encode("黄武艺","utf-8");
////            httpConn.setRequestProperty("NAME", name);
//
//            //建立输出流，并写入数据
////            OutputStream outputStream = httpConn.getOutputStream();
////            // httpConn.getOutputStream(); 获得输出流对象（其实通过这个就可以往这个请求里面写数据，这样网站那就可以获得数据了）
////            outputStream.write(requestStringBytes,0,requestStringBytes.length);
////            outputStream.close();
//
//
////            formData.append(name, file, filename);
//
////            PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
////            pw.print(requestString);
////            pw.flush();
////            pw.close();
//
//            //获得响应状态
////            Logger.e(httpConn.hea());
//            int responseCode = httpConn.getResponseCode();
//            if(HttpURLConnection.HTTP_OK == responseCode){//连接成功
//
//                //当正确响应时处理数据
//                StringBuffer sb = new StringBuffer();
//                String readLine;
//                BufferedReader responseReader;
//                //处理响应流，必须与服务器响应流输出的编码一致
//                //httpConn.getInputStream()//获得输入流对象（其实就是最后网站返回过来的数据）
//                responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
//                while ((readLine = responseReader.readLine()) != null) {
//                    sb.append(readLine).append("\n");
//                }
//                responseReader.close();
//                Logger.e(sb.toString());
//            }else{
//                Logger.e(""+responseCode);
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//            Logger.e("Exception"+ex.toString());
//        }
//    }


    private Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {
//            httpUrlConnection();

        }
    });









//    private void okHttp(){
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("from", "aaa@qq.com")
//                .addFormDataPart("to", "bbb@qq.com")
//                .addFormDataPart("subject", "test")
//                .addFormDataPart("text", "aaaaa")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://*****/*****/****/api/send ")
//                .method("POST", body)
////                .addHeader("Authorization", "Basic ********W5yeS1zZX******")
//                .addHeader("Content-Type", "multipart/form-data")
//                .build();
//        Response response = client.newCall(request).request();
//    }









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
//                    thread.start();

                    getReultForHttpPost();
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
