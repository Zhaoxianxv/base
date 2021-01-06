package com.yfy.app.net;

import com.yfy.app.net.base.BaseGetTokenReq;
import com.yfy.app.net.base.UserChangePasswordReq;
import com.yfy.app.net.base.UserGetClassListReq;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.app.net.base.UserResetCallReq;
import com.yfy.app.net.base.UserResetPasswordReq;
import com.yfy.app.net.login.ResetPasswordGetCodeReq;
import com.yfy.app.net.login.UserDuplicationLoginReq;
import com.yfy.app.net.login.UserGetDuplicationListReq;
import com.yfy.app.net.login.UserLoginReq;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 */
@Root(name = TagFinal.BODY, strict = false)
public class ReqBody {

    //----------------------base----------



    @Element(name = TagFinal.BASE_SAVE_IMG, required = false)
    public SaveImgReq saveImgReq;

    @Element(name = TagFinal.BASE_GET_TOKEN, required = false)
    public BaseGetTokenReq baseGetTookenReq;


    @Element(name = TagFinal.USER_GET_CLASS_LIST, required = false)
    public UserGetClassListReq userGetClassListReq;

    @Element(name = TagFinal.USER_GET_TERM_LIST, required = false)
    public UserGetTermListReq userGetTermListReq;

    @Element(name = TagFinal.USER_SET_MOBILE, required = false)
    public UserResetCallReq userResetCallReq;

    //------------------------login--------------------
    @Element(name = TagFinal.USER_CHANGE_PASSWORD, required = false)
    public UserChangePasswordReq userChangePasswordReq;

    @Element(name = TagFinal.USER_RESET_PASSWORD, required = false)
    public UserResetPasswordReq userResetPasswordReq;


    @Element(name = TagFinal.USER_GET_CODE_TO_EDIT_PHONE, required = false)
    public ResetPasswordGetCodeReq resetPasswordGetCodeReq;

    @Element(name = TagFinal.USER_LOGIN, required = false)
    public UserLoginReq userLoginReq;

    @Element(name = TagFinal.USER_GET_DUPLICATION_LIST, required = false)
    public UserGetDuplicationListReq userGetDuplicationListReq;

    @Element(name = TagFinal.USER_DUPLICATION_LOGIN, required = false)
    public UserDuplicationLoginReq userDuplicationLoginReq;




}
