/**
 * 
 */
package com.yfy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.CharacterParser;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * @author yfy1
 * @version 1.0
 */
@SuppressLint("NonConstantResourceId")
public class SearchUserActivity extends BaseActivity {


	private TextView tips_tv;
	private SearchUserAdapter adapter;
	private List<KeyValue> sourceDateList = new ArrayList<>();
	private CharacterParser characterParser ;

	@BindView(R.id.clear_et)
	ClearEditText clear_et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_user_main);
		characterParser= new CharacterParser();
		initRecycler();
		initView();
		getData();
		initSQToolbar();
	}



	public String select_type,title;
	public int index_position;
	private void getData() {
		sourceDateList=getIntent().getParcelableArrayListExtra(Base.data);
		title=getIntent().getStringExtra(Base.title);
		select_type=getIntent().getStringExtra(Base.type);
		index_position=getIntent().getIntExtra(Base.index,0);

		adapter.setSelect_type(select_type);
		adapter.setIndex_position(index_position);
		adapter.setDataList(sourceDateList);
		adapter.setLoadState(TagFinal.LOADING_END);

	}



	private void initSQToolbar() {
		assert toolbar!=null;
		toolbar.setTitle(title);
		if (select_type.equalsIgnoreCase(TagFinal.TRUE))return;
		toolbar.addMenuText(TagFinal.ONE_INT, R.string.ok);
		toolbar.setOnMenuClickListener(new NoFastClickListener() {
			@Override
			public void onClick(View view, int position) {
				getContactsChild(adapter.getDataList());
			}
		});
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
		adapter=new SearchUserAdapter(mActivity);

		recyclerView.setAdapter(adapter);
		adapter.setIntentStart(new StartIntentInterface() {
			@Override
			public void startIntentActivity(Intent intent, String type) {
				setResult(RESULT_OK,intent);
				finish();
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


}
