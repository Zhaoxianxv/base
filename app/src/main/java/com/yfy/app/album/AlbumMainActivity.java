package com.yfy.app.album;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.hander.AlbumAsyncTask;
import com.yfy.final_tag.hander.AlbumGetFileData;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author yfy
 */
@SuppressLint("NonConstantResourceId")
public class AlbumMainActivity extends BaseActivity implements  AlbumGetFileData {
	private static final String TAG = AlbumMainActivity.class.getSimpleName();

	@BindView(R.id.pic_total_size)
	TextView pic_total_size;
	@BindView(R.id.ok_tv)
	TextView ok_tv;
	@OnClick(R.id.ok_tv)
	void setOk(){
		selectedPhotoList.clear();
		for (Photo photo:adapter.getPhotoList()	 ) {
			if (photo.isSelected())selectedPhotoList.add(photo);
		}
		Intent intent=new Intent();
		ArrayList<Photo> photos=new ArrayList<>(selectedPhotoList);
		intent.putParcelableArrayListExtra(TagFinal.ALBUM_TAG,  photos);
		setResult(RESULT_OK,intent);

		finish();
	}

	@OnClick(R.id.bar_back_iv_album_main)
	void setBack(){
		finish();
	}

	@OnClick(R.id.clear_tv)
	void setClear(){
		clearData();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_new_main);
		Logger.eLogText(TAG);
		getData();

		initRecycler();
		getAlbumAsyncTask();

	}

	public List<Photo> selectedPhotoList=new ArrayList<>();
	@Override
	public void finish() {
		super.finish();
	}

	/*position 进album 之前 的列表下标，无就是-1*/
	public int position=-1;
	/*single=false 多选*/
	public boolean single=false;
	private void getData() {
		Bundle b = getIntent().getExtras();
		if (b != null) {
			position = b.getInt(TagFinal.ALBUM_LIST_INDEX);
			if (b.containsKey(TagFinal.ALBUM_SINGLE)) {
				single = b.getBoolean(TagFinal.ALBUM_SINGLE);
			}
		}
	}



	public AlbumMainAdapter adapter;
	public RecyclerView recyclerView;
	public void initRecycler(){
		recyclerView =  findViewById(R.id.grid_view_album_main);
		GridLayoutManager manager = new GridLayoutManager(mActivity,3, LinearLayoutManager.VERTICAL,false);
		recyclerView.setLayoutManager(manager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		adapter=new AlbumMainAdapter(mActivity);
		recyclerView.setAdapter(adapter);
		adapter.setSingle(single);
		adapter.setIntentStart(new StartIntentInterface() {
			@Override
			public void startIntentActivity(Intent intent,String type) {

				pic_total_size.setText(type);
			}
		});



	}






	public void clearData(){
		for (Photo photo :photo_album_List) {
			photo.setSelected(false);
		}
		selectedPhotoList.clear();
		pic_total_size.setText("已选\t0\tB");
		ok_tv.setText("确定(0)");
		adapter.notifyDataSetChanged();
	}


	/*菜单 返回true(显示) false（）*/
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_album, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		switch (id){
//			case R.id.album_menu_list:
//				if (StringJudge.isNotEmpty(allPhotoAlbumList)){
//					Intent intent = new Intent(mActivity, AlbumAllActivity.class);
//					Bundle bundle=new Bundle();
//					bundle.putParcelableArrayList("allPhotoAlbumList",allPhotoAlbumList);
//					intent.putExtras(bundle);
//					startActivityForResult(intent, TagFinal.UI_REFRESH);
//					clearData();
//					overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
//				}
//				break;
//			case -1:
//				break;
//
//
//		}
//
//		return super.onOptionsItemSelected(item);
//	}








	//async task

	AlbumAsyncTask mTask;
	public void getAlbumAsyncTask(){
		ViewTool.showProgressDialog(mActivity,"");
		mTask=new AlbumAsyncTask(this);
		mTask.setResolver(this);
		mTask.execute(this);
	}



	@Override
	public void onPause() {
		super.onPause();
		if (mTask!=null&&mTask.getStatus()== AsyncTask.Status.RUNNING) {
			mTask.cancel(true);
		}
	}

	public List<Photo> photo_album_List=new ArrayList<>();
	@Override
	public void OnEnd(List<Photo> list) {
		ViewTool.dismissProgressDialog();
		if (StringJudge.isEmpty(list)){
			ViewTool.showToastShort(mActivity,"没有获取到相册列表");
			return;
		}
        photo_album_List.clear();
        photo_album_List.addAll(list);
		if (StringJudge.isEmpty(photo_album_List)){
			ViewTool.showToastShort(mActivity,"没有获取到相片");
			return;
		}
		adapter.setPhotoList(photo_album_List);
		adapter.setLoadState(TagFinal.LOADING_END);
	}


	/*返回键监听*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode){
			case KeyEvent.KEYCODE_BACK:
				clearData();
				finish();
				return false;
			case KeyEvent.KEYCODE_HOME:
//				return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);

	}



}
