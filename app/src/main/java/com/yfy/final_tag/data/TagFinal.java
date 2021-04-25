package com.yfy.final_tag.data;

import android.os.Environment;

import com.yfy.final_tag.stringtool.StringUtils;

import java.io.File;

/**
 * Created by yfy on 2017/9/12.
 */

public  class TagFinal {



    public static String getAppDir(String dir){
        String dir_path=Environment.getExternalStorageDirectory().toString() + StringUtils.stringToGetTextJoint("/yfy/%1$s/",dir);
        File file = new File(dir_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir_path;
    }

    public static String getAppFile(String path){

        return StringUtils.stringToGetTextJoint(
                "%1$s/yfy/%2$s",
                Environment.getExternalStorageDirectory().toString(),
                path
        );
    }
    /**
     *   int final tag
     */
    public final static int ZERO_INT = 0;//常量 0
    public final static int ONE_INT = 1;//常量 1
    public final static int TWO_INT = 2;//常量 2
    public final static int THREE_INT = 3;//常量 3
    public final static int FOUR_INT = 4;//常量 4
    public final static int TEN_INT = 10;//常量 10
    public final static int FIFTEEN_INT = 15;//常量 10
    public final static int TEN_FIVE = 15;//常量 10





    public final static int TYPE_ITEM = ONE_INT;//常量 10
    public final static int TYPE_TOP = TWO_INT;//常量 10
    public final static int TYPE_PARENT =THREE_INT;//常量 10
    public final static int TYPE_CHILD = FOUR_INT;//常量 10
    public final static int TYPE_FOOTER = TEN_INT;//常量 10
    public final static int TYPE_REFRECH = TEN_FIVE;//常量 15
    public final static int TYPE_DETAIL = 16;//常量 15

    public final static int TYPE_IMAGE = 101;


    public final static int TYPE_CHOICE = 104;

    public final static int TYPE_TXT = 302;
    public final static int TYPE_TXT_LEFT_TITLE = 302;
    public final static int TYPE_TXT_EDIT = 107;
    public final static int TYPE_LONG_TXT_EDIT = 103;

    public final static int TYPE_CHECK = 108;
    public final static int TYPE_GROUP = 109;

    public final static int TYPE_SELECT_SINGLE = 114;
    public final static int TYPE_SELECT_STU = 115;
    public final static int TYPE_SELECT_GROUP = 110;
    public final static int TYPE_SELECT_ONE = 111;
    public final static int TYPE_SELECT_TWO = 112;

    public final static int TYPE_LIST_SINGLE = 120;
    public final static int TYPE_LIST_GROUP = 121;
    public final static int TYPE_LIST_ONE = 122;
    public final static int TYPE_LIST_TWO = 123;

    public final static int TYPE_DATE = 131;//
    public final static int TYPE_DATE_TIME = 132;//
    public final static int TYPE_IMAGE_ONE = 223;
    public final static int TYPE_STAR_TITLE = 251;
    public final static int TYPE_SELECT_USER = 293;

    public final static int TYPE_FLOW_TITLE = 331;

    public final static int LOADING = ONE_INT;//
    public final static int LOADING_COMPLETE = TWO_INT;//
    public final static int LOADING_END = THREE_INT;//

    public static final int CAMERA= 1003;//调用摄像头
    public static final int PHOTO_ALBUM = 1004;//调用相册
    public static final int NET_WIFI = 1005;//NET_WIFI
    public static final int CALL_PHONE = 1006;//NET_WIFI
    public static final int VOICE_RECORD = 1007;//NET_WIFI
    public static final int UI_TAG = 1101;//tag
    public static final int UI_CONTENT = 1102;//content
    public static final int UI_REFRESH = 1201;//页面刷新
    public static final int UI_ADD = 1202;//进入添加
    public static final int UI_ADMIN = 1203;//审核操作

    public static final int UI_SELECT_USER_SINGLE = 1301;//选人单选
    public static final int UI_SELECT_USER_MORE = 1302;//选人多选

    public static final int PAGE_NUM = TEN_INT;//常量页码条数
    /**
     *   String final tag
     */

    //常用字段名称
    public static final String NAMESPACE = "http://tempuri.org/";//

    public static final String BODY = "Body";//



    public static final String TITLE_TAG="title_tag";
    public static final String URI_TAG="uri_tag";
    public static final String ZXX="zxx";

    public static final String ALBUM_LIST_INDEX="index";
    public static final String ALBUM_SINGLE="single";
    public static final String ALBUM_TAG="album_tag";
    public static final String ALBUM_SINGE_URI="album_singe_uri";

    public static final String TRUE="true";
    public static final String FALSE="false";
    /**
     *   ------------base-------------------------------------------user相关-------------------
     */
    public static final String BASE_SAVE_IMG ="saveimg";
    public static final String USER_ADD_HEAD="addphoto";//用户添加头像

    public static final String BASE_GET_TOKEN ="gettooken";//获取登录验证token
    public static final String USER_LOGIN ="login";//登录
    public static final String USER_LOGOUT="logout";//用户退出登录logout
    public static final String USER_CHANGE_PASSWORD ="password_reset";//修改密码（晓得原密码）
    public static final String USER_RESET_PASSWORD ="reset_password_password";//重置密码
    public static final String USER_GET_CODE_TO_EDIT_PHONE ="reset_password_vcode";//重置密码获取验证码
    public static final String USER_DUPLICATION_LOGIN = "logstu";//重名学生登录
    public static final String USER_SET_MOBILE = "set_Mobile";//置电话
    public static final String USER_GET_CODE_TO_SYSTEM_PHONE ="get_phone_code";//给用户绑定号码发送验证码（返回有code）


    public static final String USER_GET_DUPLICATION_LIST = "user_duplication";//获取登录重名学生列表
    public static final String USER_GET_TERM_LIST = "gettermlistnew";//获取学期
    public static final String USER_GET_CLASS_LIST = "Physicalhealth_getclass";//获取教师班级列表
    public static final String USER_GET_CLASS_TO_ALL_STU = "get_TeachersMoral_incompletestu";//获取一个班级学生


}

