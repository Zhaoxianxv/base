package com.yfy.app.netHttp;


/**
 * Created by
 * 服务器接口地址
 */
public class ApiUrl {



    public final static String normal = "AppService";
    public final static String DIALOG = "dialog";
    public final static String DATA_ERROR = "data_error";
    public final static String DATA_IOE = "data_ioe";

    /**
     * ===============================================
     * 账号
     * ===============================================
     */
    //
    public final static String Get_Name= "AppService/getname";
    //--------------------------user--------------------------

    public final static String USER_GET_TOKEN= "AppService/gettoken";
    public final static String USER_LOGIN= "AppService/login";
    //--------------------school news-------------------------
    public final static String SCHOOL_GET_NEWS_MENU= "AppService/get_newslist_menu";
    public static final String BASE_SAVE_IMG ="AppService/saveimg";//
    public static final String USER_GET_TERM_LIST = "AppService/gettermlistnew";//获取学期
}