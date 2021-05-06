package com.yfy.app.PEquality.bean;

import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/3/29
 */
public class QEHonorRes {
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

    //honor
    private String total;
    public List<QEHonorBean> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<QEHonorBean> getData() {
        return data;
    }

    public void setData(List<QEHonorBean> data) {
        this.data = data;
    }
}
