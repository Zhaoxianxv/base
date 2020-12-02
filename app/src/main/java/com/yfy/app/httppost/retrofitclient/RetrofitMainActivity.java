package com.yfy.app.httppost.retrofitclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yfy.base.R;
import com.yfy.app.httppost.retrofitclient.net.BaseResponse;
import com.yfy.app.httppost.retrofitclient.net.BaseSubscriber;
import com.yfy.app.httppost.retrofitclient.net.CallBack;
import com.yfy.app.httppost.retrofitclient.net.ExceptionHandle.ResponeThrowable;
import com.yfy.app.httppost.retrofitclient.net.RetrofitClient;
import com.yfy.final_tag.stringtool.Logger;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class RetrofitMainActivity extends AppCompatActivity {

    public View btn, btn_get, btn_post, btn_json, btn_download, btn_upload, btn_myApi, btn_changeHostApi;

    String url1 = "http://img0.imgtn.bdimg.com/it/u=205441424,1768829584&fm=21&gp=0.jpg";
    String url2 = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201607/05/SogouInput_android_v8.3_sweb.apk?frm=new_pcjs_index";
    String url3 = "http://apk.hiapk.com/web/api.do?qt=8051&id=723";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_main);

        btn = findViewById(R.id.bt_getdata);

        btn_get = findViewById(R.id.bt_get);
        btn_post = findViewById(R.id.bt_post);
        btn_json = findViewById(R.id.bt_json);
        btn_download = findViewById(R.id.bt_download);
        btn_upload = findViewById(R.id.bt_upload);
        btn_myApi = findViewById(R.id.bt_my_api);
        btn_changeHostApi = findViewById(R.id.bt_changeHostApi);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(RetrofitMainActivity.this).createBaseApi().getData(new BaseSubscriber<IpResult>(RetrofitMainActivity.this) {

                    @Override
                    public void onError(ResponeThrowable e) {
                        Logger.e( e.code + " " + e.message);
                        Toast.makeText(RetrofitMainActivity.this, e.message, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNext(IpResult responseBody) {
                        Toast.makeText(RetrofitMainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                    }
                }, "21.22.11.33");

            }
        });


        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> maps = new HashMap<String, String>();
                maps.put("ip", "21.22.11.33");

                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(RetrofitMainActivity.this).createBaseApi().get("service/getIpInfo.php"
                        , maps, new BaseSubscriber<IpResult>(RetrofitMainActivity.this) {


                            @Override
                            public void onError(ResponeThrowable e) {


                                Logger.e( e.getMessage());
                                Toast.makeText(RetrofitMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                                Toast.makeText(RetrofitMainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> maps = new HashMap<String, String>();

                maps.put("ip", "21.22.11.33");
                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(RetrofitMainActivity.this).createBaseApi().post("service/getIpInfo.php"
                        , maps, new BaseSubscriber<ResponseBody>(RetrofitMainActivity.this) {

                            @Override
                            public void onError(ResponeThrowable e) {
                                Logger.e( e.getMessage());
                                Toast.makeText(RetrofitMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Toast.makeText(RetrofitMainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> maps = new HashMap<String, String>();

                maps.put("ip", "21.22.11.33");
                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(maps));

                RetrofitClient.getInstance(RetrofitMainActivity.this).createBaseApi().json("service/getIpInfo.php"
                        , body, new BaseSubscriber<IpResult>(RetrofitMainActivity.this) {


                            @Override
                            public void onError(ResponeThrowable e) {


                                Logger.e( e.getMessage());
                                Toast.makeText(RetrofitMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                                Toast.makeText(RetrofitMainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // ；；；；； 略
                /**
                 * look   {@link # http://www.jianshu.com/p/acfefb0a204f}
                 */
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance(RetrofitMainActivity.this).createBaseApi().download(url3, new CallBack() {

                            @Override
                            public void onStart() {
                                super.onStart();
                                Toast.makeText(RetrofitMainActivity.this, url1 + "  is  start", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onProgress(long fileSizeDownloaded) {
                                super.onProgress(fileSizeDownloaded);
                                Toast.makeText(RetrofitMainActivity.this, " downLoadeing, download:" + fileSizeDownloaded, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSucess(String path, String name, long fileSize) {
                                Toast.makeText(RetrofitMainActivity.this, name + " is  downLoaded", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });


        btn_myApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create  you APiService
                MyApiService service = RetrofitClient.getInstance(RetrofitMainActivity.this, "http://ip.taobao.com/").create(MyApiService.class);

                // execute and add observable
                RetrofitClient.getInstance(RetrofitMainActivity.this).execute(

                        service.getData("21.22.11.33"), new BaseSubscriber<BaseResponse<IpResult>>(RetrofitMainActivity.this) {

                            @Override
                            public void onError(ResponeThrowable e) {
                                Logger.e( e.getMessage());
                                Toast.makeText(RetrofitMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(BaseResponse<IpResult> responseBody) {

                                if (responseBody.isOk()) {
                                    IpResult ip = responseBody.getData();
                                    Toast.makeText(RetrofitMainActivity.this, ip.toString(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });


        btn_changeHostApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create  you APiService
                MyApiService service = RetrofitClient.getInstance(RetrofitMainActivity.this, "http://lbs.sougu.net.cn/").create(MyApiService.class);

                // execute and add observable to RxJava
                RetrofitClient.getInstance(RetrofitMainActivity.this, "http://lbs.sougu.net.cn/").execute(
                        service.getSougu(), new BaseSubscriber<SouguBean>(RetrofitMainActivity.this) {

                            @Override
                            public void onError(ResponeThrowable e) {
                                Logger.e(e.getMessage());
                                Toast.makeText(RetrofitMainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(SouguBean souguBean) {

                                Toast.makeText(RetrofitMainActivity.this, souguBean.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
    }

}