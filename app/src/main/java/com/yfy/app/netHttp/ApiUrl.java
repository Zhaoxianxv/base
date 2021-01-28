package com.yfy.app.netHttp;

import com.yfy.final_tag.stringtool.StringUtils;

/**
 * Created by AJ
 * <p/>
 * 服务器接口地址
 */
public class ApiUrl {


    public final static String SERVER_ROOT = "http://new.cdeps.sc.cn/";

    public final String normal = "AppService";

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
}