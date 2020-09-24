package com.yfy.app.PEquality;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.process.ProcessDelDetailReq;
import com.yfy.app.net.process.ProcessGetDetailReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.DateUtils;
import com.yfy.final_tag.Logger;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.RegexUtils;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class HonorDetailActivity extends BaseActivity {
    private static final String TAG = HonorDetailActivity.class.getSimpleName();
    private DateBean dateBean;
    @Bind(R.id.public_detail_item_flow_layout)
    FlowLayout item_flow;
    @Bind(R.id.public_detail_bottom_button)
    Button bottom_button;
    //    --------top_user-------------
    @Bind(R.id.public_detail_head)
    ImageView user_head;
    @Bind(R.id.public_detail_name)
    TextView user_name;
    @Bind(R.id.public_detail_state)
    TextView user_state;
    @Bind(R.id.public_detail_tell)
    TextView user_tell;
    @Bind(R.id.public_detail_title)
    TextView detail_tile;
    //---------------top detail-----------------
    @Bind(R.id.public_detail_top_flow_layout)
    FlowLayout top_flow;
    @Bind(R.id.public_detail_top_multi)
    MultiPictureView top_multi;
    @Bind(R.id.public_item_tag)
    TextView top_tag;

    //-------------layout-------------
    @Bind(R.id.public_detail_scroll)
    ScrollView scrool_layout;
    @Bind(R.id.public_detail_bg_text)
    AppCompatTextView bgtext_view;

    @OnClick(R.id.public_detail_bg_text)
    void  setBg_Txt(){
        bgtext_view.setVisibility(View.GONE);
        getDetail();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_detail);
        getData();
        dateBean=new DateBean(DateUtils.getCurrentDateName(),DateUtils.getCurrentDateValue());

        initDialog();
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }



    private boolean is_admin=false;
    private void getData(){

        is_admin=getIntent().getExtras().getBoolean(Base.type);
        getDetail();
        initView();
        initSQtoobar("详情");

    }


    private void initSQtoobar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);

        toolbar.setOnNaviClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }



    private void initView(){
        bottom_button.setText("处理");
        bottom_button.setTextColor(ColorRgbUtil.getWhite());

        top_multi.setVisibility(View.GONE);
        top_multi.clearItem();
        top_tag.setText("审批信息");
        switch (Base.process_type){
            case "":
                break;
                default:
                    detail_tile.setText("流程详情");
                    break;
        }
    }



    private void setFlowLayoutTop(List<KeyValue> top_jz){

        LayoutInflater mInflater = LayoutInflater.from(mActivity);
        if (top_flow.getChildCount()!=0){
            top_flow.removeAllViews();
        }
        for (KeyValue bean:top_jz){
            RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.public_detail_top_item,top_flow, false);
            TextView key=layout.findViewById(R.id.seal_detail_key);
            TextView value=layout.findViewById(R.id.seal_detail_value);
            RatingBar ratingBar=layout.findViewById(R.id.seal_detail_value_star);
            LinearLayout linearLayout=layout.findViewById(R.id.public_detail_txt_layout);
            MultiPictureView multi=layout.findViewById(R.id.public_detail_layout_multi);

            key.setTextColor(ColorRgbUtil.getGrayText());
            value.setTextColor(ColorRgbUtil.getBaseText());

            key.setText(bean.getKey());
            switch (bean.getType()){
                case "star":
                    linearLayout.setVisibility(View.VISIBLE);
                    multi.setVisibility(View.GONE);
                    if (bean.getValue().equals("")){
                        ratingBar.setRating(0f);
                    }else{
                        ratingBar.setRating(ConvertObjtect.getInstance().getFloat(bean.getValue()));
                    }
                    ratingBar.setVisibility(View.VISIBLE);
                    value.setVisibility(View.GONE);
                    break;
                case "image":
                    linearLayout.setVisibility(View.GONE);
                    multi.setVisibility(View.VISIBLE);
//                    for (String s:bean.getListValue()){
//                        Logger.e(s);
//                    }
                    multi.clearItem();
                    if (StringJudge.isEmpty(bean.getListValue())){
                        multi.setVisibility(View.GONE);
                    }else{
                        multi.setVisibility(View.VISIBLE);
                        multi.setList(bean.getListValue());
                        multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
                            @Override
                            public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
                                Intent intent=new Intent(mActivity, MultPicShowActivity.class);
                                Bundle b=new Bundle();
                                b.putStringArrayList(TagFinal.ALBUM_SINGE_URI,uris);
                                b.putInt(TagFinal.ALBUM_LIST_INDEX,index);
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                default:
                    linearLayout.setVisibility(View.VISIBLE);
                    multi.setVisibility(View.GONE);
                    value.setText(bean.getValue());
                    ratingBar.setVisibility(View.GONE);
                    value.setVisibility(View.VISIBLE);
                    break;
            }
            final String content=bean.getValue().trim();
            if (RegexUtils.isMobilePhoneNumber(content)){
                SpannableString ss = new SpannableString(content);
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {

                        PermissionTools.tryTellPhone(mActivity);
                    }
                }, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                ss.setSpan(new UnderlineSpan(), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3030")), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                value.setText(ss);
                value.setMovementMethod(LinkMovementMethod.getInstance());
            }



            top_flow.addView(layout);
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case TagFinal.UI_REFRESH:
                    getDetail();
                    break;

                case TagFinal.UI_ADD:
                    getDetail();
                    break;
            }
        }
    }








    private ConfirmContentWindow confirmContentWindow;
    private void initDialog(){

        confirmContentWindow = new ConfirmContentWindow(mActivity);
        confirmContentWindow.setOnPopClickListenner(new ConfirmContentWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.pop_dialog_title:
                        break;
                    case R.id.pop_dialog_content:
                        break;
                    case R.id.pop_dialog_ok:
                        delDetail();
                        break;
                }
            }
        });
    }

    private void showDialog(String title,String content,String ok){
        if (confirmContentWindow==null)return;
        confirmContentWindow.setTitle_s(title,content,ok);
        confirmContentWindow.showAtCenter();
    }



    /**
     * ----------------------------retrofit-----------------------
     */



    public void getDetail() {
        ReqEnv evn = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        ProcessGetDetailReq req = new ProcessGetDetailReq();
        //获取参数

        reqBody.processGetDetailReq = req;
        evn.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().process_get_detail(evn);
        Logger.e(evn.toString());
        call.enqueue(this);
        showProgressDialog("");
        Logger.e(evn.toString());
    }


    public void delDetail() {
        ReqEnv evn = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        ProcessDelDetailReq req = new ProcessDelDetailReq();
        //获取参数

        reqBody.processDelDetailReq = req;
        evn.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().process_del_detail(evn);
        Logger.e(evn.toString());
        call.enqueue(this);
        showProgressDialog("");
    }


    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        ResEnv respEnvelope = response.body();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.processGetDetailRes!=null) {
                String result = b.processGetDetailRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));

            }
            if (b.processDelDetailRes!=null) {
                String result = b.processDelDetailRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));

            }

        }else{
            try {
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e(TagFinal.ZXX, "onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
            scrool_layout.setVisibility(View.GONE);
            bgtext_view.setVisibility(View.VISIBLE);
        }
    }





    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());

        dismissProgressDialog();
        scrool_layout.setVisibility(View.GONE);
        bgtext_view.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }

    //
    /**
     * -----
     */
    private long lastClickTime = 0;
    //设置拦截的时间间隔 500毫秒
    private long RESTRICT_TIME = 200;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /**
         * 拦截两次时间差小于RESTRICT_TIME
         */
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFrequentlyClick()) {
                //可以在这里给点提示
                //ToastUtils.showShort("不要频繁点击！");
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public boolean isFrequentlyClick() {
        long clickTime = System.currentTimeMillis();
        long value = clickTime - lastClickTime;
        lastClickTime = clickTime;
        return value <= RESTRICT_TIME;
    }







}
