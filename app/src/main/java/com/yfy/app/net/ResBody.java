package com.yfy.app.net;

import com.yfy.app.net.attend.AttendAdminGetListRes;
import com.yfy.app.net.attend.AttendAdminSetStateRes;
import com.yfy.app.net.attend.AttendGetAddMasterRes;
import com.yfy.app.net.attend.AttendGetAddTypeRes;
import com.yfy.app.net.attend.AttendGetCountRes;
import com.yfy.app.net.attend.AttendGetDetailRes;
import com.yfy.app.net.attend.AttendSetDeleteRes;
import com.yfy.app.net.attend.AttendUserGetListRes;
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
import com.yfy.app.net.maintain.MaintainAdminGetListRes;
import com.yfy.app.net.maintain.MaintainGetCountRes;
import com.yfy.app.net.maintain.MaintainGetDetailRes;
import com.yfy.app.net.maintain.MaintainGetSectionRes;
import com.yfy.app.net.maintain.MaintainSetAddRes;
import com.yfy.app.net.maintain.MaintainSetDeleteRes;
import com.yfy.app.net.maintain.MaintainSetSectionRes;
import com.yfy.app.net.maintain.MaintainUserGetListRes;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by SmileXie on 16/7/15.
 */
@Root(name = TagFinal.BODY)
public class ResBody {
    /**
     * -------------------------attend---------------------
     */

    @Element(name = TagFinal.ATTEND_ADMIN_SET_STATE+Base.RESPONSE,required = false)
    public AttendAdminSetStateRes attendAdminSetStateRes;

    @Element(name = TagFinal.ATTEND_SET_DELETE+Base.RESPONSE,required = false)
    public AttendSetDeleteRes attendSetDeleteRes;

    @Element(name = TagFinal.ATTEND_GET_ADD_MASTER+Base.RESPONSE, required = false)
    public AttendGetAddMasterRes attendGetAddMasterRes;

    @Element(name = TagFinal.ATTEND_GET_ADD_TYPE+Base.RESPONSE, required = false)
    public AttendGetAddTypeRes attendGetAddTypeRes;

    @Element(name = TagFinal.ATTEND_GET_ITEM_DETAIL+Base.RESPONSE,required = false)
    public AttendGetDetailRes attendGetDetailRes;

    @Element(name = TagFinal.ATTEND_ADMIN_GET_LIST+Base.RESPONSE, required = false)
    public AttendAdminGetListRes attendAdminGetListRes;

    @Element(name = TagFinal.ATTEND_USER_GET_LIST+Base.RESPONSE, required = false)
    public AttendUserGetListRes attendUserGetListRes;

    @Element(name = TagFinal.ATTEND_GET_COUNT+Base.RESPONSE, required = false)
    public AttendGetCountRes attendGetCountRes;

    /**
     * ------------------------maintain-----------------------------
     */

    @Element(name = TagFinal.MAINTAIN_SET_SECTION_MSG+Base.RESPONSE, required = false)
    public MaintainSetSectionRes maintainSetSectionRes;

    @Element(name = TagFinal.MAINTAIN_SET_DELETE+Base.RESPONSE, required = false)
    public MaintainSetDeleteRes maintainSetDeleteRes;

    @Element(name = TagFinal.MAINTAIN_SET_ADD+Base.RESPONSE, required = false)
    public MaintainSetAddRes maintainSetAddRes;

    @Element(name = TagFinal.MAINTAIN_GET_ADD_SECTION+Base.RESPONSE, required = false)
    public MaintainGetSectionRes maintainGetSectionRes;

    @Element(name = TagFinal.MAINTAIN_GET_DETAIL+Base.RESPONSE, required = false)
    public MaintainGetDetailRes maintainGetDetailRes;

    @Element(name = TagFinal.MAINTAIN_ADMIN_GET_LIST+Base.RESPONSE, required = false)
    public MaintainAdminGetListRes maintainAdminGetListRes;

    @Element(name = TagFinal.MAINTAIN_USER_GET_LIST+Base.RESPONSE, required = false)
    public MaintainUserGetListRes maintainUserGetListRes;

    @Element(name = TagFinal.MAINTAIN_GET_COUNT+Base.RESPONSE, required = false)
    public MaintainGetCountRes maintainGetCountRes;

    /**
     * --------------------------------------base----------------------
     */
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
