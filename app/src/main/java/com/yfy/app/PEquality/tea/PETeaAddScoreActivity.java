package com.yfy.app.PEquality.tea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.SaveImgReq;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWMatchListView;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class PETeaAddScoreActivity extends BaseActivity {
    private static final String TAG = PETeaAddScoreActivity.class.getSimpleName();
    KeyValue stu;
    DateBean dateBean;

    @Bind(R.id.p_e_tea_add_score_stu_name)
    AppCompatTextView stu_name;
    @Bind(R.id.p_e_tea_add_score_name)
    AppCompatTextView project_name;
    @Bind(R.id.p_e_tea_add_score_line)
    View line;

    @Bind(R.id.p_e_tea_add_score_forward)
    CardView forward;
    @Bind(R.id.p_e_tea_add_score_next)
    CardView next;

    @Bind(R.id.p_e_tea_add_score_edit_content)
    EditText score_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_tea_add_score_main);
        dateBean=new DateBean();
        dateBean.setValue_long(System.currentTimeMillis(),true);
        stu=new KeyValue(TagFinal.TYPE_SELECT_STU);
        stu.setTitle("选择学生");
        stu.setRight_key("未选择");
        stu.setRight_value("");
        stu.setType("select_stu");
        getData();



    }

    public KeyValue data;
    public String title,type;
    private void getData(){
        data=getIntent().getParcelableExtra(Base.data);
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
        initSQToolbar(title);
        project_name.setText(title);
        List<String> list=StringUtils.listToStringSplitCharacters("张三,李四,王八,赵一,钱二,孙三,周五,吴六,郑七,米九,柴静,一,二,lenka,name,one,two,three",",");

        int i=0;
        for (String s:list){
//            KeyValue one=new KeyValue(s,Base.user.getHeadPic(),TagFinal.TYPE_ITEM);
            CPWBean bean=new CPWBean();
            if (i%2==0){
                bean.setType(TagFinal.TRUE);
                bean.setValue(String.valueOf(MathTool.getRandomInt(20,80)));

            }else{
                bean.setType(TagFinal.FALSE);
                bean.setValue("");
            }
            bean.setName(s);
            scanStateList.add(bean);
            if (i==0){
                forward.setCardBackgroundColor(ColorRgbUtil.getGray());
                score_edit.setText(bean.getValue());
                stu_name.setText(bean.getName());
            }
            i++;
        }



    }
    private void initSQToolbar(final String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenu(TagFinal.ONE_INT,R.drawable.ic_parent_head,ColorRgbUtil.getWhite());
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                String name=stu_name.getText().toString().trim();
                for (CPWBean bean:scanStateList){
                    if (bean.getName().equalsIgnoreCase(name)){
                        bean.setIs_select(true);
                    }else{
                        bean.setIs_select(false);
                    }
                }
                CPWMatchListView confirmPopWindow=new CPWMatchListView(mActivity);
                confirmPopWindow.setAnimationStyle(R.style.pop_window_anim_style);
                confirmPopWindow.setOnPopClickListener(new CPWMatchListView.OnPopClickListener() {
                    @Override
                    public void onClick(String type, CPWBean bean) {
                        stu_name.setText(bean.getName());
                        score_edit.setText(bean.getValue());
                    }
                });
                confirmPopWindow.showAtBottom(line,scanStateList);
            }
        });
    }



    private List<CPWBean> scanStateList=new ArrayList<>();


    @OnClick(R.id.p_e_tea_add_score_stu_name)
    void setScore(){

    }




    @OnClick(R.id.p_e_tea_add_score_forward)
    void setForWard(){
        String name=stu_name.getText().toString().trim();
        for (int i=0;i<scanStateList.size();i++){
            if (scanStateList.get(i).getName().equalsIgnoreCase(name))dataIndex=i;
        }
        if (dataIndex==0){
        }else{
            stu_name.setText(scanStateList.get(dataIndex-1).getName());
            score_edit.setText(scanStateList.get(dataIndex-1).getValue());
        }
        refreshButton();
    }

    @OnClick(R.id.p_e_tea_add_score_next)
    void setNext(){
        String name=stu_name.getText().toString().trim();
        for (int i=0;i<scanStateList.size();i++){
            if (scanStateList.get(i).getName().equalsIgnoreCase(name))dataIndex=i;
        }
        if (dataIndex==scanStateList.size()-1){

        }else{
            stu_name.setText(scanStateList.get(dataIndex+1).getName());
            score_edit.setText(scanStateList.get(dataIndex+1).getValue());
        }
        refreshButton();
    }
    private int dataIndex;
    @OnClick(R.id.p_e_tea_add_score_submit)
    void setSubmit(){
        closeKeyWord();
        String score=score_edit.getText().toString().trim();
        if (score.equalsIgnoreCase("")){
            ViewTool.showToastShort(mActivity,StringUtils.stringToGetTextJoint("请输入%1$s分数",title
            ));
            return;
        }

        showProgressDialog("");
        forward.postDelayed(new Runnable() {
            @Override
            public void run() {
                String name=stu_name.getText().toString().trim();
                for (int i=0;i<scanStateList.size();i++){
                    if (scanStateList.get(i).getName().equalsIgnoreCase(name))dataIndex=i;
                }
                if (dataIndex==scanStateList.size()-1){
                }else{
                    stu_name.setText(scanStateList.get(dataIndex+1).getName());
                    score_edit.setText(scanStateList.get(dataIndex+1).getValue());
                }
                refreshButton();
                dismissProgressDialog();
            }
        },1000);

    }




    public void refreshButton(){
        String name=stu_name.getText().toString().trim();
        for (int i=0;i<scanStateList.size();i++){
            if (scanStateList.get(i).getName().equalsIgnoreCase(name))dataIndex=i;
        }

        if (dataIndex==0){
            ViewTool.showToastShort(mActivity,"已经是第一个学生了");
            forward.setCardBackgroundColor(ColorRgbUtil.getGray());
//            ViewTool.alterGradientDrawableColor(forward,ColorRgbUtil.getGray());
        }else{
            forward.setCardBackgroundColor(ColorRgbUtil.getBaseColor());
//            ViewTool.alterGradientDrawableColor(forward,ColorRgbUtil.getBaseColor());
        }
        if (dataIndex==scanStateList.size()-1){
            next.setCardBackgroundColor(ColorRgbUtil.getGray());
            ViewTool.showToastShort(mActivity,"最后一个学生了");
//            ViewTool.alterGradientDrawableColor(next,ColorRgbUtil.getGray());
        }else{
            next.setCardBackgroundColor(ColorRgbUtil.getBaseColor());
//            ViewTool.alterGradientDrawableColor(next,ColorRgbUtil.getBaseColor());
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:

                    break;
                case TagFinal.PHOTO_ALBUM:

                    break;



            }
        }
    }


    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        req.setSession_key(Base.user.getSession_key());
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_term_list_api(env);
        call.enqueue(this);
    }


    private void saveImg(String flie_string){
        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        SaveImgReq req = new SaveImgReq();
        //获取参数
        req.setImage_file(flie_string);
        req.setFileext("jpg");
        reqBody.saveImgReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().base_save_img(reqEnv);
        call.enqueue(this);
    }
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
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    toastShow("error");
                }
            }
            if (b.saveImgRes!=null) {
                String result = b.saveImgRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result,BaseRes.class );
//                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                    List<String> list_c=multi_bean.getListValue();
//                    list_c.add(Base.RETROFIT_URI+res.getImg());
//                    adapter.notifyItemChanged(position_index,multi_bean);
//                }else{
//                    toastShow(res.getError_code());
//                }
//                if (num==1){
//                    dismissProgressDialog();
//                }else{
//                    num--;
//                }
            }
        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.stringToGetTextJoint("数据错误:%1$d",response.code()));
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
