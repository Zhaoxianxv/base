package com.yfy.app.view.date_select;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;


public class CalendarViewAdapter extends PagerAdapter {
	public static final String TAG = "CalendarViewAdapter";
	private CalendarCard[] views;
	private int initPosition;
	private CustomDate initCustomDate;

	public CalendarViewAdapter(CalendarCard[] views) {
		super();
		this.views = views;
	}

	public void setDatePos(int position, CustomDate date) {
		initPosition = position;
		initCustomDate = date;
	}

	public CustomDate getCustomDate(int position) {
		CustomDate customDate = new CustomDate(initCustomDate);
		customDate.reduceMonth(position - initPosition);
		return customDate;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		CalendarCard calendarCard = views[position % views.length];
		calendarCard.update(getCustomDate(position));
		container.addView(calendarCard);
		return calendarCard;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = views[position % views.length];
		container.removeView(view);
	}

	public CalendarCard[] getAllItems() {
		return views;
	}

}
