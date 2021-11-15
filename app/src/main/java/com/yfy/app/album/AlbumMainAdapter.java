package com.yfy.app.album;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yfy.base.R;
import com.yfy.final_tag.FileTools;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author yfy1

 * @version 1.0
 */
public class AlbumMainAdapter extends BaseRecyclerAdapter {


	public List<Photo> photoList = new ArrayList<>();

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public boolean single=false;

	public void setSingle(boolean single) {
		this.single = single;
	}



	public AlbumMainAdapter(Activity mContext) {
		super(mContext);
		ViewTool.getScreenWidth(mContext);

	}

	@Override
	public int getItemViewType(int position) {
		// 最后一个item设置为FooterView
		return TagFinal.TYPE_ITEM;

	}

	@Override
	public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
		//进行判断显示类型，来创建返回不同的View
		if (viewType == TagFinal.TYPE_ITEM) {
			View view = inflater.inflate(R.layout.album_main_item, parent, false);
			return new RecyclerViewHolder(view);

		}
		return new ErrorHolder(parent);
	}

	@Override
	public void bindHolder(ReViewHolder holder, int position) {
		if (holder instanceof RecyclerViewHolder) {
			RecyclerViewHolder reHolder = (RecyclerViewHolder) holder;
			reHolder.bean = photoList.get(position);
			reHolder.index=position;
			reHolder.setView();
		}


	}

	@Override
	public int getItemCount() {
		return photoList.size();
	}


	private class RecyclerViewHolder extends ReViewHolder {
		AppCompatImageView iv_select_state;
		ImageView iv_photo;
		RelativeLayout layout;
		int index;
		Photo bean;

		RecyclerViewHolder(View itemView) {
			super(itemView);
			iv_photo = itemView.findViewById(R.id.photo_iv_album_main_item);
			iv_select_state = itemView.findViewById(R.id.select_state_iv_album_main_item);
			layout = itemView.findViewById(R.id.layout_relative_album_main_item);
			layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent();
					if (single){
						bean.setSelected(!bean.isSelected());
						notifyItemChanged(index,bean);
						for (int i=0;i<photoList.size();i++) {
							Photo photo=photoList.get(i);
							if (bean.getPath().equalsIgnoreCase(photo.getPath()))continue;
							if (photo.isSelected()){
								photo.setSelected(false);
								notifyItemChanged(i,photo);
							}
						}
					}else{
						bean.setSelected(!bean.isSelected());
						notifyItemChanged(index,bean);

					}
					if (intentStart!=null){
						intentStart.startIntentAdapter(intent,photoSelectedNum());
					}
				}
			});


		}

		private String photoSelectedNum() {
			if (StringJudge.isNotEmpty(photoList)) {
				long size = 0;
				for (Photo photo:photoList) {
					if (photo.isSelected())
					size += FileTools.getFileSize(photo.getPath());
				}
				return "已选" + FileTools.convertBytesToOther(size);
			}else {
				return "已选\t0\tB" ;
			}
		}


		public void setView() {
			int itemWidth= ViewTool.getScreenWidth(mContext)/3;
			RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(itemWidth,itemWidth);
			layout.setLayoutParams(params);
			layout.requestLayout();

			GlideTools.loadUriImage(mContext, bean.getPath(), iv_photo);
			iv_select_state.setVisibility(View.VISIBLE);
//			if (single) {
//				iv_select_state.setVisibility(View.GONE);
//			} else {
//				iv_select_state.setVisibility(View.VISIBLE);
//			}

			if (bean.isSelected()) {
				GlideTools.loadImage(mContext, R.drawable.photo_selecteds, iv_select_state);
			} else {
				GlideTools.loadImage(mContext, R.drawable.photo_unselected, iv_select_state);
			}


		}
	}


	public StartIntentInterface intentStart;

	public void setIntentStart(StartIntentInterface intentStart) {
		this.intentStart = intentStart;
	}

}
