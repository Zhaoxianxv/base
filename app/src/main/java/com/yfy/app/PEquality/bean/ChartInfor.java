package com.yfy.app.PEquality.bean;

import java.util.List;

/**
 * Created by yfy on 2017/6/30.
 */

public class ChartInfor {




    /**
     * stuid : 1177
     * stuname : 江浩宸
     */
    private String stuid;
    private String stuname;
    private String score;
    private List<ChartBeans> project;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<ChartBeans> getProject() {
        return project;
    }

    public void setProject(List<ChartBeans> project) {
        this.project = project;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStuid() {
        return stuid;
    }

    public String getStuname() {
        return stuname;
    }
}
