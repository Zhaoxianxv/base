package com.yfy.db;

import android.content.Context;
import android.content.SharedPreferences;
import com.yfy.base.App;
/**
 *
 */
public class UserPreferences extends Preferences {
    /**
     * 配置文件名儿
     */
    private static final String PREFERENCE_NAME = "USER_INFO";


    private static UserPreferences userPreferences;

    private UserPreferences() {
    }

    public static UserPreferences getInstance() {
        if (userPreferences == null) {
            userPreferences = new UserPreferences();
        }
        return userPreferences;
    }



    public void clearUserAll(){
        clearAll();
    }



    public SharedPreferences getPreference() {
        return App.getApp().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存first
     */
    private static final String TAG_FIRST = "first";
    public void saveFirstTimeOpen(boolean name){
        saveBooolean(TAG_FIRST, name);
    }

    public boolean getIsFirstTimeOpen(){
        return getBoolean(TAG_FIRST,true);
    }



    /**
     *  保存推送 apikey
     */
    public void saveJPushKey(String key) {
        saveString("jpush_api_key", key);
    }
    public String getJPushKey() {
        return getString("jpush_api_key", "");
    }





    public void saveEbook(String tag,String code){
        saveString(tag,code);
    }
    public String getEbook(String tag){
        return getString(tag,"");
    }

    /**
     * 保存流程输入类容
     */
    public void saveContent(String password) {
        saveString("maintain_content", password);
    }
    public String getContent() {return getString("maintain_content", "");
    }


    /**
     * 保存主页模块顺序
     */
    public void saveIndex(String index_s){
        saveString("main_index",index_s);
    }
    public String getIndex(){
        return getString("main_index","");
    }

    /**
     * 保存Goods语言顺序
     */
    public void saveGoodsIndex(String index_s){
        saveString("goods_index",index_s);
    }
    public String getGoodsIndex(){
        return getString("goods_index","");
    }
    /**
     * 保存Goods语言json
     */
    public void saveGoodsJson(String index_s){
        saveString("goods_json",index_s);
    }
    public String getGoodsJson(){
        return getString("goods_json","");
    }
    /**
     * 保存tell
     */
    public void saveTell(String tell){
        saveString("tell_string",tell);
    }
    public String getTell(){
        return getString("tell_string","");
    }



    public void saveUserID(String name) {
        saveString("user_id", name);
    }
    public String getUserID() {
        return getString("user_id","");
    }


    public void saveUserSetView(String name) {
        saveString("user_set_view", name);
    }
    public String getUserSetView() {
        return getString("user_set_view","");
    }



}
