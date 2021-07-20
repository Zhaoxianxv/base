package com.yfy.app.PEquality;

import android.os.Bundle;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.viewtools.ViewTool;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class PEQualitySuggestActivity extends BaseActivity {
    private static final String TAG = PEQualitySuggestActivity.class.getSimpleName();


    @BindView(R.id.p_e_suggest_title)
    AppCompatTextView suggest_title;

    @BindView(R.id.p_e_suggest_reason)
    AppCompatTextView suggest_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_suggest_main);
        getData();
        initSQToolbar();
        initView();

    }


    private String title,name,content;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        name=getIntent().getStringExtra(Base.name);
        content=getIntent().getStringExtra(Base.content);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);


    }

    private void initView(){
        switch (title){
            case "课堂表现":
                suggest_title.setText(StringUtils.stringToGetTextJoint("打分项：%1$s",name));
                suggest_subtitle.setText(StringUtils.stringToGetTextJoint("%1$s得分：%2$s",name,content));
                break;
                default:
                    suggest_subtitle.setText(content);
                    suggest_title.setText(name);
                    break;
        }

    }

    /**
     * ----------------------------retrofit-----------------------
     */
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
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
                    Logger.e("");
                }else{
                    ViewTool.showToastShort(mActivity,"error");
                }
            }

        }else{
            try {
                assert response.errorBody() != null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            ViewTool.showToastShort(mActivity,StringUtils.stringToGetTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
