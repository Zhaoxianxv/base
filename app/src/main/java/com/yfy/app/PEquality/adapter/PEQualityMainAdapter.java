package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;


public class PEQualityMainAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public PEQualityMainAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_main_gridview_item, parent, false));
        }
        return new ErrorHolder(inflater.inflate(R.layout.public_type_error, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.name.setText(iHolder.bean.getName());
            iHolder.icon.setImageResource(iHolder.bean.getRes_image());
            ViewTool.alterGradientDrawableColor(iHolder.icon,iHolder.bean.getRes_color());
            iHolder.count.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class ItemHolder extends ReViewHolder {

        TextView name;
        AppCompatImageView count;
        AppCompatImageView icon;
        RelativeLayout layout;
        int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            count =  itemView.findViewById(R.id.oa_item_admin_count);
            name =  itemView.findViewById(R.id.oa_item_name);
            icon =  itemView.findViewById(R.id.oa_item_icon_image);
            layout = itemView.findViewById(R.id.oa_item_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemOnc!=null){
                        itemOnc.onc(bean);
                    }
                }
            });
        }
    }






    public interface ItemOnc{
        void onc(KeyValue bean);
    }
    private ItemOnc itemOnc;
    public void setItemOnc(ItemOnc itemOnc) {
        this.itemOnc = itemOnc;
    }
}
