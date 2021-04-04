package com.yfy.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yfy.app.PEquality.PEQualityMainTestActivity;
import com.yfy.app.PEquality.tea.PETeaMainActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.chart.BarChartActivity;
import com.yfy.app.drawableBg.DrawableBgActivity;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.app.duty_evaluate.PracticeAddActivity;
import com.yfy.app.httppost.HttpPostMainActivity;
import com.yfy.app.httppost.retrofitclient.RetrofitMainActivity;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.spannable_string.SpannableStringMainActivity;
import com.yfy.app.voice.VoiceMainActivity;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.greendao.view.TypeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SelectedModeTypeActivity extends BaseActivity {
    private static final String TAG = SelectedModeTypeActivity.class.getSimpleName();

    private SelectedModeTypeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        initRecycler();
        initSQToolbar();
        setAdapterData();
    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("DOME");
        toolbar.cancelNavi();


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
        adapter=new SelectedModeTypeAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setModeItem(new SelectedModeTypeAdapter.ModeItem() {
            @Override
            public void modeItem(String name) {
                Intent intent;
                switch (name){
                    case "TypeActivity":
                        startActivity(new Intent(mActivity, TypeActivity.class));
                        break;
                    case "CameraActivity":
                        intent=new Intent(mActivity, PracticeAddActivity.class);
                        startActivityForResult(intent,TagFinal.UI_ADD);                        break;
                    case "视频":
                        startActivity(new Intent(mActivity,BarChartActivity.class));
                        break;
                    case "drawable":
                        startActivity(new Intent(mActivity,DrawableBgActivity.class));
                        break;
                    case "SpannableStringMainActivity":
                        startActivity(new Intent(mActivity,SpannableStringMainActivity.class));
                        break;
                    case "BarChartActivity":
                        startActivity(new Intent(mActivity,BarChartActivity.class));

                        break;
                    case "Voice":
                        startActivity(new Intent(mActivity,VoiceMainActivity.class));
                        break;
                    case "Version":
                        startActivity(new Intent(mActivity,VersionDetailActivity.class));
                        break;
                    case "HttpPostMainActivity":
                        startActivity(new Intent(mActivity,HttpPostMainActivity.class));
                        break;
                    case "RetrofitMainActivity":
                        startActivity(new Intent(mActivity,RetrofitMainActivity.class));
                        break;
                    case "体育素质(tea)":
                        intent=new Intent(mActivity, PETeaMainActivity.class);
                        intent.putExtra(Base.title,"体育素质评价");
                        startActivity(intent);
                        break;
                    case "体育素质(stu)":
                        startActivity(new Intent(mActivity, PEQualityMainTestActivity.class));
                        break;
                    case "德育评价(tea)":
                        intent=new Intent(mActivity,SelectedClassActivity.class);
                        intent.putExtra(Base.mode_type,"duty_evaluate");
                        startActivity(intent);
                        break;
                    case "德育评价(stu)":
                        intent=new Intent(mActivity, DutyEvaluateStuMainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

    }
    public List<String> list=new ArrayList<>();
    public List<KeyValue> keyValue_adapter=new ArrayList<>();
    private void setAdapterData(){
        list.add("TypeActivity");
        list.add("CameraActivity");
        list.add("视频");
        list.add("Version");
        list.add("Voice");
        list.add("drawable");
        list.add("SpannableStringMainActivity");
        list.add("BarChartActivity");

        list.add("GRpcMainActivity");
        list.add("HttpPostMainActivity");
        list.add("RetrofitMainActivity");
        list.add("体育素质(tea)");
        list.add("体育素质(stu)");
        list.add("德育评价(tea)");
        list.add("德育评价(stu)");

        keyValue_adapter.clear();
        for (String s:list){
            KeyValue one=new KeyValue(TagFinal.TYPE_ITEM);
            one.setTitle(s);
            keyValue_adapter.add(one);
        }

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
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
                    Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    toastShow("error");
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
