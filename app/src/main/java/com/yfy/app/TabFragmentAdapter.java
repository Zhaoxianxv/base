package com.yfy.app;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Administrator on 2016/7/29.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> titles;
    private Context context;
    private List<Fragment> fragments;
    FragmentManager fm;

    public void setData(List<Fragment> fragments, List<String> titles){
        for (Fragment fragment:this.fragments){
            fm.beginTransaction().remove(fragment);
        }
        this.fragments = fragments;
        this.titles = titles;
        notifyDataSetChanged();
    }

    public TabFragmentAdapter(List<Fragment> fragments, List<String> titles, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
        this.fm=fm;
//        Logger.e("zxx", "titles.size()------" +titles.size());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
//        return 1;
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragments.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }

}

