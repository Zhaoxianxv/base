package com.yfy.app.netHttp;



import com.yfy.base.Base;

import org.simpleframework.xml.Order;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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


    @FormUrlEncoded
    @POST(ApiUrl.USER_GET_TERM_LIST)
    Call<ResponseBody> user_get_term_list_api(
            @Field(Base.session_key) String session_key
    );

    @FormUrlEncoded
    @POST(ApiUrl.BASE_SAVE_IMG)
    Call<ResponseBody> base_save_img_api(
            @Field(Base.session_key) String session_key,
            @Field("fileext") String fileext,
            @Field("image_file") String image_file
    );

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

    @FormUrlEncoded
    @POST(ApiUrl.USER_GET_STU_TO_SEARCH_CHAR)
    Call<ResponseBody> base_get_stu_to_search_char_api(
            @Field(Base.session_key) String session_key,
            @Field("key") String key
    );

    @FormUrlEncoded
    @POST(ApiUrl.USER_RESET_PASS_WORD_TO_ADMIN)
    Call<ResponseBody> base_reset_pass_to_admin_api(
            @Field(Base.session_key) String session_key,
            @Field("stuid") int stuid
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
    Call<ResponseBody> getGetNameApi(
            @Part MultipartBody.Part file);


    @Multipart
    @POST(ApiUrl.Get_Name)
    Call<ResponseBody> getGetNameImage(@Part("description") RequestBody description);




}
