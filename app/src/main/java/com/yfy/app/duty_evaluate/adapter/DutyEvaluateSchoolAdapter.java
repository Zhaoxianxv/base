package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by
 */

public class DutyEvaluateSchoolAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public DutyEvaluateSchoolAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }


    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
//        return dataList.get(position).getView_type();
        return TagFinal.TYPE_ITEM;
    }
    @NonNull
    @Override
    public ReViewHolder initViewHolder( ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_honor_main_item_layout, parent, false));
        }


        return new ErrorHolder(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {

        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.initDialogList();
            iHolder.left_title.setText(iHolder.bean.getLeft_title());
            iHolder.left_sub.setText(iHolder.bean.getTitle());
            iHolder.left_stu.setText(StringUtils.stringToGetTextJoint("学生%1$d",position));



            iHolder.left_content.setText(iHolder.bean.getContent());
            iHolder.right_state.setText(iHolder.bean.getRight());
            iHolder.right_score.setText(iHolder.bean.getRight_value());
            ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,mContext.getResources().getColor(R.color.red));
            if (StringJudge.isEmpty(iHolder.bean.getListValue())){
//                iHolder.multi.setVisibility(View.GONE);
                List<String> list=new ArrayList<>();
                list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603269209118&di=b3b911cc2c6b8e07f7ff9b163a58a641&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2404852592%2C1529663443%26fm%3D214%26gp%3D0.jpg");
                list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603269208912&di=b8d978a827eb0e04e906fbccc82505ab&imgtype=0&src=http%3A%2F%2Fwww.ps123.cn%2Fuploads%2Fallimg%2F140725%2F3_140725140431_1.jpg");
                iHolder.multi.setList(list);
            }else{
                iHolder.multi.setVisibility(View.VISIBLE);
                iHolder.multi.setList(iHolder.bean.getListValue());
            }
            switch (iHolder.bean.getRight()){
                case "已通过":
                    iHolder.right_score.setVisibility(View.VISIBLE);
                    iHolder.line.setVisibility(View.VISIBLE);
                    iHolder.line.setBackgroundColor(ColorRgbUtil.getForestGreen());
                    iHolder.right_state.setTextColor(ColorRgbUtil.getForestGreen());
                    iHolder.right_score.setTextColor(ColorRgbUtil.getForestGreen());
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getForestGreen());
                    break;
                case "已拒绝":
                    iHolder.right_state.setTextColor(ColorRgbUtil.getGray());
                    iHolder.right_score.setVisibility(View.GONE);
                    iHolder.line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getGray());
                    break;
                case "待审核":
                    iHolder.right_state.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.OrangeRed));
                    iHolder.right_score.setVisibility(View.GONE);
                    iHolder.line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getResourceColor(mContext,R.color.OrangeRed));
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    private class ItemHolder extends ReViewHolder {
        TextView left_title;
        TextView left_sub;
        TextView left_stu;
        TextView left_content;
        TextView right_state;
        TextView right_score;
        View line;
        MultiPictureView multi;
        LinearLayout bg;
        RelativeLayout layout;
        KeyValue bean;
        int index;
        ItemHolder(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.p_e_honor_item_layout);
            bg =  itemView.findViewById(R.id.p_e_honor_radio_group);
            line =  itemView.findViewById(R.id.p_e_honor_right_line);
            left_title =  itemView.findViewById(R.id.p_e_honor_left_title);
            left_sub =  itemView.findViewById(R.id.p_e_honor_left_sub);
            left_stu =  itemView.findViewById(R.id.p_e_honor_left_sub_name);
            left_content =  itemView.findViewById(R.id.p_e_honor_content);
            right_state =  itemView.findViewById(R.id.p_e_honor_right_state);
            right_score =  itemView.findViewById(R.id.p_e_honor_right_score);
            multi =  itemView.findViewById(R.id.p_e_honor_item_multi);
            multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
                @Override
                public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
                    Intent intent=new Intent(mContext, MultPicShowActivity.class);
                    Bundle b=new Bundle();
                    b.putStringArrayList(TagFinal.ALBUM_SINGE_URI,uris);
                    b.putInt(TagFinal.ALBUM_LIST_INDEX,index);
                    intent.putExtras(b);
                    mContext.startActivity(intent);
                }
            });
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.getType().equalsIgnoreCase(TagFinal.TRUE)){


                        setCPWlListBeanData(bean);
                    }
                }
            });
        }

        CPWListBeanView cpwListBeanView;
        List<CPWBean> cpwBeans=new ArrayList<>();
        private void setCPWlListBeanData(KeyValue bean){
            switch (bean.getRight()){
                case "已通过":
                   return;
                case "已拒绝":
                    return;
                case "待审核":
                    if (StringJudge.isEmpty(cpwBeans)){
                        List<String> list=StringUtils.listToStringSplitCharacters("已通过,已拒绝",",");
                        for(String s:list){
                            CPWBean cpwBean =new CPWBean();
                            cpwBean.setName(s);
                            cpwBean.setId(s);
                            cpwBeans.add(cpwBean);
                        }
                    }
                    cpwListBeanView.setDatas(cpwBeans);
                    cpwListBeanView.showAtCenter();
                    break;
            }


        }
        private void initDialogList(){
            cpwListBeanView = new CPWListBeanView(mContext);
            cpwListBeanView.setOnPopClickListener(new NoFastClickListener() {
                @Override
                public void fastPopClick(CPWBean cpwBean, String type) {
                    cpwListBeanView.dismiss();
                    bean.setRight(cpwBean.getName());
                    if(cpwBean.getName().equalsIgnoreCase("已通过")){
                        bean.setRight_value("20\t分");
                    }

                    bg.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            notifyItemChanged(index,bean);
                        }
                    },1000);

                }
            });
        }


    }



}
