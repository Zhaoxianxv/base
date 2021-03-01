package com.yfy.app.duty_evaluate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.greendao.bean.TermBean;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateStuDevelopAdapter;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateStuNormalAdapter;
import com.yfy.app.duty_evaluate.bean.DutyEvaluateRes;
import com.yfy.app.duty_evaluate.bean.InfoBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.recycerview.GridDividerLineNotBottom;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.view.SQToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@SuppressLint("NonConstantResourceId")
public class DutyEvaluateStuMainActivity extends BaseActivity implements AssetsGetFileData {
    private static final String TAG = DutyEvaluateStuMainActivity.class.getSimpleName();


    @BindView(R.id.duty_evaluate_stu_name)
    TextView stu_name_tv;
    @BindView(R.id.duty_evaluate_stu_head)
    AppCompatImageView stu_head_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_evaluate_stu_main);
        initSQToolbar("五育评价");
        initView();
        initRecyclerView();
        initRecyclerViewDevelop();
        getAssetsData("duty_evaluate_get_stu_develop_detail.txt");
    }




    public TextView menu_title;
    public TermBean selected_termBean;
    public void initSQToolbar(String title) {
        selected_termBean = NormalDataSaveTools.getInstance().getTermBeanToGreenDao();

        assert toolbar!=null;
        toolbar.setTitle(title);
        menu_title=toolbar.addMenuText(TagFinal.ONE_INT, "");
        if (selected_termBean.getName().equalsIgnoreCase("")){
            menu_title.setText("选择学期");
        }else{
            menu_title.setText(selected_termBean.getName());
        }
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case TagFinal.ONE_INT:
                        Intent intent=new Intent(mActivity,SelectedTermActivity.class);
                        startActivityForResult(intent,TagFinal.UI_TAG);
                        break;
                }

            }
        });


    }



    private void initView(){
        GlideTools.chanMult(mActivity,Base.user.getHeadPic(),stu_head_iv,R.drawable.icon_account_fill);
        stu_name_tv.setText(StringUtils.stringToGetTextJoint("%1$s\t·\t%2$s",Base.user.getName(),"35"));
    }

    public DutyEvaluateStuNormalAdapter adapter_normal;
    public RecyclerView normal_recycler;
    public List<KeyValue> adapter_data_list=new ArrayList<>();
    private void initRecyclerView(){
        normal_recycler =findViewById(R.id.duty_evaluate_stu_normal_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,3);
        normal_recycler.setLayoutManager(manager);

        normal_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
        adapter_normal =new DutyEvaluateStuNormalAdapter(mActivity);
        normal_recycler.setAdapter(adapter_normal);

        List<String> list=StringUtils.listToStringSplitCharacters("9月·35分,10月·30分,11月·38分,12月·未完成,1月·未完成",",");
        List<String> stu_score=StringUtils.listToStringSplitCharacters("0,1,2",",");
        for (String s:list){
            KeyValue keyValue=new KeyValue(s,R.mipmap.main_delay_service);
            keyValue.setTitle(s);
            keyValue.setRight_name("2020");
            keyValue.setRight_key("2020");

            switch (s){
                case "9月·35分":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("9");
                    break;
                case "10月·30分":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("10");
                    break;
                case "11月·38分":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("11");
                    break;
                case "12月·未完成":
                    keyValue.setRight_name("2020");
                    keyValue.setRight_key("12");
                    break;
                case "1月·未完成":
                    keyValue.setRight_name("2021");
                    keyValue.setRight_key("1");
                    break;
                default:
                    keyValue.setValue(MathTool.randomStringAtList(stu_score));
                    break;
            }
            adapter_data_list.add(keyValue);
        }
        adapter_normal.setDataList(adapter_data_list);
        adapter_normal.setLoadState(TagFinal.LOADING_END);


        adapter_normal.setOnClieckAdapterLayout(new DutyEvaluateStuNormalAdapter.OnClickAdapterLayout() {
            @Override
            public void layoutOnClick(KeyValue keyValue) {

                Intent intent;
                if (keyValue.getTitle().equalsIgnoreCase("1月·未完成")){
                    intent=new Intent(mActivity,DutyEvaluateStuAddActivity.class);

                    intent.putExtra(Base.year_value,keyValue.getRight_name());
                    intent.putExtra(Base.month_value,keyValue.getRight_key());
                    startActivity(intent);
                }else{
                    KeyValue stu=new KeyValue();
                    stu.setName(Base.user.getName());
                    stu.setId(Base.user.getIdU());

                    intent=new Intent(mActivity,DutyEvaluateStuDetailActivity.class);
                    intent.putExtra(Base.stu_bean, stu);
                    intent.putExtra(Base.year_value,keyValue.getRight_name());
                    intent.putExtra(Base.month_value,keyValue.getRight_key());
                    startActivity(intent);

                }
            }
        });
    }


    public RecyclerView develop_recycler;
    public DutyEvaluateStuDevelopAdapter adapter_develop;
    public List<KeyValue> adapter_develop_data=new ArrayList<>();
    private void initRecyclerViewDevelop(){
        develop_recycler =findViewById(R.id.duty_evaluate_stu_develop_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,3);
        develop_recycler.setLayoutManager(manager);

        develop_recycler.addItemDecoration(new GridDividerLineNotBottom(Color.TRANSPARENT));
        adapter_develop =new DutyEvaluateStuDevelopAdapter(mActivity);
        develop_recycler.setAdapter(adapter_develop);
        adapter_develop.setOnClieckAdapterLayout(new DutyEvaluateStuDevelopAdapter.OnClickAdapterLayout() {
            @Override
            public void layoutOnClick(KeyValue keyValue) {
                KeyValue stu=new KeyValue();
                stu.setName(Base.user.getName());
                stu.setId(Base.user.getIdU());

                Intent intent;
                switch(keyValue.getTitle()){
                    case "班级认定":
                    case "家长认定":

                        DateBean dateBean=new DateBean();
                        dateBean.setValue_long(System.currentTimeMillis(),true);

                        intent=new Intent(mActivity,DutyEvaluateStuDetailActivity.class);
                        intent.putExtra(Base.stu_bean, stu);
                        intent.putExtra(Base.year_value,dateBean.getYearName());
                        intent.putExtra(Base.month_value,dateBean.getMonthName());
                        startActivity(intent);
                        break;
                    case "校内活动":
                    case "校外实践":
                    case "比赛成绩":
                        intent=new Intent(mActivity,DutyEvaluatePracticeActivity.class);
                        intent.putExtra(Base.title,keyValue.getTitle());
                        intent.putExtra(Base.term_bean,selected_termBean);
                        startActivity(intent);
                        break;
                        default:
                            break;
                }

            }
        });
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_TAG:
                    assert data!=null;
                    selected_termBean=data.getParcelableExtra(Base.data);
                    menu_title.setText(selected_termBean.getName());
                    break;

            }
        }
    }



    /**
     * ----------------------------retrofit-----------------------
     */


    /**
     * -------------------------async task----------
     */






    //async task

    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        showProgressDialog("");
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }



    private void initAdapterData(DutyEvaluateRes res){
        adapter_develop_data.clear();


        for (InfoBean info:res.getInfo()){
            KeyValue bean=new KeyValue();
            bean.setView_type(TagFinal.TYPE_ITEM);
            bean.setTitle(info.getParent_title());
            bean.setValue(info.getParent_all_score());

            adapter_develop_data.add(bean);
        }


        adapter_develop.setDataList(adapter_develop_data);
        adapter_develop.setLoadState(TagFinal.LOADING_END);

    }


    @Override
    public void doUpData(String content) {
        dismissProgressDialog();
        if (StringJudge.isEmpty(content)){
            ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
        }else{
            DutyEvaluateRes res=gson.fromJson(content,DutyEvaluateRes.class);
            initAdapterData(res);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()==AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }
}




