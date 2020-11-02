package com.yfy.app;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.PEquality.PEHonorMainActivity;
import com.yfy.app.PEquality.tea.PETeaScoreTypeActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedClassAdapter extends BaseRecyclerAdapter {



    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }



    public SelectedClassAdapter(SelectedClassActivity mContext){
        super(mContext);
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
                    //单/多选
                    Intent intent;
                    switch (bean.getType()){

                        case "成绩录入":
                            intent=new Intent(mContext,PETeaScoreTypeActivity.class  );
                            intent.putExtra(Base.title,bean.getTitle());
                            intent.putExtra(Base.type,bean.getType());
                            mContext.startActivity(intent);
                            break;
                        case "荣誉比赛":
                            intent=new Intent(mContext,PEHonorMainActivity.class);
                            intent.putExtra(Base.title,bean.getType());
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
                            default:
                                intent=new Intent(mContext,SelectStuActivity.class);
                                intent.putExtra(Base.index,1);
                                intent.putExtra(Base.title,"选择学生");
                                intent.putExtra(Base.type,bean.getType());
                                mContext.startActivity(intent);
                                break;
                    }
                }
            });

        }



    }










}
