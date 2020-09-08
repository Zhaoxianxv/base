package com.yfy.app.PEquality.bean;


import com.yfy.final_tag.data.TagFinal;

import java.util.List;

public class SchoolInfo {

    /**
     * schoolname : 成都市礼仪职业中学
     * school
     * id : 3
     * schooltype :
     * stucount : 15
     * boycount : 11
     * girlcount : 4
     */

    private String schoolname;
    private String schoolid;
    private String schooltype;
    private String stucount;
    private String boycount;
    private String girlcount;
    private List<Motbean> nationlist;
    private List<ScoreBean> score;
    private List<Grade> gradelist;

    //-------------head-----------

    private int view_type=TagFinal.TYPE_ITEM;


    private String session_key;//性别
    private String headpic;//性别
    private String term;//性别
    private String realname;//性别
    private String pass_word;//性别











    public List<Motbean> getNationlist() {
        return nationlist;
    }

    public void setNationlist(List<Motbean> nationlist) {
        this.nationlist = nationlist;
    }

    public List<ScoreBean> getScore() {
        return score;
    }

    public void setScore(List<ScoreBean> score) {
        this.score = score;
    }

    public List<Grade> getGradelist() {
        return gradelist;
    }

    public void setGradelist(List<Grade> gradelist) {
        this.gradelist = gradelist;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getSchooltype() {
        return schooltype;
    }

    public void setSchooltype(String schooltype) {
        this.schooltype = schooltype;
    }

    public String getStucount() {
        return stucount;
    }

    public void setStucount(String stucount) {
        this.stucount = stucount;
    }

    public String getBoycount() {
        return boycount;
    }

    public void setBoycount(String boycount) {
        this.boycount = boycount;
    }

    public String getGirlcount() {
        return girlcount;
    }

    public void setGirlcount(String girlcount) {
        this.girlcount = girlcount;
    }
}
