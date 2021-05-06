package com.yfy.app.duty_evaluate.bean;

import java.util.List;

public class StuReportRes {

    /**
     * result : true
     * error_code : "teacherscore": "0",
     *   "stuscore": "0",
     */

    private String result;
    private String error_code;

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

    //---------------menu--------------------
    private List<MenuBean> menulist;

    public List<MenuBean> getMenulist() {
        return menulist;
    }

    public void setMenulist(List<MenuBean> menulist) {
        this.menulist = menulist;
    }


    //-----------------------studies_getvote------------------
    private String teacherscore;
    private String stuscore;
    private List<ReportOne> tablelist;

    public String getTeacherscore() {
        return teacherscore;
    }

    public void setTeacherscore(String teacherscore) {
        this.teacherscore = teacherscore;
    }

    public String getStuscore() {
        return stuscore;
    }

    public void setStuscore(String stuscore) {
        this.stuscore = stuscore;
    }

    public List<ReportOne> getTablelist() {
        return tablelist;
    }

    public void setTablelist(List<ReportOne> tablelist) {
        this.tablelist = tablelist;
    }

    //--------------dossier--------------

    private List<ScoreInfo>  scoreinfo;
    private List<HealthBean>  healthinfo;
    private List<StuReportBase>  stuinfo;

    public List<ScoreInfo> getScoreinfo() {
        return scoreinfo;
    }

    public void setScoreinfo(List<ScoreInfo> scoreinfo) {
        this.scoreinfo = scoreinfo;
    }
    public List<HealthBean> getHealthinfo() {
        return healthinfo;
    }

    public void setHealthinfo(List<HealthBean> healthinfo) {
        this.healthinfo = healthinfo;
    }

    public List<StuReportBase> getStuinfo() {
        return stuinfo;
    }

    public void setStuinfo(List<StuReportBase> stuinfo) {
        this.stuinfo = stuinfo;
    }


    //-------------------stu get Stu list-----------------
    private List<StuBeanReport> stulist;

    public List<StuBeanReport> getStulist() {
        return stulist;
    }

    public void setStulist(List<StuBeanReport> stulist) {
        this.stulist = stulist;
    }


}
