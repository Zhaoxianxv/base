package com.yfy.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.app.bean.BaseClass;
import com.yfy.app.bean.BaseGrade;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.bean.TermBean;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.base.Base;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SelectedClassActivity extends BaseActivity {
    private static final String TAG = SelectedClassActivity.class.getSimpleName();

    public SelectedClassAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        Logger.e(TAG);
        getData();
        select_term= NormalDataSaveTools.getInstance().getTermBeanToGreenDao();
        initRecycler();
        initSQToolbar();
    }

    private String mode_type;
    private void getData(){
        select_term=NormalDataSaveTools.getInstance().getTermBeanToGreenDao();
        mode_type=getIntent().getStringExtra(Base.mode_type);
    }


    public TermBean select_term;
    private TextView menu_one;
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("班级列表");
        menu_one =toolbar.addMenuText(TagFinal.ONE_INT,"");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                Intent intent=new Intent(mActivity,SelectedTermActivity.class);
                startActivityForResult(intent,TagFinal.UI_TAG);
            }
        });
        if (StringJudge.isEmpty(select_term.getId())){
            menu_one.setText("选择学期");
        }else{
            menu_one.setText(select_term.getName());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_TAG:
                    assert data!=null;
                    select_term=data.getParcelableExtra(Base.data);
                    menu_one.setText(select_term.getName());
                    break;
                case -1:
                    break;

            }
        }
    }




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
                ColorRgbUtil.getGainsboro()));
        adapter=new SelectedClassAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setMode_type(mode_type);

        setAdapterData();
    }



    public List<KeyValue> keyValue_adapter=new ArrayList<>();

    private void setAdapterData(){
        List<BaseClass> class_list=NormalDataSaveTools.getInstance().getClassBeanToGreenDao();
        if (StringJudge.isEmpty(class_list)){
            class_list=new ArrayList<>();
        }
        Logger.e(""+class_list.size());
        keyValue_adapter.clear();
        for (BaseClass s:class_list){
            KeyValue one=new KeyValue(s.getClassname(),"",TagFinal.TYPE_ITEM);
            one.setId(s.getClassid());
            one.setTitle(s.getClassname());
            one.setRight_value("");

            keyValue_adapter.add(one);
        }

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    /**
     * ----------------------------retrofit-----------------------
     */

    public  List<KeyValue> adapter_data_list=new ArrayList<>();
    public void getClassList() {

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
            if (b.userGetClassListRes !=null){
                String result=b.userGetClassListRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    adapter_data_list.clear();
                    List<BaseGrade> list=res.getGradelist();
                    for (BaseGrade s:list){
                        for (BaseClass bean:s.getClasslist()){
                            KeyValue keyValue=new KeyValue(StringUtils.stringToGetTextJoint("%1$s-%2$s",s.getGradename(),bean.getClassname()),bean.getClassid());
                            adapter_data_list.add(keyValue);
                        }
                    }
                    adapter.setDataList(adapter_data_list);
                    adapter.setLoadState(TagFinal.LOADING_END);
                }else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
            }
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    for (TermBean bean:res.getTerm()){
                        if (bean.getIsnow().equalsIgnoreCase("1")){
                            select_term.setId(bean.getId());
                            select_term.setName(bean.getName());
                            NormalDataSaveTools.getInstance().saveCurrentTerm(bean);
                            menu_one.setText(select_term.getName());
                        }
                    }
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
            ViewTool.showToastShort(mActivity,StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

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
