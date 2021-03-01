package com.yfy.app.PEquality.bean;


import com.yfy.greendao.bean.TermBean;

import java.util.List;

public class SchoolRes {

    /**
     * result : true
     * error_code :
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
    //--------------------school----------
    private List<SchoolInfo> schoollist;
    public List<SchoolInfo> getSchoollist() {
        return schoollist;
    }

    public void setSchoollist(List<SchoolInfo> schoollist) {
        this.schoollist = schoollist;
    }


    //---------------------------getstulist=-=-=-=-=-=-=-=-=-=-

    private List<StuBean> stulist;

    public List<StuBean> getStulist() {
        return stulist;
    }

    public void setStulist(List<StuBean> stulist) {
        this.stulist = stulist;
    }


    //--------------------stu-----------
    private List<TermBean> termlist;


    public List<TermBean> getTermlist() {
        return termlist;
    }

    public void setTermlist(List<TermBean> termlist) {
        this.termlist = termlist;
    }



}
