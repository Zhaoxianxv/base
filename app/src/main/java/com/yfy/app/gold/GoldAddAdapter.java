package com.yfy.app.gold;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmDateWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.tool_textwatcher.MyWatcher;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressLint("NonConstantResourceId")
public class GoldAddAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    public List<KeyValue> getDataList() {
        return dataList;
    }


    public GoldAddActivity mContext;
    public GoldAddAdapter(GoldAddActivity mContext){
        super(mContext);
        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getView_type();
    }
    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TagFinal.TYPE_DATE) {
            View view = inflater.inflate(R.layout.public_type_choice_interval, parent, false);
            return new DateHolder(view);
        }
        if (viewType == TagFinal.TYPE_LONG_TXT_EDIT) {
            View view = inflater.inflate(R.layout.public_type_txt_long_edit_interval, parent, false);
            return new LongTxtEditHolder(view);
        }
        if (viewType == TagFinal.TYPE_TXT_EDIT) {
            View view = inflater.inflate(R.layout.public_type_txt_edit_interval, parent, false);
            return new TxtEditHolder(view);
        }

        return new ErrorHolder(parent);
    }
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof DateHolder) {
            DateHolder dateH = (DateHolder) holder;
            dateH.index_position=position;
            dateH.bean=dataList.get(position);
            dateH.setView();
        }

        if (holder instanceof LongTxtEditHolder) {
            LongTxtEditHolder long_edit = (LongTxtEditHolder) holder;
            long_edit.index=position;
            long_edit.bean=dataList.get(position);
            long_edit.setView();

        }

        if (holder instanceof TxtEditHolder) {
            TxtEditHolder txt_edit = (TxtEditHolder) holder;
            txt_edit.index=position;
            txt_edit.bean=dataList.get(position);
            txt_edit.setView();



        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    private class DateHolder extends ReViewHolder {
        TextView apply_name;
        TextView apply_value;
        KeyValue bean;
        int index_position;
        DateHolder(View itemView) {
            super(itemView);
            apply_name = itemView.findViewById(R.id.public_type_choice_key);
            apply_value = itemView.findViewById(R.id.public_type_choice_value);
            apply_value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.closeKeyWord();
                    dateDialog.showAtBottom();
                }
            });
        }
        private ConfirmDateWindow dateDialog;
        private void initDateDialog() {
            dateDialog = new ConfirmDateWindow(mContext);
            dateDialog.setOnPopClickListener(new NoFastClickListener() {
                @Override
                public void fastPopClick(View view) {
                    switch (view.getId()) {
                        case R.id.set:
                            DateBean dateBean=new DateBean();
                            dateBean.setValue_long(dateDialog.getTimeLong(),true);
                            bean.setRight_name(dateBean.getName());
                            bean.setRight_value(dateBean.getValue());

                            notifyItemChanged(index_position,bean);
                            dateDialog.dismiss();
                            break;
                        case R.id.cancel:
                            dateDialog.dismiss();
                            break;
                    }
                }
            });
        }


        public void setView(){
            initDateDialog();
            apply_name.setText(StringUtils.getTextJoint("%1$s",bean.getTitle()));
            if (StringJudge.isEmpty(bean.getRight_value())){
                apply_value.setTextColor(ColorRgbUtil.getGrayText());
                apply_value.setText(bean.getRight_key());
            }else {
                apply_value.setTextColor(ColorRgbUtil.getBaseText());
                apply_value.setText(bean.getRight_name());
            }
        }
    }

    public class LongTxtEditHolder extends ReViewHolder {
        TextView apply_name;
        EditText apply_edit;
        KeyValue bean;
        int index;

        LongTxtEditHolder(View itemView) {
            super(itemView);
            apply_name = itemView.findViewById(R.id.public_type_long_txt_edit_key);
            apply_edit = itemView.findViewById(R.id.public_type_long_txt_edit_value);
            apply_edit.addTextChangedListener(new MyWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    bean.setValue(s.toString().trim());
                }
            });
        }
        public void setView(){
            apply_name.setText(StringUtils.getTextJoint("%1$s",bean.getTitle()));
            if (StringJudge.isEmpty(bean.getValue())){
                apply_edit.setHint(bean.getKey());
                apply_edit.setText("");
            }else{
                apply_edit.setText(bean.getValue());
            }
        }
    }
    private class TxtEditHolder extends ReViewHolder {
        TextView apply_name;
        EditText apply_edit;
        KeyValue bean;
        int index;

        TxtEditHolder(View itemView) {
            super(itemView);
            apply_name = itemView.findViewById(R.id.public_type_txt_edit_key);
            apply_edit = itemView.findViewById(R.id.public_type_txt_edit_value);
            apply_edit.addTextChangedListener(new MyWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    bean.setValue(s.toString().trim());
                }
            });



        }

        public void setView(){
            ViewTool.editControlInputType(apply_edit,"numberSigned|numberDecimal");
            apply_name.setText(StringUtils.getTextJoint("%1$s\t",bean.getTitle()));
            if (StringJudge.isEmpty(bean.getValue())){
                apply_edit.setHint(bean.getKey());
                apply_edit.setText("");
            }else{
                apply_edit.setText(bean.getValue());
            }
        }
    }










    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }
}
