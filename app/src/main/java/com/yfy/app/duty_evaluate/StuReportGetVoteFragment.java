package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yfy.app.duty_evaluate.adapter.StuReportVoteAdapter;
import com.yfy.app.duty_evaluate.bean.ReportThree;
import com.yfy.app.duty_evaluate.bean.ReportTwo;

import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.fragment.BaseFragment;
import com.yfy.final_tag.data.TagFinal;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by yfy on 2017/8/1.
 */

@SuppressLint("NonConstantResourceId")
public class StuReportGetVoteFragment extends BaseFragment {


    public Gson gson;

    private StuReportVoteAdapter adapter;
    @BindView(R.id.public_recycler_single_top_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.public_recycler_single_top_layout)
    View top_layout;
    @BindView(R.id.public_top_text)
    TextView parent_name;
    @Override
    public void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.public_recycler_single_top_layout);
        gson=new Gson();
        initRecycler();
        getData();
    }



    private List<ReportTwo> tablelist=new ArrayList<>();
    public List<ReportThree> adapter_list=new ArrayList<>();
    public String user_type,show_type;
    private void getData() {
        tablelist = getArguments().getParcelableArrayList(Base.data);
        user_type = getArguments().getString(Base.type);
        show_type = getArguments().getString(Base.type_name);
        adapter_list.clear();
        boolean first=true;
        for (ReportTwo two:tablelist){
            ReportThree three=new ReportThree();
            three.setView_type(TagFinal.TYPE_PARENT);
            three.setTablename(two.getTablename());
            adapter_list.add(three);
            if (first){
                first=false;
                parent_name.setText(two.getTablename());
            }
            for (ReportThree reportThree:two.getTablelist()){

                reportThree.setTablename_parent(two.getTablename());
                reportThree.setView_type(TagFinal.TYPE_CHILD);
                adapter_list.add(reportThree);
            }
        }
        adapter.setUser_type(user_type);
        adapter.setShow_type(show_type);
        adapter.setDataList(adapter_list);
        adapter.setLoadState(TagFinal.LOADING_END);

    }





    public void selectItem(String type){
        if (adapter==null)return;
        if (tablelist==null)return;
        for (ReportTwo two:tablelist){
            for (ReportThree reportThree:two.getTablelist()){
                if (show_type.equalsIgnoreCase("tea_vote")){
                    reportThree.setTeacherselect(type);
                }else{
                    reportThree.setStuselect(type);
                }
            }
        }
        adapter.setDataList(adapter_list);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    public void initRecycler(){
//        parent_name.setBackgroundColor(ColorRgbUtil.getWhite());

        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new StuReportVoteAdapter(mActivity);
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        // 用来标记是否正在向上滑动
        private boolean isSlidingUpward = false;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isSlidingUpward = dy > 0;
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int first=manager.findFirstVisibleItemPosition();
            RecyclerView.ViewHolder holder;
            if (isSlidingUpward){
                holder = recyclerView.findViewHolderForAdapterPosition(first+1);
                if (holder!=null){
                    if (holder instanceof StuReportVoteAdapter.ParentHolder){
                        int headerMoveY = holder.itemView.getTop() - top_layout.getMeasuredHeight();
                        if (headerMoveY<0) {
                            top_layout.setTranslationY(headerMoveY);
                        }
                    } else if (holder instanceof StuReportVoteAdapter.ChildHolder){
                        top_layout.setTranslationY(0);
                        parent_name.setText(((StuReportVoteAdapter.ChildHolder) holder).bean.getTablename_parent());
                    }
                }
            }else{
                holder = recyclerView.findViewHolderForAdapterPosition(first);
                if (holder!=null){
                    if (holder instanceof StuReportVoteAdapter.ChildHolder){
                        parent_name.setText(((StuReportVoteAdapter.ChildHolder) holder).bean.getTablename_parent());
                    }
                }
            }

        }
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            // 当不滑动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 获取最后一个完全显示的itemPosition
                int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                int itemCount = manager.getItemCount();

                // 判断是否滑动到了最后一个item，并且是向上滑动
                if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                    // 加载更多
//                    refresh(false,startdate ,enddate , TagFinal.REFRESH_MORE);
                }
            }
        }
    };













}