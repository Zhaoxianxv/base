/**
 * 
 */
package com.yfy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author yfy1
 * 全部展开
 */
public class GridViewForScroll extends GridView {

	public GridViewForScroll(Context context) {
		super(context);
	}

	public GridViewForScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
