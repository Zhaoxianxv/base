package com.yfy.app.spannable_string;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.glide.DrawableLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.stringtool.TextToolSpan;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class SpannableStringMainActivity extends BaseActivity {
    private static final String TAG = SpannableStringMainActivity.class.getSimpleName();



    @BindView(R.id.public_text)
    TextView text;
    @BindView(R.id.public_text_one)
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


        Drawable drawable_two=DrawableLess.$tint(mActivity.getResources().getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp),Color.parseColor("#942328"));
        Drawable drawable_one=DrawableLess.$tint(mActivity.getResources().getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp),Color.parseColor("#3182c4"));
        drawable_two.setBounds(0, 0, one.getLineHeight(),one.getLineHeight());//让图片与文字对齐
        drawable_one.setBounds(0, 0, one.getLineHeight(),one.getLineHeight());//让图片与文字对齐
        ImageSpan two = new ImageSpan(drawable_two);
        ImageSpan imgSpan = new ImageSpan(drawable_one);

        SpannableStringBuilder sb=new SpannableStringBuilder();
        SpannableString sb_one = new SpannableString("0 我的成绩");
        SpannableString sb_two = new SpannableString("0 班级平均成绩");
        TextToolSpan.$spannableStringColor(sb_two,Color.parseColor("#942328"));
        TextToolSpan.$spannableStringColor(sb_one,Color.parseColor("#3182c4"));

        sb_one.setSpan(imgSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb_two.setSpan(two, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append(sb_one).append("\t").append(sb_two);
        one.setText(sb);


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
