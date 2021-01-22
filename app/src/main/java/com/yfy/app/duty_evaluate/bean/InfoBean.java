package com.yfy.app.duty_evaluate.bean;

import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/1/19
 */
public class InfoBean {

    /**
     * parent_title : 遵守纪律
     * parent_score : 32
     * parent_all_score : 42
     */

    private String parent_title;
    private String parent_score;
    private String parent_all_score;
    private List<SeBean> evaluate_list;

    public List<SeBean> getEvaluate_list() {
        return evaluate_list;
    }

    public void setEvaluate_list(List<SeBean> evaluate_list) {
        this.evaluate_list = evaluate_list;
    }

    public String getParent_title() {
        return parent_title;
    }

    public void setParent_title(String parent_title) {
        this.parent_title = parent_title;
    }

    public String getParent_score() {
        return parent_score;
    }

    public void setParent_score(String parent_score) {
        this.parent_score = parent_score;
    }

    public String getParent_all_score() {
        return parent_all_score;
    }

    public void setParent_all_score(String parent_all_score) {
        this.parent_all_score = parent_all_score;
    }
}
