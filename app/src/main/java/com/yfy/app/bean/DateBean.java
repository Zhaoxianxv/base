package com.yfy.app.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateBean implements Parcelable {
    public static final long DAY_LONG=86400000;
    private String name;
    private String value;
    //type==date name\value返回正常时间值，type==all，name返回"全部"，value返回“”；
    private String type="date";
    private long value_long;
    private boolean is_time=true;



    public DateBean() {
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        switch (type){
            case "date":
                Date date = new Date(value_long);
                initDateFormat(is_time);
                name=name_f.format(date);
                break;
            case "all":
                if (StringJudge.isEmpty(name)) name="全部";
                break;
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        switch (type){
            case "date":
                Date date = new Date(value_long);
                initDateFormat(is_time);
                value=value_f.format(date);
                break;
            case "all":
                if (StringJudge.isEmpty(value)) value="";
                break;
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getValue_long() {
        return value_long;
    }

    public void setValue_long(long value_long) {
        this.value_long = value_long;
    }

    //true name为年月日; false为年月日：时分
    public void setValue_long(long value_long,boolean is) {
        this.value_long = value_long;
        this.is_time = is;

    }
    /*拓展携带数据 state 用于标记当前对象天状态 ：空：不标记，*/
    private String state_color="";

    public String getState_color() {
        return state_color;
    }

    public void setState_color(String state_color) {
        this.state_color = state_color;
    }
    //------------------------tools------------


    private SimpleDateFormat name_f,value_f;
    public void initDateFormat(boolean is){
        if(StringJudge.isNull(value_f)){
            if (is){
                name_f = new SimpleDateFormat("yyyy年MM月dd");
                value_f = new SimpleDateFormat("yyyy/MM/dd");
            }else{
                value_f = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                name_f = new SimpleDateFormat("yyyy年MM月dd HH:mm");
            }
        }
    }




    public int getYearName(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
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




    //------------tool--------------

    public void addOneDay(){
        value_long+=DAY_LONG;
    }





    //获取选择月的天
    public  int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int days = 0;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29;
        }
        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

















    //判断是否是当前 年、月、日
    public boolean isCurrentDay() {
        int curYear = getNowYear();
        int curMonth = getNowMonth();
        int curDay = getNowMonthDay();
        return getSelectYearNameInt() == curYear && getSelectMonthNameInt() == curMonth && getSelectDayNameInt() == curDay;
    }

    public boolean isCurrentMonth() {
        int curYear = getNowYear();
        int curMonth = getNowMonth();
        return getSelectYearNameInt() == curYear && getSelectMonthNameInt() == curMonth ;
    }

    public boolean isCurrentYear() {
        int curYear = getNowYear();
        return getSelectYearNameInt() == curYear;
    }




    //获取当前 年、月、日、时、分、秒、毫秒
    public  int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public  int getNowMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public  int getNowMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }


    public  int getNowHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public  int getNowMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
    //秒
    public  int getNowSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
    //秒内毫秒
    public  int getNowMilliSecond() {
        return Calendar.getInstance().get(Calendar.MILLISECOND);
    }


    //获取选中 年、月、日、
    public int getSelectYearNameInt(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    public int getSelectMonthNameInt(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    public int getSelectDayNameInt(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getSelectMonthNameString(){
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


    /**
     * ------------------------------星期--------------------------------------
     * 星期日 : Sunday= 1;
     */



    public String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public int getNowWeekNameInt() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
    }
    public String getNowWeekNameString(){
        return getWeekIntConvertString(getNowWeekNameInt());
    }

    public int getSelectWeekNameInt() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(value_long);
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return w;
    }
    public int getSelectWeekFirstNameInt(int year,int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.get(Calendar.DAY_OF_WEEK)-1;
    }
    public String getSelectWeekNameString(){
        return getWeekIntConvertString(getSelectWeekNameInt());
    }

    //int week 转 string week
    public String getWeekIntConvertString(int week) {
        if (week < 0){
            week = 0;
        }
        return weekOfDays[week];
    }


    /**
     * ---------------返回一个特定的日期对象--------
     */


    public DateBean(int year,int month,int dayOfMonth,boolean is_time) {



        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.value_long = c.getTimeInMillis();
        this.is_time=is_time;

    }

    public void setDateYMD(int year,int month,int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.value_long = c.getTimeInMillis();
    }



    public  DateBean getObjectForDay(DateBean date, int day) {

        return new DateBean(date.getSelectYearNameInt(), date.getSelectMonthNameInt(), day,true);
    }


    // true 返回 year false 返回 month
    private int getYearOrMonth(int year, int month, boolean is) {
        if (month > 12) {
            year += month / 12;
            month = month % 12;
        } else if (month == 0) {
            year -= 1;
            month = 12;
        } else if (month < 0) {
            year -= Math.abs(month) / 12 + 1;
            month = 12 - Math.abs(month) % 12;
        }
        if (is) {
            return year;
        } else {
            return month;
        }

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
        dest.writeString(this.type);
        dest.writeLong(this.value_long);
        dest.writeByte(this.is_time ? (byte) 1 : (byte) 0);
        dest.writeString(this.state_color);
        dest.writeSerializable(this.name_f);
        dest.writeSerializable(this.value_f);
        dest.writeStringArray(this.weekOfDays);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.value = source.readString();
        this.type = source.readString();
        this.value_long = source.readLong();
        this.is_time = source.readByte() != 0;
        this.state_color = source.readString();
        this.name_f = (SimpleDateFormat) source.readSerializable();
        this.value_f = (SimpleDateFormat) source.readSerializable();
        this.weekOfDays = source.createStringArray();
    }

    protected DateBean(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
        this.type = in.readString();
        this.value_long = in.readLong();
        this.is_time = in.readByte() != 0;
        this.state_color = in.readString();
        this.name_f = (SimpleDateFormat) in.readSerializable();
        this.value_f = (SimpleDateFormat) in.readSerializable();
        this.weekOfDays = in.createStringArray();
    }

    public static final Parcelable.Creator<DateBean> CREATOR = new Parcelable.Creator<DateBean>() {
        @Override
        public DateBean createFromParcel(Parcel source) {
            return new DateBean(source);
        }

        @Override
        public DateBean[] newArray(int size) {
            return new DateBean[size];
        }
    };
}
