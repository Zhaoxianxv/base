package com.yfy.app.PEquality;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.yfy.app.PEquality.adapter.PEAttendListAdapter;
import com.yfy.app.PEquality.adapter.PEAttitudeStuMainAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.final_tag.data.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PEAttitudeStuActivity extends BaseActivity {
    private static final String TAG = PEAttitudeStuActivity.class.getSimpleName();

    public PEAttitudeStuMainAdapter adapter_attitude;
    public PEAttendListAdapter adapter_attend;
    @BindView(R.id.attend_txt)
    AppCompatTextView attend_txt;
    @BindView(R.id.attend_card)
    CardView attend_card;
    @BindView(R.id.attitude_txt)
    AppCompatTextView attitude_txt;
    @BindView(R.id.attitude_card)
    CardView attitude_card;
    @BindView(R.id.attitude_add_card)
    CardView attitude_add_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_attitude_stu);
        getData();
        initRecycler();
        initSQToolbar();

    }


    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);


    }

    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    private RecycleViewDivider divider;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        divider = new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.gray));
        adapter_attitude =new PEAttitudeStuMainAdapter(mActivity);
        adapter_attend=new PEAttendListAdapter(mActivity);



        setAdapterData(true);
//        attend_txt.setText("请假记录");
//        attitude_txt.setText("学习态度");
    }


    @OnClick(R.id.attend_txt)
    void setAttend(){
        setAdapterData(false);
    }

    @OnClick(R.id.attitude_txt)
    void setAttitude(){
        setAdapterData(true);
    }
    @OnClick(R.id.attitude_add_txt)
    void setAddAttitude(){
        setAdapterData(true,"");
    }


    private void setAdapterData(boolean is,String add){

//            recyclerView.addItemDecoration(divider);
        attitude_add_card.setVisibility(View.VISIBLE);
        attitude_card.setVisibility(View.GONE);
        attend_card.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter_attitude);
        setAddAttitudeData();
    }

    private void setAdapterData(boolean is){


        if (is){
//            recyclerView.addItemDecoration(divider);
            attitude_card.setVisibility(View.VISIBLE);
            attend_card.setVisibility(View.GONE);
            attitude_add_card.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter_attitude);
            setAttitudeData();
        }else{
//            recyclerView.removeItemDecoration(divider);
            attitude_card.setVisibility(View.GONE);
            attitude_add_card.setVisibility(View.GONE);
            attend_card.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter_attend);
            setAttendData();
        }
    }
    private void setAttitudeData(){
        keyValue_adapter.clear();




        KeyValue two=new KeyValue("","",TagFinal.TYPE_ITEM);
        KeyValue one=new KeyValue("","",TagFinal.TYPE_ITEM);
        one.setTitle("2020.5.21·下午第二节课");
        one.setLeft_title("-6分");
        one.setContent("旷课");
        one.setRight("张丹");

        two.setTitle("2020.5.21·下午第二节课");
        two.setLeft_title("-2分");
        two.setContent("大课间体育活动违纪或缺席");
        two.setRight("张丹");

        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(one);



        adapter_attitude.setDataList(keyValue_adapter);
        adapter_attitude.setLoadState(TagFinal.LOADING_END);


    }
    private void setAddAttitudeData(){
        keyValue_adapter.clear();



        String list_content="组织能力优异,短跑能力优异,乒乓表现优异";
        List<String> list=StringUtils.listToStringSplitCharacters(list_content,",");
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setContent(s);
            one.setTitle("2020.5.21·下午第二节课");

            one.setLeft_title(StringUtils.stringToGetTextJoint("+%1$s分",String.valueOf(MathTool.getRandomInt(1,5))));
            one.setRight("记录人：张丹");
            keyValue_adapter.add(one);
        }



        adapter_attitude.setDataList(keyValue_adapter);
        adapter_attitude.setLoadState(TagFinal.LOADING_END);


    }

    private void setAttendData(){
        keyValue_adapter.clear();
        String list_content="感冒,走亲访友,发烧";
        List<String> list=StringUtils.listToStringSplitCharacters(list_content,",");
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setContent(s);
            one.setTitle("2020.5.21");
            one.setLeft_title("下午第二节课");
            one.setRight("记录人：张丹");
            keyValue_adapter.add(one);
        }

        adapter_attend.setDataList(keyValue_adapter);
        adapter_attend.setLoadState(TagFinal.LOADING_END);
    }
    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        req.setSession_key(Base.user.getSession_key());

        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_term_list_api(env);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    toastShow("error");
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
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
