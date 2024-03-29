package com.yfy.app.PEquality.work;

import android.os.Bundle;

import com.yfy.app.bean.StuBean;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.viewtools.ViewTool;



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



    public StuBean stuBean;
    private void getData(){
        stuBean=getIntent().getParcelableExtra(Base.data);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("学生作业完成处理打分");
    }




    /**
     * ----------------------------retrofit-----------------------
     */




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


    }




    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
