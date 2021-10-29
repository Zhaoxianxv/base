package com.yfy.app.album;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yfy.dujiangyan.R;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.viewtools.ViewUtils;
import com.yfy.view.CheckImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yfy1

 * @version 1.0
 */
public class AlbumMainAdapter extends BaseAdapter {


	public List<Photo> selectedPhotoList = new ArrayList<>();
	private List<Photo> photoList = new ArrayList<Photo>();
	public LayoutInflater inflater;
	private boolean single;
	public Activity mActivity;

	public AlbumMainAdapter(Activity context, List<Photo> list) {
		photoList = list;
		inflater = LayoutInflater.from(context);
		mActivity =context;

	}

	public AlbumMainAdapter(Activity context, List<Photo> list, boolean single) {
		this(context, list);
		this.single = single;
	}

	@Override
	public int getCount() {
		return photoList.size();
	}

	@Override
	public Object getItem(int position) {
		return photoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int position2 = position;
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.album_one_item_gridview, null);
			holder.photo =  convertView.findViewById(R.id.photo);
			holder.selected_iv =  convertView.findViewById(R.id.selected_iv);

			ViewGroup.LayoutParams params =  holder.photo.getLayoutParams();
			params.width = itemWidth;
			params.height = itemWidth;
			holder.photo.requestLayout();

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Photo photo = photoList.get(position);
		holder.selected_iv.setChecked(photo.isSelected());


		GlideTools.loadUriImage(mActivity,photoList.get(position).getPath(),holder.photo);

		convertView.setOnClickListener(new NoFastClickListener() {

			@Override
			public void fastClick(View v) {
				onInnerClick(v, position2, photoList);
			}
		});

		return convertView;
	}

	public synchronized void onInnerClick(View v, int position, List<Photo> list) {
		if (single) {
			singleCheck(v, position, list);
		} else {
			multipleCheck(v, position, list);
		}
		if (listenner != null) {
			listenner.onChecked(v,selectedPhotoList);
		}
	}

	private void singleCheck(View v, int position, List<Photo> list) {
		Photo photo = list.get(position);
		ViewHolder holder = (ViewHolder) v.getTag();
		for (Photo bean:list){
			bean.setSelected(false);
		}
		selectedPhotoList.clear();
		photo.setSelected(true);
		holder.selected_iv.setChecked(true);
		notifyDataSetChanged(list);
		selectedPhotoList.add(photo);
	}

	private void multipleCheck(View v, int position, List<Photo> list) {
		Photo photo = list.get(position);
		boolean b = photo.isSelected();
		ViewHolder holder = (ViewHolder) v.getTag();
		if (b) {
			selectedPhotoList.remove(photo);
		} else {
			selectedPhotoList.add(photo);
		}
		holder.selected_iv.setChecked(!b);
		photo.setSelected(!b);
	}

	//清理所有选中
	public void clearSeleter(){
		for (Photo photo:photoList){
			photo.setSelected(false);
		}
		notifyDataSetInvalidated();
	}


	private CheckedListenner listenner = null;

	public void setCheckedListenner(CheckedListenner listenner) {
		this.listenner = listenner;
	}

	public static interface CheckedListenner {
		public abstract void onChecked(View v, List<Photo> list);
	}



	public void notifyDataSetChanged(List<Photo> list) {
		this.photoList = list;
		super.notifyDataSetChanged();
	}

	public class ViewHolder {
		public ImageView photo;
		public CheckImageView selected_iv;
	}


	private int itemWidth = 10;
	public void initItemSize(GridView gridView,int mScreenWidth) {
		itemWidth = (mScreenWidth - gridView.getPaddingLeft() - gridView.getPaddingRight() - ViewUtils.getHorizontalSpacing(gridView)) / 3;
	}

}

