package com.yfy.app.PEquality.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.LinkedList;

public class KnowledgeAnswerPagerAdapter extends PagerAdapter {
    private Context mcontext;
    private LayoutInflater inflater;
    public LinkedList<View> viewCache = new LinkedList<View>();
    LinkedList<KeyValue> list = new LinkedList<KeyValue>();
    public void reSetData(LinkedList<KeyValue> data_list) {
        this.list =data_list;
        inflater = LayoutInflater.from(mcontext);
        viewCache.clear();
        for (int i = 0; i < list.size(); i++) {
            View view = inflater.inflate(R.layout.knowledge_library_pager_item_layout, null);
            AppCompatTextView title=view.findViewById(R.id.attitude_library_title);
            AppCompatTextView value=view.findViewById(R.id.attitude_library_answer);
            title.setText(StringUtils.stringToGetTextJoint("%1$s(%2$d/%3$d)",list.get(i).getName(),i,list.size()));
            value.setText(list.get(i).getValue());
            viewCache.add(view);
        }
        notifyDataSetChanged();
    }


    //    public KnowledgeAnswerListViewAdapter list_adapter;
//    public ListView listview;
//    private  void initListView(){
//        listview=findViewById(R.id.attitude_answer_list);
//        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
//        list_adapter=new KnowledgeAnswerListViewAdapter(mActivity);
//        listview.setAdapter(list_adapter);
//
//        keyValue_adapter.clear();
//
//        KeyValue one=new KeyValue("A、古罗马","",TagFinal.TYPE_ITEM);
//        KeyValue two=new KeyValue("B、古希腊","",TagFinal.TYPE_ITEM);
//        KeyValue three=new KeyValue("C、法国文字超出一行文字超出一行文字超出一行文字超出一行","",TagFinal.TYPE_ITEM);
//        KeyValue four=new KeyValue("D、英国","",TagFinal.TYPE_ITEM);
//
//        keyValue_adapter.add(one);
//        keyValue_adapter.add(two);
//        keyValue_adapter.add(three);
//        keyValue_adapter.add(four);
//        list_adapter.setDatas(keyValue_adapter);
//    }
    public KnowledgeAnswerPagerAdapter(Context mcontext) {
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
