package com.yfy.app.duty_evaluate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.greendao.bean.StuBean;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateRecodeAdapter;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetClassAllStuReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.DateUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.view.SQToolBar;
import com.yfy.view.time.CustomDatePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

public class DutyEvaluateRecordActivity extends BaseActivity {
    private static final String TAG = DutyEvaluateRecordActivity.class.getSimpleName();

    public DutyEvaluateRecodeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        initSQToolbar();
        initDatePicker();
        getClassAllStu();
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

        year_s=String.valueOf(bean.getYearName());
        month_s=bean.getMonthNameTwo();

        select_date_tv.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
        customDatePicker1 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String data=time.split(" ")[0].substring(0,time.split(" ")[0].lastIndexOf("-"));
                year_s=data.split("-")[0];
                month_s=data.split("-")[1];
                select_date_tv.setText(StringUtils.stringToGetTextJoint("%1$s-%2$s",year_s,month_s));
                getClassAllStu();
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



    /**
     * ----------------------------retrofit-----------------------
     */

    public void getClassAllStu() {
        adapter.setYear_s(year_s);
        adapter.setMonth_s(month_s);

        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetClassAllStuReq req = new UserGetClassAllStuReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());
        req.setClassid(ConvertObjtect.getInstance().getInt(class_id));
        req.setTermid(MathTool.stringToInt( NormalDataSaveTools.getInstance().getTermBeanToGreenDao().getId()));


        reqBody.userGetClassAllStuReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_class_all_stu_api(env);
        call.enqueue(this);
        ViewTool.showProgressDialog(mActivity,"");
    }

    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetClassAllStuRes !=null){
                String result=b.userGetClassAllStuRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    initStuCpData(res);
                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
            }
        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        toastShow(R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
