package com.yfy.app.voice;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import com.yfy.base.R;


public class AudioRecorderAdapter extends ArrayAdapter<Recorder> {

	private List<Recorder> mDatas;
	private Context mContext;
	
	private int mMinItemWidth;
	private int mMaxItemWidth;

	private LayoutInflater mInflater;


	//
	public AudioRecorderAdapter(Context context, List<Recorder> datas) {
		super(context, -1, datas);
		mContext=context;
		mDatas=datas;
		
		mInflater=LayoutInflater.from(context);
		
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		mMaxItemWidth = (int) (outMetrics.widthPixels * 0.8f);
		mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
		
	}

	/* *
	 * ViewHolderģʽ
	 * (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.voicenotes_recorder_item, parent, false);
			
			holder=new ViewHolder();
			holder.currentTime=(TextView) convertView.findViewById(R.id.currentTime_text);
			holder.seconds=(TextView) convertView.findViewById(R.id.recorder_time);
			holder.mLength=convertView.findViewById(R.id.recorder_length);

			convertView.setTag(holder);
			
			//Recorder recorder = mDatas.get(position);
			//convertView.setTag(recorder.getmCurrentTime());
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		

		holder.currentTime.setText(getItem(position).getmCurrentTime());
		holder.seconds.setText(Math.round(getItem(position).getTime())+"\"");
		ViewGroup.LayoutParams lp=holder.mLength.getLayoutParams();
		lp.width=(int) (mMinItemWidth + (mMaxItemWidth/60f * getItem(position).getTime()));
		

		return convertView;
	}

	/**
	 * Item�ؼ�
	 * @author songshi
	 *
	 */
	private class ViewHolder {
		public TextView currentTime;
		public TextView seconds;
		public View mLength;

	}
	

}
