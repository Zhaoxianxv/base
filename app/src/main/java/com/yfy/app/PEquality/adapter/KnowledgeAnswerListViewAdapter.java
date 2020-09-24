package com.yfy.app.PEquality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.dialog.CPWBean;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeAnswerListViewAdapter extends BaseAdapter {


    private Context context;
    private List<CPWBean> datas;

    public void setDatas(List<CPWBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public KnowledgeAnswerListViewAdapter(Context context) {
        this.datas = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.public_singe_checked_layout , null);
            viewHolder.name = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(datas.get(position).getName());
        viewHolder.name.setChecked(datas.get(position).isIs_select());

        return convertView;
    }

    public class ViewHolder{
        CheckedTextView name;
    }


}
