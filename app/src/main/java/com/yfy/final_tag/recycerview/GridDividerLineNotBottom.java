package com.yfy.final_tag.recycerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author b不绘制底边
 *
 */
public class GridDividerLineNotBottom extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int lineWidth = 5;

    public GridDividerLineNotBottom(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

    }

    public GridDividerLineNotBottom(int color) {
        mDivider = new ColorDrawable(color);
    }

    public GridDividerLineNotBottom() {
        this(Color.parseColor("#cccccc"));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + lineWidth;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + lineWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + lineWidth;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
//        Logger.e(pos+"------"+spanCount+"--------"+childCount);
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是最后一列，则不需要绘制右边

            if (pos % spanCount+1 == spanCount)return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0) return true;
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列，则不需要绘制右边
                if (pos >= childCount) return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
//        Logger.e(pos+"------"+spanCount+"--------"+childCount);
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
//            childCount = childCount - childCount % spanCount;
            // 如果是最后一行，则不需要绘制底部
            if (pos/spanCount+1 == childCount/spanCount) return true;
//            return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount) return true;
            } else {
                // StaggeredGridLayoutManager 且横向滚动
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        boolean b = state.willRunPredictiveAnimations();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        // 如果是最后一行，则不需要绘制底部
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
            if (isLastColum(parent, itemPosition, spanCount, childCount)){
                outRect.set(0, 0, 0, 0);
            }else{
                outRect.set(0, 0, lineWidth, 0);
            }
        }else{
            // 如果是最后一列，则不需要绘制右边
            if (isLastColum(parent, itemPosition, spanCount, childCount)) {
                if (b){
                    outRect.set(0, 0, lineWidth, lineWidth);
                }else {
                    outRect.set(0, 0, 0, lineWidth);
                }
            }else{
                outRect.set(0, 0, lineWidth, lineWidth);
            }
        }
    }
}
