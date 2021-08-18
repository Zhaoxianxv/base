package com.yfy.final_tag.dialog;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.stringtool.TextToolSpan;

import java.util.ArrayList;
import java.util.List;


/**
 * 弹窗视图
 */
public class CPWMatchListMinWidthView extends PopupWindow  {
	public Activity context;
	public ListView listview;
	public PopListAdapter adapter;
	public String type;

	public CPWMatchListMinWidthView(Activity context) {
		super(context);
		this.context = context;
		initalize();
		type="";
	}


	private void initalize() {
		type="";
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.confirm_pop_list_normal_view, null);
		listview = view.findViewById(R.id.pop_list);//发起群聊
		adapter=new PopListAdapter();
		listview.setAdapter(adapter);
		listview.setOnItemClickListener((parent, view1, position, id) -> {
			//获取选中的参数
//                int index = state_list .getCheckedItemPosition();     // 即获取选中位置
			if(ListView.INVALID_POSITION != position) {
				if (listener !=null){
					listener.popClick(adapter.datas.get(position),type);
				}
				dismiss();
			}
		});
		setContentView(view);
		initWindow();
	}


	private void initWindow() {
		DisplayMetrics d = context.getResources().getDisplayMetrics();
		this.setWidth((int) (d.widthPixels * 0.4));//width比例
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
	private void backgroundAlpha(Activity context, float bgAlpha) {
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



	//----------------------------data--------------------



	public class PopListAdapter extends BaseAdapter {
		public List<CPWBean> datas;
		PopListAdapter() {
			this.datas = new ArrayList<>();
		}

		void setDatas(List<CPWBean> datas) {
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
			PopListAdapter.ViewHolder viewHolder ;
			if (convertView == null) {
				viewHolder = new PopListAdapter.ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.public_type_txt_single_select, null);
				viewHolder.name = convertView.findViewById(R.id.public_type_txt_name);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (PopListAdapter.ViewHolder)convertView.getTag();
			}
			CPWBean bean=datas.get(position);
			viewHolder.name.setText(bean.getName());
			if (bean.isIs_select()){
				TextToolSpan.$spannableAddIconColor(
						context,
						viewHolder.name,
						StringUtils.stringToGetTextJoint("0\t%1$s",bean.getName()),
						R.drawable.ic_arrow_play_right_black_24dp,
						ColorRgbUtil.getResourceColor(context,R.color.OrangeRed));
			}else {
				if (position%2==0){
					TextToolSpan.$spannableAddIconColor(
							context,
							viewHolder.name,
							StringUtils.stringToGetTextJoint("0\t%1$s",bean.getName()),
							R.drawable.ic_check_selected,
							ColorRgbUtil.getBaseColor());
				}else{
					TextToolSpan.$spannableAddIconColor(
							context,
							viewHolder.name,
							StringUtils.stringToGetTextJoint("0\t%1$s",bean.getName()),
							R.drawable.ic_check_unselect,
							ColorRgbUtil.getGray());
				}
			}
//			viewHolder.name.setText(sb);

//			if (bean.isIs_show()){
//				viewHolder.name.setChecked(bean.isIs_select());
//				bean.setIs_show(false);
//			}

			return convertView;
		}
		public class ViewHolder{
			TextView name;
		}
	}




	private PopClickListener listener = null;

	public void setOnPopClickListener(PopClickListener listener) {
		this.listener = listener;
	}




}