package com.yfy.final_tag.dialog;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 弹窗视图
 */
public class CPWMatchListView extends PopupWindow  {
	private Activity context;
	public ListView listview;
	private PopListAdapter adapter;
	private String type;

	public CPWMatchListView(Activity context) {
		super(context);
		this.context = context;
		initalize();
	}


	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_list_macth_view, null);
		listview = view.findViewById(R.id.pop_list);//发起群聊
		adapter=new PopListAdapter(context);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//获取选中的参数
//                int index = state_list .getCheckedItemPosition();     // 即获取选中位置
				if(ListView.INVALID_POSITION != position) {
					if (listenner!=null){
						listenner.onClick(type, adapter.datas.get(position));
					}
					dismiss();
				}
			}
		});
		setContentView(view);
		initWindow();
	}


	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 0.3));//width比例

		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha( context, 0.5f);//弹出出后透明度
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha( context, 1f);//退出后透明
			}
		});
	}
	//设置添加屏幕的背景透明度
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}


	public void showAtBottom(View view, List<CPWBean> groups) {
		setData(groups);
		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
	}




	public void setData(List<CPWBean> groups){
		adapter.setDatas(groups);
	}

	private OnPopClickListenner listenner = null;

	public void setOnPopClickListenner(OnPopClickListenner listenner) {
		this.listenner = listenner;
	}

	public static interface OnPopClickListenner {
		public void onClick(String type, CPWBean bean);
	}




	//----------------------------data--------------------



	public class PopListAdapter extends BaseAdapter {
		private Context context;
		List<CPWBean> datas;
		PopListAdapter(Context context) {
			this.context = context;
			this.datas = new ArrayList<>();
		}

		public void setDatas(List<CPWBean> datas) {
			this.datas = datas;
			notifyDataSetChanged();
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
			PopListAdapter.ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new PopListAdapter.ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.public_checked_textview , null);
				viewHolder.name = convertView.findViewById(android.R.id.text1);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (PopListAdapter.ViewHolder)convertView.getTag();
			}
			CPWBean bean=datas.get(position);

			viewHolder.name.setText(bean.getName());

			if (bean.isIs_select()){
				viewHolder.name.setTextColor(ColorRgbUtil.getBaseColor());
			}else {
				if (position%2==0){
					viewHolder.name.setTextColor(ColorRgbUtil.getForestGreen());
				}else{
					viewHolder.name.setTextColor(ColorRgbUtil.getGray());
				}
			}
			if (bean.isIs_show()){
				viewHolder.name.setChecked(bean.isIs_select());
				bean.setIs_show(false);
			}

			return convertView;
		}
		public class ViewHolder{
			CheckedTextView name;
		}
	}
}