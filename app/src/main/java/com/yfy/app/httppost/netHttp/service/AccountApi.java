package com.yfy.app.httppost.netHttp.service;



import com.yfy.app.httppost.netHttp.ApiUrl;
import com.yfy.greendao.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created
 * 封装与Account相关的请求
 */
public interface AccountApi {

    @FormUrlEncoded
    @POST(ApiUrl.Get_Code)
    Call<ResponseBody> getCodeApi(@Field("gradeid") String gradeid,
                                  @Field("fxid") String fxid);

//
//    @Multipart
//    @POST(ApiUrl.Get_Name)
//    Call<ResponseBody> getGetNameApi(@Part("description") RequestBody description,
//                                     @Part MultipartBody.Part file);

    @Multipart
    @POST(ApiUrl.Get_Name)
    Call<ResponseBody> getGetNameApi(@Part MultipartBody.Part file);


    @Multipart
    @POST(ApiUrl.Get_Name)
    Call<ResponseBody> getGetNameImage(@Part("description") RequestBody description);





}
