package com.yfy.app.PEquality;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfy.app.PEquality.adapter.PEQualitySkillsAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.Logger;
import com.yfy.final_tag.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PEQualitySkillsActivity extends BaseActivity {
    private static final String TAG = PEQualitySkillsActivity.class.getSimpleName();

    private PEQualitySkillsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        initDialog();
        initSQToolbar();
//        getTerm();

        setAdapterData();

    }


    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenu(TagFinal.ONE_INT,R.drawable.ic_help_white_24dp);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
             showDialog("得分规则","得分规则说明","确定");
            }
        });

    }


    private ConfirmContentWindow confirmContentWindow;
    private void initDialog(){

        confirmContentWindow = new ConfirmContentWindow(mActivity);
        confirmContentWindow.setOnPopClickListenner(new ConfirmContentWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.pop_dialog_title:
                        break;
                    case R.id.pop_dialog_content:
                        break;
                    case R.id.pop_dialog_ok:
                        break;
                }
            }
        });
    }

    private void showDialog(String title,String content,String ok){
        if (confirmContentWindow==null)return;
        confirmContentWindow.setTitle_s(title,content,ok);
        closeKeyWord();
        confirmContentWindow.showAtCenter();
    }

    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.gray)));
        adapter=new PEQualitySkillsAdapter(mActivity);
        recyclerView.setAdapter(adapter);
    }



    private void setAdapterData(){
        keyValue_adapter.clear();


        KeyValue one=new KeyValue("","",TagFinal.TYPE_ITEM);
        one.setTitle("总分");
        one.setContent("技能项简评");
        one.setRight("88");

        KeyValue two=new KeyValue("","",TagFinal.TYPE_ITEM);
        two.setTitle("必考项 1");
        two.setContent("球类");
        two.setRight("88");

        KeyValue three=new KeyValue("","",TagFinal.TYPE_ITEM);
        three.setTitle("必考项 ");
        three.setContent("体操");
        three.setRight("88");

        KeyValue four=new KeyValue("","",TagFinal.TYPE_ITEM);
        four.setTitle("选考项 ");
        four.setContent("田径、民传、新兴体育");
        four.setRight("88");

        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(three);
        keyValue_adapter.add(four);

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_term_list(env);
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
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
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
