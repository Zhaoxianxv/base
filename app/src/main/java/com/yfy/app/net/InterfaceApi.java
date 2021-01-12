package com.yfy.app.net;



import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 接口请求
 */
public interface InterfaceApi {

    //--------------------base-----------------------

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.BASE_SAVE_IMG})
    @POST(Base.POST_URI)
    Call<ResEnv> base_save_img(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.BASE_GET_TOKEN})
    @POST(Base.POST_URI)
    Call<ResEnv> base_get_tooken(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_GET_TERM_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> user_get_term_list_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_GET_CLASS_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> user_get_class_list_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_GET_CLASS_TO_ALL_STU})
    @POST(Base.POST_URI)
    Call<ResEnv> user_get_class_all_stu_api(@Body ReqEnv Envelope);



    //----------------------------user--------------------


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_SET_MOBILE})
    @POST(Base.POST_URI)
    Call<ResEnv> user_reset_call(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_CHANGE_PASSWORD})
    @POST(Base.POST_URI)
    Call<ResEnv> user_change_password_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_RESET_PASSWORD})
    @POST(Base.POST_URI)
    Call<ResEnv> user_reset_password(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_LOGIN})
    @POST(Base.POST_URI)
    Call<ResEnv> user_login(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_DUPLICATION_LOGIN})
    @POST(Base.POST_URI)
    Call<ResEnv> user_duplication_login(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_GET_DUPLICATION_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> user_get_duplication_user(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.USER_GET_CODE_TO_EDIT_PHONE})
    @POST(Base.POST_URI)
    Call<ResEnv> user_reset_password_get_code(@Body ReqEnv Envelope);


    /**
     * --------------------------------maintain----------------------------------
     */


}
