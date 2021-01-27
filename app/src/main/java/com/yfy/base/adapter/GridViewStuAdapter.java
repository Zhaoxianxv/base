package com.yfy.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewStuAdapter extends BaseAdapter{
	
	private List<KeyValue> keyValueList;
	private Context mContext;
	private LayoutInflater inflater;

	public void setKeyValueList(List<KeyValue> keyValueList) {
		this.keyValueList = keyValueList;
		notifyDataSetChanged();
	}

	public GridViewStuAdapter(Context context) {
		this.keyValueList =new ArrayList<>();
		this.mContext =context;
		inflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return keyValueList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.public_stu_gridview_item, null);
			holder= new ViewHolder();
			holder.icon= convertView.findViewById(R.id.stu_grid_head);
			holder.name = convertView.findViewById(R.id.stu_grid_name);
			holder.state = convertView.findViewById(R.id.stu_grid_state);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.bean=keyValueList.get(position);
		holder.name.setText(holder.bean.getName());

		return convertView;
	}
	

	
	
	
	class ViewHolder {
		AppCompatImageView icon;
		AppCompatTextView state;
		AppCompatTextView name;
		KeyValue bean;
        
    }

}
