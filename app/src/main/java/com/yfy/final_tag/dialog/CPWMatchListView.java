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
import com.yfy.final_tag.listener.NoFastClickListener;

import java.util.List;


/**
 * 弹窗视图
 */
public class CPWMatchListView extends PopupWindow  {
	public Activity context;
	public ListView listview;
	public PopListAdapter adapter;
	public String type;
	public List<CPWBean> datas;
	public CPWMatchListView(Activity context, List<CPWBean> groups) {
		super(context);
		this.context = context;
		this.datas = groups;
		initalize();
	}


	private void initalize() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_pop_list_match_view, null);
		listview = view.findViewById(R.id.pop_list);//发起群聊
		View line_view = view.findViewById(R.id.pop_list_view);//发起群聊
		adapter=new PopListAdapter(context);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//获取选中的参数
//                int index = state_list .getCheckedItemPosition();     // 即获取选中位置
				if(ListView.INVALID_POSITION != position) {
					if (listener !=null){
						listener.popClick(datas.get(position),type);
					}
					dismiss();
				}
			}
		});
		line_view.setOnClickListener(new NoFastClickListener() {
			@Override
			public void fastClick(View view) {
				dismiss();

			}
		});
		setContentView(view);
		initWindow();
	}


	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 1.0));//width比例

		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.update();
		//实例化一个ColorDrawable颜色为半透明、
		ColorDrawable dw = new ColorDrawable(ColorRgbUtil.getResourceColor(context,R.color.public_transparent));
		//设置SelectPicPopupWindow弹出窗体的背景 不设置就是全透明：0
		this.setBackgroundDrawable(dw);
//		backgroundAlpha(context, 0f);//0.0-1.0
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha( context, 1f);
			}
		});
	}
	//设置添加屏幕的背景透明度
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
//		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}


	public void showAtBottom(View view) {

		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
	}
	public void showAtBottom(View view,String type) {
		//从新设置一个背景


		showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 0);
	}





	private PopClickListener listener = null;

	public void setOnPopClickListener(PopClickListener listener) {
		this.listener = listener;
	}





	//----------------------------data--------------------



	public class PopListAdapter extends BaseAdapter {

		public PopListAdapter(Context context) {


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
				convertView = LayoutInflater.from(context).inflate(R.layout.public_checked_textview , null);
				viewHolder.name = convertView.findViewById(android.R.id.text1);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.name.setText(datas.get(position).getName());
			if (datas.get(position).isIs_show()){
				viewHolder.name.setChecked(datas.get(position).isIs_select());
				datas.get(position).setIs_show(false);
			}

			return convertView;
		}
		public class ViewHolder{
			CheckedTextView name;
		}
	}
}