package com.yfy.greendao;

import com.yfy.final_tag.data.TagFinal;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;


@Entity
public class KeyValueDb {
    /**
     *  查询数据 功能model_type\type\type_user_id\type
     */
    @NotNull
    private String model_type="";//用于判断了个功能木快5.7
    @NotNull
    private String type="";//用于判断查询数据类型
    @NotNull
    private String type_id="";//用于判断查询数据类型
    @NotNull
    private String type_parent_id="";//用于判断查询数据类型
    @NotNull
    private String type_user_id="";//用于判断查询数据类型
    @NotNull
    private String term="";
    //用于保存数据
    @NotNull
    private String key="";//描述值
    @NotNull
    private String value="";//值
    @NotNull
    private String name="";//值名
    @NotNull
    private String title="";
    @NotNull
    private String rank="";

    @NotNull
    private String image="";//图片
    @NotNull
    private String time="";
    @NotNull
    private String file_path="";
    @NotNull
    private String parent_id="";//7.9
    @NotNull
    private String child_id="";
    @NotNull
    private String key_value_id="";//id
    @NotNull
    private int num=-1;
    @NotNull
    private float time_duration=-1;
    @NotNull
    private boolean required=true;//用于判断是否必填
    @NotNull
    private int view_type=TagFinal.TYPE_ITEM;
    @Id
    private Long id;
    @Generated(hash = 740160533)
    public KeyValueDb(@NotNull String model_type, @NotNull String type,
            @NotNull String type_id, @NotNull String type_parent_id,
            @NotNull String type_user_id, @NotNull String term, @NotNull String key,
            @NotNull String value, @NotNull String name, @NotNull String title,
            @NotNull String rank, @NotNull String image, @NotNull String time,
            @NotNull String file_path, @NotNull String parent_id,
            @NotNull String child_id, @NotNull String key_value_id, int num,
            float time_duration, boolean required, int view_type, Long id) {
        this.model_type = model_type;
        this.type = type;
        this.type_id = type_id;
        this.type_parent_id = type_parent_id;
        this.type_user_id = type_user_id;
        this.term = term;
        this.key = key;
        this.value = value;
        this.name = name;
        this.title = title;
        this.rank = rank;
        this.image = image;
        this.time = time;
        this.file_path = file_path;
        this.parent_id = parent_id;
        this.child_id = child_id;
        this.key_value_id = key_value_id;
        this.num = num;
        this.time_duration = time_duration;
        this.required = required;
        this.view_type = view_type;
        this.id = id;
    }
    @Generated(hash = 980486890)
    public KeyValueDb() {
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getModel_type() {
        return this.model_type;
    }
    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }
    public String getParent_id() {
        return this.parent_id;
    }
    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
    public String getChild_id() {
        return this.child_id;
    }
    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }
    public boolean getRequired() {
        return this.required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
    public int getView_type() {
        return this.view_type;
    }
    public void setView_type(int view_type) {
        this.view_type = view_type;
    }
    public String getKey_value_id() {
        return this.key_value_id;
    }
    public void setKey_value_id(String key_value_id) {
        this.key_value_id = key_value_id;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getFile_path() {
        return this.file_path;
    }
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
    public float getTime_duration() {
        return this.time_duration;
    }
    public void setTime_duration(float time_duration) {
        this.time_duration = time_duration;
    }
    public String getType_user_id() {
        return this.type_user_id;
    }
    public void setType_user_id(String type_user_id) {
        this.type_user_id = type_user_id;
    }
    public String getType_id() {
        return this.type_id;
    }
    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
    public String getType_parent_id() {
        return this.type_parent_id;
    }
    public void setType_parent_id(String type_parent_id) {
        this.type_parent_id = type_parent_id;
    }
    public String getTerm() {
        return this.term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getRank() {
        return this.rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }


}
