package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.bean.ResultRes;
import com.yfy.app.netHttp.ApiUrl;
import com.yfy.app.netHttp.HttpNetHelpInterface;
import com.yfy.app.netHttp.HttpPostActivity;
import com.yfy.app.netHttp.RestClient;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.CharacterParser;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * @author yfy1
 * @version 1.0
 */
@SuppressLint("NonConstantResourceId")
public class SearchStuPassWordActivity extends HttpPostActivity implements HttpNetHelpInterface {
	private static final String TAG = SearchStuPassWordActivity.class.getSimpleName();

	private TextView tips_tv;
	private SearchStuPassWordAdapter adapter;
	public List<KeyValue> sourceDateList = new ArrayList<>();
	private CharacterParser characterParser ;

	@BindView(R.id.clear_et)
	ClearEditText clear_et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_user_main);
		characterParser= new CharacterParser();
		initConfirmDialog();
		initRecycler();
		initView();
		getData();
		initSQToolbar();
	}



	public String select_type,title;
	private void getData() {
//		sourceDateList=getIntent().getParcelableArrayListExtra(Base.data);
//
//
//		adapter.setDataList(sourceDateList);
//		adapter.setLoadState(TagFinal.LOADING_END);

	}



	private void initSQToolbar() {
		assert toolbar!=null;
		toolbar.setTitle("重置密码");

	}


	private ConfirmContentWindow confirmContentWindow;
	private void initConfirmDialog(){
		confirmContentWindow = new ConfirmContentWindow(mActivity);
		confirmContentWindow.setPopClickListener(new NoFastClickListener() {
			@Override
			public void fastPopClick(View view) {
				switch (view.getId()){
					case R.id.pop_dialog_title:
						break;
					case R.id.pop_dialog_ok:
						confirmContentWindow.dismiss();
						resetStuPass("");
						break;
				}
			}
		});
	}

	public void showConfirmDialog(String content){
		if (confirmContentWindow==null)return;
		confirmContentWindow.setTitle("提示",content,"确定","取消");
		confirmContentWindow.showAtCenter();
	}













	public void getContactsChild(List<KeyValue> groups){
		ArrayList<KeyValue> childs=new ArrayList<>();
		for (KeyValue group:groups){
			if (!group.isRequired()) continue;
			childs.add(group);
		}

		Intent intent=new Intent();
		intent.putParcelableArrayListExtra(Base.data, childs);
		setResult(RESULT_OK,intent);
		finish();

	}



	private RecyclerView recyclerView;
	public void initRecycler(){

		recyclerView =  findViewById(R.id.notice_search);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		//添加分割线
		recyclerView.addItemDecoration(new RecycleViewDivider(
				mActivity,
				LinearLayoutManager.HORIZONTAL,
				1,
				getResources().getColor(R.color.gray)));
		adapter=new SearchStuPassWordAdapter(mActivity);

		recyclerView.setAdapter(adapter);
		adapter.setIntentStart(new StartIntentInterface() {
			@Override
			public void startIntentActivity(Intent intent, String type) {

				showConfirmDialog("");
			}
		});

	}






	private void initView() {
		tips_tv =  findViewById(R.id.tips_tv);
		clear_et.addTextChangedListener(new com.yfy.final_tag.tool_textwatcher.EmptyTextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				filterData(s.toString());
			}
		});
	}




	private void filterData(String filterStr) {
		List<KeyValue> filterDateList = new ArrayList<>();
		filterDateList.clear();
		if (!TextUtils.isEmpty(filterStr)) {
			for (KeyValue contactMember : sourceDateList) {
				String name = contactMember.getName();
				if (contactMember.getView_type()== TagFinal.TYPE_PARENT)continue;
				if (name.indexOf(filterStr) != -1 || characterParser.getSelling(name).startsWith(filterStr)) {
					filterDateList.add(contactMember);
				}
			}
		} else {
			tips_tv.setVisibility(View.GONE);
			recyclerView.setVisibility(View.VISIBLE);
			adapter.setDataList(filterDateList);
			adapter.setLoadState(2);
			return;
		}

		if (filterDateList.size() == 0) {
			tips_tv.setVisibility(View.VISIBLE);
			recyclerView.setVisibility(View.GONE);
		} else {
			tips_tv.setVisibility(View.GONE);
			recyclerView.setVisibility(View.VISIBLE);
			adapter.setDataList(filterDateList);
			adapter.setLoadState(2);
		}
	}


	/**
	 * ----------------------------retrofit-----------------------
	 */


	public void getStu(String search_s)  {

		//获取参数


		Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().base_get_stu_to_search_char_api(
				Base.user.getSession_key(),
				search_s
		);
		setNetHelper(this,bodyCall,false, ApiUrl.USER_GET_STU_TO_SEARCH_CHAR);

	}


	public void resetStuPass(String stu_id)  {

		//获取参数


		Call<ResponseBody> bodyCall = RestClient.instance.getAccountApi().base_reset_pass_to_admin_api(
				Base.user.getSession_key(),
				MathTool.stringToInt(stu_id)
		);
		setNetHelper(this,bodyCall,false, ApiUrl.USER_RESET_PASS_WORD_TO_ADMIN);

	}



	@Override
	public void success(String api_name, String result) {
		if (!isActivity())return;
		ViewTool.dismissProgressDialog();


		switch (api_name){

			case ApiUrl.USER_GET_STU_TO_SEARCH_CHAR:
				ResultRes res= gson.fromJson(result,ResultRes.class);
				if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
					ViewTool.showToastShort(mActivity,R.string.success_do);
					setResult(RESULT_OK);
					finish();
				}else{
					ViewTool.showToastShort(mActivity,res.getError_code());
				}
				break;
			case ApiUrl.USER_RESET_PASS_WORD_TO_ADMIN:
				ResultRes info=gson.fromJson(result, ResultRes.class);
				if (info.getResult().equals(TagFinal.TRUE)){
					ViewTool.showToastShort(mActivity,"");
				}else{
					ViewTool.showToastShort(mActivity,info.getError_code());
				}
				break;
		}


	}


	@Override
	public void fail(String api_name) {
	}



	@Override
	public boolean isActivity() {
		return AppLess.isTopActivy(TAG);
	}
}
