package com.yfy.final_tag.data;


import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;




// Collections.sort(list, new Comparator<IllGroup>() {
//            @Override
//            public int compare(IllGroup p1, IllGroup p2) {
//                if(p1.getPosition() > p2.getPosition()){
//                    return 1;//-1从大到小
//                }
//                if(p1.getPosition() == p2.getPosition()){
//                    return 0;
//                }
//                return -1;
//                //可以按User对象的其他属性排序，只要属性支持compareTo方法
//            }
//        });

/**
 * ---------数学函数----------
 */
public class MathTool {

    //Math.ceil(): 天花板的意思，就是逢余进一
    //Math.floor() : 地板的意思，就是逢余舍一

    //System.out.println(Math.sqrt(16)); // 4.0 计算平方根
    //System.out.println(Math.cbrt(8)); // 2.0 计算立方根
    //System.out.println(Math.pow(3, 2)); // 9.0 计算a的b次方
    //System.out.println(Math.max(2.3, 4.5));// 4.5 计算最大值
    //System.out.println(Math.min(2.3, 4.5));// 2.3 计算最小值
    //System.out.println(Math.abs(-10.4)); // 10.4 取绝对值
    //System.out.println(Math.abs(10.1)); // 10.1 取绝对值

    //返回[min_num,max_num+min_num)区间的数
    //System.out.println(Math.random() * 2 + 1);// [1, 3)的double类型的数
    public static double getRandom(int min_num,int max_num){
        return Math.random() * max_num+min_num;
    }
    //[min_num, min+max)
    public static float getRandomInt(int min_num,int max_num){
        return getRound(Math.random() * max_num+min_num) ;
    }



    /**
     * round 四舍五入，float时返回int值，double时返回long值
     *  rint 四舍五入，返回double值 注意.5的时候会取偶数 异常的尴尬
     */
    //System.out.println(Math.round(10.1)); // 10；System.out.println(Math.round(10.5)); // 11
    //System.out.println(Math.round(-10.5)); // -10；System.out.println(Math.round(-10.51)); // -11
    //System.out.println(Math.rint(10.1)); // 10.0；System.out.println(Math.rint(10.7)); // 11.0
    //System.out.println(Math.rint(11.5)); // 12.0；System.out.println(Math.rint(10.5)); // 10.0
    //System.out.println(Math.rint(10.51)); // 11.0；System.out.println(Math.rint(-10.5)); // -10.0
    //System.out.println(Math.rint(-11.5)); // -12.0；System.out.println(Math.rint(-10.51)); // -11.0
    //System.out.println(Math.rint(-10.6)); // -11.0；System.out.println(Math.rint(-10.2)); // -10.0
    public static long getRound(double num){
        return Math.round(num);
    }
    public static double getRound(float num){
        return Math.round(num);
    }


    public static double getRint(double num){
        return Math.rint(num);
    }

    /**
     * ceil天花板的意思，就是逢余进一
     *  floor地板的意思，就是逢余舍一
     */
    //System.out.println(Math.ceil(-10.1)); // -10.0；System.out.println(Math.ceil(10.7)); // 11.0
    //System.out.println(Math.ceil(-0.7)); // -0.0；System.out.println(Math.ceil(0.0)); // 0.0
    //System.out.println(Math.ceil(-0.0)); // -0.0；System.out.println(Math.ceil(-1.7)); // -1.0

    //System.out.println(Math.floor(-10.1)); // -11.0；System.out.println(Math.floor(10.7)); // 10.0
    //System.out.println(Math.floor(-0.7)); // -1.0；System.out.println(Math.floor(0.0)); // 0.0
    //System.out.println(Math.floor(-0.0)); // -0.0


    /**
     * 小数处理
     */
    public static String stringToGetTwoToDecimals(float f){
        //构造方法的字符格式这里如果小数不足2位,会以0补足.(不包括 0)
        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        //format 返回的是字符串
        return decimalFormat.format(f);
    }


    public static String stringToGetToDecimals(String str,int num){
        if (StringJudge.isEmpty(str)){
            return "0";
        }
        int length=str.length();
        int idx=str.lastIndexOf(".");//注意不能用转义符
        if (length-idx<=num){
            return str;
        }else{
            return str.substring(0,idx+num);
        }

    }

    public static int getIntToDecimals(float f){
        return stringToInt(stringToGetTwoToDecimals(f));
    }



    public static int stringToInt(String str){
        int idx=str.lastIndexOf(".");//注意不能用转义符
        if (idx==-1){
            return Integer.valueOf(str);
        }else{
            String strNum=str.substring(0,idx);
            return Integer.valueOf(strNum);
        }
    }
    /** 在 list数据中随机选出 [min,max]范围个数的 list
     *
     *
     */

    //[min_num, min+max)
    public static int randomIntMinMax(int min_num,int max_num){
        String str=String.valueOf(getRound(Math.random() * max_num+min_num));

        return stringToInt(str);
    }


    public static List<String> randomLIstAtList(List<String> list,int min,int max){
        List<String> new_list=new ArrayList<>();
        if (StringJudge.isEmpty(list))return new_list;
        int num=randomIntMinMax(min,max);
        Logger.e(String.valueOf(num)+"分");

        List<String> stringList=new ArrayList<>(list);
        for (int i=0;i<Math.min(num,list.size());i++){
            String str=String.valueOf(Math.random() * stringList.size());

            int position=stringToInt(str);
            new_list.add(stringList.get(position));
            stringList.remove(position);

        }
        return new_list;
    }

    public static String randomStringAtList(List<String> list){
        if (StringJudge.isEmpty(list))return "not string ***";
        String str=String.valueOf(Math.random() * list.size());
        int idx=stringToInt(str);
        return list.get(idx);
    }

    public static boolean isLastRowToFirstColumn(int all_num, int position, int column){

        if (position % column == 0 && (all_num - 1) / column == position / column) {

            return true;
        } else {
            return false;
        }
    }
    public static boolean isLastRowToLastColumn(int all_num, int position, int column) {
        if (all_num == 0) return false;
        if ((all_num-1) == position && all_num % column == 0) {
            return true;
        } else {
            return false;
        }
    }



}
