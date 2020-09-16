package com.yfy.app.PEquality;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.app.PEquality.adapter.KnowledgeLibraryPagerAdapter;
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
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PEQualityKnowledgeLibraryActivity extends BaseActivity {
    private static final String TAG = PEQualityKnowledgeLibraryActivity.class.getSimpleName();


    @Bind(R.id.public_detail_bg_text)
    AppCompatTextView bgtext_view;

    @OnClick(R.id.public_detail_bg_text)
    void  setBg_Txt(){
        bgtext_view.setVisibility(View.GONE);
        data_list.clear();


        KeyValue one=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);


        KeyValue two=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);


        KeyValue three=new KeyValue("发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);


        KeyValue four=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);


        data_list.add(one);
        data_list.add(two);
        data_list.add(three);
        data_list.add(four);

        if (StringJudge.isEmpty(data_list)){
            pager.setVisibility(View.GONE);
            bgtext_view.setVisibility(View.VISIBLE);
        }else{
            pager.setVisibility(View.VISIBLE);
            bgtext_view.setVisibility(View.GONE);
        }

        pager_adapter.reSetData(data_list);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_knowledge_library);
        getData();
        initSQToolbar();
        initPager();

    }


    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);

    }
    public LinkedList<KeyValue> data_list = new LinkedList<KeyValue>();
    private ViewPager pager;
    private KnowledgeLibraryPagerAdapter pager_adapter;

    private void initPager(){
        pager =  findViewById(R.id.knowledge_library_pager);
        if (StringJudge.isEmpty(data_list)){
            pager.setVisibility(View.GONE);
            bgtext_view.setVisibility(View.VISIBLE);
        }else{
            pager.setVisibility(View.VISIBLE);
            bgtext_view.setVisibility(View.GONE);
        }
        pager_adapter=new KnowledgeLibraryPagerAdapter(mActivity);
        pager.setAdapter(pager_adapter);
        pager_adapter.reSetData(data_list);
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
