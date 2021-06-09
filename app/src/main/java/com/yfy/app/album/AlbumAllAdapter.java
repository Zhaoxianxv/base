package com.yfy.app.album;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yfy.base.R;
import com.yfy.base.adapter.AbstractAdapter;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.glide.PhotoAlbum;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author yfy
 * @version 1.0
 */
public class AlbumAllAdapter extends AbstractAdapter<PhotoAlbum> {

	/**
	 */
	public AlbumAllAdapter(Context context, List<PhotoAlbum> list) {
		super(context, list);
	}

	@Override
	public void renderData(int position, DataViewHolder holder,
			List<PhotoAlbum> list) {
		PhotoAlbum photoAlbum = list.get(position);
		TextView album_name = holder.getView(TextView.class, R.id.album_name);
		ImageView album_photo = holder.getView(ImageView.class, R.id.album_photo);
		album_name.setText(StringUtils.stringToGetTextJoint("%1$s(%2$d)",photoAlbum.getName(),photoAlbum.photoList.size()));

		if (StringJudge.isEmpty(photoAlbum.photoList))return;

		Glide.with(context)
				.load(photoAlbum.photoList.get(0).getPath())
				.apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(16, 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT)))
                .into(album_photo);



	}

	@Override
	public AbstractAdapter.ResInfo getResInfo() {
		ResInfo resInfo = new ResInfo();
		resInfo.layout = R.layout.album_all_item_listview;
		resInfo.initIds = new int[] { R.id.album_photo, R.id.album_name };
		return resInfo;
	}
}
