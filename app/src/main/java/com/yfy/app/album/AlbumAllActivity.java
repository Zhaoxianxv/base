package com.yfy.app.album;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.glide.PhotoAlbum;
import com.yfy.final_tag.listener.NoFastClickListener;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;


/**
 * @author yfy
 */
public class AlbumAllActivity extends BaseActivity {


	public ListView album_listview;
	public AlbumAllAdapter adapter;
	private Intent intent;
	public ArrayList<PhotoAlbum> allPhotoAlbumList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_new_all);
		getData();
		init();
		initSQToolbar();
	}
	/**
	 */
	public void getData(){
		Bundle b = getIntent().getExtras();
		if (b != null) {
			if (b.containsKey("allPhotoAlbumList")) {
				allPhotoAlbumList=b.getParcelableArrayList("allPhotoAlbumList");
			}
		}
	}

	private void initSQToolbar() {

		Toolbar toolbar=  findViewById(R.id.album_all_title_bar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_left_nav);
		toolbar.setTitle("相册");
		toolbar.setNavigationOnClickListener(new NoFastClickListener() {
			@Override
			public void fastClick(View v) {
				onBackPressed();
			}
		});

	}
	private void init() {
		intent = getIntent();
		album_listview =  findViewById(R.id.album_listview);
		album_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle b = new Bundle();
				b.putInt("position", position);
				intent.putExtras(b);
				setResult(RESULT_OK, intent);
				onPageBack();
				overridePendingTransition(R.anim.activity_close_enter,R.anim.activity_close_exit);
			}
		});
		adapter = new AlbumAllAdapter(mActivity, allPhotoAlbumList);
		album_listview.setAdapter(adapter);

	}


}
