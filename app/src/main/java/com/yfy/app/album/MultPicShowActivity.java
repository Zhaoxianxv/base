package com.yfy.app.album;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.view.image.PinchImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class MultPicShowActivity extends BaseActivity {
    private List<String> list=new ArrayList<>();
    private String title;
    private ViewPager pager;
    private int index=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_pager);
        initSQToolbar();
        getData();
        final LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }
            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PinchImageView piv;
                if (viewCache.size() > 0) {
                    piv = viewCache.remove();
                    piv.reset();
                } else {
                    piv = new PinchImageView(mActivity);
                }
                GlideTools.loadImage(mActivity,list.get(position) , piv);
                container.addView(piv);
                return piv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                container.removeView(piv);
                viewCache.add(piv);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                GlideTools.loadImage(mActivity,list.get(position) , piv);

            }
        });
        if (index!=-1){
            if (index<list.size()){
                pager.setCurrentItem(index);
            }
        }

    }
    public void getData(){
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey(TagFinal.ALBUM_SINGE_URI)) {
                list = b.getStringArrayList(TagFinal.ALBUM_SINGE_URI);
            }
            if (b.containsKey("title")) {
                title = b.getString("title");
            }
            if (b.containsKey(TagFinal.ALBUM_LIST_INDEX)) {
                index = b.getInt(TagFinal.ALBUM_LIST_INDEX);
            }
        }
    }
    private void initSQToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.show_pic_one_title_bar);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (title!=null){
//            toolbar.setTitle(title);
//        }else{
//            toolbar.setTitle("返回");
//        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
