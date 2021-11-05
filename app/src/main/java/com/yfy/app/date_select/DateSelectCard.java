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
import com.yfy.final_tag.data.ColorRgbUtil;


@SuppressLint("ClickableViewAccessibility")
public class DateSelectCard extends View {

	private static final int TOTAL_COL = 7; //
	private static final int TOTAL_ROW = 6; //

	private Paint mCirclePaint; //
	private Paint mTextPaint; //
	public int mViewWidth; //
	public int mViewHeight; //
	private int mCellSpace; //
	private Row rows[] = new Row[TOTAL_ROW]; //
	private DateBean mShowDate; //


	private int touchSlop; //
	private boolean callBackCellSpace;

	private Cell mClickCell;
	private float mDownX;
	private float mDownY;



	public DateSelectCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public DateSelectCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DateSelectCard(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.parseColor("#F24949")); //
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		initDate();
	}

	private void initDate() {
		mShowDate = new DateBean();
		mShowDate.setValue_long(System.currentTimeMillis(),true);
		fillDate();//
	}

	private void fillDate() {
		int monthDay = mShowDate.getNowMonthDay(); //
		int lastMonthDays = mShowDate.getMonthDays(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt()-1); //上月天数

		int currentMonthDays = mShowDate.getMonthDays(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt()); //选中月天数
		int firstDayWeek = mShowDate.getSelectWeekFirstNameInt(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt());//选中月第一天星期几

		boolean isCurrentMonth = false;
		if (mShowDate.isCurrentMonth()) {
			isCurrentMonth = true;
		}


		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL;
				if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
					day++;
					rows[j].cells[i] = new Cell(
							mShowDate.getObjectForDay(mShowDate, day),
							State.CURRENT_MONTH_DAY,
							i,
							j
					);
					if (isCurrentMonth && day == monthDay) {
						rows[j].cells[i] = new Cell(
								mShowDate.getObjectForDay(mShowDate,day),
								State.TODAY,
								i,
								j
						);
					}

				} else if (position < firstDayWeek) {
					rows[j].cells[i] = new Cell(
							new DateBean(mShowDate.getSelectYearNameInt(),mShowDate.getSelectMonthNameInt() - 1, lastMonthDays - (firstDayWeek - position - 1),true),
							State.PAST_MONTH_DAY,
							i,
							j
					);
				} else if (position >= firstDayWeek + currentMonthDays) {
					rows[j].cells[i] = new Cell(
							new DateBean(mShowDate.getSelectYearNameInt(), mShowDate.getSelectMonthNameInt() + 1, position - firstDayWeek - currentMonthDays + 1,true),
							State.NEXT_MONTH_DAY,
							i,
							j
					);
				}
			}
		}
		if (mDateSelectClickListener!=null){
			mDateSelectClickListener.changeDate(mShowDate);
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
		mTextPaint.setTextSize(mCellSpace / 3);
	}

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
	//判断选着的日期范围
	private boolean isEffectClick(int col, int row) {
		if (col < 0 || col >= 7 || row < 0 || row >= 6) {
			return false;
		}
		State state = rows[row].cells[col].state;
//		return true;//所有
		return state == State.CURRENT_MONTH_DAY || state == State.TODAY;//可点选中月的日期击和今天
	}

	/**
	 *
	 *
	 */
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= TOTAL_ROW)
			return;

		if (rows[row] != null) {
			mClickCell = new Cell(
					rows[row].cells[col].date,
					rows[row].cells[col].state,
					rows[row].cells[col].i,
					rows[row].cells[col].j
			);
			DateBean date = rows[row].cells[col].date;
			mDateSelectClickListener.clickDate(date);

			invalidate();
		}
	}

	/**
	 *
	 *
	 * @author wuwenjie
	 *
	 */
	class Row {
		public int j;

		Row(int j) {
			this.j = j;
		}

		public Cell[] cells = new Cell[TOTAL_COL];
		public void drawCells(Canvas canvas) {
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cells[i].drawSelf(canvas);
				}
			}
		}

	}

	/**
	 *
	 *
	 * @author wuwenjie
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



		public void drawSelf(Canvas canvas) {
			float centerX = mCellSpace * (i + 0.5f);
			float centerY = mCellSpace * (j + 0.5f);
			float radius = mCellSpace / 2f;
			switch (state) {
				case SELECTED_DAY:
					mTextPaint.setColor(Color.parseColor("#fffffe"));
					mCirclePaint.setColor(Color.parseColor("#87C126"));
					// canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
					// (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
					// mCirclePaint);
					canvas.drawRoundRect(
							new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
							10,
							10,
							mCirclePaint
					);
					break;
				case TODAY: //
					mTextPaint.setColor(Color.parseColor("#fffffe"));
					mCirclePaint.setColor(Color.parseColor("#F24949"));
					canvas.drawRoundRect(
							new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
							10,
							10,
							mCirclePaint
					);
					break;
				case CURRENT_MONTH_DAY: //
					mTextPaint.setColor(Color.GRAY);
					break;
				case PAST_MONTH_DAY: //
				case NEXT_MONTH_DAY: //
					mTextPaint.setColor(Color.parseColor("#fffffe"));
					break;
				case UNREACH_DAY: //
					mTextPaint.setColor(Color.BLACK);
					break;
				case STATE_COLOR_ONE:
					mTextPaint.setColor(Color.WHITE);
					canvasTwo(canvas, centerX, centerY,radius , mCirclePaint,getPink(),getPink());
					break;
				default:
					break;
			}

			String content = date.getSelectDayNameInt() + "";
			canvas.drawText(
					content,
					(float) ((i + 0.5) * mCellSpace - mTextPaint.measureText(content) / 2),
					(float) ((j + 0.7) * mCellSpace - mTextPaint.measureText(content, 0, 1) / 2),
					mTextPaint
			);
		}
	}

	enum State {
		CURRENT_MONTH_DAY,
		PAST_MONTH_DAY,
		NEXT_MONTH_DAY,
		UNREACH_DAY,
		SELECTED_DAY,
		/*全部*/
		ALL_DAY,
		/*今天*/
		TODAY,
		/*对应 DateBean state_color*/
		STATE_COLOR_ONE,
		STATE_COLOR_TWO,
		STATE_COLOR_THREE,
		STATE_COLOR_FOUR,
	}

	public void update() {
		fillDate();
		invalidate();
	}

	public void update(DateBean date) {

		mShowDate.setValue_long(date.getValue_long());
		update();
	}










	/*一天分为上下两部分 color 上半部背景颜色 color1下半，设置颜色*/
	public void canvasTwo(Canvas canvas,float centerX,float centerY,float radius,Paint paint,int color,int color1){
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


	private int getRed(){
		return  Color.rgb(176,63,79);
	}//

	private int getGreen(){
		return Color.rgb(51,173,48);
	}

	private int getAtten(){
		return  Color.rgb(240,128,128);
	}//

	private int getClock_on(){ return  Color.rgb(146,146,122); }
	public  int getPink(){
		return Color.parseColor("#FFC0CB");
	}


	/**
	 *
	 *
	 *
	 */
	private DateSelectClickListener mDateSelectClickListener;

	public void setmDateSelectClickListener(DateSelectClickListener mDateSelectClickListener) {
		this.mDateSelectClickListener = mDateSelectClickListener;
	}

	public interface DateSelectClickListener {
		void clickDate(DateBean date); //

		void changeDate(DateBean date); //
	}


}
