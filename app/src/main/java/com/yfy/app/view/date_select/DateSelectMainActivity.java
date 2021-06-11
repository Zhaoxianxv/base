package com.yfy.app.view.date_select;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.bean.DateBean;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;

import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class DateSelectMainActivity extends BaseActivity implements CalendarCard.OnCellClickListener {
	private final static String TAG = DateSelectMainActivity.class.getSimpleName();

	private TextView monthText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_select_main);
		initSQToolbar();
		getData();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
		monthText =  this.findViewById(R.id.tvCurrentMonth);
		CustomDate customDate=new CustomDate();
		monthText.setText(customDate.year + " - " + customDate.month + "");
		CalendarCard[] views = new CalendarCard[5];
		for (int i = 0; i < views.length; i++) {
			views[i] = new CalendarCard(this, this);
		}
	}

	private void initSQToolbar(){
		assert toolbar!=null;
		toolbar.setTitle("选择日期");
		toolbar.setNavi(R.drawable.ic_action_name);
	}


	public DateBean select_date;
	private void getData() {
		select_date = getIntent().getParcelableExtra(Base.data);
	}




	@OnClick(R.id.btnPreMonth)
	void setPreMonth(){
	}
	@OnClick(R.id.btnNextMonth)
	void setNextMonth(){
	}


	@Override
	public void clickDate(CustomDate date) {
		Intent intent = new Intent();
		intent.putExtra(Base.data, date);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void changeDate(CustomDate date) {
	}

}
