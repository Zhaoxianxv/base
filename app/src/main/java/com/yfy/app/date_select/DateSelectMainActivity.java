package com.yfy.app.date_select;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yfy.app.bean.DateBean;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;



import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class DateSelectMainActivity extends BaseActivity {
	private final static String TAG = DateSelectMainActivity.class.getSimpleName();



	@BindView(R.id.date_select_month)
	TextView select_month;
	@OnClick(R.id.date_select_month)
	void selectDate(){

	}


	@OnClick(R.id.date_select_btn_PreMonth)
	void setPreMonth(){
		initDate(false);
	}
	@OnClick(R.id.date_select_btn_btnNextMonth)
	void setNextMonth(){
		initDate(true);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_select_main);

		Logger.e(TAG);
		initDateDate();
		initSQToolbar();
		getData();



	}

	public TextView title_tv;

	private void initSQToolbar(){
		assert toolbar!=null;
		title_tv=toolbar.setTitle("选择日期");
//		toolbar.setNavi(R.drawable.ic_nag_back,);
	}


	public DateBean select_date;

	private void getData() {
		Bundle b = getIntent().getExtras();
		if (b != null) {
			if (b.containsKey(Base.data)) {
				select_date = b.getParcelable(Base.data);
			}else{
				select_date=new DateBean();
				select_date.setValue_long(System.currentTimeMillis(),true);
			}
		}else{
			select_date=new DateBean();
			select_date.setValue_long(System.currentTimeMillis(),true);
		}
		select_month.setText(StringUtils.stringToGetTextJoint("%1$d月",select_date.getSelectMonthNameInt()));
		title_tv.setText(StringUtils.stringToGetTextJoint("%1$d年",select_date.getSelectYearNameInt()));
		calendarCard.update(select_date);
	}





	public DateSelectCard calendarCard;
	private void initDateDate() {

		calendarCard =  findViewById(R.id.calendar_card);
		calendarCard.setmDateSelectClickListener(new DateSelectCard.DateSelectClickListener() {
			@Override
			public void clickDate(DateBean date) {
				select_date.setValue_long(date.getValue_long());
				select_month.setText(StringUtils.stringToGetTextJoint("%1$d月",select_date.getSelectMonthNameInt()));
				title_tv.setText(StringUtils.stringToGetTextJoint("%1$d年",select_date.getSelectYearNameInt()));

				Intent intent=new Intent();
				intent.putExtra(Base.data,select_date);
				setResult(RESULT_OK,intent);
				finish();
			}

			@Override
			public void changeDate(DateBean date) {

			}
		});

	}









	private void initDate(boolean  is) {



		int yearNameInt=select_date.getSelectYearNameInt();
		int monthNameInt=select_date.getSelectMonthNameInt();

		int element_month=is?monthNameInt+1:monthNameInt-1;
		DateBean customDate = new DateBean(
				getYearOrMonth(yearNameInt,element_month,true),
				getYearOrMonth(yearNameInt,element_month,false),
				1,
				true
		);


		calendarCard.update(customDate);

		select_date.setValue_long(customDate.getValue_long());
		select_month.setText(StringUtils.stringToGetTextJoint("%1$d月",select_date.getSelectMonthNameInt()));
		title_tv.setText(StringUtils.stringToGetTextJoint("%1$d年",select_date.getSelectYearNameInt()));

		Logger.e(select_date.getName());

	}


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
