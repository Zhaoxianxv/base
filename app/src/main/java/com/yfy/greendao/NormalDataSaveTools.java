package com.yfy.greendao;

import com.yfy.app.bean.TermBean;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.greendao.tool.GreenDaoManager;

import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/1/22
 */
public class NormalDataSaveTools {

    private final String TERM_TYPE_GREEN_DAO = "term_bean_data";
    private final String HONOR_TYPE_GREEN_DAO = "honor_bean_data";

    private static NormalDataSaveTools normalDataSaveApi;

    public synchronized static NormalDataSaveTools getInstance() {
        if (normalDataSaveApi == null) {
            normalDataSaveApi = new NormalDataSaveTools();
        }
        return normalDataSaveApi;
    }


    //---------------------保存当前学期、提取当前学期-------------------


    public void saveCurrentTerm(TermBean term) {
        List<KeyValueDb> db_index = GreenDaoManager.getInstance().getKeyValueDbList("where type = ?", TERM_TYPE_GREEN_DAO);
        if (StringJudge.isNotEmpty(db_index)) {
            for (KeyValueDb db : db_index) {
                GreenDaoManager.getInstance().removeKeyValue(db);
            }
        }
        KeyValueDb keyValue = new KeyValueDb();
        keyValue.setName(term.getName());
        keyValue.setKey_value_id(term.getId());
        keyValue.setValue(term.getIsnow());
        keyValue.setType("term_bean_data");
        GreenDaoManager.getInstance().saveKeyValueDb(keyValue);
    }

    public TermBean getTermBeanToGreenDao() {
        TermBean termBean = new TermBean();
        List<KeyValueDb> db_index = GreenDaoManager.getInstance().getKeyValueDbList("where type = ?", TERM_TYPE_GREEN_DAO);
        if (StringJudge.isEmpty(db_index)) {
            termBean.setName("");
            termBean.setId("");
            termBean.setIsnow("");
        } else {
            KeyValueDb db = db_index.get(0);
            termBean.setId(db.getKey_value_id());
            termBean.setName(db.getName());
            termBean.setIsnow(db.getValue());
        }
        return termBean;
    }


    public void saveHonor(KeyValueDb keyValue){

        keyValue.setType(HONOR_TYPE_GREEN_DAO);
        GreenDaoManager.getInstance().saveKeyValueDb(keyValue);
    }
}
