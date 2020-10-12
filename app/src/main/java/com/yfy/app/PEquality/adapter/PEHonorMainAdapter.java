package com.yfy.app.PEquality.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.PEHonorMainActivity;
import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.RegexUtils;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 */

public class PEHonorMainAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    private PEHonorMainActivity mActivity;
    public PEHonorMainAdapter(PEHonorMainActivity mContext) {
        super(mContext);
        this.mActivity = mContext;
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return dataList.get(position).getView_type();
    }
    @NonNull
    @Override
    public ReViewHolder initViewHolder( ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_honor_main_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_FLOW_TITLE) {
            return new TopH(inflater.inflate(R.layout.public_type_flow, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof TopH) {
            TopH topH = (TopH) holder;
            topH.bean = dataList.get(position);
            topH.title.setText(topH.bean.getTitle());
            topH.right_arrow.setVisibility(View.GONE);
            topH.setFlowLayoutTop(topH.bean.getCpwBeanArrayList());

        }
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.initDialogList();
            iHolder.left_title.setText(iHolder.bean.getLeft_title());
            iHolder.left_sub.setText(iHolder.bean.getTitle());
            iHolder.left_content.setText(iHolder.bean.getContent());
            iHolder.right_state.setText(iHolder.bean.getRight());
            iHolder.right_score.setText(iHolder.bean.getRight_value());
            ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,mContext.getResources().getColor(R.color.red));
            if (StringJudge.isEmpty(iHolder.bean.getListValue())){
//                iHolder.multi.setVisibility(View.GONE);
                List<String> list=new ArrayList<>();
                list.add(Base.user.getHeadPic());
                list.add(Base.user.getHeadPic());
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
                    iHolder.right_state.setTextColor(ColorRgbUtil.getOrangeRed());
                    iHolder.right_score.setVisibility(View.GONE);
                    iHolder.line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getOrangeRed());
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
            cpwListBeanView = new CPWListBeanView(mActivity);
            cpwListBeanView.setOnPopClickListenner(new CPWListBeanView.OnPopClickListenner() {
                @Override
                public void onClick(CPWBean cpwBean, String type) {
                    cpwListBeanView.dismiss();
                    bean.setRight(cpwBean.getName());
                    if(cpwBean.getName().equalsIgnoreCase("已通过")){
                        bean.setRight_value("20\t分");
                    }
                    mActivity.showProgressDialog("");
                    bg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.dismissProgressDialog();
                            notifyItemChanged(index,bean);
                        }
                    },1000);

                }
            });
        }


    }



    private class TopH extends ReViewHolder {
        AppCompatTextView title;
        AppCompatImageView right_arrow;
        FlowLayout flow;
        RelativeLayout layout;
        KeyValue bean;
        TopH(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.public_type_flow_layout);
            title =  itemView.findViewById(R.id.public_type_flow_title);
            right_arrow =  itemView.findViewById(R.id.public_type_flow_arrow);
            flow =  itemView.findViewById(R.id.public_type_flow_flow);
        }


        private void setFlowLayoutTop(List<CPWBean> top_jz){

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            for (CPWBean bean:top_jz){
                RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.public_detail_top_item,flow, false);
                TextView key=layout.findViewById(R.id.seal_detail_key);
                TextView value=layout.findViewById(R.id.seal_detail_value);
                RatingBar ratingBar=layout.findViewById(R.id.seal_detail_value_star);
                LinearLayout linearLayout=layout.findViewById(R.id.public_detail_txt_layout);
                MultiPictureView multi=layout.findViewById(R.id.public_detail_layout_multi);

                key.setTextColor(ColorRgbUtil.getGrayText());
                value.setTextColor(ColorRgbUtil.getBaseText());
                key.setText(bean.getName());
                switch (bean.getType()){
                    case "star":
                        linearLayout.setVisibility(View.VISIBLE);
                        multi.setVisibility(View.GONE);
                        if (bean.getValue().equals("")){
                            ratingBar.setRating(0f);
                        }else{
                            ratingBar.setRating(ConvertObjtect.getInstance().getFloat(bean.getValue()));
                        }
                        ratingBar.setVisibility(View.VISIBLE);
                        value.setVisibility(View.GONE);
                        break;
                    case "image":
                        linearLayout.setVisibility(View.GONE);
                        multi.setVisibility(View.VISIBLE);

//                        multi.clearItem();
//                        if (StringJudge.isEmpty(bean.getListValue())){
//                            multi.setVisibility(View.GONE);
//                        }else{
//                            multi.setVisibility(View.VISIBLE);
//                            multi.setList(bean.getListValue());
//                            multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
//                                @Override
//                                public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
//                                    Intent intent=new Intent(mContext, MultPicShowActivity.class);
//                                    Bundle b=new Bundle();
//                                    b.putStringArrayList(TagFinal.ALBUM_SINGE_URI,uris);
//                                    b.putInt(TagFinal.ALBUM_LIST_INDEX,index);
//                                    intent.putExtras(b);
//                                    mContext.startActivity(intent);
//                                }
//                            });
//                        }
                        break;
                    default:
                        linearLayout.setVisibility(View.VISIBLE);
                        multi.setVisibility(View.GONE);
                        value.setText(bean.getValue());
                        ratingBar.setVisibility(View.GONE);
                        value.setVisibility(View.VISIBLE);
                        break;
                }
                final String content=bean.getValue().trim();
                if (RegexUtils.isMobilePhoneNumber(content)){
                    SpannableString ss = new SpannableString(content);
                    ss.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View view) {

                            PermissionTools.tryTellPhone(mContext);
                        }
                    }, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3030")), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    value.setText(ss);
                    value.setMovementMethod(LinkMovementMethod.getInstance());
                }



                flow.addView(layout);
            }
        }



    }



}
