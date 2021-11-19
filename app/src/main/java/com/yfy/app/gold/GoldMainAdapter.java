package com.yfy.app.gold;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.charting.utils.Fill;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.tool.GreenDaoManager;
import com.yfy.greendao.tool.NormalDataSaveTools;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * Created by yfy1 on 2016/10/17.
 */
@SuppressLint("NonConstantResourceId")
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
            goldH.inidex=position;
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
        int inidex;
        KeyValueDb bean;
        GoldH(View itemView) {
            super(itemView);
            title=  itemView.findViewById(R.id.tv_date_main_adapter_gold);
            gram=  itemView.findViewById(R.id.tv_gram_num_main_adapter_gold);
            money=  itemView.findViewById(R.id.tv_money_num_main_adapter_gold);

            layout=itemView.findViewById(R.id.layout_main_adapter_gold);
            gram.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    if (bean.getType().equalsIgnoreCase("gold_embezzle")){
                        confirmContentWindow.setType(bean.getType());
                        showDialog("提示","是否确定已还");
                    }

                }
            });
            title.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    confirmContentWindow.setType("del");
                    showDialog("提示","是否删除");
                }
            });

        }
        public void setView(){
            initDialog();
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


        private ConfirmContentWindow confirmContentWindow;
        private void initDialog(){
            confirmContentWindow = new ConfirmContentWindow(mContext);
            confirmContentWindow.setPopClickListener(new NoFastClickListener() {
                @Override
                public void fastPopClick(View view, String type) {

                    switch (view.getId()){
                        case R.id.pop_dialog_title:
                            break;
                        case R.id.pop_dialog_ok:
                            confirmContentWindow.dismiss();
                            switch (type){
                                case "del":
                                    GreenDaoManager.getInstance().removeKeyValue(bean);
                                    if (intentStart!=null){
                                        Intent intent=new Intent();
                                        intentStart.startIntentAdapter(intent,"del");
                                    }
                                    break;
                                case "gold_embezzle":
                                    bean.setRank(TagFinal.TRUE);
                                    notifyItemChanged(inidex,bean);
                                    NormalDataSaveTools.getInstance().saveGoldData(bean,bean.getType());
                                    break;
                            }


                            break;
                    }
                }
            });
        }

        public void showDialog(String title,String content){
            if (confirmContentWindow==null)return;
            confirmContentWindow.setTitle_s(title,content);
            confirmContentWindow.showAtCenter();
        }


    }





    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }



}
