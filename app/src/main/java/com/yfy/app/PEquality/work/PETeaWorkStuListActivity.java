package com.yfy.app.PEquality.work;

import android.content.Intent;
import android.os.Bundle;

import com.yfy.app.PEquality.adapter.PETeaWorkStuListAdapter;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.TermBean;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.viewtools.ViewTool;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PETeaWorkStuListActivity extends HttpPostActivity implements HttpNetHelpInterface {
    private static final String TAG = PETeaWorkStuListActivity.class.getSimpleName();

    public PETeaWorkStuListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_tea_work_stu_list);
        Logger.eLogText(TAG);
        getData();
//        initRecycler();
        initSQToolbar();
    }



    public TermBean select_term;
    public CPWBean classCPWBean;
    public DateBean select_date;
    private void getData(){
        classCPWBean=getIntent().getParcelableExtra(Base.class_bean);
        select_term=getIntent().getParcelableExtra(Base.term_bean);
        select_date=getIntent().getParcelableExtra(Base.date);

        getStuScoreItem();
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(select_date.getName());
    }
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,3, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new PETeaWorkStuListAdapter(mActivity);
        recyclerView.setAdapter(adapter);
//        adapter.setIntentStart(new StartIntentInterface() {
//            @Override
//            public void startIntentActivity(Intent intent,String type) {
//
//                pic_total_size.setText(type);
//            }
//        });



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


    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
