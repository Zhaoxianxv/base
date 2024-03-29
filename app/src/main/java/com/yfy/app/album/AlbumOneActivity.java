package com.yfy.app.album;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yfy.app.album.AlbumOneAdapter.CheckedListenner;
import com.yfy.app.album.PhotoAlbumHelper.OnEndListenner;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.FileTools;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.glide.PhotoAlbum;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author yfy
 * @version 1.0
 */
@SuppressLint("NonConstantResourceId")
public class AlbumOneActivity extends BaseActivity implements OnEndListenner,CheckedListenner {

	@BindView(R.id.pic_gridview)
	GridView pic_gridview;
	@BindView(R.id.pic_total_size)
	TextView pic_total_size;
	@BindView(R.id.ok_tv)
	TextView ok_tv;
	@BindView(R.id.album_one_title_bar)
	Toolbar toolbar;

	private AlbumOneAdapter adapter;
	private ArrayList<Photo> photoList;//相册分类组
	public List<Photo> selectedPhotoList = new ArrayList<>();//选中图片
	public ArrayList<PhotoAlbum> allPhotoAlbumList = new ArrayList<>();//全部图片
	private int position;
	private boolean single;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_new_one);
		selectedPhotoList.clear();
		initSQToolbar();
		initViews();
	}

	@Override
	public void finish() {
		for (Iterator<Photo> iterator = selectedPhotoList.iterator(); iterator.hasNext();) {
			Photo photo = iterator.next();
			photo.setSelected(false);
		}
		selectedPhotoList.clear();
		pic_total_size.setText("已选0B");
		ok_tv.setText("确定(0)");
		adapter.notifyDataSetChanged();
		adapter.clearSeleter();

		super.finish();
	}

	private void initSQToolbar() {

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_left_nav);
		toolbar.setTitle("相册");
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	private void initViews() {
		getData();
		startAdaterTask();
	}

	private void getData() {
		Bundle b = getIntent().getExtras();
		if (b != null) {
			position = b.getInt(TagFinal.ALBUM_LIST_INDEX);
			if (b.containsKey(TagFinal.ALBUM_SINGLE)) {
				single = b.getBoolean(TagFinal.ALBUM_SINGLE);
			}
			if (b.containsKey("existing")) {
			}
		}
	}

	private void startAdaterTask() {
		if (StringJudge.isEmpty(allPhotoAlbumList)) {
			PhotoAlbumHelper helper = new PhotoAlbumHelper(this);
			helper.setOnEndListenner(this);
			helper.execute(false);
		} else {
			photoList = allPhotoAlbumList.get(position).photoList;
			adapter = new AlbumOneAdapter(mActivity, photoList, single);
			if (position == 0) {
				toolbar.setTitle("最近相片");
			} else {
				toolbar.setTitle(photoList.get(0).getAlbumName());
			}
			pic_gridview.setAdapter(adapter);
			adapter.setCheckedListenner(AlbumOneActivity.this);
		}
	}





	@Override
	public void OnEnd(ArrayList<PhotoAlbum> list) {
		if (list==null||list.size()==0){
			ViewTool.showToastShort(mActivity,"没能获取到你的相册");
		}
		allPhotoAlbumList = list;
		photoList = list.get(position).photoList;

		adapter = new AlbumOneAdapter(this, photoList, single);
		if (position == 0) {
			toolbar.setTitle("最近相片");
		} else {
			toolbar.setTitle(photoList.get(0).getAlbumName());
		}
		pic_gridview.setAdapter(adapter);
		adapter.setCheckedListenner(this);
	}

	@Override
	public void onChecked(View v, List<Photo> list) {
		selectedPhotoList=list;
		photoSelectedNum(list);
	}

	private void photoSelectedNum(List<Photo> list) {
		if (list.size() >= 0) {
			long size = 0;
			ok_tv.setText("确定(" + list.size() + ")");
			for (Iterator<Photo> iterator = list.iterator(); iterator.hasNext();) {
				Photo photo = iterator.next();
				size += FileTools.getFileSize(photo.getPath());
			}
			String sizeStr = "已选" + FileTools.convertBytesToOther(size);
			pic_total_size.setText(sizeStr);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, requestCode, data);
		switch (resultCode) {
			case RESULT_OK:
				int position = data.getExtras().getInt("position");
				if (position < 0) {
					setResult(RESULT_OK);
					finish();
				} else {
					photoList = allPhotoAlbumList.get(position).photoList;
					adapter.notifyDataSetChanged(photoList);
					if (position == 0) {
						toolbar.setTitle("最近相片");
					} else {
						toolbar.setTitle(photoList.get(0).getAlbumName());
					}
				}
				break;
		}
	}



	@OnClick(R.id.clear_tv)
	void setclear(){
		clearData();
	}
	public void clearData(){
		for (Iterator<Photo> iterator = photoList.iterator(); iterator.hasNext();) {
			Photo photo = iterator.next();
			photo.setSelected(false);
		}
		pic_total_size.setText("已选0B");
		ok_tv.setText("确定(0)");
		adapter.notifyDataSetChanged(photoList);
		adapter.clearSeleter();
	}
	@OnClick(R.id.ok_tv)
	void setok(){
		Intent intent=new Intent();
		ArrayList<Photo> photos=new ArrayList<>(selectedPhotoList);
		intent.putParcelableArrayListExtra(TagFinal.ALBUM_TAG,  photos);
		setResult(RESULT_OK,intent);
		clearData();
		onPageBack();
	}


	//菜单 返回true(显示) false（）
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_album, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id){
			case R.id.album_menu_list:

				if (allPhotoAlbumList==null){

				}else{
					if (StringJudge.isEmpty(allPhotoAlbumList)){
					}else{
						Intent intent = new Intent(mActivity, AlbumAllActivity.class);
						Bundle bundle=new Bundle();
						bundle.putParcelableArrayList("allPhotoAlbumList",allPhotoAlbumList);
						intent.putExtras(bundle);
						startActivityForResult(intent, 1);
						clearData();
						overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
						break;
					}
				}


		}

		return super.onOptionsItemSelected(item);
	}



}
