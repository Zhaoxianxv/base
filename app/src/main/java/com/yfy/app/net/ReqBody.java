package com.yfy.app.net;

import com.yfy.app.net.attend.AttendAdminGetListReq;
import com.yfy.app.net.attend.AttendAdminSetStateReq;
import com.yfy.app.net.attend.AttendGetAddMasterReq;
import com.yfy.app.net.attend.AttendGetAddTypeReq;
import com.yfy.app.net.attend.AttendGetCountReq;
import com.yfy.app.net.attend.AttendGetDetailReq;
import com.yfy.app.net.attend.AttendSetDeleteReq;
import com.yfy.app.net.attend.AttendUserGetListReq;
import com.yfy.app.net.base.BaseGetTokenReq;
import com.yfy.app.net.base.UserChangePasswordReq;
import com.yfy.app.net.base.UserGetClassAllStuReq;
import com.yfy.app.net.base.UserGetClassListReq;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.app.net.base.UserResetCallReq;
import com.yfy.app.net.base.UserResetPasswordReq;
import com.yfy.app.net.login.ResetPasswordGetCodeReq;
import com.yfy.app.net.login.UserDuplicationLoginReq;
import com.yfy.app.net.login.UserGetDuplicationListReq;
import com.yfy.app.net.login.UserLoginReq;
import com.yfy.app.net.maintain.MaintainAdminGetListReq;
import com.yfy.app.net.maintain.MaintainGetCountReq;
import com.yfy.app.net.maintain.MaintainGetDetailReq;
import com.yfy.app.net.maintain.MaintainGetSectionReq;
import com.yfy.app.net.maintain.MaintainSetAddReq;
import com.yfy.app.net.maintain.MaintainSetDeleteReq;
import com.yfy.app.net.maintain.MaintainSetSectionReq;
import com.yfy.app.net.maintain.MaintainUserGetListReq;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 */
@Root(name = TagFinal.BODY, strict = false)
public class ReqBody {
    /**
     * -----------------------attend------------------------
     */

    @Element(name = TagFinal.ATTEND_ADMIN_SET_STATE,required = false)
    public AttendAdminSetStateReq attendAdminSetStateReq;

    @Element(name = TagFinal.ATTEND_SET_DELETE,required = false)
    public AttendSetDeleteReq attendSetDeleteReq;

    @Element(name = TagFinal.ATTEND_GET_ADD_MASTER, required = false)
    public AttendGetAddMasterReq attendGetAddMasterReq;

    @Element(name = TagFinal.ATTEND_GET_ADD_TYPE, required = false)
    public AttendGetAddTypeReq attendGetAddTypeReq;

    @Element(name = TagFinal.ATTEND_GET_ITEM_DETAIL,required = false)
    public AttendGetDetailReq attendGetDetailReq;

    @Element(name = TagFinal.ATTEND_ADMIN_GET_LIST, required = false)
    public AttendAdminGetListReq attendAdminGetListReq;

    @Element(name = TagFinal.ATTEND_USER_GET_LIST, required = false)
    public AttendUserGetListReq attendUserGetListReq;

    @Element(name = TagFinal.ATTEND_GET_COUNT, required = false)
    public AttendGetCountReq attendGetCountReq;



    /**
     * ------------------------maintain------------------
     */

    @Element(name = TagFinal.MAINTAIN_SET_SECTION_MSG, required = false)
    public MaintainSetSectionReq maintainSetSectionReq;

    @Element(name = TagFinal.MAINTAIN_SET_DELETE, required = false)
    public MaintainSetDeleteReq maintainSetDeleteReq;

    @Element(name = TagFinal.MAINTAIN_SET_ADD, required = false)
    public MaintainSetAddReq maintainSetAddReq;

    @Element(name = TagFinal.MAINTAIN_GET_ADD_SECTION, required = false)
    public MaintainGetSectionReq maintainGetSectionReq;

    @Element(name = TagFinal.MAINTAIN_GET_DETAIL, required = false)
    public MaintainGetDetailReq maintainGetDetailReq;

    @Element(name = TagFinal.MAINTAIN_ADMIN_GET_LIST, required = false)
    public MaintainAdminGetListReq maintainAdminGetListReq;

    @Element(name = TagFinal.MAINTAIN_USER_GET_LIST, required = false)
    public MaintainUserGetListReq maintainUserGetListReq;

    @Element(name = TagFinal.MAINTAIN_GET_COUNT, required = false)
    public MaintainGetCountReq maintainGetCountReq;





    //----------------------base----------



    @Element(name = TagFinal.BASE_SAVE_IMG, required = false)
    public SaveImgReq saveImgReq;

    @Element(name = TagFinal.BASE_GET_TOKEN, required = false)
    public BaseGetTokenReq baseGetTookenReq;


    @Element(name = TagFinal.USER_GET_CLASS_LIST, required = false)
    public UserGetClassListReq userGetClassListReq;

    @Element(name = TagFinal.USER_GET_CLASS_TO_ALL_STU, required = false)
    public UserGetClassAllStuReq userGetClassAllStuReq;

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
