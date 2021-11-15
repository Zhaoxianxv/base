package com.yfy.app.date_select;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.yfy.app.bean.DateBean;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ClickableViewAccessibility")
public class DateSelectStateCard extends View {

	private static final int TOTAL_COL = 7; //
	private static final int TOTAL_ROW = 6; //
	/*日历背景画笔*/
	private Paint mCirclePaint; //
	/*日历数字画笔*/
	private Paint mTextPaint;
	public int mViewWidth; //
	public int mViewHeight; //
	private int mCellSpace; //
	public Row rows[] = new Row[TOTAL_ROW]; //
	private DateBean mShowDate; //


	private int touchSlop; //
	private boolean callBackCellSpace;

	private float mDownX;
	private float mDownY;
	public Context mContext;



	public DateSelectStateCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public DateSelectStateCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DateSelectStateCard(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext=context;
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(ColorRgbUtil.getResourceColor(mContext, R.color.OrangeRed)); //
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		initDate();
	}

	private void initDate() {
		mShowDate = new DateBean();
		mShowDate.setValue_long(System.currentTimeMillis(),true);
		fillDate();//
	}

	private void fillDate() {

		/*上月天数*/
		int lastMonthDays = mShowDate.getMonthDays(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt()-1);
		/*选中月天数*/
		int currentMonthDays = mShowDate.getMonthDays(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt());
		/*选中月第一天星期几*/
		int firstDayWeek = mShowDate.getSelectWeekFirstNameInt(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt());

		/*判断是否是当前月*/
//		boolean isCurrentMonth = false;
//		if (mShowDate.isCurrentMonth()) {
//			isCurrentMonth = true;
//		}


		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL;
				if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
					day++;
					/*某天的时间对象date_row*/
					DateBean date_row=mShowDate.getObjectForDay(mShowDate, day);

					rows[j].cells[i] = new Cell(date_row, State.ALL_DAY, i, j);


					/*为日历控件添加标记类型*/
					for (DateBean dateBean:state_color_list){
						if (dateBean.getSelectDayNameInt()==day&&dateBean.getSelectYearNameInt()==date_row.getSelectYearNameInt()&&date_row.getSelectMonthNameInt()==dateBean.getSelectMonthNameInt()){
							/*实列对象Cell*/
							date_row.setState_color(dateBean.getState_color());
							rows[j].cells[i].date=date_row;
							switch (dateBean.getState_color()){
								case "1":
									rows[j].cells[i].state=State.STATE_COLOR_TWO;
									break;
								case "5":
									rows[j].cells[i].state=State.STATE_COLOR_ONE;
									break;
							}
							/*上半颜色*/
							switch (dateBean.getState_color()){
								case "1":
									break;
								case "5":
									rows[j].cells[i].setTop_color(ColorRgbUtil.getResourceColor(mContext,R.color.Orange));
									break;
							}
							/*下半颜色*/
							switch (dateBean.getState_color()){
								case "1":
								case "5":
									rows[j].cells[i].setBottom_color(ColorRgbUtil.getResourceColor(mContext,R.color.Red));
									break;
							}
						}
					}
				} else if (position < firstDayWeek) {
					/*上个月*/
					rows[j].cells[i] = new Cell(
							new DateBean(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt() - 1, lastMonthDays - (firstDayWeek - position - 1),true),
							State.PAST_MONTH_DAY,
							i,
							j
					);
				} else if (position >= firstDayWeek + currentMonthDays) {
					/*下个月*/
					rows[j].cells[i] = new Cell(
							new DateBean(mShowDate.getSelectYearNameInt(), mShowDate.getSelectMonthNameInt() + 1, position - firstDayWeek - currentMonthDays + 1,true),
							State.NEXT_MONTH_DAY,
							i,
							j
					);
				}
			}
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < TOTAL_ROW; i++) {
			if (rows[i] != null) {
				rows[i].drawCells(canvas);
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
		mViewHeight = h;
		mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
		mTextPaint.setTextSize(mCellSpace / 3f);
	}



	/**
	 *
	 */
	class Row {
		public int j;
		Row(int j) {
			this.j = j;
		}
		public Cell[] cells = new Cell[TOTAL_COL];
		public void drawCells(Canvas canvas) {
			for (Cell cell : cells) {
				if (cell != null) {
					cell.drawSelf(canvas);
				}
			}
		}

	}

	/**
	 *
	 */
	class Cell {
		public DateBean date;
		public State state;
		public int i;
		public int j;
		/*一天分为上下两部分 top 上半部背景颜色*/
		public int top_color;
		public int bottom_color;
		public void setTop_color(int top_color) {
			this.top_color = top_color;
		}

		public void setBottom_color(int bottom_color) {
			this.bottom_color = bottom_color;
		}


		public Cell(DateBean date, State state, int i, int j) {
			super();
			this.date = date;
			this.state = state;
			this.i = i;
			this.j = j;
		}


		/*日期显示样式*/
		public void drawSelf(Canvas canvas) {
			float centerX = mCellSpace * (i + 0.5f);
			float centerY = mCellSpace * (j + 0.5f);
			float radius = mCellSpace / 2f;
			switch (state) {
				case ALL_DAY: //
					mTextPaint.setColor(Color.GRAY);
					mCirclePaint.setColor(Color.WHITE);
					canvas.drawRoundRect(
							new RectF(centerX - radius+1, centerY - radius+1, centerX + radius-1, centerY + radius-1),
							0,
							0,
							mCirclePaint);
					break;
				case PAST_MONTH_DAY: //
				case NEXT_MONTH_DAY: //
					mTextPaint.setColor(Color.parseColor("#fffffe"));
					break;
				case STATE_COLOR_ONE:
					canvasTwo(canvas, centerX, centerY,radius , top_color, bottom_color);
					break;
				default:
					break;
			}

			/*画布~显示日期数字*/
			String current_day_name = String.valueOf(date.getSelectDayNameInt());
			canvas.drawText(
					current_day_name,
					(float) ((i + 0.5) * mCellSpace - mTextPaint.measureText(current_day_name) / 2),
					(float) ((j + 0.7) * mCellSpace - mTextPaint.measureText(current_day_name, 0, 1) / 2),
					mTextPaint
			);
		}
	}








	/*更新界面*/
	public void update(DateBean date) {

		mShowDate.setValue_long(date.getValue_long());
		update();
	}
	public void update() {
		fillDate();
		invalidate();
	}




	/*某天样式枚举*/
	enum State {
		PAST_MONTH_DAY,
		NEXT_MONTH_DAY,
		/*全部*/
		ALL_DAY,
		/*对应 DateBean state_color*/
		STATE_COLOR_ONE,
		STATE_COLOR_TWO,
		STATE_COLOR_THREE,
		STATE_COLOR_FOUR,
	}




	/*点击某天监听函数*/
	private DateSelectClickListener mDateSelectClickListener;

	public void setDateSelectClickListener(DateSelectClickListener mDateSelectClickListener) {
		this.mDateSelectClickListener = mDateSelectClickListener;
	}

	public interface DateSelectClickListener {
		void clickDate(DateBean date,String type); //
	}


	/*点击事件交互重写分发事件到某天*/
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownX = event.getX();
				mDownY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				float disX = event.getX() - mDownX;
				float disY = event.getY() - mDownY;
				if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
					int col = (int) (mDownX / mCellSpace);
					int row = (int) (mDownY / mCellSpace);
					if (isEffectClick(col, row)) {//
						measureClickCell(col, row);
					}
				}
				break;
			default:
				break;
		}

		return true;
	}
	/*判断分发事件的日期范围*/
	private boolean isEffectClick(int col, int row) {
		if (col < 0 || col >= 7 || row < 0 || row >= 6) {
			return false;
		}
//		State state = rows[row].cells[col].state;
		/*可点日的样式State.ALL_DAY；return true;所有*/
//		return state == State.ALL_DAY ;
		return true ;

	}


	/*分发事件响应函数*/
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= TOTAL_ROW) return;
		if (rows[row] != null) {
			DateBean date = rows[row].cells[col].date;
			mDateSelectClickListener.clickDate(date,date.getState_color());
		}
	}
	/**
	 * ----------------添加日期颜色标注
	 */

	/*用于外部数据标记当天信息*/
	public List<DateBean> state_color_list=new ArrayList<>();
	public void update(DateBean date,List<DateBean> days) {
		mShowDate = date;
		this.state_color_list=days;
		update();
	}


	/*一天分为上下两部分 color 上半部背景颜色 color1下半，设置颜色*/
	public void canvasTwo(Canvas canvas,float centerX,float centerY,float radius,int color,int color1){
		mTextPaint.setColor(Color.WHITE);
		mCirclePaint.setColor(color);
		canvas.drawRoundRect(
				new RectF(centerX - radius+1, centerY - radius+1, centerX + radius-1, centerY ),
				0,
				0,
				mCirclePaint);

		mCirclePaint.setColor(color1);
		canvas.drawRoundRect(
				new RectF(centerX - radius+1, centerY , centerX + radius-1, centerY + radius-1),
				0,
				0,
				mCirclePaint);
	}






}
