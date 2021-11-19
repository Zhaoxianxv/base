package com.yfy.app.gold;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.glide.ZoomImage;
import com.yfy.final_tag.hander.PicAsyncTask;
import com.yfy.final_tag.hander.SaveImageAsync;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class GoldAddActivity extends HttpPostActivity implements  SaveImageAsync {
    private static final String TAG = GoldAddActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        Logger.eLogText(TAG);
        list_title= StringUtils.listToArrays(StringUtils.getResourceStringArray(mActivity,R.array.gold_type));
        dateBean=new DateBean();
        dateBean.setValue_long(System.currentTimeMillis(),true);
        initSQToolbar();
        initRecycler();
        initDialogList();
        setCPWlListBeanData();
        Timer tExit  = new Timer();
        tExit.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        cpwListBeanView.showAtCenter();
                    }
                });
            }
        }, 1000);
    }






    public List<String> list_title=new ArrayList<>();
    public String select_gold_type;
    public DateBean dateBean;


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("添加作业");
        toolbar.addMenuText(TagFinal.ONE_INT,R.string.ok);
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position) {

                addTeaItem();
            }
        });
    }



    public CPWListBeanView cpwListBeanView;
    public List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            for(String s:list_title){
                CPWBean cpwBean =new CPWBean();
                cpwBean.setName(s);
                cpwBean.setId(s);
                cpwBeans.add(cpwBean);
            }
        }
        cpwListBeanView.setDatas(cpwBeans);
    }
    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastPopClick(CPWBean cpwBean, String type) {
                cpwListBeanView.dismiss();
                select_gold_type=cpwBean.getId();
                switch (select_gold_type){
                    case "gold_enter":
                        setEnterData();
                        break;
                    case "gold_exit":
                        setExitData();
                        break;
                    case "gold_embezzle":
                        setEmbezzleData();
                        break;
                }

            }
        });
    }



    public List<KeyValue> keyValueAdapterData =new ArrayList<>();
    public GoldAddAdapter adapter;
    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new GoldAddAdapter(GoldAddActivity.this);
        recyclerView.setAdapter(adapter);


    }


    private void setEnterData(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_DATE);
        one.setTitle("日期");
        one.setRight_value(dateBean.getValue());
        one.setRight_name(dateBean.getName());
        one.setRight_key("未选择");
        one.setType("date");
        one.setId("");



        KeyValue three=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        three.setTitle("克数");
        three.setKey("");
        three.setValue("");
        three.setType("gram");

        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        two.setTitle("金额");
        two.setKey("");
        two.setValue("");
        two.setType("money");





        keyValueAdapterData.add(one);
        keyValueAdapterData.add(three);
        keyValueAdapterData.add(two);


        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    private void setExitData(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_DATE);
        one.setTitle("日期");
        one.setRight_value(dateBean.getValue());
        one.setRight_name(dateBean.getName());
        one.setRight_key("未选择");
        one.setType("date");
        one.setId("");




        KeyValue three=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        three.setTitle("克数");
        three.setKey("");
        three.setValue("");
        three.setType("gram");

        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        two.setTitle("金额");
        two.setKey("");
        two.setValue("");
        two.setType("money");





        keyValueAdapterData.add(one);
        keyValueAdapterData.add(three);
        keyValueAdapterData.add(two);


        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    private void setEmbezzleData(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_DATE);
        one.setTitle("日期");
        one.setRight_value(dateBean.getValue());
        one.setRight_name(dateBean.getName());
        one.setRight_key("未选择");
        one.setType("date");
        one.setId("");


        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        two.setTitle("金额");
        two.setKey("");
        two.setValue("");
        two.setType("money");



        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);


        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    public void addTeaItem() {
        if (select_gold_type==null) {
            cpwListBeanView.showAtCenter();
            return;
        }
        String gram_req="",date_req="",money_req="";

        List<KeyValue> adapter_list=adapter.getDataList();
        for (KeyValue bean:adapter_list){
            switch (bean.getType()){

                case "date":
                    if (StringJudge.isEmpty(bean.getRight_value())){
                        ViewTool.showToastShort(mActivity,bean.getRight_key());
                        return;
                    }
                    date_req=bean.getRight_name();
                    break;

                case "gram":
                    if (select_gold_type.equalsIgnoreCase("gold_embezzle")){
                        gram_req=TagFinal.FALSE;
                        continue;
                    }
                    if (StringJudge.isEmpty(bean.getValue())){
                        ViewTool.showToastShort(mActivity,bean.getKey());
                        return;
                    }
                    gram_req=bean.getValue();
                    break;
                case "money":
                    if (StringJudge.isEmpty(bean.getValue())){
                        ViewTool.showToastShort(mActivity,bean.getKey());
                        return;
                    }
                    money_req=bean.getValue();
                    break;

            }
        }


        KeyValueDb keyValueDb =new KeyValueDb();
        keyValueDb.setName(date_req);
        keyValueDb.setRank(gram_req);
        keyValueDb.setValue(money_req);
        NormalDataSaveTools.getInstance().saveGoldData(keyValueDb,select_gold_type);
        finish();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:
                    mTask=new PicAsyncTask(this);
                    mTask.execute(FileCamera.photo_camera);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    List<String> photo_list=new ArrayList<>();
                    for (Photo photo:photo_a){
                        photo_list.add(photo.getPath());
                    }
                    mTask=new PicAsyncTask(this);
                    mTask.execute(StringUtils.arraysToListString(photo_list));
                    break;

            }
        }
    }


    /**
     * ----------------------------retrofit-----------------------
     */




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }














    /*PicAsyncTask*/

    public int num=0;
    public PicAsyncTask mTask;

    @Override
    public List<String> doIn(String... arg0) {
        List<String> list=new ArrayList<>(Arrays.asList(arg0));
        List<String> base64_list=new ArrayList<>();
        num=0;
        for (String s:list){
            base64_list.add(ZoomImage.fileToBase64Str(s));
            num++;
        }
        return base64_list;
    }
    @Override
    public void doUpData(List<String> list) {
        if (StringJudge.isEmpty(list)){
            ViewTool.showToastShort(mActivity,"未选择图片");
            ViewTool.dismissProgressDialog();
        }else{
            for (String s:list){
            }
        }
    }
    @Override
    public void onPre() {
        ViewTool.showProgressDialog(mActivity,"");
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }













}
