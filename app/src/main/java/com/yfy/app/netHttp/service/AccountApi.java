package com.yfy.app.netHttp.service;



import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.bean.CodeRes;
import com.yfy.app.netHttp.bean.Result;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created
 * 封装与Account相关的请求
 */
public interface AccountApi {


    //-------------------------------user----------------------
    @FormUrlEncoded
    @POST(ApiUrl.USER_GET_TOKEN)
    Observable<String> base_get_token_api(
            @Field("staffid") int staffid,
            @Field("usertype") String usertype,
            @Field("appid") String appid,
            @Field("andios") String andios
    );
    @FormUrlEncoded
    @POST(ApiUrl.USER_LOGIN)
    Observable<String> base_user_login_api(
            @Field("staffid") int staffid,
            @Field("usertype") String usertype,
            @Field("appid") String appid,
            @Field("andios") String andios
    );

    //-------------------------------school news-----------------------
    @FormUrlEncoded
    @POST(ApiUrl.SCHOOL_GET_NEWS_MENU)
    Observable<String> school_get_news_menu_api(
            @Field("no") String no,
            @Field("fxid") String fxid
    );
    @FormUrlEncoded
    @POST(ApiUrl.SCHOOL_GET_NEWS_MENU)
    Call<ResponseBody> school_get_news_menu_api_string(
            @Field("no") String no,
            @Field("fxid") String fxid
    );


    @Multipart
    @POST(ApiUrl.Get_Name)
    Observable<String> getGetNameImage(@Part("description") RequestBody description);





}
