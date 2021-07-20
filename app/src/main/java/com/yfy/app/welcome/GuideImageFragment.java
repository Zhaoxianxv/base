package com.yfy.app.welcome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yfy.app.welcome.utils.v4.FragmentPagerItem;
import com.yfy.base.R;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;

import androidx.fragment.app.Fragment;

public class GuideImageFragment extends Fragment {
    ImageView iv_image;
    Bitmap bitmap;
    int fragmentPagerItemPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = inflater.from(getActivity()).inflate(R.layout.initial_fragment_guide, container, false);
        iv_image=contentView.findViewById(R.id.guide_iv_image);//
        fragmentPagerItemPosition = FragmentPagerItem.getPosition(getArguments());
        if (fragmentPagerItemPosition == 0) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.homepaga);//initial one pager
        } else if (fragmentPagerItemPosition == 1) {
            bitmap = BitmapFactory.decodeResource(getResources(),  R.mipmap.homepaga);//initial two pager
        } else if (fragmentPagerItemPosition == 2) {
            bitmap = BitmapFactory.decodeResource(getResources(),  R.mipmap.homepaga);//initial three pager
        }
        if (StringJudge.isNotNull(bitmap)) {
            ViewTool.setImageBitmap(iv_image, bitmap);
        }

        return contentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewTool.releaseImageView(iv_image);
        iv_image = null;
        ViewTool.releaseBitmap(bitmap);
        bitmap = null;
    }

}
