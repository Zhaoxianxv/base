package com.yfy.app.net;

import com.yfy.app.net.base.BaseGetTokenRes;
import com.yfy.app.net.base.UserChangePasswordRes;
import com.yfy.app.net.base.UserGetClassAllStuRes;
import com.yfy.app.net.base.UserGetClassListRes;
import com.yfy.app.net.base.UserGetTermListRes;
import com.yfy.app.net.base.UserResetCallRes;
import com.yfy.app.net.base.UserResetPasswordRes;
import com.yfy.app.net.login.ResetPasswordGetCodeRes;
import com.yfy.app.net.login.UserDuplicationLoginRes;
import com.yfy.app.net.login.UserGetDuplicationListRes;
import com.yfy.app.net.login.UserLoginRes;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by SmileXie on 16/7/15.
 */
@Root(name = TagFinal.BODY)
public class ResBody {

    @Element(name = TagFinal.BASE_SAVE_IMG +Base.RESPONSE, required = false)
    public SaveImgRes saveImgRes;

    @Element(name = TagFinal.BASE_GET_TOKEN +Base.RESPONSE, required = false)
    public BaseGetTokenRes baseGetTokenRes;

    @Element(name = TagFinal.USER_GET_CODE_TO_EDIT_PHONE +Base.RESPONSE, required = false)
    public ResetPasswordGetCodeRes resetPasswordGetCodeRes;

    @Element(name = TagFinal.USER_GET_CLASS_LIST+Base.RESPONSE, required = false)
    public UserGetClassListRes userGetClassListRes;

    @Element(name = TagFinal.USER_GET_CLASS_TO_ALL_STU+Base.RESPONSE, required = false)
    public UserGetClassAllStuRes userGetClassAllStuRes;

    @Element(name = TagFinal.USER_GET_TERM_LIST +Base.RESPONSE, required = false)
    public UserGetTermListRes userGetTermListRes;

    @Element(name = TagFinal.USER_SET_MOBILE+Base.RESPONSE, required = false)
    public UserResetCallRes userResetCallRes;

    @Element(name = TagFinal.USER_LOGIN +Base.RESPONSE, required = false)
    public UserLoginRes userLoginRes;

    @Element(name = TagFinal.USER_GET_DUPLICATION_LIST +Base.RESPONSE, required = false)
    public UserGetDuplicationListRes userGetDuplicationListRes;

    @Element(name = TagFinal.USER_DUPLICATION_LOGIN +Base.RESPONSE, required = false)
    public UserDuplicationLoginRes userDuplicationLoginRes;

    @Element(name = TagFinal.USER_CHANGE_PASSWORD +Base.RESPONSE, required = false)
    public UserChangePasswordRes userChangePasswordRes;

    @Element(name = TagFinal.USER_RESET_PASSWORD +Base.RESPONSE, required = false)
    public UserResetPasswordRes userResetPasswordRes;






}
