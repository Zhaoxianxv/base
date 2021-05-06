package com.yfy.final_tag.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

/**
 * Created by yfy on 2018/5/28.
 *      A---B---C---D---E---F
 *      10--11--12--13--14--15
 */
public class ColorRgbUtil {
    //
    public static int getResourceColor(Activity mActivity,int color_id){
        return mActivity.getResources().getColor(color_id);
    }
    public static int getParseColor(String color_s){
        return Color.parseColor(color_s);
    }


    public static int getBaseColor(){
        return Color.rgb(148,35,40);
    }

    public static int getOrangeRed(){
        return Color.rgb(255,69,0);
    }
    public static int getOrange(){
        return Color.rgb(255,128,0);
    }

    public static int getGreen(){
        return Color.rgb(0,128,0);
    }
    public static int getBlue(){
        return Color.parseColor("#0000E1");
    }
    public static int getGrayText(){
        return Color.parseColor("#A8A8A8");
    }

    public static int getBaseText(){
        return Color.parseColor("#2B2B2B");
    }

    public static int getWhite(){
        return Color.parseColor("#FFFFFF");
    }





    //     <color name="Crimson">#DC143C</color>           <!--:猩红: -->
    public static int getCrimson(){
        return Color.parseColor("#DC143C");
    }
    //      <color name="Pink">#FFC0CB</color>              <!--:粉红:  -->
    public static int getPink(){
        return Color.parseColor("#FFC0CB");
    }
    //      <color name="Gainsboro">#DCDCDC</color>         <!--亮灰	-->
    public static int getGainsboro(){
        return Color.parseColor("#DCDCDC");
    }
    //      <color name="LightGrey">#D3D3D3</color>         <!--浅灰色	-->
    public static int getLightGrey(){
        return Color.parseColor("#D3D3D3");
    }
    //      <color name="Silver">#C0C0C0</color>            <!--银白色	-->
    public static int getSilver(){
        return Color.parseColor("#C0C0C0");
    }

    public static int getMaroon(){
        return Color.rgb(128,0,0);
    }
    public static int getLightPink(){
        return Color.parseColor("#FFB6C1");
    }

    //   <!--淡珊瑚色	-->
    public static int getLightCoral(){ return Color.rgb(240,128,128); }
    //<!--森林绿-->
    public static int getForestGreen(){ return Color.rgb(34,139,34); }








    public static int getDrakGreen(){
        return Color.rgb(34,139,34);
    }
    public static int getGray(){
        return Color.rgb(128,128,128);
    }
    public static int getMediumBlue(){
        return Color.rgb(0, 0, 205);
    }



}
