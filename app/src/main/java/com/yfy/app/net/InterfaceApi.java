package com.yfy.app.net;



import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 接口请求
 */
public interface InterfaceApi {
    /**
     * --------------------------------maintain----------------------------------
     */
    @Headers({Base.Content_Type, Base.SOAP_ACTION+ TagFinal.MAINTAIN_SET_SECTION_MSG})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_set_section_msg_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+ TagFinal.MAINTAIN_SET_DELETE})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_set_delete_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+ TagFinal.MAINTAIN_SET_ADD})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_set_add(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINTAIN_GET_ADD_SECTION})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_get_add_section_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINTAIN_GET_DETAIL})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_get_detail_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINTAIN_ADMIN_GET_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_admin_get_list_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINTAIN_USER_GET_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_user_get_list_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.MAINTAIN_GET_COUNT})
    @POST(Base.POST_URI)
    Call<ResEnv> maintain_get_count_api(@Body ReqEnv Envelope);


    /**
     *----------------------attend-----------------------
     */

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_ADMIN_SET_STATE})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_admin_set_state_api(@Body ReqEnv Envelope);


    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_SET_DELETE})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_set_delete_item_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_GET_ADD_MASTER})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_get_add_master_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_GET_ADD_TYPE})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_get_add_type_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_GET_ITEM_DETAIL})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_get_item_detail_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_ADMIN_GET_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_admin_get_list_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_USER_GET_LIST})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_user_get_list_api(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.ATTEND_GET_COUNT})
    @POST(Base.POST_URI)
    Call<ResEnv> attend_get_count_api(@Body ReqEnv Envelope);
    //--------------------base-----------------------

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.BASE_SAVE_IMG})
    @POST(Base.POST_URI)
    Call<ResEnv> base_save_img(@Body ReqEnv Envelope);

    @Headers({Base.Content_Type, Base.SOAP_ACTION+TagFinal.BASE_GET_TOKEN})
    @POST(Base.POST_URI)
    Call<ResEnv> base_get_token(@Body ReqEnv Envelope);

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
