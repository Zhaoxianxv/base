package com.yfy.app.duty_evaluate.bean;

import java.util.List;

public class ReportOne {

    /**
     * tableid : 202
     * tablename : 一级指标
     * tabletype : select
     */

    private String tableid;
    private String tablename;
    private String tabletype;

    private List<ReportTwo> tablelist;


    public List<ReportTwo> getTablelist() {
        return tablelist;
    }

    public void setTablelist(List<ReportTwo> tablelist) {
        this.tablelist = tablelist;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTabletype() {
        return tabletype;
    }

    public void setTabletype(String tabletype) {
        this.tabletype = tabletype;
    }
}
