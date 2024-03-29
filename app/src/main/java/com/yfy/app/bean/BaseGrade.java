package com.yfy.app.bean;



import java.util.List;

public class BaseGrade {

    /**
     * gradename : 1年级
     * gradeid : 0
     */

    private String gradename;
    private String gradeid;
    private List<BaseClass> classlist;

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public List<BaseClass> getClasslist() {
        return classlist;
    }

    public void setClasslist(List<BaseClass> classlist) {
        this.classlist = classlist;
    }
}
