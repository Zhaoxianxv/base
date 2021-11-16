package com.yfy.app.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.yfy.app.WebActivity;
import com.yfy.app.login.bean.UserRes;
import com.yfy.app.net.login.UserLoginReq;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.R;
import com.yfy.keyboard.password.KeyboardTouchListener;
import com.yfy.keyboard.password.KeyboardUtil;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.GreenDaoManager;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.glide.RxCaptcha;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.rsa.AES;
import com.yfy.greendao.User;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.yfy.final_tag.glide.RxCaptcha.TYPE.NUMBER;


/**
 * @version 1.0
 */
@SuppressLint("NonConstantResourceId")
public class LoginActivity extends HttpPostActivity implements HttpNetHelpInterface {

	private final static String TAG =LoginActivity.class.getSimpleName();



	@BindView(R.id.login_phone)
	EditText account;
	@BindView(R.id.login_password)
	EditText password;
	@BindView(R.id.login_code)
	EditText code;
	@BindView(R.id.login_code_image)
	ImageView code_icon;
	private String edit_name = "";
	private String edit_password = "";
	public String edit_code = "";
	private String type = "";
	private String code_s="";
	private RxCaptcha rxCaptcha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		initSQToolbar();
		initDialog();
		rxCaptcha=RxCaptcha.build();
		rxCaptcha.backColor(R.color.exchange_able_4)
				.codeLength(4)
				.fontSize(60)
				.lineNumber(2)
				.size(200, 70)
				.type(NUMBER)
				.into(code_icon);
		code_s=rxCaptcha.getCode();
		initMoveKeyBoard();
		initPrivateProtocol();
	}

	@OnClick(R.id.login_code_image)
	void setImage(){
		rxCaptcha=RxCaptcha.build();
		rxCaptcha.backColor(R.color.exchange_able_4)
				.codeLength(4)
				.fontSize(60)
				.lineNumber(2)
				.size(200, 70)
				.type(NUMBER)
				.into(code_icon);
		code_s=rxCaptcha.getCode();
	}

	private void initSQToolbar(){
		assert toolbar!=null;
		toolbar.setTitle("登录");
	}
	ConfirmAlbumWindow album_select;
	private void initDialog() {
		album_select = new ConfirmAlbumWindow(mActivity);
		album_select.setOne_select("教师");
		album_select.setTwo_select("学生");
		album_select.setName("请选择账号类型");
		album_select.setOnPopClickListener(new NoFastClickListener() {
			@Override
			public void fastPopClick(View view) {
				switch (view.getId()) {
					case R.id.popu_select_one:
						type = "2";
						ViewTool.showProgressDialog(mActivity,"");
						getToken("login");
						break;
					case R.id.popu_select_two:
						type = "1";
						ViewTool.showProgressDialog(mActivity,"");
						getToken("login");
						break;
				}

			}
		});
	}

	@OnClick(R.id.login_button)
	void setlogin(){

		if (isCanSend()){
			album_select.showAtBottom();
		}
	}



	@OnClick(R.id.login_forget_password)
	void setForget(){
		Intent intent=new Intent(mActivity,ResetPasswordActivity.class);
		startActivity(intent);
	}







	private boolean isCanSend() {
		closeKeyWord();
		edit_name = account.getText().toString().trim();
		edit_password = password.getText().toString().trim();
		edit_code = code.getText().toString().trim();
		if (StringJudge.isEmpty(edit_code)) {
			ViewTool.showToastShort(mActivity,R.string.please_edit_code);
			return false;
		}
		if (!edit_code.equals(code_s)){
			ViewTool.showToastShort(mActivity,R.string.please_edit_yse_code);
			return false;
		}
		if (StringJudge.isEmpty(edit_name)) {
			ViewTool.showToastShort(mActivity,R.string.please_edit_account);
			return false;
		}
		if (StringJudge.isEmpty(edit_password)) {
			ViewTool.showToastShort(mActivity,R.string.please_edit_password);
			return false;
		}
		keyboardUtil.hideKeyboardLayout();
		return isFirstProtocolLogin();

	}


    /**
	 * ----------------------------retrofit-----------------------
	 */





	public void getToken(String s) {
		Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().base_get_token_api(
				"SRTghhy",
				"DS$dfs@$$%");
		setNetHelper(this,bodyCall,true, ApiUrl.USER_GET_TOKEN);

	}
	private void login_(String token) {
		//登陆时传的Constants.APP_ID：
		String user_name,password_name;
		String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
		user_name=AES.decrypTToString(edit_name);
		password_name=AES.decrypTToString(edit_password);

		UserLoginReq req = new UserLoginReq();
		//获取参数
		req.setUsername(user_name);
		req.setPassword(password_name);
		req.setRole_id(type);
		req.setAppid(ANDROID_ID);
		req.setFirsttoken(token);
		Logger.eLogText(req.toString());

		Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().base_login_api(
				req.getUsername(),
				req.getPassword(),
				req.getRole_id(),
				req.getAppid(),
				req.getAndios(),
				req.getFirsttoken()
		);
		setNetHelper(this,bodyCall,false,ApiUrl.USER_LOGIN);

	}








	@Override
	public void success(String api_name, String result) {
		if (!isActivity())return;
		UserRes res= gson.fromJson(result,UserRes.class);
		switch (api_name){
			case ApiUrl.USER_GET_TOKEN:
				Logger.eLogText(result);
				if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
					login_(res.getToken());
				}else{
					ViewTool.dismissProgressDialog();
					ViewTool.showToastShort(mActivity,res.getError_code());
				}
				break;
			case ApiUrl.USER_LOGIN:
				Logger.eLogText(result);
				if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)) {
					ViewTool.dismissProgressDialog();
					saveUser(res,TagFinal.FALSE);
				} else {
					ViewTool.dismissProgressDialog();
					ViewTool.showToastShort(mActivity,res.getError_code());
				}
				break;

			default:
				break;
		}
	}


	@Override
	public void fail(String api_name) {
		if (!isActivity())return;
		Logger.eLogText("fail");
	}


	@Override
	public boolean isActivity() {
		return AppLess.isTopActivy(TAG);
	}

	private void saveUser(UserRes res, String login_type){
		UserPreferences.getInstance().saveFirstLogin(false);//保存已经登录一次


		ViewTool.showToastShort(mActivity,"登录成功");
		GreenDaoManager.getInstance().clearUser();
		User user=new User();
		user.setSession_key(res.getSession_key());
		user.setHeadPic(res.getHeadPic());
		user.setFxid(res.getFxid());
		user.setName(res.getName());
		user.setPwd(edit_password);
		user.setUsertype( type.equals("1")? Base.USER_TYPE_STU : Base.USER_TYPE_TEA);
		user.setIdU(res.getId());
		user.setClassid(res.getClassid());
		user.setUsername(res.getUsername());
		user.setRightlist(res.getRightlist());
		user.setIsDuplication(login_type);
		Base.user=user;
		UserPreferences.getInstance().saveUserID(user.getIdU());
		GreenDaoManager.getInstance().saveUser(user);

		setResult(RESULT_OK);
		finish();
	}




	//password edit keyboard
	@BindView(R.id.all_ed)
	LinearLayout rootView;
	@BindView(R.id.sv_main)
	ScrollView scrollView;
	public KeyboardUtil keyboardUtil;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ) {
			if(keyboardUtil.isShow){
				keyboardUtil.hideSystemKeyBoard();
				keyboardUtil.hideAllKeyBoard();
				keyboardUtil.hideKeyboardLayout();
			}else {
				return super.onKeyDown(keyCode, event);
			}

			return false;
		} else
			return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("ClickableViewAccessibility")
	private void initMoveKeyBoard() {
		keyboardUtil = new KeyboardUtil(this, rootView, scrollView);
		keyboardUtil.setOtherEdittext(account,code);
		// monitor the KeyBarod state
		keyboardUtil.setKeyBoardStateChangeListener(new KeyBoardStateListener());
		// monitor the finish or next Key
		keyboardUtil.setInputOverListener(new inputOverListener());
		password.setOnTouchListener(new KeyboardTouchListener(keyboardUtil, KeyboardUtil.INPUTTYPE_ABC, -1));
	}

	class KeyBoardStateListener implements KeyboardUtil.KeyBoardStateChangeListener {

		@Override
		public void KeyBoardStateChange(int state, EditText editText) {
//            System.out.println("state" + state);
//            System.out.println("editText" + editText.getText().toString());
		}
	}

	class inputOverListener implements KeyboardUtil.InputFinishListener {

		@Override
		public void inputHasOver(int onclickType, EditText editText) {
//            System.out.println("onclickType" + onclickType);
//            System.out.println("editText" + editText.getText().toString());
		}
	}



	/**
	 * ---------------隐私协议书 Protocol
	 */

	public boolean is_protocol=false;
	@BindView(R.id.relative_layout_protocol_login_main)
	RelativeLayout layout_protocol_login_main;
	@OnClick(R.id.relative_layout_protocol_login_main)
	void setSelectProtocol(){
		is_protocol=!is_protocol;
		rbt_check_flag_login.setChecked(is_protocol);

	}
	@BindView(R.id.radio_bt_protocol_login_main)
	RadioButton rbt_check_flag_login;


	@OnClick(R.id.version_private)
	void setPrivate(){

		Intent intent = new Intent(mActivity, WebActivity.class);
		Bundle b = new Bundle();
		b.putString(TagFinal.URI_TAG, Base.HUA_WEI_PRIVATE);
		b.putString(TagFinal.TITLE_TAG, "隐私声明");
		intent.putExtras(b);
		startActivity(intent);
	}
	@OnClick(R.id.version_agreement)
	void setAgreement(){

		Intent intent = new Intent(mActivity, WebActivity.class);
		Bundle b = new Bundle();
		b.putString(TagFinal.URI_TAG, Base.HUA_WEI_AGREEMENT);
		b.putString(TagFinal.TITLE_TAG, "用户协议");
		intent.putExtras(b);
		startActivity(intent);
	}

	//初始化界面
	public void initPrivateProtocol(){
		if (UserPreferences.getInstance().getIsFirstLogin()){
			layout_protocol_login_main.setVisibility(View.VISIBLE);
			rbt_check_flag_login.setChecked(false);
		}else{
			layout_protocol_login_main.setVisibility(View.GONE);

		}
	}
	//判断app第一次登录隐私阅读条件  false 未阅读
	public boolean isFirstProtocolLogin(){
		if (UserPreferences.getInstance().getIsFirstLogin()){
			if (rbt_check_flag_login.isChecked()){
				return true;
			}else{
				ViewTool.showToastShort(mActivity,"请点击已阅读用户条例和隐私协议！");
				return false;
			}
		}else{
			return true;
		}

	}

}
