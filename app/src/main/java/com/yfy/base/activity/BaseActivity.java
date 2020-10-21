package com.yfy.base.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.google.gson.Gson;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.BaseGetTokenReq;
import com.yfy.base.App;
import com.yfy.base.R;
import com.yfy.greendao.tool.GreenDaoManager;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.base.Base;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.view.SQToolBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Daniel on 2016/3/8.
 * <p/>
 * 说明：
 * 1、封裝Toolbar，我參考了v4.Toolbar, 重写写了一个Toolbar(SQToolbar类)，用法很简单。
 * <p/>
 * 2、封装了ProgressDialog的显示和隐藏
 * <p/>
 * 3、重写了setContentView()方法，并在里面添加ButterKnife,这样就不用在每个子Activity里调用ButterKnife.bind(this)了
 * <p/>
 * 4、还封装了onPageBack()
 * <p/>
 * 5、封装了toast()方法
 * 注：依赖： compile 'com.jakewharton:butterknife:7.0.1'
 */
public class BaseActivity extends AppCompatActivity implements Callback<ResEnv> {

    public ProgressDialog dialog;
    public BaseActivity mActivity;
    public Gson gson;

    public static float mDensity = 0;
    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;


    @Nullable
    @Bind(R.id.sq_toolbar)
    public SQToolBar toolbar;

    /**
     * 重写onCreate，从新定义Activity的初始时的生命周期
     * <p/>
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
        String userID=UserPreferences.getInstance().getUserID();
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

    /**
     * 保存Subscription對象到compositeSubscription里面，
     * 当Activity销毁的时候，会在onDestory()方法调用 compositeSubscription.unsubscribe();
     */


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onPageBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }






    /**
     * 全局Toast,log,ProgressDialog
     */

    public void toastShow(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void toastShow(@StringRes int text) {
       toastShow(mActivity.getString(text));
    }
    // 显示一个ProgressDialog
    public void showProgressDialog(String title, String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setCancelable(false);
        if (title != null && !title.equals("")) {
            dialog.setTitle(title);
        }
        if (message != null && !message.equals("")) {
            dialog.setMessage(message);
        }
        dialog.show();
    }
    public void showProgressDialog(String message) {
        showProgressDialog(null, message);
    }
    public void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
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
     * 尽量使用onPageBack()方法来销毁页面，不要直接调用finish()，
     * 这种方式的好处有2种：1、方便添加退出动画。2、方便做Umeng统计。
     */
    public void onPageBack() {
        finish();
    }

//
//    /**
//     * -----
//     */
//    private long lastClickTime = 0;
//    //设置拦截的时间间隔 500毫秒
//    private long RESTRICT_TIME = 500;
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        /**
//         * 拦截两次时间差小于RESTRICT_TIME
//         */
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isFrequentlyClick()) {
//                //可以在这里给点提示
//                //ToastUtils.showShort("不要频繁点击！");
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//    public boolean isFrequentlyClick() {
//        long clickTime = System.currentTimeMillis();
//        long value = clickTime - lastClickTime;
//        lastClickTime = clickTime;
//        return value <= RESTRICT_TIME;
//    }





    protected String token="";

    public void getToken(String s) {
        token=s;
        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        BaseGetTokenReq req = new BaseGetTokenReq();
        //获取参数

        reqBody.baseGetTookenReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().base_get_tooken(reqEnv);
        call.enqueue(this);
        Logger.e(s);
        Logger.e(reqEnv.toString());

    }
//
//    public void login(String token) {
//        if (Base.user==null)return;
//        //登陆时传的Constants.APP_ID：
//        String apikey=JPushInterface.getRegistrationID(mActivity);
//        if(apikey==null){
//            apikey="";
//        }
//        String user_name,password_name;
//        user_name=AES.decrypTToString(Base.user.getName());
//        password_name=AES.decrypTToString(Base.user.getPwd());
//
//        ReqEnv reqEnv = new ReqEnv();
//        ReqBody reqBody = new ReqBody();
//        UserLoginReq req = new UserLoginReq();
//        //获取参数
//        req.setUsername(user_name);
//        req.setPassword(password_name);
//        req.setRole_id(Base.user.getUsertype().equals(TagFinal.USER_TYPE_TEA)?"2":"1");
//        req.setAppid(apikey);
//        req.setFirsttoken(token);
//
//        reqBody.userLoginReq = req;
//        reqEnv.body = reqBody;
//        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_login(reqEnv);
//        call.enqueue(this);
//        Logger.e(reqEnv.toString());
//
//    }
//    public void duplicationLogin(String token) {
//        //登陆时传的Constants.APP_ID：
//        if (Base.user==null)return;
//        String apikey=JPushInterface.getRegistrationID(mActivity);
//        if(apikey==null){
//            apikey="";
//        }
//        String user_name,password_name;
//
//
//        user_name=AES.decrypTToString(Base.user.getName());
//        password_name=AES.decrypTToString(Base.user.getPwd());
//
//
//        ReqEnv reqEnv = new ReqEnv();
//        ReqBody reqBody = new ReqBody();
//        UserDuplicationLoginReq req = new UserDuplicationLoginReq();
//        //获取参数
//
//        req.setUsername(user_name);
//        req.setPassword(password_name);
//        req.setRole_id(Base.user.getUsertype().equals(TagFinal.USER_TYPE_TEA)?"2":"1");
//        req.setAppid(apikey);
//        req.setFirsttoken(token);
//        req.setStuid(ConvertObjtect.getInstance().getInt(Base.user.getIdU()));
//
//        reqBody.userDuplicationLoginReq = req;
//        reqEnv.body = reqBody;
//        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_duplication_login(reqEnv);
//        call.enqueue(this);
//        Logger.e(reqEnv.toString());
//    }


    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {


    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {

    }

}
