package com.yfy.app.netHttp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yfy.app.bean.ResultRes;
import com.yfy.base.App;
import com.yfy.base.R;
import com.yfy.greendao.tool.GreenDaoManager;
import com.yfy.db.UserPreferences;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.json.JsonParser;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 *
 */
@SuppressLint("NonConstantResourceId")
public class HttpPostActivity extends AppCompatActivity implements  Callback<ResponseBody> {

    public HttpPostActivity mActivity;
    public Gson gson;

    public static float mDensity = 0;
    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;



    @Nullable
    @BindView(R.id.sq_toolbar)
    public SQToolBar toolbar;

    /**
     * 重写onCreate，从新定义Activity的初始时的生命周期
     * 包括：初始化变量-> 初始化控件-> 加载数据
     * 好处：职责单一
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        mActivity = this;
        if (mDensity == 0) {
            initDensity();
        }

        gson=new Gson();
        App.getApp().addActivity(this);
        checkWcfInit();

    }

    private void checkWcfInit() {
        String userID= UserPreferences.getInstance().getUserID();
        if (StringJudge.isEmpty(userID)){
            Base.user = null;
        }else{
            if (StringJudge.isEmpty(GreenDaoManager.getInstance().loadAllUser())){
                Base.user = null;
            }else{
                Base.user =  GreenDaoManager.getInstance().loadAllUser().get(0);
            }
        }

    }



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //初始化 ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        //初始化 ButterKnife
        ButterKnife.bind(this);
    }

    public void onResume() {
        super.onResume();
        toolbar=  findViewById(R.id.sq_toolbar);

    }

    public void onPause() {
        super.onPause();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }






    //隐藏软键盘
    public void closeKeyWord() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            view.onWindowFocusChanged(false);
            InputMethodManager inputManger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void showKeyWord() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取屏幕密度、宽高等信息
     */
    private void initDensity() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }


    /**
     * 复制粘贴
     */
    public static void copy(String content, Context context)
    {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
    /**
     * 用于判断activity是否zaiding
     */
    public boolean isActivity(){
        return true;
    }



    /**
     * 保存Subscription對象到compositeSubscription里面，当Activity销毁的时候，会在onDestory()方法调用 compositeSubscription.unsubscribe();
     */
    public CompositeSubscription compositeSubscription = new CompositeSubscription();

    public void addToCompositeSubscription(Subscription s) {
        compositeSubscription.add(s);
    }

    /**
     * ------------------------------------关于网络请求------------------------------------------
     */




    //上次点击的时间
    private long lastClickTime = 0;
    //true可以点击（距离上次点击大于500有效）
    private boolean isFastClick() {
        //当前系统时间
        long currentTime = System.currentTimeMillis();
        //是否允许点击
        boolean isAllowClick = currentTime - lastClickTime >= 500;
        lastClickTime = currentTime;
        return isAllowClick;
    }

    public HttpNetHelpInterface netHelper;
    //is progressbar
    public void setNetHelper(HttpNetHelpInterface api, Call<ResponseBody> bodyCall, boolean is,String type) {
        if (netHelper == null) {
            netHelper = api;
        }
        if (is){
            ViewTool.showProgressDialog(mActivity,"");
        }
        Logger.e(type);
        if (type.equalsIgnoreCase(ApiUrl.DIALOG)){
            if (isFastClick()){
                bodyCall.enqueue(this);
            }
        }else{
            bodyCall.enqueue(this);
        }
    }
    public void setNetHelper(HttpNetHelpInterface api, Call<ResponseBody> bodyCall, boolean is,String type,boolean ismore) {
        if (netHelper == null) {
            netHelper = api;
        }
        if (is){
            ViewTool.showProgressDialog(mActivity,"");
        }
        //控制反复加载 ismore
        if (ismore){
            if (isFastClick()){
                bodyCall.enqueue(this);
            }
        }else{
            bodyCall.enqueue(this);
        }
    }
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        List<String> names= StringUtils.listToStringSplitCharacters(call.request().url().toString().trim(), "/");
        String api_name= StringUtils.stringToGetTextJoint("%1$s/%2$s",names.get(names.size()-2),names.get(names.size()-1));
        try {
            if (response.body()==null){
                ViewTool.showToastShort(mActivity,"数据出错");
                Logger.e(StringUtils.getTextJoint("--%1$s--:%2$s",api_name,response.message()));
                if (netHelper !=null){
                    netHelper.success(ApiUrl.DATA_ERROR,"");
                }
            }else{
                String result= response.body().string();
                if (JsonParser.isSuccess(result)){
//                    Logger.e(StringUtils.getTextJoint("http:%1$s",names.get(names.size()-1)));
//                    Logger.e(StringUtils.getTextJoint("http:%1$s:\n%2$s",names.get(names.size()-1),result));
                    ResultRes res=gson.fromJson(result,ResultRes.class);
                    if (res.getResult().equalsIgnoreCase(TagFinal.FALSE)){
                        if (res.getError_code().equalsIgnoreCase(Base.login_error_code)){
                            ViewTool.showToastShort(mActivity,"登录过期");
                            Base.user=null;
                            GreenDaoManager.getInstance().clearUser();
                        }
                    }
                    if (netHelper !=null){
                        netHelper.success(api_name,result);
                    }
                }else{
                    ViewTool.dismissProgressDialog();
                    Logger.e(StringUtils.stringToGetTextJoint("%1$s:jSON错误",names.get(names.size()-1)));
                    ViewTool.showToastShort(mActivity,JsonParser.getErrorCode(result));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
//            if (netHelper !=null){
//                netHelper.success(ApiUrl.DATA_IOE,"");
//            }
            ViewTool.dismissProgressDialog();
            Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$s",names.get(names.size()-1),"没有数据:IOException"));
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        List<String> names= StringUtils.listToStringSplitCharacters(call.request().url().toString().trim(), "/");
        String api_name= StringUtils.stringToGetTextJoint("%1$s/%2$s",names.get(names.size()-2),names.get(names.size()-1));
        ViewTool.dismissProgressDialog();
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);
        Logger.e(names.get(names.size()-1));
        if (netHelper !=null){
            netHelper.fail(api_name);
        }
    }

}
