package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.PEquality.adapter.PEHonorMainAdapter;
import com.yfy.app.PEquality.bean.QEHonorBean;
import com.yfy.app.PEquality.bean.QEHonorRes;
import com.yfy.app.SelectedTermActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.app.bean.TermBean;
import com.yfy.json.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DutyEvaluateTeaHonorMainActivity extends HttpPostActivity implements AssetsGetFileData {
    private static final String TAG = DutyEvaluateTeaHonorMainActivity.class.getSimpleName();

    private PEHonorMainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initDialogList();
        initRecycler();
        initSQToolbar();
        getAssetsData("duty_score_admin.txt");

    }

    public TermBean select_term;
    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        select_term=getIntent().getParcelableExtra(Base.term_bean);
    }
    private TextView menu_one;
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        menu_one=toolbar.addMenuText(TagFinal.ONE_INT,select_term.getName());
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent();
                intent.setClass(mActivity, SelectedTermActivity.class);
                startActivity(intent);
            }
        });

    }



    public KeyValue select_value;
    public int select_adapter_index;
    CPWListBeanView cpwListBeanView;
    List<CPWBean> cpwBeans=new ArrayList<>();
    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastPopClick(CPWBean cpwBean, String type) {
                cpwListBeanView.dismiss();

                if(cpwBean.getValue().equalsIgnoreCase("0"))return;
                setHonorState(cpwBean.getName(),cpwBean.getValue());

            }
        });
    }


    public void setCpwBean(String id){
        cpwBeans.clear();
        if (StringJudge.isEmpty(cpwBeans)){
            cpwBeans.add(new CPWBean("通过","1",id,""));
            cpwBeans.add(new CPWBean("拒绝","2",id,""));
            cpwBeans.add(new CPWBean("取消","0",id,""));
        }
        cpwListBeanView.setDatas(cpwBeans);
        cpwListBeanView.showAtCenter();
    }



    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new PEHonorMainAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent) {
                String id=intent.getStringExtra(Base.id);
                select_value=intent.getParcelableExtra(Base.data);
                select_adapter_index=intent.getIntExtra(Base.index,0);
                setCpwBean(id);
            }
        });
    }




    public float all_score=0f;
    public float map_score_f=0f;
    public int num=0;
    private void setAdapterData(List<QEHonorBean> list){


        if (StringJudge.isEmpty(list)){
            ViewTool.showToastShort(mActivity,"暂无数据");
            return;
        }
        keyValue_adapter.clear();


        Map<String ,String> map_num=new HashMap<>();
        Map<String ,String> map_score=new HashMap<>();
        all_score=0f;

        for(QEHonorBean bean:list){


            KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
            two.setId(String.valueOf(bean.getId()));
            two.setContent(bean.getReson());
            two.setLeft_title(bean.getDate());
            two.setName(bean.getStuName());
            two.setTitle(bean.getTitle());




            two.setRight_value(StringUtils.stringToGetTextJoint(
                    "%1$s\t分",
                    MathTool.stringToGetTwoToDecimals(bean.getScore())));

            switch (bean.getIspassed()){
                case 1:
                    two.setRight("已通过");

                    all_score+=bean.getScore();
                    if (StringJudge.isEmpty(map_num.get(bean.getTitle()))){
                        map_num.put(bean.getTitle(),"1");
                    }else{
                        num=MathTool.stringToInt(map_num.get(bean.getTitle()));
                        num++;
                        map_num.remove(bean.getTitle());
                        map_num.put(bean.getTitle(),String.valueOf(num));

                    }

                    if (StringJudge.isEmpty(map_score.get(bean.getTitle()))){
                        map_score.put(bean.getTitle(),MathTool.stringToGetTwoToDecimals(bean.getScore()));
                    }else{
                        map_score_f= ConvertObject.getInstance().getFloat(map_score.get(bean.getTitle()));

                        map_score_f+=bean.getScore();
                        map_score.remove(bean.getTitle());
                        map_score.put(bean.getTitle(),MathTool.stringToGetTwoToDecimals(map_score_f));
                    }

                    break;
                case 2:
                    two.setRight("已拒绝");
                    break;
                case 0:
                    two.setRight("待审核");
                    break;
            }




            two.setListValue(StringUtils.listToStringSplitCharacters(bean.getImage(),","));
            keyValue_adapter.add(two);



        }




//        if (Base.user.getUsertype().equalsIgnoreCase(Base.USER_TYPE_STU)){
        if (false){

            KeyValue pie_bean=new KeyValue(TagFinal.TYPE_CHECK);
            pie_bean.setValue(MathTool.stringToGetTwoToDecimals(all_score));
            List<CPWBean> pie_cps=new ArrayList<>();
            for (Map.Entry<String, String> entry : map_score.entrySet()) {
                System.out.println("key:" + entry.getKey() + ";value:" + entry.getValue());
                CPWBean bean=new CPWBean();
                bean.setName(entry.getKey());
                bean.setValue(entry.getValue());
                pie_cps.add(bean);
            }
            pie_bean.setCpwBeanArrayList(pie_cps);
            keyValue_adapter.add(0,pie_bean);



            List<CPWBean> cps=new ArrayList<>();
            for (Map.Entry<String, String> entry : map_num.entrySet()) {
                System.out.println("key:" + entry.getKey() + ";value:" + entry.getValue());
                CPWBean bean=new CPWBean();
                bean.setName(StringUtils.stringToGetTextJoint("%1$s获奖:%2$s次",entry.getKey(),entry.getValue()));
                bean.setValue("");
                bean.setType("");
                cps.add(bean);
            }
            CPWBean bean=new CPWBean();
            bean.setName(StringUtils.stringToGetTextJoint("总共得分:%1$s分",MathTool.stringToGetTwoToDecimals(all_score)));
            bean.setType("");
            bean.setValue("");
            cps.add(bean);


            KeyValue all=new KeyValue(TagFinal.TYPE_FLOW_TITLE);
            all.setTitle("");
            all.setValue(MathTool.stringToGetTwoToDecimals(all_score));
            all.setCpwBeanArrayList(cps);
            keyValue_adapter.add(0,all);
        }

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    getAssetsData("duty_score_add.txt");
                    break;
                case TagFinal.UI_TAG:
                    assert data!=null;
                    select_term=data.getParcelableExtra(Base.data);
                    menu_one.setText(select_term.getName());
                    break;

            }
        }
    }




    /**
     * ----------------------------retrofit-----------------------
     */



    public void setHonorState(String id,String state){

        switch (state){
            case "1":
                select_value.setRight("已通过");
                break;
            case "2":
                select_value.setRight("已拒绝");
                break;
            case "0":
                select_value.setRight("待审核");
                break;
        }




        adapter.notifyItemChanged(select_adapter_index,select_value);

    }





    //async task

    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }

    @Override
    public void doUpData(String result) {

        if (StringJudge.isEmpty(result)){
            ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
        }else{
            QEHonorRes honorRes= gson.fromJson(result,QEHonorRes.class);
            if (honorRes.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                setAdapterData(honorRes.getData());
            }else{
               ViewTool.showToastShort(mActivity,JsonParser.getErrorCode(result));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }
    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
