package com.yfy.app.PEquality;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.yfy.app.PEquality.adapter.PEHonorMainAdapter;
import com.yfy.app.PEquality.bean.QEHonorBean;
import com.yfy.app.PEquality.bean.QEHonorRes;
import com.yfy.app.SelectStuActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.json.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

public class PEHonorMainActivity extends BaseActivity implements AssetsGetFileData {
    private static final String TAG = PEHonorMainActivity.class.getSimpleName();

    public PEHonorMainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        getData();
        initRecycler();
        initSQToolbar();
        getAssetsData("duty_score_admin.txt");
    }


    private String title,type;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        if (type.equalsIgnoreCase(TagFinal.TRUE)){
            toolbar.addMenuText(TagFinal.ONE_INT,"选择学生");
        }else{
            toolbar.addMenuText(TagFinal.ONE_INT,"添加");
        }

        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                Intent intent;
                if (type.equalsIgnoreCase(TagFinal.TRUE)){
                    intent=new Intent(mActivity,SelectStuActivity.class);
                    intent.putExtra(Base.index,1);
                    intent.putExtra(Base.title,"选择学生");
                    intent.putExtra(Base.type,title);
                    startActivity(intent);

                }else{
                    intent=new Intent(mActivity,PEHonorAddActivity.class);
                    startActivityForResult(intent,TagFinal.UI_ADD);
                }


            }
        });

    }
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter=new PEHonorMainAdapter(this);
        recyclerView.setAdapter(adapter);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
//                    setAdapterData();
                    break;
            }
        }
    }

    private void setAdapterData(List<QEHonorBean> list){


        if (StringJudge.isEmpty(list)){
            ViewTool.showToastShort(mActivity,"暂无数据");
            return;
        }
        keyValue_adapter.clear();



        for(QEHonorBean bean:list){

            KeyValue two=new KeyValue(TagFinal.TYPE_ITEM);
            two.setId(String.valueOf(bean.getId()));
            two.setContent(bean.getReson());
            two.setLeft_title(bean.getDate());
            two.setName(bean.getStuName());
            two.setTitle(bean.getTitle());
            two.setType(type);

            switch (bean.getIspassed()){
                case 1:
                    two.setRight("已通过");
                    break;
                case 2:
                    two.setRight("已拒绝");
                    break;
                default:
                    two.setRight("待审核");
                    break;
            }
            two.setRight_value(StringUtils.stringToGetTextJoint(
                    "%1$s\t分",
                    MathTool.stringToGetTwoToDecimals(bean.getScore())
            ));
            two.setListValue(StringUtils.listToStringSplitCharacters(bean.getImage(),","));
            keyValue_adapter.add(two);





        }





        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }

    /**
     * ----------------------------retrofit-----------------------
     */


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
                ViewTool.showToastShort(mActivity, JsonParser.getErrorCode(result));
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
