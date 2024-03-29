package com.yfy.app.bean;

import java.util.List;

public class BaseRes {
    private String result;
    private String error_code;
    private String tooken;
    private String img;
    private String stuid;
    private String classname;
    private String url;
    private String packagename;
    private int versionCode;


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTooken() {
        return tooken;
    }

    public void setTooken(String tooken) {
        this.tooken = tooken;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }


    /**
     * -------------USER_GET_MOBILE = "get_Mobile";//获取电话
     */

    private String Mobile;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    /**
     * ------------
     */
    private String headpic;
    private String session_key;
    private UserBean userinfo;
        public UserBean getUserinfo() {

        return userinfo;
    }


    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setUserinfo(UserBean userinfo) {
        this.userinfo = userinfo;
    }


    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }


    /**
     * ------------term---------
     */
    private List<TermBean> term;

    public List<TermBean> getTerm() {
        return term;
    }

    public void setTerm(List<TermBean> term) {
        this.term = term;
    }


    /**
     * --------------Verification_Code验证码
     */
    private String Verification_Code;
    private String login_name;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getVerification_Code() {
        return Verification_Code;
    }

    public void setVerification_Code(String verification_Code) {
        Verification_Code = verification_Code;
    }

    //----------------base grade----------
    private List<BaseGrade> gradelist;

    public List<BaseGrade> getGradelist() {
        return gradelist;
    }

    public void setGradelist(List<BaseGrade> gradelist) {
        this.gradelist = gradelist;
    }


    //--------------incomplete stu---------------

    private List<StuBean> incompletestu;

    public List<StuBean> getIncompletestu() {
        return incompletestu;
    }

    public void setIncompletestu(List<StuBean> incompletestu) {
        this.incompletestu = incompletestu;
    }
}
