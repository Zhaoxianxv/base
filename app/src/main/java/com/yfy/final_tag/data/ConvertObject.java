package com.yfy.final_tag.data;


/**
 * 转换数据类型
 *
 */
public class ConvertObject {

    private static ConvertObject object;

    public static ConvertObject getInstance(){
        if (object ==null){
            object =new ConvertObject();
        }
        return object;
    }

    public int getInt(String s){
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
        return Integer.valueOf(s);
    }

    public float getFloat(String s){
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return -1;
        }
        return Float.valueOf(s);
    }

    public String getString(float f){

        return String.valueOf(f);
    }
    public String getString(int i){

        return String.valueOf(i);
    }


}
