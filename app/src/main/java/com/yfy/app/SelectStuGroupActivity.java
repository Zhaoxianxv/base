package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.StuBean;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.NormalDataSaveTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * 学生多选
 */
@SuppressLint("NonConstantResourceId")
public class SelectStuGroupActivity extends HttpPostActivity implements HttpNetHelpInterface {
    private static final String TAG = SelectStuGroupActivity.class.getSimpleName();

    @BindView(R.id.radio_bt_select_all_stu_select_stu_group)
    RadioButton rbt_all_stu_state_check;

    @OnClick(R.id.relative_layout_select_all_stu_select_stu_group)
    void setAll(){
        initAdapterData("all");
    }

    @BindView(R.id.bt_select_state_stu_select_stu_group)
    Button bt_stu_group_select;
    @OnClick(R.id.bt_select_state_stu_select_stu_group)
    void setSelectStuGroup(){
        album_select.showAtCenter();
    }

    public boolean stu_bean_all_select=false;
    public boolean stu_bean_woman_select=false;
    public boolean stu_bean_man_select=false;

    public SelectStuGroupAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_stu_group_main);
        term_id= NormalDataSaveTools.getInstance().getTermBeanToGreenDao().getId();
        getData();
        initRecycler();
        initTypeDialog();
        rbt_all_stu_state_check.setChecked(false);
        bt_stu_group_select.setText("男女生选择");
    }


    public String term_id,class_id;
    public String title,type;
    public int index;
    private void getData(){
        class_id=getIntent().getStringExtra(Base.id);
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
        index=getIntent().getIntExtra(Base.index,0);
        initSQToolbar(title);
        getStu(true);
    }



    private void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        if (type.equalsIgnoreCase("select_stu")){
            toolbar.addMenuText(TagFinal.ONE_INT,"确定");
            toolbar.setOnMenuClickListener(new NoFastClickListener() {
                @Override
                public void fastMenuClick(View view, int position) {

                    List<StuBean> select_stu=new ArrayList<>();
                    List<StuBean> all_stu=adapter.getDataList();
                    for (StuBean stu:all_stu){
                        if (stu.isIs_selected())select_stu.add(stu);
                    }
                    Intent intent=new Intent();
                    intent.putParcelableArrayListExtra(Base.data,(ArrayList<? extends Parcelable>) select_stu);
                    intent.putExtra(Base.index,index);
                    intent.putParcelableArrayListExtra("all", (ArrayList<? extends Parcelable>) all_stu);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

        toolbar.setOnNaviClickListener(new NoFastClickListener() {

            @Override
            public void fastClick(View v) {
                finish();
            }
        });
    }

    public RecyclerView recyclerView;
    public void initRecycler(){
        recyclerView =  findViewById(R.id.public_recycler);
        GridLayoutManager manager = new GridLayoutManager(mActivity,4, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new SelectStuGroupAdapter(mActivity);
        recyclerView.setAdapter(adapter);




        adapter.setIntentStart(new StartIntentInterface() {
            @Override
            public void startIntentActivity(Intent intent, String type) {
                boolean is=intent.getBooleanExtra(Base.state,true);
                closeKeyWord();
                if (is)return;
                rbt_all_stu_state_check.setChecked(false);
                bt_stu_group_select.setText("男女生选择");
            }
        });
    }


    public ConfirmAlbumWindow album_select;
    private void initTypeDialog() {
        album_select = new ConfirmAlbumWindow(mActivity);
        album_select.setOne_select("全部男生");
        album_select.setTwo_select("全部女生");
        album_select.setName("男女生选择");
        album_select.setOnPopClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                switch (view.getId()) {
                    case R.id.popu_select_one:
                        initAdapterData("man");
                        break;
                    case R.id.popu_select_two:
                        initAdapterData("woman");
                        break;
                }
            }
        });
    }



    public void initAdapterData(String type){
        switch (type){
            case "all":
                if (stu_bean_all_select){
                    stu_bean_all_select=false;
                    bt_stu_group_select.setText("男女生选择");
                }else{
                    stu_bean_all_select=true;
                    bt_stu_group_select.setText("已选全班学生");
                }
                rbt_all_stu_state_check.setChecked(stu_bean_all_select);
                break;
            case "man":
                stu_bean_all_select=false;
                stu_bean_man_select=true;
                stu_bean_woman_select=false;
                rbt_all_stu_state_check.setChecked(false);
                bt_stu_group_select.setText("已选全班男生");
                break;
            case "woman":
                stu_bean_all_select=false;
                stu_bean_man_select=false;
                stu_bean_woman_select=true;
                rbt_all_stu_state_check.setChecked(false);
                bt_stu_group_select.setText("已选全班女生");
                break;
        }
        if (StringJudge.isEmpty(stuBeanList)){
            ViewTool.showToastShort(mActivity,"没有获取到学生");
        }else{
            for (StuBean bean: stuBeanList){
                switch (type){
                    case "all":
                        bean.setIs_selected(stu_bean_all_select);
                        break;
                    case "man":
                        if (bean.getStusex().equalsIgnoreCase("男")){
                            bean.setIs_selected(stu_bean_man_select);
                        }else{
                            bean.setIs_selected(stu_bean_woman_select);
                        }
                        break;
                    case "woman":
                        if (bean.getStusex().equalsIgnoreCase("女")){
                            bean.setIs_selected(stu_bean_woman_select);
                        }else{
                            bean.setIs_selected(stu_bean_man_select);
                        }
                        break;
                }
            }

            adapter.setDataList(stuBeanList);
            adapter.setLoadState(TagFinal.LOADING_END);
        }

    }



    /**
     * ----------------------------retrofit-----------------------
     */






    public List<StuBean> stuBeanList=new ArrayList<>();
    public void getStu(boolean is){



        Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().satisfaction_tea_get_stu_api(
                Base.user.getSession_key(),
                ConvertObject.getInstance().getInt(class_id),
                MathTool.stringToInt(term_id)
        );
        setNetHelper(this,bodyCall,is, ApiUrl.SATISFACTION_TEA_GET_STU);
    }

    @Override
    public void success(String api_name, String result) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        if (api_name.equalsIgnoreCase(ApiUrl.SATISFACTION_TEA_GET_STU)){
            BaseRes res=gson.fromJson(result, BaseRes.class);
            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                stuBeanList.clear();

                if (StringJudge.isEmpty(res.getIncompletestu())){
                    ViewTool.showToastShort(mActivity,"没有获取到学生");
                }else{
//                    stuBeanList.add(stu_bean_all_select);
//                    stuBeanList.add(stu_bean_man_select);
//                    stuBeanList.add(stu_bean_woman_select);
                    stuBeanList.addAll(res.getIncompletestu());
                    adapter.setDataList(stuBeanList);
                    adapter.setLoadState(TagFinal.LOADING_END);
                }
            }else{
                ViewTool.showToastShort(mActivity,res.getError_code());
            }
        }
    }


    @Override
    public void fail(String api_name) {
    }



    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
