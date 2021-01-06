package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.bean.TermBean;
import com.yfy.base.R;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedTermAdapter extends BaseRecyclerAdapter {



    public List<TermBean> dataList;


    public void setDataList(List<TermBean> dataList) {
        this.dataList = dataList;

    }

    SelectedTermAdapter(Activity mContext){
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
        if (position == TagFinal.TYPE_ITEM) return new SelectedTermH(inflater.inflate(R.layout.selected_singe_item_layout, parent,false));


        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(@NonNull ReViewHolder holder, int position) {

        if (holder instanceof SelectedTermH) {
            SelectedTermH selectedTermH = (SelectedTermH) holder;
            selectedTermH.bean=dataList.get(position);
            selectedTermH.name.setText(selectedTermH.bean.getName());
//            if (selectedTermH.bean.getIsnow().equalsIgnoreCase("1")){
//                selectedTermH.name.setTextColor(ColorRgbUtil.getBaseColor());
//            }else{
//                selectedTermH.name.setTextColor(ColorRgbUtil.getGray());
//            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class SelectedTermH extends ReViewHolder {
        TextView name;
        TextView type;
        TermBean bean;
        SelectedTermH(View itemView) {
            super(itemView);
            name=  itemView.findViewById(R.id.selected_item_name);
            type=  itemView.findViewById(R.id.selected_item_type);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //单/多选
                    Intent intent=new Intent();
                    intent.putExtra(Base.data,bean);
                    mContext.setResult(RESULT_OK,intent);
                    mContext.finish();
                }
            });

        }
    }






}
