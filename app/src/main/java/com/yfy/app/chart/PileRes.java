package com.yfy.app.chart;

import com.yfy.json.JsonObj;


/**
 * Created by zxx.
 * Date: 2021/4/7
 */
public class PileRes  {



    public String name;
    public int id_nam;
    private PileBean salary_obj;




    public PileRes(String name, int id_nam) {
        this.name = name;
        this.id_nam = id_nam;
    }


    public PileBean getSalary_obj() {
        return salary_obj;
    }

    public void setSalary_obj(PileBean salary_obj) {
        this.salary_obj = salary_obj;
    }

    public int getId_nam() {
        return id_nam;
    }

    public void setId_nam(int id_nam) {
        this.id_nam = id_nam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
