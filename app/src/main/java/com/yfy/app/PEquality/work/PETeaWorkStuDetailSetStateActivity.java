package com.yfy.app.PEquality.work;

import android.content.Intent;
import android.os.Bundle;

import com.yfy.app.bean.TermBean;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.List;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;


public class PETeaWorkStuDetailSetStateActivity extends HttpPostActivity implements HttpNetHelpInterface {
    private static final String TAG = PETeaWorkStuDetailSetStateActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        Logger.eLogText(TAG);
        getData();
        initSQToolbar();
    }



    public TermBean select_term;
    public CPWBean classCPWBean;
    private void getData(){
        classCPWBean=getIntent().getParcelableExtra(Base.class_bean);
        select_term=getIntent().getParcelableExtra(Base.term_bean);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("学生作业完成处理打分");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_ADD:
                    break;
                case -1:
                    break;
            }
        }
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    public void getStuScoreItem() {




    }



    @Override
    public void success(String api_name, String result) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
//        if (api_name.equalsIgnoreCase(ApiUrl.QUALITY_TEA_GET_SCORE_ITEM_LIST)){
//            QualityRes res=gson.fromJson(result, QualityRes.class);
//            Logger.eLogText(StringUtils.getTextJoint("%1$s:%2$s",api_name,result));
//            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                List<QualityStu> list=res.getStulist();
//            }else{
//                ViewTool.showToastShort(mActivity,res.getError_code());
//            }
//        }
    }


    @Override
    public void fail(String api_name) {
        if (!isActivity())return;


    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
