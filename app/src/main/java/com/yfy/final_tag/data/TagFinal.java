package com.yfy.final_tag.data;

import android.os.Environment;

/**
 * Created by yfyandr on 2017/9/12.
 */

public  class TagFinal {


    public static String getAppFile(){
        return Environment.getExternalStorageDirectory().toString() + "/yfy/";
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

    public final static int LOADING = ONE_INT;//
    public final static int LOADING_COMPLETE = TWO_INT;//
    public final static int LOADING_END = THREE_INT;//

    public static final int CAMERA= 1003;//调用摄像头
    public static final int PHOTO_ALBUM = 1004;//调用相册
    public static final int NET_WIFI = 1005;//NET_WIFI
    public static final int CALL_PHONE = 1006;//NET_WIFI
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
    //基址
    public final static String CHENGDU_SHIYAN = "https://www.cdeps.sc.cn/AppService.svc";//成都实验
    public static final String NET_SOAP_ACTION = "http://tempuri.org/AppService/";
    public static final int TIME_OUT = 10000;
    public final static String WCF_TXT = "wcf.txt";
    public static final int UPLOAD_LIMIT = 100 * 1024;
    public static final long TOTAL_UPLOAD_LIMIT = 4 * 1024 * 1024;

    //常用字段名称
    public static final String NAMESPACE = "http://tempuri.org/";//

    public static final String TEM = "tem";//
    public static final String BODY = "Body";//
    public static final String RESPONSE = "Response";//
    public static final String RESULT = "Result";//
    public static final String XMLNS = "xmlns";//
    public static final String session_key = "session_key";//
    public static final String type = "type";//
    public static final String state = "state";//
    public static final String page = "page";//
    public static final String size = "size";//
    public static final String id = "id";//





    public static final String TRUE="true";
    public static final String FALSE="false";
    public static final String REFRESH="refresh";
    public static final String REFRESH_MORE="refresh_more";
    public static final String MAP_TXT_TAG="map_txt";
    public static final String MAP_PIC_TAG="map_pic";
    public static final String ALBUM_LIST_INDEX="index";
    public static final String ALBUM_SINGLE="single";
    public static final String ALBUM_TAG="album_tag";
    public static final String ALBUM_SINGE_URI="album_singe_uri";
    public static final String ACTION_INTENT_FILTER="zxx.intent.badge";
    public static final String BADGE="badge";//桌面小红点badge
    public static final String CLASS_BEAN="class_bean";//班级对象tag
    public static final String PRAISE_TAG="praise";
    public static final String DELETE_TAG="delete";
    public static final String OBJECT_TAG="object_tag";
    public static final String ID_TAG="id_tag";
    public static final String NAME_TAG="name_tag";
    public static final String TITLE_TAG="title_tag";
    public static final String CONTENT_TAG="content_tag";
    public static final String HINT_TAG="hint_tag";
    public static final String URI_TAG="uri_tag";
    public static final String CLASS_ID="class_id";//
    public static final String FORBID_TAG="forbid_tag";
    public static final String USER_TYPE_TEA="tea";
    public static final String USER_TYPE_STU="stu";
    public static final String ZXX="zxx";

    /**
     *   ------------base------------------------
     */


    public static final String BASE_SAVE_IMG ="saveimg";//获取个人中心基础数据
    public static final String BASE_GET_TOKEN ="gettooken";//获取登录验证token





    public static final String USER_BASE_DATA="get_stu_baseinfo";//获取个人中心基础数据
    public static final String USER_BASE_UPDATA="set_stu_baseinfo";//设置个人中心基础数据
    public static final String AUTHENTICATION_LOGIN ="signup";//
    public static final String AUTHENTICATION_GET_STU ="getstuxx";//获取学生
    public static final String AUTHEN_SET_STU="setstuxx";//设置学生信息
    public static final String BASE_GET_CLASS_ALL="setstuxx";//获取全部班级


    /**
     * -------------------user相关-------------------
     */
    public static final String USER_LOGIN ="login";//登录
    public static final String USER_GET_DUPLICATION_LIST = "user_duplication";//获取登录重名学生列表
    public static final String USER_DUPLICATION_LOGIN = "logstu";//重名学生登录
    public static final String USER_LOGOUT="logout";//用户退出登录logout
    public static final String USER_CHANGE_PASSWORD ="password_reset";//修改密码（晓得原密码）
    public static final String USER_RESET_PASSWORD ="reset_password_password";//重置密码
    public static final String USER_GET_CODE_TO_EDIT_PHONE ="reset_password_vcode";//重置密码获取验证码
    public static final String USER_ADD_HEAD="addphoto";//用户添加头像
    public static final String USER_GET_ADMIN ="user_right";//获取权限

    public static final String USER_GET_CODE_TO_SYSTEM_PHONE ="get_phone_code";//给用户绑定号码发送验证码（返回有code）


    public static final String GETNOTICENUM = "getnoticenum";//小红点
    public static final String CLEAR_NOTICE_NUM = "clearnoticenum";//清除红点
    public static final String READNOTICE="readnotice";//阅读小红
    public static final String USER_SIGN ="sign";//登录签到
    public static final String USER_GET_MOBILE = "get_Mobile";//获取电话
    public static final String USER_SET_MOBILE = "set_Mobile";//置电话
    public static final String USER_GET_CURRENT_TERM = "getCurrentTermnew";//获取当前学期
    public static final String USER_GET_TERM_LIST = "gettermlistnew";//获取学期
    public static final String USER_INFO="user_info";//
    /**
     * -------------------vote--affice-----------------------------
     */
    public static final String VOTE_GET_MAIN_LIST = "getvotelist";//组列表信息
    public static final String VOTE_GET_ITEM_DETAIL = "getvotebyid";//item detail
    public static final String VOTE_SUBMIT = "vote";//提交

    public static final String SCHOOL_GET_MENU="get_newslist_menu";//获取栏目列表
    public static final String SCHOOL_GET_NEWS_LIST ="get_newslist_list";//获取栏目新闻列表
    public static final String SCHOOL_GET_NEWS_BANNER = "get_newslist_picture";


    /**
     * -----------------食谱----------------------
     */

    public static final String FOOT_BOOK_GET_WEEK = "get_cookbook";//获取本周食谱
    public static final String FOOT_BOOK_PRAISE = "cookbook_praise";//食谱点赞
    public static final String FOOT_BOOK_GET_POPULAR = "get_popular_cookbook";//获取排行食谱
    public static final String FOOT_BOOK_ADD_SUGGEST = "cookbook_words";//提交建议


    /**
     * -------------------e_book-------------------------------
     */
    public static final String BOOK_GET_TAG = "get_book_tag";//获取
    public static final String BOOK_GET_USER_LIST = "get_book_list";//获取
    public static final String VIDEO_GET_TAG = "get_video_tag";//获取
    public static final String VIDEO_GET_MAIN_LIST = "get_video_list";//获取


    /**
     * ----------------notice----------------------
     */
    public final static String NOTICE_GET_USER_DETAIL = "get_notice_content";//消息内容（图片）
    public final static String NOTICE_GET_USER_LIST = "receive_notice_list";//获取收件箱信息列表
    public final static String NOTICE_GET_SEND_BOX_LIST = "send_notice_list";//发件箱消息列表
    public final static String NOTICE_GET_SEND_BOX_DETAIL = "get_notice_readstate";//发件箱消息回执详情
    public final static String NOTICE_READ = "read_notice";//标记已读notice
    public final static String NOTICE_GET_TEA = "get_contacts_tea";// notice 获取联系人tea
    public final static String NOTICE_GET_STU = "get_contacts_stu";// notice 获取联系人stu
    public final static String NOTICE_SEND = "send_notice";//发送消息
    public final static String NOTICE_GET_TEA_CONTACTS = "get_contacts_tea";// notice 获取联系人tea get_gxxcnotice_user


    /**
     * ---------------------------流程报修-----------
     */
    public final static String MAINTENANCE_GET_COUNT = "get_maintain_review_count";//获取需要处理次数
    public final static String MAINTENANCE_GET_MAIN_LIST_USER = "get_Maintain_usersyxx";//获取首页列表普通用户dealstate;//user：0; admin:-1
    public final static String MAINTENANCE_GET_MAIN_LIST_ADMIN = "get_Maintain_adminsyxx";//获取首页列表管理用户
    public final static String MAINTENANCE_DELETE_MAINTAIN = "delete_maintain";//撤销自己申请
    public final static String MAINTENANCE_SET_SECTION = "setMaintainclassid";//部门转交
    public final static String MAINTENANCE_GET_SECTION = "getMaintainclass";//获取部门分类
    public final static String MAINTENANCE_ADD = "addMaintainnewsyxx";//新增报修
    public final static String MAINTENANCE_ADMIN_SET_STATE = "setMaintain_syxx";//同意，拒绝， dealstate,//1,完成2，拒绝,3 维修中
    public final static String MAINNEW_GET_DETAIL= "get_maintain";//获取需要处理次数

    /**
     *  审核列表 attendance
     */
    public final static String ATTENDANCE_GET_COUNT ="get_attendance_review_count";//审核操作数量
    public final static String ATTENDANCE_GET_USER_LIST = "attendance_list_syxx";//获取首页列表普通用户
    public final static String ATTENDANCE_GET_ADMIN_LIST = "attendance_review_list_syxx";//获取首页审核列表
    public final static String ATTENDANCE_GET_AUDITOR_LIST ="attendance_approve";//审核人列表
    public final static String ATTENDANCE_GET_TYPE ="attendance_type";//请假type
    public final static String ATTENDANCE_APPLY ="attendance_submit1";//新增提交cdpx
    public final static String ATTENDANCE_ADMIN_DO ="attendance_did_review_syxx";//审核操作(同意1，申请2，驳回3，校长4)
    public final static String ATTENDANCE_DELETE ="delete_attendance";//撤销申请

    /**
     * -----------------------shape----------------------
     */
    public final static String SHAPE_MAIN_LIST= "WB_get_class";//获取shape首页列表
    public final static String SHAPE_DID_PRAISE= "WB_did_praise";//为Item praise
    public final static String SHAPE_DID_DELETE= "WB_delete";//删除 Item
    public final static String SHAPE_DID_ADD= "WB_add";//新增 Item
    public final static String SHAPE_DID_DELETE_REPLY= "WB_delete_reply";//删除 Item 的回复
    public final static String SHAPE_GET_TAG= "WB_get_tag";//获取tag
    public final static String SHAPE_DID_REPLY= "WB_did_reply";//回复 WB_did_reply
    public final static String SHAPE_PERSON_DETAIL= "WB_person_detail";//个人详情
    public final static String SHAPE_PERSON_GET_CLASS= "WB_person_get_class";//个人详情

    /**
     * -----------------------个人成果---------------------------
     * （学生自己上串）
     */
    public final static String HONOR_ADD = "add_reward";//个人添加个人成果
    public final static String HONOR_GET_STU_REWARD = "get_stu_reward";//获取学生个人成果列表
    public final static String HONOR_DELETE_ONE_REWARD = "del_reward";//delete单条个人成果
    public final static String HONOR_TYPE="get_rewardtype";//个人成果荣誉类型
    public final static String HONOR_RANK="get_rewardrank";//个人成果荣誉等级（省，市区）


    /**
     * ------------------------学生奖励---------------------
     */

    public final static String AWARD_TEA_CLASS_LIST = "get_tea_course";//老师进入查看时班级列表
    public final static String AWARD_CLASS_AWARD_DETAIL = "get_class_award";//老师进入班级详情列表
    public final static String AWARD_GET_COURSE = "get_tea_award_self";//管理学生列表(添加学生奖励时选择学科后的学生列表)
    public final static String AWARD_DEL_UTIL = "del_award_students";//取消学科优生
    public final static String AWARD_ADD_UTIL = "set_award_students";//添加学生奖励
    public final static String AWARD_STU_CLASS_LIST = "get_stu_award";//学生进入学科优生
    public final static String AWARD_STU_GET_INFO="award_student_info_stu";//学生看自己
    public final static String AWARD_TEA_GET_STU_INFO="award_student_info";//老师看学生
    public final static String AWARD_TEA_ADD = "send_award";//老师发放奖励
    public final static String AWARD_STU_ADD = "send_stu_award";//学生上传奖励
    public final static String get_award_students="get_award_students";//
    public final static String AWARD_GET_TYPE="get_award_type";//

    /**
     * ---------------------------功能室申请------------
     */
    public final static String ORDER_GET_COUNT="review_funcRoom_count";//个数
    public final static String ORDER_GET_USER_LIST ="my_funcRoom";//我的记录-1 全部 5 已结束
    public final static String ORDER_GET_ADMIN_LIST ="review_funcRoom_record";//审核记录 list
    public final static String ORDER_GET_LOGISTICS_LIST ="logistics_funcRoom";//后勤处理列表
    public final static String ORDER_GET_DETAIL="get_funcRoom_detail";//单条详情
    public final static String ORDER_GET_ROOM_NAME="get_funcRoom_name";//获取功能室名称
    public final static String ORDER_GET_APPLY_GRADE ="get_funcRoom_type";//获取预约等级
    public final static String ORDER_QUERY = "query_funcRoom";//查功能室状态
    public final static String ORDER_ADD="submit_funcRoomsyxx";//添加申请
    public final static String ORDER_ADMIN_SET ="review_funcRoom";//审核接口：2拒绝1同意
    public final static String ORDER_SET_SCORE="set_funcRoom_score";//打分 islogistics(0不打，1打), logisticsscore, logisticscontent,


    /**
     * ---------------------------教评测------------------
     */
    public final static String TEA_JUDGE_GET_ADD_TYPE ="get_teacher_judge_class";//获取添加考评类型
    public final static String TEA_JUDGE_GET_STATISTICS_TYPE ="get_teacher_judge_statistics_class";//获取统计考评类型
    public final static String TEA_JUDGE_GET_YEAR ="get_teacher_judge_year";//获取考评年份。
    public final static String TEA_JUDGE_GET_STATISTICS_DATA ="get_teacher_judge_statistics";//获取考评统计数据
    public final static String TEA_JUDGE_GET_INFO_DETAIL ="get_teacher_judge_info";//获取单个考评的详细内容
    public final static String TEA_JUDGE_GET_INFO_YEAR_LIST ="get_teacher_judge_record_list";//获取统计单项考评某一年获奖记录列表
    public final static String TEA_JUDGE_GET_ADD_PARAMETER ="get_teacher_judge_parameter";//获取添加考评参数
    public final static String TEA_JUDGE_ADD="add_teacher_judge";//添加考评
    public final static String TEA_JUDGE_DEL_PIC ="del_teacher_judge_image";//删除考评附件
    public final static String TEA_JUDGE_DEL_ITEM ="del_teacher_judge_list";//del item


    /**
     * -----------------文化物品申领----------------
     */
    public final static String GOODS_GET_COUNT_ADMIN ="get_office_supply_review_count";//获取申领审核个数
    public final static String GOODS_GET_COUNT_MASTER ="get_office_supply_master_count";//获取需要审核的个数
    public final static String GOODS_GET_COUNT_SCHOOL ="get_office_supply_school_count";//获取待处理申请条数
    public final static String GOODS_GET_USER_RECORD_LIST ="get_office_supply_user";//我的申领记录
    public final static String GOODS_GET_ADMIN_RECORD_LIST ="get_office_supply_adminnew";//管理审核申领记录
    public final static String GOODS_GET_MASTER_RECORD_LIST ="get_office_supply_bymaster";//获取待审核的采购物品列表
    public final static String GOODS_GET_SCHOOL_RECORD_LIST ="get_office_supply_byschool";//来自分校的办公用品申领审核列表
    public final static String GOODS_GET_MASTER_USER ="get_office_supply_master";//获取审核新物品校长列表
    public final static String GOODS_GET_STATIONERY_TYPE ="get_office_supply_type";//获取办公物品分类
    public final static String GOODS_GET_ITEM_DETAILS ="get_office_supply_content";//单条申领详情

    public final static String GOODS_DELETE_ITEM="delete_office_supply";//撤销申请
    public final static String GOODS_ADD_APPLY ="add_office_supply";//添加申领物品(日常添加masterid传0新增传接口get_office_supply_master)
    public final static String GOODS_ADD_STATIONERY_TYPE ="add_office_supply_goods";//添加物品
    public final static String GOODS_ADMIN_APPLY ="set_office_supply";//申请审核（管理/校长）
    public final static String GOODS_SEARCH_GET_STATIONERY_TYPE ="search_office_supply_classify";//搜索物品分类

    public final static String GOODS_NUM_GET_COUNT ="get_office_supply_surpluscount_count";//物品数量添加待审核数量
    public final static String GOODS_NUM_GET_USER_LIST ="get_office_supply_surpluscount";//
    public final static String GOODS_NUM_GET_ADMIN_LIST ="get_office_supply_countadmin";//我操作的办公用品库存库存数量修改记录
    public final static String GOODS_NUM_GET_BYID="get_office_supply_countbyid";//获取单条申请记录详情
    public final static String GOODS_NUM_DO_STATE="set_office_supply_countstate";//
    public final static String GOODS_NUM_ADD ="set_office_supply_count";//增加物品数量接口
    /**
     * ---------------------duty--行政值周---------------------------
     */

    public final static String USER_GET_WEEK_ALL="get_termweek_all";//所有学期，周列表
    public final static String DUTY_GET_USER="get_dutyreport_list";//获取记录
    public final static String DUTY_GET_PLANE="get_dutyreport_plane";//duty add 选日期
    public final static String DUTY_GET_ADD_DETAILS="get_dutyreport_mydetail";//duty get add  details 获取值周操作信息
    public final static String DUTY_ADD="add_dutyreport";//增加或修改记录
    public final static String DUTY_CHANGE="set_dutyreport_plane";//临调设置
    public final static String DUTY_TYPE="get_dutyreport_type";//"校级行政值周", "中层行政值周
    public final static String DUTY_DEL_IMAGE="del_dutyreport_image";//删除值周图片接口，和以前删除一样
    public final static String USER_GET_TERM_WEEK="get_termweek_byterm";//指定学期周列表


    /**
     *------------------------event 大事记--------------------
     */
    public final static String EVENT_ADD="add_evenet_list";//
    public final static String EVENT_GET_SECTION ="get_evenet_depart";//获取部门
    public final static String EVENT_GET_WEEK="get_evenet_week";//获取周的列表
    public final static String EVENT_GET_WEEK_LIST="get_evenet_listbyweek";//按周显示大事记
    public final static String EVENT_GET_DATE_LIST="get_evenet_list";//按日期显示大事记(部门id 显示所有部门传0:id 传0为所有周)
    public final static String EVENT_GET_USER_LIST ="get_evenet_mylist";//我的大事记
    public final static String EVENT_DEL="del_evenet";//删除大事记
    public final static String EVENT_DEL_IMAGE="del_evenet_image";//删除大事记图片


    /**
     * -------------------教师师德 学校管理 满意率评测---------------------- satisfaction
     */
    //stu
    public final static String SATISFACTION_STU_GET_TEAS ="get_TeachersMoral_teachers";// 学生获取师德满意率教师列表
    public final static String SATISFACTION_STU_GET_SCHOOL = "get_TeachersMoral_detailschool";//学生获取学校管理满意率列表
    public final static String SATISFACTION_STU_SET_SCHOOL_SCORE ="set_TeachersMoral_school";//给学校打分
    public final static String SATISFACTION_STU_SET_TEA_SCORE ="set_TeachersMoral";// 给教师打分
    public final static String SATISFACTION_GET_DETAILS ="get_TeachersMoral_detail";// 获取考评项目
    //tea
    public final static String SATISFACTION_TEA_GET_MAIN ="get_TeachersMoral_resultself";//获取教师自己的考评结果
    public final static String SATISFACTION_TEA_GET_STU ="get_TeachersMoral_incompletestu";//获取未打分学生列表

    /**
     * ------------------------check---------------------
     */
    //stu
    public final static String CHECK_GET_TYPE="illstate_inspecttype";//获取获取检查类型
    public final static String CHECK_GET_CLASS="illstate_grouplist";//获取检查班级/教师分组
    public final static String CHECK_GET_STU="illstate_userlist";//获取分组学生/教师情况
    public final static String CHECK_GET_STU_ITEM_DETAIL ="illstate_detail";//学生单挑生病记录
    public final static String CHECK_GET_STU_ALL_DETAIL ="illstate_userhisotry";//学生全部记录
    public final static String CHECK_SUBMIT_YES="illstate_addnormal";//当前班级正常时提交
    public final static String CHECK_ADD_PARENT="illstate_addill";//增加生病学生
    public final static String CHECK_ADD_CHILD= "illstate_addillstate";//add生病state
    public final static String CHECK_DEL_CHILD="illstate_delillstate";//删除缺勤记录
    public final static String CHECK_DEL_PARENT="illstate_delill";//
    //tea
    public final static String CHECK_GET_ILL_TYPE="illstate_illtype";//获取缺勤种类
    public final static String CHECK_GET_TJ="illstate_checklist";//获取缺勤统计
    public final static String CHECK_GET_STU_TO_TYPE_ILL="illstate_checkdetail";//获取某种病情的学生

    //------------------------------seal--印章-------------------------
    public final static String SEAL_GET_MASTER_COUNT="signet_approval_count";//
    public final static String SEAL_GET_USER_LIST="signet_askfor_list";//
    //-1所有，100未通过，1校级领导审核通过，2待取章、待盖章,3已取章，5已还章、已盖章
    public final static String SEAL_GET_ADMIN_LIST="signet_approval_list";
    public final static String SEAL_GET_APPLY_MASTER_LIST="signet_Approval_user";//获取审批人列表
    public final static String SEAL_GET_ITEM_BYID="signet_single";//
    public final static String SEAL_GET_ALL="signet_borrow_list";//获取已经提交的数据
    public final static String SEAL_GET_SEAL_TYPE="signet_list";//获取印章种类
    public final static String SEAL_SUBMIT="signet_add";//添加    新增为0，修改为具体的id
    public final static String SEAL_DO="signet_set";//操作id，signet_single获取opear


    //----------------------DELAY  延时服务--------------------
    public final static String DELAY_GET_CLASS_LIST_TEA = "get_Elective_class";//用户获取延时服务班级-任课
    public final static String DELAY_GET_CLASS_LIST_REPLACE ="get_Elective_replace";//用户获取选修班级-代课
    public final static String DELAY_GET_CLASS_STU_LIST ="get_Elective_stunew";//获取班级学生列表
    public final static String DELAY_DEL="del_Elective_list";//删除考察选项（使用修改）
    public final static String DELAY_GET_ITEM_DETAIL ="get_Elective_detail";//获取某条考评信息详情
    public final static String DELAY_TEA_ADD ="add_Elective_detail";//tea对单个stu添加考勤
    public final static String DELAY_GET_TYPE ="get_Elective_opear";//获取tea考勤类型：isclass 0为给学生打分选项 1为给班级打分选项
    public final static String DELAY_GET_COPY_LIST ="get_Elective_evaluate";// 获取的选择列表（tea复制考勤操作）
    public final static String DELAY_COPY_SET ="set_Elective_evaluate";//tea复制考勤
    public final static String DELAY_ADMIN_GET_CLASS_LIST ="get_Elective_report";//服务管理获取班级
    public final static String DELAY_ADMIN_CLASS_SET ="set_Elective_adminnew";//服务管理巡查班级
    public final static String DELAY_ADMIN_GET_TODAY_LIST ="get_Elective_admin";//服务管理获取当天tea的考勤(isaddbyme代表是否是我添加的，如果是我添加的可以修改)
    public final static String DELAY_ADMIN_get_TO_CLASS_EVENT_DETAIL ="get_Elective_situation";//获取行政人员对班级的巡查考勤

    //没有使用
    public final static String DELAY_TIME_CHECK_LIST ="get_Elective_evaluatecheck";//
    public final static String DELAY_TIME_CHECK_SET ="set_Elective_evaluatecheck";//
    public final static String DELAY_DELIMAGE ="del_Elective_image";//删除图片
    public final static String DELAY_NORMAL ="add_Elective_normal";//批量设置学生正常状态

    /**
     * -------------------boss-------------------
     */
    public final static String BOSS_LATE_LIST="get_late_list_headmaster";//
    public final static String BOSS_MAINTAIN_LIST="getMaintain_headmaster";//getMaintain_headmaster
    public final static String BOSS_MAINTAIN_LIST_DETAIL="get_Maintain_byid_headmaster";//
    public final static String BOSS_ATTEN_LIST="attendance_list_headmaster";//
    public final static String BOSS_ORDER_LIST="review_funcRoom_record_headmaster";//


    //-------------------process-------------------
    public final static String PROCESS_GET_USER_COUNT="get_process_count";//获取我申请的流程列表
    public final static String PROCESS_GET_USER_DATA_LIST="get_process_list";//获取我申请的流程列表
    public final static String PROCESS_GET_ITEM_DETAIL="get_process_detail";//获取流程详情
    public final static String PROCESS_DEL_ITEM_DETAIL="set_process_delete";//流程删除
    public final static String PROCESS_ADMIN_GET_DATA_LIST ="get_process_adminlist";//获取要审核d申请流程列表
    public final static String PROCESS_ADMIN_SET_CLASS ="set_process_class";//设置流程状态
    public final static String PROCESS_ADMIN_SET_STATE ="set_process_state";//设置流程状态
    public final static String PROCESS_ADD ="add_process";//
    public final static String PROCESS_GET_TYPE ="get_process_type";//
    public final static String PROCESS_GET_TERM ="get_process_term";//
    public final static String PROCESS_GET_GROUP ="get_process_groupresult";//

}

