package com.yfy.final_tag.data;


import java.text.DecimalFormat;

/**
 * ---------数学函数----------
 */
public class MathTool {

    //Math.sqrt() : 计算平方根
    //Math.cbrt() : 计算立方根
    //Math.pow(a, b) : 计算a的b次方
    //Math.max( , ) : 计算最大值
    //Math.min( , ) : 计算最小值
    //Math.abs() : 取绝对值
    //Math.ceil(): 天花板的意思，就是逢余进一
    //Math.floor() : 地板的意思，就是逢余舍一
    //Math.rint(): 四舍五入，返回double值。注意.5的时候会取偶数
    //Math.round(): 四舍五入，float时返回int值，double时返回long值
    //Math.random(): 取得一个[0, 1)范围内的随机数



    //System.out.println(Math.sqrt(16)); // 4.0
    //System.out.println(Math.cbrt(8)); // 2.0
    //System.out.println(Math.pow(3, 2)); // 9.0
    //System.out.println(Math.max(2.3, 4.5));// 4.5
    //System.out.println(Math.min(2.3, 4.5));// 2.3
    //
    ///**
    // * abs求绝对值
    // */
    //System.out.println(Math.abs(-10.4)); // 10.4
    //System.out.println(Math.abs(10.1)); // 10.1


    //返回[min_num,max_num+min_num)区间的数
    //System.out.println(Math.random() * 2 + 1);// [1, 3)的double类型的数
    public static double getRandom(int min_num,int max_num){
        return Math.random() * max_num+min_num;
    }
    public static float getRandomInt(int min_num,int max_num){
        return getRound(Math.random() * max_num+min_num) ;
    }

    /**
     * round 四舍五入，float时返回int值，double时返回long值
     */
    //System.out.println(Math.round(10.1)); // 10
    //System.out.println(Math.round(10.5)); // 11
    //System.out.println(Math.round(-10.5)); // -10
    //System.out.println(Math.round(-10.51)); // -11
    /**
     * rint 四舍五入，返回double值 注意.5的时候会取偶数 异常的尴尬
     */
    //System.out.println(Math.rint(10.1)); // 10.0
    //System.out.println(Math.rint(10.7)); // 11.0
    //System.out.println(Math.rint(11.5)); // 12.0
    //System.out.println(Math.rint(10.5)); // 10.0
    //System.out.println(Math.rint(10.51)); // 11.0
    //System.out.println(Math.rint(-10.5)); // -10.0
    //System.out.println(Math.rint(-11.5)); // -12.0
    //System.out.println(Math.rint(-10.51)); // -11.0
    //System.out.println(Math.rint(-10.6)); // -11.0
    //System.out.println(Math.rint(-10.2)); // -10.0
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
     */
    //System.out.println(Math.ceil(-10.1)); // -10.0
    //System.out.println(Math.ceil(10.7)); // 11.0
    //System.out.println(Math.ceil(-0.7)); // -0.0
    //System.out.println(Math.ceil(0.0)); // 0.0
    //System.out.println(Math.ceil(-0.0)); // -0.0
    //System.out.println(Math.ceil(-1.7)); // -1.0
    //
    //System.out.println("-------------------");
    //
    /**
     * floor地板的意思，就是逢余舍一
     */
    //System.out.println(Math.floor(-10.1)); // -11.0
    //System.out.println(Math.floor(10.7)); // 10.0
    //System.out.println(Math.floor(-0.7)); // -1.0
    //System.out.println(Math.floor(0.0)); // 0.0
    //System.out.println(Math.floor(-0.0)); // -0.0

    public static String stringToGetTwoToDecimals(float f){
        //构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat=new DecimalFormat(".00");
        //format 返回的是字符串
        return decimalFormat.format(f);
    }
}
