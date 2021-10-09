package com.yfy.final_tag.stringtool;

import android.util.Log;

import com.yfy.final_tag.data.TagFinal;

/**
 * Created by efan on 2017/4/13.
 */

public class Logger {

    //设为false关闭日志
    private static final boolean LOG_ENABLE = true;
    //设为false关闭日志
    public static final boolean IS_SHOW_LOG_FLAG = true;
    public static final boolean IS_SHOW_LOG_NET_FLAG = false;//http_net显示
    public static final boolean IS_SHOW_LOG_LONG_TEXT_FLAG = false;//长文本显示Flag

    public static void i(String tag, String msg){
        if (LOG_ENABLE){
            Log.i(tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if (LOG_ENABLE){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if (LOG_ENABLE){
            Log.d(tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if (LOG_ENABLE){
            Log.w(tag, msg);
        }
    }

    public static void e( String msg){
        if (LOG_ENABLE){
            Log.e(TagFinal.ZXX, msg);
        }
    }

    public static void e(String tag, String msg){
        if (LOG_ENABLE){
            //自身有一定长度的String
            if(msg.length() > 4000) {
                int num=3000;
                for(int i=0;i<msg.length();i+=num){
                    if(i+num<msg.length()){
                        Log.e(tag,msg.substring(i, i+num));
                    }else{
                        Log.e(tag,msg.substring(i, msg.length()));
                    }
                }
            } else{
                Log.e(tag,msg);
            }
        }
    }
    public static void eLongText(String tag, String msg){
        if (IS_SHOW_LOG_FLAG){
            //自身有一定长度的String
            if(msg.length() > 4000) {
                if (IS_SHOW_LOG_LONG_TEXT_FLAG){
                    int num=3000;
                    for(int i=0;i<msg.length();i+=num){
                        if(i+num<msg.length()){
                            Log.e(tag,msg.substring(i, i+num));
                        }else{
                            Log.e(tag,msg.substring(i, msg.length()));
                        }
                    }
                }
            }else{
                Log.e(tag,msg);
            }
        }


    }
    public static void eLogText(String msg){
        if (IS_SHOW_LOG_FLAG){
            Log.e(TagFinal.ZXX, msg);
        }
    }


}
