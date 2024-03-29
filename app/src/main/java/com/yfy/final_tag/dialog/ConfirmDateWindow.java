package com.yfy.final_tag.dialog;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yfy.base.R;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.dialog.adapter.NumericWheelAdapter;
import com.yfy.final_tag.dialog.widget.WheelView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 弹窗视图
 */
public class ConfirmDateWindow extends PopupWindow  {
	private Activity context;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private TextView cancel;
	private TextView ok;

	private String time;



	public String getTimeValue() {
		String time = String.format(Locale.CHINA,
				"%04d/%02d/%02d",
				year.getCurrentItem()+1950,
				month.getCurrentItem()+1,
				day.getCurrentItem()+1);
		return time;
	}
	public String getTimeName() {
		String time = String.format(Locale.CHINA,
				"%04d年%02d月%02d",
				year.getCurrentItem()+1950,
				month.getCurrentItem()+1,
				day.getCurrentItem()+1);
		return time;
	}

	public ConfirmDateWindow(Activity context) {
		super(context);
		this.context = context;
		initalize();
	}


	public void setCancelName(String cancelName) {
		if (StringJudge.isEmpty(cancelName)){
			cancel.setText("");
		}else{
			cancel.setText(cancelName);
		}
	}


		private void initalize() {
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
		int curHour = c.get(Calendar.HOUR_OF_DAY);
		int curMin = c.get(Calendar.MINUTE);

		LayoutInflater inflater = LayoutInflater.from(context);
		View window = inflater.inflate(R.layout.confirm_date, null);
//		title = view.findViewById(R.id.popu_select_title);//
		year = (WheelView) window.findViewById(R.id.new_year);
		initYear();
		month = (WheelView) window.findViewById(R.id.new_month);
		initMonth();
		day = (WheelView) window.findViewById(R.id.new_day);
		initDay(curYear,curMonth);



		// 设置当前时间
		year.setCurrentItem(curYear - 1950);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		year.setVisibleItems(7);
		month.setVisibleItems(7);
		day.setVisibleItems(7);



		ok = (TextView) window.findViewById(R.id.set);
		cancel= (TextView) window.findViewById(R.id.cancel);

		ok.setOnClickListener(onClickListener);
		cancel.setOnClickListener(onClickListener);

		setContentView(window);
		initWindow();
	}

	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 1));//width比例

		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha( context, 1f);//0.0-1.0
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha( context, 1f);
			}
		});
	}

	//设置添加屏幕的背景透明度
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}

	public void showAtBottom(View view) {
		//弹窗位置设置
		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
		//showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
		//        listPopupWindow.setHorizontalOffset(Math.abs((view.getWidth()) / 2));//水平距离
		//        listPopupWindow.setVerticalOffset(0);//垂直距离
	}

	public void showAtCenter() {
		//弹窗位置设置
		backgroundAlpha( context, 0.5f);//0.0-1.0
//		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
		//showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
		showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	public void showAtBottom() {
		//弹窗位置设置
		backgroundAlpha( context, 0.5f);//0.0-1.0
		showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}



	public View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (listener != null) {
				if (StringJudge.isEmpty(type)){
					listener.popClick(v);
				}else{
					listener.popClick(v,type);
				}
			}
		}
	};

	private PopClickListener listener = null;

	public void setOnPopClickListener(PopClickListener listener) {
		this.listener = listener;
	}




	/**
	 * 初始化年
	 */
	private void initYear() {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context,1950, 2050);
		numericWheelAdapter.setLabel(" 年");
		//		numericWheelAdapter.setTextSize(15);  设置字体大小
		year.setViewAdapter(numericWheelAdapter);
		year.setCyclic(true);
	}

	/**
	 * 初始化月
	 */
	private void initMonth() {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context,1, 12, "%02d");
		numericWheelAdapter.setLabel(" 月");
		//		numericWheelAdapter.setTextSize(15);  设置字体大小
		month.setViewAdapter(numericWheelAdapter);
		month.setCyclic(true);
	}

	/**
	 * 初始化天
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(context,1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel(" 日");
		day.setViewAdapter(numericWheelAdapter);
		day.setCyclic(true);
	}

	/**
	 *
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
			case 0:
				flag = true;
				break;
			default:
				flag = false;
				break;
		}
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = 31;
				break;
			case 2:
				day = flag ? 29 : 28;
				break;
			default:
				day = 30;
				break;
		}
		return day;
	}














	//区分同时选择几个时间
	public String type="";

	public void setType(String type) {
		this.type = type;
	}
	public long getTimeLong() {
		int yearNameInt=year.getCurrentItem()+1950;
		int monthNameInt=month.getCurrentItem()+1;
		int dayOfMonth=day.getCurrentItem()+1;

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR,getYearOrMonth(yearNameInt,monthNameInt,true));
		c.set(Calendar.MONTH, getYearOrMonth(yearNameInt,monthNameInt,false)-1);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);



		Date time=new Date(c.getTimeInMillis());
		return time.getTime();
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


}