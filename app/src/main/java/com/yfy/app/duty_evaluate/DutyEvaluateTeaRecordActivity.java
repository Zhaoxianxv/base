package com.yfy.app.duty_evaluate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.app.bean.StuBean;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateRecodeAdapter;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.DateUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.time.CustomDatePicker;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;

public class DutyEvaluateTeaRecordActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateTeaRecordActivity.class.getSimpleName();

    public DutyEvaluateRecodeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        initSQToolbar();
        initDatePicker();
    }

    public String class_id;
    public KeyValue classBean;
    public void getData(){
        classBean=getIntent().getParcelableExtra(Base.class_bean);
        class_id=classBean.getValue();

    }

    private TextView select_date_tv;
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(classBean.getTitle());
        select_date_tv=toolbar.addMenuText(TagFinal.ONE_INT,"");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                customDatePicker1.show(StringUtils.stringToGetTextJoint("%1$s-%2$s-01 01:01",year_s,month_s));
            }
        });


    }





    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration(new RecycleViewDivider(
//                mActivity,
//                LinearLayoutManager.HORIZONTAL,
//                1,
//                ColorRgbUtil.getGainsboro()));
        adapter=new DutyEvaluateRecodeAdapter(mActivity);
        recyclerView.setAdapter(adapter);


    }



    public String year_s,month_s;
    public CustomDatePicker customDatePicker1;
    private void initDatePicker() {

        DateBean bean=new DateBean();
        bean.setValue_long(System.currentTimeMillis(),false);

        year_s=String.valueOf(bean.getSelectYearNameInt());
        month_s=bean.getSelectMonthNameString();

        select_date_tv.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
        customDatePicker1 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String data=time.split(" ")[0].substring(0,time.split(" ")[0].lastIndexOf("-"));
                year_s=data.split("-")[0];
                month_s=data.split("-")[1];
                select_date_tv.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
//                getClassAllStu();
            }
        }, "2000-01-01 01:01", DateUtils.getDateTime("yyyy-MM-dd HH:mm"));
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动


    }










    public List<KeyValue> scanStateList=new ArrayList<>();
    private void initStuCpData(BaseRes res){
        scanStateList.clear();
        if (StringJudge.isEmpty(res.getIncompletestu())){
            ViewTool.showToastShort(mActivity,StringUtils.stringToGetTextJoint("未获取到\t%1$s\t的学生",classBean.getName()));
            return;
        }
        for (StuBean stu:res.getIncompletestu()){
            KeyValue bean=new KeyValue();
            bean.setName(stu.getStuname());
            bean.setValue(stu.getStuname());
            bean.setId(stu.getStuid());
            scanStateList.add(bean);
        }
        adapter.setDataList(scanStateList);
        adapter.setLoadState(TagFinal.LOADING_END);
    }



    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
