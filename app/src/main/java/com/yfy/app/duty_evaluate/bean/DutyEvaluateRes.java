package com.yfy.app.duty_evaluate.bean;

import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/1/19
 */
public class DutyEvaluateRes {

    private String result;
    private String error_code;
    private String all_score;
    private List<InfoBean> info;


    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
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

    public String getAll_score() {
        return all_score;
    }

    public void setAll_score(String all_score) {
        this.all_score = all_score;
    }
}
