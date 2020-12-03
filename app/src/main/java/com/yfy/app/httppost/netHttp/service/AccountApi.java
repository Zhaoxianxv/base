package com.yfy.app.httppost.netHttp.service;



import com.yfy.app.httppost.netHttp.ApiUrl;
import com.yfy.greendao.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created
 * 封装与Account相关的请求
 */
public interface AccountApi {


    @POST(ApiUrl.Get_Code)
    Call<ResponseBody> getCodeApi(@QueryMap Map<String,Object> map);



    @POST(ApiUrl.Get_Name)
    Call<ResponseBody> getGetNameApi();





}