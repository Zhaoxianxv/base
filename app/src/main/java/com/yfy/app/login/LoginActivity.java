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
import android.widget.ScrollView;

import com.yfy.app.bean.BaseRes;
import com.yfy.app.login.bean.Stunlist;
import com.yfy.app.login.bean.UserRes;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.login.UserDuplicationLoginReq;
import com.yfy.app.net.login.UserGetDuplicationListReq;
import com.yfy.app.net.login.UserLoginReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.keyboard.password.KeyboardTouchListener;
import com.yfy.final_tag.keyboard.password.KeyboardUtil;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.tool.GreenDaoManager;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.glide.RxCaptcha;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.rsa.AES;
import com.yfy.greendao.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static com.yfy.final_tag.glide.RxCaptcha.TYPE.NUMBER;


/**
 * @version 1.0
 */
@SuppressLint("NonConstantResourceId")
public class LoginActivity extends BaseActivity {

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
//		initMoveKeyBoard();
		initDialogList();
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




	public CPWListBeanView cpwListBeanView;
	List<CPWBean> cpwBeans=new ArrayList<>();
	private void setCPWlListBeanData(List<Stunlist> stunlist){
		if (StringJudge.isEmpty(stunlist)){
			ViewTool.showToastShort(mActivity,R.string.success_not_details);
			return;
		}else{
			cpwBeans.clear();
			for(Stunlist opear:stunlist){
				CPWBean cpwBean =new CPWBean();
				cpwBean.setName(StringUtils.stringToGetTextJoint("%1$s(%2$s)",opear.getStuname(),opear.getClassname()));
				cpwBean.setId(opear.getStuid());
				cpwBeans.add(cpwBean);
			}
		}

		closeKeyWord();
		cpwListBeanView.setDatas(cpwBeans);
		cpwListBeanView.showAtCenter();

	}
	private void initDialogList(){
		cpwListBeanView = new CPWListBeanView(mActivity);
		cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
			@Override
			public void fastPopClick(CPWBean cpwBean, String type) {
				duplicationLogin_(cpwBean.getId());
				cpwListBeanView.dismiss();
			}
		});
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
//		keyboardUtil.hideKeyboardLayout();
		return true;
	}


    /**
	 * ----------------------------retrofit-----------------------
	 */




	private void getDuplicationList() {
		ReqEnv reqEnv = new ReqEnv();
		ReqBody reqBody = new ReqBody();
		UserGetDuplicationListReq req = new UserGetDuplicationListReq();
		String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
		//获取参数
		String firstTo=AES.decrypTToString(edit_name+edit_password+type+ANDROID_ID+"and");
		req.setUsername(edit_name);
		req.setPassword(edit_password);
		req.setRole_id(type);
		req.setAppid(ANDROID_ID);
		req.setFirsttoken(firstTo);


		reqBody.userGetDuplicationListReq = req;
		reqEnv.body = reqBody;
		Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_duplication_user(reqEnv);
		call.enqueue(this);
		ViewTool.showProgressDialog(mActivity,"");
		Logger.e(reqEnv.toString());
	}
	private void duplicationLogin_(String stu_id) {
		//登陆时传的Constants.APP_ID：
		ReqEnv reqEnv = new ReqEnv();
		ReqBody reqBody = new ReqBody();
		UserDuplicationLoginReq req = new UserDuplicationLoginReq();
		//获取参数
		String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
		String firstTo=AES.decrypTToString(edit_name+edit_password+type+ANDROID_ID+"and");

		req.setUsername(edit_name);
		req.setPassword(edit_password);
		req.setStuid(ConvertObject.getInstance().getInt(stu_id));
		req.setRole_id(type);
		req.setAppid(ANDROID_ID);
		req.setAndios("and");
		req.setFirsttoken(firstTo);


		reqBody.userDuplicationLoginReq = req;
		reqEnv.body = reqBody;
		Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_duplication_login(reqEnv);
		call.enqueue(this);
	}
	private void login_(String token) {
		//登陆时传的Constants.APP_ID：
//		String apikey=JPushInterface.getRegistrationID(mActivity);
//		if(apikey==null){
//			apikey="";
//		}
		String user_name,password_name;
		String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);

		user_name=AES.decrypTToString(edit_name);
		password_name=AES.decrypTToString(edit_password);

		ReqEnv reqEnv = new ReqEnv();
		ReqBody reqBody = new ReqBody();
		UserLoginReq req = new UserLoginReq();
		//获取参数
		req.setUsername(user_name);
		req.setPassword(password_name);
		req.setRole_id(type);
		req.setAppid(ANDROID_ID);
		req.setFirsttoken(token);

		reqBody.userLoginReq = req;
		reqEnv.body = reqBody;
		Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_login(reqEnv);
		call.enqueue(this);
		Logger.e(reqEnv.toString());

	}

	@Override
	public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
		if (!isActivity())return;
		List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
		String name=names.get(names.size()-1);
		ResEnv respEnvelope = response.body();
		if (respEnvelope != null) {
			ResBody b=respEnvelope.body;
			if (b.userLoginRes !=null){
				String result=b.userLoginRes.result;
				Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
				UserRes res= gson.fromJson(result,UserRes.class);
				if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)) {
					ViewTool.dismissProgressDialog();
					saveUser(res,TagFinal.FALSE);
				} else {
					switch (res.getError_code()){
						case "重名":
							getDuplicationList();
							break;
						case "password":
							ViewTool.dismissProgressDialog();
							Intent intent=new Intent(mActivity,ResetPasswordToHavePasswordActivity.class);
							intent.putExtra(Base.id, type.equals("1") ? Base.USER_TYPE_STU + "_" + res.getUserid() : Base.USER_TYPE_TEA + "_" + res.getUserid());
							startActivity(intent);
							break;
							default:
								ViewTool.dismissProgressDialog();
								ViewTool.showToastShort(mActivity,res.getError_code());
								break;
					}

				}
			}
			if (b.userGetDuplicationListRes !=null){
				String result=b.userGetDuplicationListRes.result;
				Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
				UserRes res= gson.fromJson(result,UserRes.class);
				setCPWlListBeanData(res.getStunlist());
				ViewTool.dismissProgressDialog();
			}
			if (b.userDuplicationLoginRes !=null){
				String result=b.userDuplicationLoginRes.result;
				Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
				ViewTool.dismissProgressDialog();
				UserRes res= gson.fromJson(result,UserRes.class);
				if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)) {
					saveUser(res,TagFinal.TRUE);
				}else{
					switch (res.getError_code()){
						case "password":
							Intent intent=new Intent(mActivity,ResetPasswordToHavePasswordActivity.class);
							intent.putExtra(Base.id, type.equals("1") ? Base.USER_TYPE_STU + "_" + res.getUserid() : Base.USER_TYPE_TEA + "_" + res.getUserid());
							startActivity(intent);
							break;
						default:
							ViewTool.showToastShort(mActivity,res.getError_code());
							break;
					}
				}
			}
			if (b.baseGetTokenRes !=null){
				String result=b.baseGetTokenRes.result;
				Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
				BaseRes res=gson.fromJson(result,BaseRes.class);
				if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
					if (token.equalsIgnoreCase("login")){
						login_(res.getTooken());
					}
				}else{
					ViewTool.showToastShort(mActivity,res.getError_code());
				}
			}
		}else{
			ViewTool.dismissProgressDialog();
			try {
				assert response.errorBody()!=null;
				String s=response.errorBody().string();
				Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
			} catch (IOException e) {
				Logger.e("onResponse: IOException");
				e.printStackTrace();
			}
			ViewTool.showToastShort(mActivity,StringUtils.getTextJoint("数据错误:%1$d",response.code()));
		}


	}


	@Override
	public void onFailure(Call<ResEnv> call, Throwable t) {
		if (!isActivity())return;
		ViewTool.dismissProgressDialog();
		Logger.e( "onFailure  "+call.request().headers().toString() );
		ViewTool.showToastShort(mActivity,R.string.fail_do_not);

	}

	@Override
	public boolean isActivity() {
		return AppLess.isTopActivy(TAG);
	}

	private void saveUser(UserRes res, String login_type){
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
	private KeyboardUtil keyboardUtil;
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

}
