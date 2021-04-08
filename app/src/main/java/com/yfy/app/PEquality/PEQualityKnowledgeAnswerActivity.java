package com.yfy.app.PEquality;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

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
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
@SuppressLint("NonConstantResourceId")
public class PEQualityKnowledgeAnswerActivity extends BaseActivity {
    private static final String TAG = PEQualityKnowledgeAnswerActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_knowledge_answer);
        getData();
        initSQToolbar();
        initPager();
        setAdapterData();
        initDialog();
    }


    private String title ,type;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        if(type.equalsIgnoreCase(TagFinal.FALSE))return;
        toolbar.addMenuText(TagFinal.ONE_INT,"结束答题");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                showDialog("提示","结束本次答题并计算最终分数");
            }
        });
    }


    private ConfirmContentWindow confirmContentWindow;
    private void initDialog(){

        confirmContentWindow = new ConfirmContentWindow(mActivity);
        confirmContentWindow.setPopClickListener(new NoFastClickListener() {

            @Override
            public void popClick(View view) {

                switch (view.getId()){
                    case R.id.pop_dialog_title:
                        break;
                    case R.id.pop_dialog_ok:
                        confirmContentWindow.dismiss();
                        break;
                }
            }
        });
    }

    private void showDialog(String title,String content){
        if (confirmContentWindow==null)return;
        confirmContentWindow.setTitle_s(title,content);
        confirmContentWindow.showAtCenter();
    }

    @BindView(R.id.knowledge_answer_pager)
    ViewPager pager;
    public LinkedList<KeyValue> data_list = new LinkedList<>();
    public KnowledgeLibraryPagerAdapter pager_adapter;
    public void initPager(){
        pager_adapter=new KnowledgeLibraryPagerAdapter(mActivity);
        pager.setAdapter(pager_adapter);
    }
    private void setAdapterData(){

        data_list.clear();
        KeyValue keyValue=new KeyValue(TagFinal.TYPE_ITEM);
        List<CPWBean> cpwbeanList=new ArrayList<>();
        keyValue.setTitle("奥林匹克运动会的发源地");
        cpwbeanList.add(new CPWBean("A、古罗马",TagFinal.FALSE));
        cpwbeanList.add(new CPWBean("B、古希腊",TagFinal.TRUE));
        cpwbeanList.add(new CPWBean("C、法国文字超出一行文字超出一行文字超出一行文字超出一行",TagFinal.FALSE));
        cpwbeanList.add(new CPWBean("D、英国",TagFinal.FALSE));

        keyValue.setCpwBeanArrayList(cpwbeanList);
        keyValue.setType(TagFinal.TRUE);//单选
        keyValue.setId(type);//可不可以交互
        keyValue.setRight("A");//正确答案
        keyValue.setRight_name(TagFinal.TRUE);




        KeyValue not=new KeyValue(TagFinal.TYPE_ITEM);
        List<CPWBean> cpwbeanNot=new ArrayList<>();
        not.setTitle("奥林匹克运动会的发源地");
        cpwbeanNot.add(new CPWBean("A、古罗马",TagFinal.TRUE));
        cpwbeanNot.add(new CPWBean("B、古希腊",TagFinal.FALSE));
        cpwbeanNot.add(new CPWBean("C、法国文字超出一行文字超出一行文字超出一行文字超出一行",TagFinal.FALSE));
        cpwbeanNot.add(new CPWBean("D、英国(没有完成答题)",TagFinal.FALSE));
        not.setCpwBeanArrayList(cpwbeanNot);
        not.setType(TagFinal.TRUE);
        not.setId(type);
        not.setRight("A");//正确答案
        not.setRight_name("");

        data_list.add(keyValue);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        data_list.add(keyValue);
        data_list.add(not);
        if (StringJudge.isEmpty(data_list)){
            pager.setVisibility(View.GONE);
        }else{
            pager.setVisibility(View.VISIBLE);
        }
        pager_adapter.reSetData(data_list);

    }





    /**
     * ----------------------------retrofit-----------------------
     */

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
                    Logger.e("");
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
                assert response.errorBody() != null;
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
