package com.yfy.app.PEquality.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.LinkedList;

public class KnowledgeLibraryPagerAdapter extends PagerAdapter {
    private Context mcontext;
    public LayoutInflater inflater;
    public LinkedList<View> viewCache = new LinkedList<>();
    LinkedList<KeyValue> list = new LinkedList<>();
    public KnowledgeAnswerListViewAdapter list_adapter;
    public void reSetData(LinkedList<KeyValue> data_list) {
        this.list =data_list;
        inflater = LayoutInflater.from(mcontext);
        list_adapter=new KnowledgeAnswerListViewAdapter(mcontext);
        viewCache.clear();
        for (int i = 0; i < list.size(); i++) {
            KeyValue bean=list.get(i);
            View view = inflater.inflate(R.layout.p_e_knowledge_answer_pager_item, null);
            AppCompatTextView title=view.findViewById(R.id.attitude_answer_title);
            ListView listview=view.findViewById(R.id.attitude_answer_list);
            if (bean.getType().equalsIgnoreCase(TagFinal.TRUE)){
                //单选
                listview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            }else{
                listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            }

            if (bean.getId().equalsIgnoreCase(TagFinal.TRUE)){
                listview.setClickable(true);
            }else{
                listview.setClickable(false);
            }
            listview.setAdapter(list_adapter);
            list_adapter.setDatas(bean.getCpwBeanArrayList());
            title.setText(StringUtils.stringToGetTextJoint("(%2$d/%3$d)、\t%1$s",list.get(i).getTitle(),i+1,list.size()));
            viewCache.add(view);
        }
        notifyDataSetChanged();
    }




    public KnowledgeLibraryPagerAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    //设置viewpage内部东西的方法，如果viewpage内没有子空间滑动产生不了动画效果
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewCache.get(position));
        return viewCache.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewCache.get(position));

    }


}
