package com.yfy.app;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedModeTypeAdapter extends BaseRecyclerAdapter {



    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }


    public SelectedModeTypeAdapter(Activity context) {
        super(context);
        this.dataList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            return new SelectedClassH(inflater.inflate(R.layout.selected_singe_item_layout, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {

        if (holder instanceof SelectedClassH) {
            SelectedClassH selectedTermH = (SelectedClassH) holder;
            selectedTermH.bean=dataList.get(position);
            selectedTermH.name.setText(selectedTermH.bean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class SelectedClassH extends ReViewHolder {
        TextView name;
        TextView type;
        KeyValue bean;
        SelectedClassH(View itemView) {
            super(itemView);
            name=  itemView.findViewById(R.id.selected_item_name);
            type=  itemView.findViewById(R.id.selected_item_type);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modeItem!=null){
                        modeItem.modeItem(bean.getTitle());
                    }

                }
            });
        }
    }



    public interface ModeItem{
        void modeItem(String name);
    }

    private ModeItem modeItem;

    public void setModeItem(ModeItem modeItem) {
        this.modeItem = modeItem;
    }
}
