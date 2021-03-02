package com.yfy.greendao.tool;

import com.yfy.greendao.bean.BaseClass;
import com.yfy.greendao.bean.StuBean;
import com.yfy.greendao.bean.TermBean;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.greendao.KeyValueDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/1/22
 */
public class NormalDataSaveTools {

    private final String TERM_TYPE_GREEN_DAO = "term_bean_data";
    private final String TYPE_GREEN_STU_DATA = "type_green_stu_data";
    private final String TYPE_GREEN_CLASS_DATA = "type_green_class_data";
    private final String HONOR_TYPE_GREEN_DAO = "honor_bean_data";

    private static NormalDataSaveTools normalDataSaveApi;

    public synchronized static NormalDataSaveTools getInstance() {
        if (normalDataSaveApi == null) {
            normalDataSaveApi = new NormalDataSaveTools();
        }
        return normalDataSaveApi;
    }

    public void saveClassData(BaseClass bean) {

        KeyValueDb keyValue = new KeyValueDb();
        keyValue.setName(bean.getClassname());
        keyValue.setKey_value_id(bean.getClassid());
        keyValue.setType(TYPE_GREEN_CLASS_DATA);
        GreenDaoManager.getInstance().saveKeyValueDb(keyValue);

    }

    public List<BaseClass> getClassBeanToGreenDao() {
        List<KeyValueDb> db_index = GreenDaoManager.getInstance().getKeyValueDbList("where type = ?", TYPE_GREEN_CLASS_DATA);
        List<BaseClass> baseClassList=new ArrayList<>();
        if (StringJudge.isNotEmpty(db_index)) {
            for (KeyValueDb db : db_index) {
                BaseClass baseClass=new BaseClass();
                baseClass.setClassname(db.getName());
                baseClass.setClassid(db.getKey_value_id());
                baseClassList.add(baseClass);
            }
        }
        return baseClassList;
    }

    //--------------------保存stu
    public void saveStuData(StuBean stu) {

        KeyValueDb keyValue = new KeyValueDb();
        keyValue.setName(stu.getStuname());
        keyValue.setKey_value_id(stu.getStuid());
        keyValue.setType(TYPE_GREEN_STU_DATA);
        GreenDaoManager.getInstance().saveKeyValueDb(keyValue);

    }
    public List<StuBean> getStuBeanToGreenDao() {
        List<KeyValueDb> db_index = GreenDaoManager.getInstance().getKeyValueDbList("where type = ?", TYPE_GREEN_STU_DATA);
        List<StuBean> stuBeanList=new ArrayList<>();
        if (StringJudge.isNotEmpty(db_index)) {
            for (KeyValueDb db : db_index) {
                StuBean stu=new StuBean();
                stu.setStuname(db.getName());
                stu.setStuid(db.getKey_value_id());
                stuBeanList.add(stu);
            }
        }
        return stuBeanList;
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
        keyValue.setType(TERM_TYPE_GREEN_DAO);
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
