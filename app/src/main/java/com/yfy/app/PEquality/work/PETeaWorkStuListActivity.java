package com.yfy.app.PEquality.work;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.yfy.app.PEquality.adapter.PETeaWorkStuListAdapter;
import com.yfy.app.bean.TermBean;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;


public class PETeaWorkStuListActivity extends HttpPostActivity implements HttpNetHelpInterface {
    private static final String TAG = PETeaWorkStuListActivity.class.getSimpleName();

    public PETeaWorkStuListAdapter adapter_attitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        Logger.eLogText(TAG);
        getData();
        initRecycler();
        initSQToolbar();
    }



    public TermBean select_term;
    public CPWBean classCPWBean;
    private void getData(){
        classCPWBean=getIntent().getParcelableExtra(Base.class_bean);
        select_term=getIntent().getParcelableExtra(Base.term_bean);

        getStuScoreItem();
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("班级");
    }
    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public void initRecycler(){
        swipeRefreshLayout =  findViewById(R.id.public_swip);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                getStuScoreItem();
            }
        });

        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));

        adapter_attitude=new PETeaWorkStuListAdapter(mActivity);
        recyclerView.setAdapter(adapter_attitude);
        adapter_attitude.setItemIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent) {
//
                intent.setClass(mActivity, PETeaWorkStuDetailSetStateActivity.class);
                startActivityForResult(intent, TagFinal.UI_REFRESH);

            }
        });
    }

    public void closeSwipeRefresh(){
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 200);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    break;
                case -1:
                    break;
            }
        }
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    public void getStuScoreItem() {




    }



    @Override
    public void success(String api_name, String result) {
        if (!isActivity())return;
        closeSwipeRefresh();
        ViewTool.dismissProgressDialog();
//        if (api_name.equalsIgnoreCase(ApiUrl.QUALITY_TEA_GET_SCORE_ITEM_LIST)){
//            QualityRes res=gson.fromJson(result, QualityRes.class);
//            Logger.eLogText(StringUtils.getTextJoint("%1$s:%2$s",api_name,result));
//            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                List<QualityStu> list=res.getStulist();
//                for (QualityStu stu:list){
//                }
//                adapter_attitude.setDataList(list);
//                adapter_attitude.setLoadState(TagFinal.LOADING_END);
//            }else{
//                ViewTool.showToastShort(mActivity,res.getError_code());
//            }
//        }
    }


    @Override
    public void fail(String api_name) {
        if (!isActivity())return;
        closeSwipeRefresh();


    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
