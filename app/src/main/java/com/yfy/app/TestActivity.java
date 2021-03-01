package com.yfy.app;

import android.os.Bundle;

import com.yfy.app.bean.ResultRes;
import com.yfy.app.net.SaveImgReq;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class TestActivity extends HttpPostActivity implements HttpNetHelpInterface {
//    FlowLayout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void closeSwipeRefresh(){

    }


    public void saveImg(String flie_string){
        SaveImgReq req = new SaveImgReq();
        //获取参数

        req.setImage_file(flie_string);
        req.setFileext("jpg");


        Call<ResponseBody> bodyCall= RestClient.instance.getAccountApi().base_save_img_api(
                req.getSession_key(),
                req.getFileext(),
                req.getImage_file()
        );
        setNetHelper(this,bodyCall,false, ApiUrl.BASE_SAVE_IMG);
    }




    public void test(boolean is){
        SaveImgReq req = new SaveImgReq();

        Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().user_get_term_list_api(
                req.getSession_key()
        );
        setNetHelper(this,bodyCall,is, ApiUrl.DIALOG);


    }

    @Override
    public void success(String api_name, String result) {
        Logger.e(StringUtils.getTextJoint("%1$s:%2$s",api_name,result));
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        closeSwipeRefresh();
        if (api_name.equalsIgnoreCase(ApiUrl.USER_GET_TERM_LIST)){
            ResultRes res= gson.fromJson(result,ResultRes.class);
            if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                ViewTool.showToastShort(mActivity, R.string.success_do);
                setResult(RESULT_OK);
                finish();
            }else{
                ViewTool.showToastShort(mActivity,res.getError_code());
            }
        }
    }


    @Override
    public void fail(String api_name) {
        if (!isActivity())return;
        closeSwipeRefresh();


    }


    public void f(){
        String result="";
        String api_name="";
        ResultRes res= gson.fromJson(result,ResultRes.class);
        switch (api_name){
            case ApiUrl.USER_GET_TERM_LIST:
                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                    ViewTool.showToastShort(mActivity,R.string.success_do);
                    setResult(RESULT_OK);
                    finish();
                } else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
                break;
            case ApiUrl.DIALOG:
                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                    Logger.e("");
                } else{
                    ViewTool.showToastShort(mActivity,res.getError_code());
                }
                break;
        }

    }


}