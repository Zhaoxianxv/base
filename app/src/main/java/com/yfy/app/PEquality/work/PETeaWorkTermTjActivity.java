package com.yfy.app.PEquality.work;

import android.os.Bundle;

import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.viewtools.ViewTool;


/**
 * --------学生作业统计一学期一次：按学生作业完成排名
 */
public class PETeaWorkTermTjActivity extends HttpPostActivity implements HttpNetHelpInterface {
    private static final String TAG = PETeaWorkTermTjActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        Logger.eLogText(TAG);
        getData();
        initSQToolbar();
    }



    public CPWBean classCPWBean;

    private void getData(){
        classCPWBean=getIntent().getParcelableExtra(Base.class_bean);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("作业统计");
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
