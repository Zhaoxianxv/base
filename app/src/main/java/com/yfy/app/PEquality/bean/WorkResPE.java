package com.yfy.app.PEquality.bean;

import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/11/9
 */
public class WorkResPE {


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

    //--------------------month_day_list----------

    public List<WorkDayPE> month_day_list;

    public List<WorkDayPE> getMonth_day_list() {
        return month_day_list;
    }

    public void setMonth_day_list(List<WorkDayPE> month_day_list) {
        this.month_day_list = month_day_list;
    }
}
