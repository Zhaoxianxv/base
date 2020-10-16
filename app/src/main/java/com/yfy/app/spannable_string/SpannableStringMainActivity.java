package com.yfy.app.spannable_string;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.widget.TextView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.stringtool.TextToolSpan;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Response;

public class SpannableStringMainActivity extends BaseActivity {
    private static final String TAG = SpannableStringMainActivity.class.getSimpleName();



    @Bind(R.id.public_text)
    TextView text;
    @Bind(R.id.public_text_one)
    TextView one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_spannable);

        initSQToolbar();

        TextToolSpan.$spannableAddIconColor(mActivity,
                text,
                StringUtils.stringToGetTextJoint("0%1$s","萨福克速度快"),
                R.drawable.rectangle_square10_gray,
                ColorRgbUtil.getBaseColor()
        );
        TextToolSpan.$spannableAddIconColor(mActivity,
                one,
                StringUtils.stringToGetTextJoint("0%1$s","萨福克速度快"),
                R.drawable.ic_check_box_outline_blank_black_24dp,
                ColorRgbUtil.getBaseColor()
        );


    }

    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("");

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
