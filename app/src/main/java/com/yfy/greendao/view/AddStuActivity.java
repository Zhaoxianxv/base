package com.yfy.greendao.view;

import android.os.Bundle;
import android.view.View;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.app.bean.BaseClass;
import com.yfy.app.bean.StuBean;
import com.yfy.app.bean.TermBean;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddStuActivity extends BaseActivity {

    private static final String TAG = AddStuActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);

        initRecycler();
        getData();

    }
    public List<StuBean> stu_list;
    public String type,title;
    private void getData(){
        stu_list=NormalDataSaveTools.getInstance().getStuBeanToGreenDao();
        if (StringJudge.isEmpty(stu_list)){
            stu_list=new ArrayList<>();
        }

        type=getIntent().getStringExtra(Base.type);
        title=getIntent().getStringExtra(Base.title);
        initSQToolbar(title);
        initView(type);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"确定");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                addKeyValue();
            }
        });


    }


    private void addKeyValue(){
        switch (type){
            case "stu":
                saveStu();
                break;
            case "class":
                saveClass();
                break;
            case "term":
                saveTerm();
                break;

        }
    }


    public List<KeyValue> keyValueAdapterData =new ArrayList<>();
    public AddStuAdapter adapter;
    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new AddStuAdapter(mActivity);
        recyclerView.setAdapter(adapter);


    }

    private void initView(String type){
        switch (type){
            case "stu":
                setStuView();
                break;
            case "class":
                setClassView();
                break;
            case "term":
                setTermView();
                break;

        }
    }
    private void setStuView(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        one.setTitle("name");
        one.setValue("");
        one.setKey("请输入姓名");

        two.setTitle("id");
        two.setValue("");
        two.setKey("请输入id(100开头)");

        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setClassView(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        one.setTitle("name");
        one.setValue("");
        one.setKey("请输入班级名称");

        two.setTitle("id");
        two.setValue("");
        two.setKey("请输入id(200开头)");

        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setTermView(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        one.setTitle("name");
        one.setValue("");
        one.setKey("请输入学期名称");

        two.setTitle("id");
        two.setValue("");
        two.setKey("请输入id(200开头)");

        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void saveStu(){
        StuBean stuBean =new StuBean();
        for (KeyValue value:adapter.getDataList()){
            if (StringJudge.isEmpty(value.getValue())){
                ViewTool.showToastShort(mActivity, StringUtils.getTextJoint("%1$s!没有填写",value.getTitle()));
                return;
            }
            switch (value.getTitle()){
                case "name":
                    stuBean.setStuname(value.getValue());
                    break;
                case "id":
                    stuBean.setStuid(value.getValue());
                    break;
            }
        }
        NormalDataSaveTools.getInstance().saveStuData(stuBean);
        ViewTool.showToastShort(mActivity,R.string.success_do);
        finish();
    }



    private void saveClass(){
        BaseClass baseClass =new BaseClass();
        for (KeyValue value:adapter.getDataList()){
            if (StringJudge.isEmpty(value.getValue())){
                ViewTool.showToastShort(mActivity, StringUtils.getTextJoint("%1$s!没有填写",value.getTitle()));
                return;
            }
            switch (value.getTitle()){
                case "name":
                    baseClass.setClassname(value.getValue());
                    break;
                case "id":
                    baseClass.setClassid(value.getValue());
                    break;
            }
        }
        NormalDataSaveTools.getInstance().saveClassData(baseClass);
        ViewTool.showToastShort(mActivity,R.string.success_do);
        finish();
    }


    private void saveTerm(){
        TermBean term =new TermBean();
        for (KeyValue value:adapter.getDataList()){
            if (StringJudge.isEmpty(value.getValue())){
                ViewTool.showToastShort(mActivity, StringUtils.getTextJoint("%1$s!没有填写",value.getTitle()));
                return;
            }
            switch (value.getTitle()){
                case "name":
                    term.setName(value.getValue());
                    break;
                case "id":
                    term.setId(value.getValue());
                    break;
            }
        }
        term.setIsnow("1");
        NormalDataSaveTools.getInstance().saveCurrentTerm(term);
        ViewTool.showToastShort(mActivity,R.string.success_do);
        finish();
    }



    /**
     * ----------------------------retrofit-----------------------
     */





    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
