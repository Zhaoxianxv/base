package com.yfy.app.PEquality;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yfy.app.PEquality.adapter.KnowledgeAnswerListViewAdapter;
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
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PEQualityKnowledgeAnswerActivity extends BaseActivity {
    private static final String TAG = PEQualityKnowledgeAnswerActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_knowledge_answer);
        getData();
        initSQToolbar();
//        initListView();

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
    public KnowledgeAnswerListViewAdapter list_adapter;
    public ListView listview;
    private  void initListView(){
        listview=findViewById(R.id.attitude_answer_list);
//        listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        list_adapter=new KnowledgeAnswerListViewAdapter(mActivity);
        listview.setAdapter(list_adapter);

        keyValue_adapter.clear();

        KeyValue one=new KeyValue("A、古罗马","",TagFinal.TYPE_ITEM);
        KeyValue two=new KeyValue("B、古希腊","",TagFinal.TYPE_ITEM);
        KeyValue three=new KeyValue("C、法国文字超出一行文字超出一行文字超出一行文字超出一行","",TagFinal.TYPE_ITEM);
        KeyValue four=new KeyValue("D、英国","",TagFinal.TYPE_ITEM);

        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(three);
        keyValue_adapter.add(four);
        list_adapter.setDatas(keyValue_adapter);
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
