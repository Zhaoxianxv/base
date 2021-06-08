package com.yfy.app.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.yfy.final_tag.stringtool.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateBean implements Parcelable {
    public static final long DAY_LONG=86400000;
    public String name;
    public String name_year_month;
    public String value;
    public long value_long;
    public boolean is_time=false;
    public SimpleDateFormat name_f ;
    public SimpleDateFormat value_f ;
    public DateBean(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public long getValue_long() {
        return value_long;
    }

    public void setValue_long(long value_long) {
        this.value_long = value_long;
        Date date = new Date(value_long);
        name=name_f.format(date);
        value=value_f.format(date);
    }
    //单独使用要确保name_f不为空
    public void setDateYMD(int year,int month,int dayOfMonth) {
        Date time=new Date( year-1900, month, dayOfMonth);
        this.value_long = time.getTime();
        name=name_f.format(time);
        value=value_f.format(time);
    }
    //true name为年月日; false为年月日：时分
    @SuppressLint("SimpleDateFormat")
    public void setValue_long(long value_long,boolean is) {
        this.value_long = value_long;
        this.is_time = is;
        Date date = new Date(value_long);
        if (is_time){
            name_f = new SimpleDateFormat("yyyy年MM月dd");
            value_f = new SimpleDateFormat("yyyy/MM/dd");
            name=name_f.format(date);
            value=value_f.format(date);
        }else{
            value_f = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            name_f = new SimpleDateFormat("yyyy年MM月dd HH:mm");
            name=name_f.format(date);
            value=value_f.format(date);
        }

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        Date date;
        try {
            if (is_time){
                date= value_f.parse(value);
                name=name_f.format(date);
            }else{
                date= value_f.parse(value);
                name=name_f.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            value_long=0;
            date=new Date(value_long);
            if (is_time){
                name=name_f.format(date);
            }else{
                name=name_f.format(date);
            }
            return;
        }
        assert date != null;
        this.value_long= date.getTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public DateBean() {
    }

    protected DateBean(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<DateBean> CREATOR = new Creator<DateBean>() {
        @Override
        public DateBean createFromParcel(Parcel source) {
            return new DateBean(source);
        }

        @Override
        public DateBean[] newArray(int size) {
            return new DateBean[size];
        }
    };










    //------------tool--------------

    public void addOneDay(){
        value_long+=DAY_LONG;
        Date date = new Date(value_long);
        if (is_time){
            name=name_f.format(date);
            value=value_f.format(date);
        }else{
            name=name_f.format(date);
            value=value_f.format(date);
        }
    }

    public int getYearName(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    public int getMonthName(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    public int getDayName(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public String getMonthNameTwo(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        int num=calendar.get(Calendar.MONTH)+1;
        String content="";
        if (num<10){
            content= StringUtils.stringToGetTextJoint("0%1$d",num);
        }else{
            content=String.valueOf(num);
        }
        return content;
    }



    public String getWeekOfDate() {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekOfDays[w];
    }

    public boolean isCurrentDay() {
        int curYear = getYear();
        int curMonth = getMonth();
        int curDay = getCurrentMonthDay();
        return getYearName() == curYear && getMonthName() == curMonth && getDayName() == curDay;
    }
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
    }


    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

}
