package com.yfy.final_tag.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;


/**
 * Created by yfy on 2017/9/25.
 *
 *  要创建一个AlertDialog，就要用到AlertDialog.Builder中的create()方法。
 *  使用AlertDialog.Builder创建对话框需要了解以下几个方法：
 *  setTitle ：为对话框设置标题
 *  setIcon ：为对话框设置图标
 *  setMessage：为对话框设置内容
 *  setView ： 给对话框设置自定义样式
 *  setItems ：设置对话框要显示的一个list，一般用于显示几个命令时
 *  setMultiChoiceItems ：用来设置对话框显示一系列的复选框
 *  setNeutralButton    ：普通按钮
 *  setPositiveButton   ：给对话框添加"Yes"按钮
 *  setNegativeButton ：对话框添加"No"按钮
 *  create ： 创建对话框
 *  show ：显示对话框
 */

public class DialogTools {

    public static DialogTools dialogTools;

    private String ok_String="确定";
    private String cancle_String="取消";
    public  DialogInterface.OnClickListener cancel=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
    public  DialogInterface.OnClickListener ok=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };


    private DialogTools() {
    }
    public static DialogTools getInstance() {
        if (dialogTools == null) {
            dialogTools = new DialogTools();
        }
        return dialogTools;
    }
    /**
     * 对话框
     * @param details
     * @param okListener
     */
    public void showDialog(Activity mActivity, String title, @StringRes int details, DialogInterface.OnClickListener okListener){
        showDialog(mActivity,title,mActivity.getResources().getString(details),okListener);
    }

    public void showDialog(Activity mActivity,String title,String details, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(details);
        builder.setTitle(title);
        if (okListener!=null){
            builder.setPositiveButton(ok_String, okListener);
        }
        builder.setNegativeButton(cancle_String, cancel);
        builder.create().show();
    }

    public void showDialog(Activity mActivity,String title,String details){
        showDialog(mActivity,title,details,null);
    }
    /**
     * 单选列表对话框
     * @param mActivity
     * @param title
     * @param sex
     * @param okListener 选中监听
     */
    public void showSingleChioceDialog(
            Activity mActivity,
            String title,
            String[] sex,
            DialogInterface.OnClickListener okListener)
    {
        AlertDialog.Builder single = new AlertDialog.Builder(mActivity);
        single.setTitle(title);
        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，
         * 第三个参数给每一个单选项绑定一个监听器
         */
        single.setSingleChoiceItems(sex, -1, okListener);
        single.show();
    }
    /**
     * view dialog
     * @param mActivity
     * @param view
     * @param title
     * @param okListener
     */
    public void  showViewTextDialog(
            Activity mActivity,
            View view,
            String title,
            String ok,
            DialogInterface.OnClickListener okListener)
    {
        AlertDialog.Builder builder_view = new AlertDialog.Builder(mActivity);
        builder_view.setTitle(title);

//        builder_view.setIcon(android.R.drawable.ic_dialog_info);
        builder_view.setView(view);

        if (okListener!=null){
            builder_view.setPositiveButton(ok,okListener);
        }
        builder_view .setNegativeButton(cancle_String, cancel);
        builder_view.show();
    }

    /**
     * 多选列表
     * @param mActivity
     * @param title
     * @param sex
     * @param okListener
     */

    public void showMultiChoiceDialog(
            Activity mActivity,
            String title,
            String[] sex,
            DialogInterface.OnMultiChoiceClickListener multiListenner,
            DialogInterface.OnClickListener okListener,
            String positive,
            String negative)
    {
        if (positive==null|positive.length()==0){
            positive=ok_String;
        }
        if (negative==null|negative.length()==0){
            negative=cancle_String;
        }
        AlertDialog.Builder multi=new AlertDialog.Builder(mActivity);
        multi.setTitle(title);
        multi.setIcon(android.R.drawable.ic_dialog_info);
        if (multiListenner!=null){
            multi.setMultiChoiceItems(sex, null,multiListenner);
        }
        if (okListener!=null){
            multi.setPositiveButton(positive, okListener);
        }else{
            multi.setPositiveButton(positive, ok);
        }
        multi .setNegativeButton(negative, cancel);
        multi.show();
    }
    public void showMultiChoiceDialog(Activity mActivity, String title, String[] sex) {
        showMultiChoiceDialog(mActivity,title,sex,null,null,null,null);
    }
    public void showMultiChoiceDialog(
            Activity mActivity,
            String title,
            String[] sex,
            DialogInterface.OnMultiChoiceClickListener multiListenner) {
        showMultiChoiceDialog(mActivity,title,sex,multiListenner,null,null,null);
    }
    public void showMultiChoiceDialog(
            Activity mActivity,
            String title,
            String[] sex,
            DialogInterface.OnClickListener okListener) {
        showMultiChoiceDialog(mActivity,title,sex,null,okListener,null,null);
    }
    /**
     *
     * @param mActivity
     * @param title
     * @param okListener
     */

    public void showItemsDialog(final Activity mActivity, String title,DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder items=new AlertDialog.Builder(mActivity);
        items.setTitle(title);
//        items.setItems(R.array.title, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                CharSequence strDialogBody=mActivity.getString(R.string.app_name);
//                String[] it = mActivity.getResources().getStringArray(R.array.title);
//                new AlertDialog.Builder(mActivity)
//                        .setMessage(strDialogBody + it[which])
//                        .setNeutralButton(R.string.app_ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // TODO Auto-generated method stub
//                                //在这里做要处理的事情
//                            }
//                        }).show();
//            }
//        });
        items.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //在这里做要处理的事情
            }
        });
        items.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        items.show();
    }


    /**
     *
     * @param mActivity
     * @param liste
     *
     * showDatePickerDialog(new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

    String date2 = DateUtils.getDate2(year, month, dayOfMonth);
    start_time = DateUtils.getDate(year, month, dayOfMonth);
    leave_date.setText(date2);
    }
    });
     */

    @SuppressWarnings("ResourceType")
    public void showTimePickerAndTimeDialog(Context mActivity, TimePickerDialog.OnTimeSetListener liste){

        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        boolean is24HourView =true;
        TimePickerDialog picker = new TimePickerDialog(
                mActivity,
                android.app.AlertDialog.THEME_HOLO_LIGHT,
                liste ,
                hourOfDay,
                minute,
                is24HourView);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.show();
    }
    @SuppressWarnings("ResourceType")
    public void showDatePickerDialog(Context mActivity, DatePickerDialog.OnDateSetListener liste){

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog picker = new DatePickerDialog(
                mActivity,
                android.app.AlertDialog.THEME_HOLO_LIGHT,//样式
                liste ,
                year,
                month,
                day);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.show();
    }


    @SuppressWarnings("ResourceType")
    public void showDateYearMonth(Context mActivity, DatePickerDialog.OnDateSetListener liste){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DatePickerDialog picker = new DatePickerDialog(
                mActivity,
                android.app.AlertDialog.THEME_HOLO_LIGHT,//样式
                liste ,
                year,
                month,
                day);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.show();
        //只显示年月，隐藏掉日
        DatePicker dp = findDatePicker((ViewGroup) picker.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup)((ViewGroup)dp.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE);
            //如果想隐藏掉年，将getChildAt(2)改为getChildAt(0)
        }
    }
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
}
