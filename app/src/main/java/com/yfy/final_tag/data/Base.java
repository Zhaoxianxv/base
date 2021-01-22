package com.yfy.final_tag.data;


import com.yfy.greendao.User;

/**
 * Created by Daniel on 2016/3/8.
 */
public class Base {
    public static String DB_NAME="yfydbone";


    public static final String AUTHORITY = "com.yfy.base.fileProvider";//android 7.0文件访问权限Tag（要和Provider一直）


    public static final String RETROFIT_URI = "http://testeps.yfyit.com/";
    public static final String SOAP_ACTION = "SOAPAction: http://tempuri.org/AppService/";//
    public static final String POST_URI = "/AppService.svc";//

    public static final String NAMESPACE = "http://tempuri.org/";//
    public static final String Content_Type = "Content-Type: text/xml;charset=UTF-8";//
    //app更新地址
    public static final String AUTO_UPDATE_URI = "http://www.yfyit.com/apk/cdsyxx.txt";
    public static final String HUA_WEI_PRIVATE = "http://www.yfyit.com/yszc.html";//
    public static final String HUA_WEI_AGREEMENT = "http://www.yfyit.com/yhxy.html";//


    /**
     *  json字符串
     */

    public static final String TEM = "tem";//
    public static final String ARR = "arr";//
    public static final String BODY = "Body";//
    public static final String RESPONSE = "Response";//
    public static final String RESULT = "Result";//
    public static final String XMLNS = "xmlns";//


    public static final String can_edit = "can_edit";//
    public static final String content = "content";//
    public static final String count = "count";//
    public static final String class_id = "class_id";//
    public static final String class_name = "class_name";//
    public static final String class_bean = "class_bean";//
    public static final String stu_bean = "stu_bean";//
    public static final String stu_name = "stu_name";//
    public static final String stu_id = "stu_id";//
    public static final String data = "data";//
    public static final String date = "date";//

    public static final String page = "page";//
    public static final String pagesize = "pagesize";//
    public static final String phone = "phone";//
    public static final String id = "id";//
    public static final String index = "index";//
    public static final String image = "image";//
    public static final String name = "name";//
    public static final String num = "num";//
    public static final String mode_type = "mode_type";//


    public static final String reason = "reason";//
    public static final String size = "size";//
    public static final String session_key = "session_key";//
    public static final String state = "state";//
    public static final String title = "title";//
    public static final String type = "type";//
    public static final String token = "token";//
    public static final String term_bean = "term_bean";//
    public static final String value = "value";//
    public static final String voice = "voice";//
    public static final String year_value="year_value";
    public static final String month_value="month_value";

    public static final String error_code = "session_key不正确";


    public static final int fxid = 13;
    public static final int UPLOAD_LIMIT = 100 * 1024;
    public static final long TOTAL_UPLOAD_LIMIT = 4 * 1024 * 1024;


    public static float density = 0;



    //tea
    public static String user_check_type="stu";
    public static final String USER_TYPE_STU="stu";
    public static final String USER_TYPE_TEA="tea";
    public static String year="";
    public static String process_type="";






    public static User user = null;

}
