package com.yfy.app.gold;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.greendao.KeyValueDb;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * Created by yfy1 on 2016/10/17.
 */
public class GoldMainAdapter extends BaseRecyclerAdapter {



    public List<KeyValueDb> dataList;


    public void setDataList(List<KeyValueDb> dataList) {
        this.dataList = dataList;

    }

    public GoldMainAdapter(Activity mContext){
        super(mContext);
        this.dataList = new ArrayList<>();

    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @NotNull
    @Override
    public ReViewHolder initViewHolder(@NonNull ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) return new GoldH(inflater.inflate(R.layout.gold_main_item, parent,false));


        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(@NonNull ReViewHolder holder, int position) {

        if (holder instanceof GoldH) {
            GoldH goldH = (GoldH) holder;
            goldH.bean=dataList.get(position);
            goldH.setView();

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class GoldH extends ReViewHolder {
        TextView title;
        TextView money;
        TextView gram;
        RelativeLayout layout;

        KeyValueDb bean;
        GoldH(View itemView) {
            super(itemView);
            title=  itemView.findViewById(R.id.tv_date_main_adapter_gold);
            gram=  itemView.findViewById(R.id.tv_gram_num_main_adapter_gold);
            money=  itemView.findViewById(R.id.tv_money_num_main_adapter_gold);

            layout=itemView.findViewById(R.id.layout_main_adapter_gold);
            layout.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    if (bean.getType().equalsIgnoreCase("gold_embezzle")) {
                        if (intentStart!=null){
                            Intent intent=new Intent();
                            intentStart.startIntentAdapter(intent,String.valueOf(bean.getId()));
                        }
                    }
                }
            });

        }
        public void setView(){
            title.setText(bean.getName());
            money.setText(bean.getValue());
            if (bean.getType().equalsIgnoreCase("gold_embezzle")){

                gram.setTextColor(Color.WHITE);
                if (bean.getRank().equalsIgnoreCase(TagFinal.TRUE)){
                    gram.setText("已还");
                    gram.setBackgroundColor(ColorRgbUtil.getResourceColor(mContext,R.color.Green));
                }else{
                    gram.setText("未还");
                    gram.setBackgroundColor(ColorRgbUtil.getResourceColor(mContext,R.color.main_red));
                }
            }else{

                gram.setTextColor(Color.BLACK);
                gram.setText(bean.getRank());
            }

        }
    }





    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }



}
