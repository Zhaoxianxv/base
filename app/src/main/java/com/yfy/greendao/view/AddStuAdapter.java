package com.yfy.greendao.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.SelectStuActivity;
import com.yfy.app.album.SingePicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.dialog.ConfirmDateAndTimeWindow;
import com.yfy.final_tag.dialog.ConfirmDateWindow;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.tool_textwatcher.MyWatcher;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 */
public class AddStuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<KeyValue> dataList;
    private BaseActivity mContext;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    public List<KeyValue> getDataList() {
        return dataList;
    }


    private void addItemData(int position, List<KeyValue> two){
        dataList.addAll(position+1, two);
        notifyItemRangeInserted(position, two.size());
    }
    private void removeItemData(String id) {
        for (int i = 0; i < dataList.size(); i++) {
            KeyValue remove=dataList.get(i);
            if (id.equalsIgnoreCase(remove.getId())){
                dataList.remove(i);
                notifyItemRangeRemoved(i, 1);

                removeItemData(id);
                return;
            }
        }
    }



    public AddStuAdapter(BaseActivity mContext){
        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getView_type();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TagFinal.TYPE_DATE_TIME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_choice_interval, parent, false);
            return new DateTimeHolder(view);
        }
        if (viewType == TagFinal.TYPE_DATE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_choice_interval, parent, false);
            return new DateHolder(view);
        }
        if (viewType == TagFinal.TYPE_SELECT_SINGLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_choice_interval, parent, false);
            return new ChoiceListHolder(view);
        }
        if (viewType == TagFinal.TYPE_SELECT_STU) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_choice_interval, parent, false);
            return new SelectStuHolder(view);
        }
        if (viewType == TagFinal.TYPE_LONG_TXT_EDIT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_txt_long_edit_interval, parent, false);
            return new LongTxtEditHolder(view);
        }
        if (viewType == TagFinal.TYPE_TXT_EDIT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_txt_edit_interval, parent, false);
            return new TxtEditHolder(view);
        }
        if (viewType == TagFinal.TYPE_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_multi_add_interval, parent, false);
            return new MultiHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DateHolder) {
            DateHolder dateH = (DateHolder) holder;
            dateH.index_position=position;
            dateH.initDateDialog();
            dateH.bean=dataList.get(position);
            dateH.apply_name.setText(StringUtils.getTextJoint("%1$s",dateH.bean.getTitle()));
            if (StringJudge.isEmpty(dateH.bean.getRight_value())){
                dateH.apply_value.setTextColor(ColorRgbUtil.getGrayText());
                dateH.apply_value.setText(dateH.bean.getRight_key());
            }else {
                dateH.apply_value.setTextColor(ColorRgbUtil.getBaseText());
                dateH.apply_value.setText(dateH.bean.getRight_name());
            }
        }
        if (holder instanceof SelectStuHolder) {
            SelectStuHolder stuH = (SelectStuHolder) holder;
            stuH.index_position=position;
            stuH.bean=dataList.get(position);
            stuH.apply_name.setText(StringUtils.getTextJoint("%1$s",stuH.bean.getTitle()));
            if (StringJudge.isEmpty(stuH.bean.getRight_value())){
                stuH.apply_value.setTextColor(ColorRgbUtil.getGrayText());
                stuH.apply_value.setText(stuH.bean.getRight_key());
            }else {
                stuH.apply_value.setTextColor(ColorRgbUtil.getBaseText());
                stuH.apply_value.setText(stuH.bean.getRight_name());
            }
        }
        if (holder instanceof DateTimeHolder) {
            DateTimeHolder dateTimeH = (DateTimeHolder) holder;
            dateTimeH.index_position=position;
            dateTimeH.initDateTimeDialog();
            dateTimeH.bean=dataList.get(position);
            dateTimeH.apply_name.setText(StringUtils.getTextJoint("%1$s",dateTimeH.bean.getTitle()));
            if (StringJudge.isEmpty(dateTimeH.bean.getRight_value())){
                dateTimeH.apply_value.setTextColor(ColorRgbUtil.getGrayText());
                dateTimeH.apply_value.setText(dateTimeH.bean.getRight_key());
            }else {
                dateTimeH.apply_value.setTextColor(ColorRgbUtil.getBaseText());
                dateTimeH.apply_value.setText(dateTimeH.bean.getRight_name());
            }
        }
        if (holder instanceof ChoiceListHolder) {
            ChoiceListHolder listH = (ChoiceListHolder) holder;
            listH.initListDialog();
            listH.index_position=position;
            listH.bean=dataList.get(position);
            listH.apply_name.setText(listH.bean.getTitle());
            if (StringJudge.isEmpty(listH.bean.getRight_value())){
                listH.apply_value.setTextColor(ColorRgbUtil.getGrayText());
                listH.apply_value.setText(listH.bean.getRight_key());
            }else{
                listH.apply_value.setTextColor(ColorRgbUtil.getBaseText());
                listH.apply_value.setText(listH.bean.getRight_name());
            }
        }

        if (holder instanceof MultiHolder) {
            MultiHolder multiH = (MultiHolder) holder;
            multiH.position_index=position;
            multiH.bean=dataList.get(position);
            multiH.multi_name.setText(multiH.bean.getTitle());
            multiH.multi.setVisibility(View.VISIBLE);
            multiH.multi.setList(multiH.bean.getListValue());
        }


        if (holder instanceof LongTxtEditHolder) {
            LongTxtEditHolder long_edit = (LongTxtEditHolder) holder;
            long_edit.index=position;
            long_edit.bean=dataList.get(position);
            long_edit.apply_name.setText(StringUtils.getTextJoint("%1$s",long_edit.bean.getTitle()));
            if (StringJudge.isEmpty(long_edit.bean.getValue())){
                long_edit.apply_edit.setHint(long_edit.bean.getKey());
            }else{
                long_edit.apply_edit.setText(long_edit.bean.getValue());
            }
        }
        if (holder instanceof TxtEditHolder) {
            TxtEditHolder txt_edit = (TxtEditHolder) holder;
            txt_edit.index=position;
            txt_edit.bean=dataList.get(position);
            txt_edit.apply_name.setText(StringUtils.getTextJoint("%1$s\t",txt_edit.bean.getTitle()));
            if (StringJudge.isEmpty(txt_edit.bean.getValue())){
                txt_edit.apply_edit.setHint(txt_edit.bean.getKey());
            }else{
                txt_edit.apply_edit.setText(txt_edit.bean.getValue());
            }
            List<String> listOne=StringUtils.listToStringSplitCharacters(txt_edit.bean.getType(), "_");
            switch (""){
                case "txt":
                    txt_edit.apply_edit.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "int":
                    txt_edit.apply_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case "decimal":
                    if (listOne.size()!=1){
                        txt_edit.apply_edit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
                    }else{
                        txt_edit.apply_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    break;
                    default:
                        break;
            }
            if (txt_edit.bean.getIs_edit().equalsIgnoreCase(TagFinal.FALSE)){
                txt_edit.apply_edit.setFocusable(false);
            }else{
                txt_edit.apply_edit.setFocusable(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    private class DateHolder extends RecyclerView.ViewHolder {
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
            dateDialog.setOnPopClickListenner(new ConfirmDateWindow.OnPopClickListenner() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.set:
                            bean.setRight_name(dateDialog.getTimeName());
                            bean.setRight_value(dateDialog.getTimeValue());
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
    }
    private class DateTimeHolder extends RecyclerView.ViewHolder {
        TextView apply_name;
        TextView apply_value;
        KeyValue bean;
        int index_position;

        DateTimeHolder(View itemView) {
            super(itemView);
            apply_name = itemView.findViewById(R.id.public_type_choice_key);
            apply_value = itemView.findViewById(R.id.public_type_choice_value);
            apply_value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.closeKeyWord();
                    dateAndTimeDialog.showAtBottom();

                }
            });
        }

        private ConfirmDateAndTimeWindow dateAndTimeDialog;
        private void initDateTimeDialog() {
            dateAndTimeDialog = new ConfirmDateAndTimeWindow(mContext);
            dateAndTimeDialog.setOnPopClickListenner(new ConfirmDateAndTimeWindow.OnPopClickListenner() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.set:
                            bean.setRight_name(dateAndTimeDialog.getTimeName());
                            bean.setRight_value(dateAndTimeDialog.getTimeValue());
                            notifyItemChanged(index_position,bean);
                            dateAndTimeDialog.dismiss();
                            break;
                        case R.id.cancel:
                            dateAndTimeDialog.dismiss();
                            break;
                    }
                }
            });
        }
    }
    private class SelectStuHolder extends RecyclerView.ViewHolder {
        TextView apply_name;
        TextView apply_value;
        KeyValue bean;
        int index_position;
        SelectStuHolder(View itemView) {
            super(itemView);
            apply_name=  itemView.findViewById(R.id.public_type_choice_key);
            apply_value=  itemView.findViewById(R.id.public_type_choice_value);
            apply_value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.closeKeyWord();
                    Intent intent=new Intent(mContext,SelectStuActivity.class);
                    intent.putExtra(Base.index,index_position);
                    intent.putExtra(Base.title,"选择学生");
                    intent.putExtra(Base.type,bean.getType());
                    mContext.startActivityForResult(intent,TagFinal.UI_REFRESH);
                }
            });
        }



    }
    private class ChoiceListHolder extends RecyclerView.ViewHolder {
        TextView apply_name;
        TextView apply_value;
        KeyValue bean;
        int index_position;
        ChoiceListHolder(View itemView) {
            super(itemView);
            apply_name=  itemView.findViewById(R.id.public_type_choice_key);
            apply_value=  itemView.findViewById(R.id.public_type_choice_value);
            apply_value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.closeKeyWord();
                    setDialogData(StringUtils.listToStringSplitCharacters(bean.getContent(),","));
                }
            });
        }

        private void setDialogData(List<String> types){
            cpwListView.setType("type");
            if (StringJudge.isEmpty(types)){
                return;
            }else{
                dialog_name_list.clear();
                for (String s:types){
                    CPWBean cpwBean=new CPWBean();
                    cpwBean.setValue(s);
                    cpwBean.setName(s);
                    dialog_name_list.add(cpwBean);
                }
                cpwListView.setDatas(dialog_name_list);
                cpwListView.showAtCenter();
            }
        }
        private CPWListBeanView cpwListView;
        private List<CPWBean> dialog_name_list=new ArrayList<>();
        private void initListDialog(){
            cpwListView = new CPWListBeanView(mContext);
            cpwListView.setOnPopClickListenner(new CPWListBeanView.OnPopClickListenner() {
                @Override
                public void onClick(CPWBean cpwBean, String type) {
                    switch (type){
                        case "type":
                            bean.setRight_name(cpwBean.getName());
                            bean.setRight_value(cpwBean.getValue());



                            if (bean.getGroup_id().equalsIgnoreCase(TagFinal.TRUE)){
                                //是否关联数据
                                removeItemData("choice");
                                List<KeyValue> list=new ArrayList<>();
                                KeyValue three=new KeyValue(TagFinal.TYPE_TXT_EDIT);
                                three.setTitle(cpwBean.getName());
                                three.setKey("未打分");
                                three.setValue("88");
                                three.setType("choice");
                                three.setId("choice");
                                list.add(three);
                                addItemData(index_position,list);
                            }else{
                                removeItemData("choice");
                                List<KeyValue> list=new ArrayList<>();
                                if (cpwBean.getName().equalsIgnoreCase("其他")){
                                    list.clear();
                                    KeyValue three=new KeyValue(TagFinal.TYPE_LONG_TXT_EDIT);
                                    three.setTitle("其他扣分说明");
                                    three.setKey("输入扣分说明");
                                    three.setValue("");
                                    three.setType("choice");
                                    three.setId("choice");
                                    KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
                                    one.setTitle("其他扣分");
                                    one.setKey("扣分分数");
                                    one.setValue("");
                                    one.setType("choice");
                                    one.setId("choice");

                                    list.add(three);
                                    list.add(one);
                                    addItemData(index_position,list);

                                }
                                if (cpwBean.getName().equalsIgnoreCase("已通过")){
                                    list.clear();
                                    KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
                                    one.setTitle("荣誉打分");
                                    one.setKey("荣誉打分");
                                    one.setValue("");
                                    one.setType("choice");
                                    one.setId("choice");
                                    list.add(one);
                                    addItemData(index_position,list);
                                }
                                notifyDataSetChanged();
                            }
                            cpwListView.dismiss();
                            break;
                    }
                }
            });
        }
    }
    private class LongTxtEditHolder extends RecyclerView.ViewHolder {
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
    }
    private class TxtEditHolder extends RecyclerView.ViewHolder {
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
    }
    private class MultiHolder extends RecyclerView.ViewHolder {
        TextView multi_name;
        MultiPictureView multi;
        KeyValue bean;
        int position_index;

        MultiHolder(View itemView) {
            super(itemView);
            multi_name = itemView.findViewById(R.id.public_add_multi_name);
            multi = itemView.findViewById(R.id.public_add_multi);
            initAbsListView();
            initDialog();

        }

        ConfirmAlbumWindow album_select;
        private void initDialog() {
            album_select = new ConfirmAlbumWindow(mContext);
            album_select.setTwo_select(mContext.getResources().getString(R.string.album));
            album_select.setOne_select(mContext.getResources().getString(R.string.take_photo));
            album_select.setName(mContext.getResources().getString(R.string.upload_type));
            album_select.setOnPopClickListenner(new ConfirmAlbumWindow.OnPopClickListenner() {
                @Override
                public void onClick(View view) {
                    if (sealChoice!=null){
                        sealChoice.refresh(bean, position_index);
                    }

                    switch (view.getId()) {
                        case R.id.popu_select_one:
                            PermissionTools.tryCameraPerm(mContext);
                            break;
                        case R.id.popu_select_two:
                            PermissionTools.tryWRPerm(mContext);
                            break;
                    }
                }
            });
        }
        private void initAbsListView() {
            multi.setAddClickCallback(new MultiPictureView.AddClickCallback() {
                @Override
                public void onAddClick(View view) {
                    mContext.closeKeyWord();
                    album_select.showAtBottom();
                }
            });
            multi.setClickable(false);
            multi.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
                @Override
                public void onDeleted(@NotNull View view, int index) {
                    multi.removeItem(index,true);
                    bean.getListValue().remove(index);
                    notifyItemChanged(position_index, bean);
                }
            });
            multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
                @Override
                public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
                    Intent intent=new Intent(mContext, SingePicShowActivity.class);
                    Bundle b=new Bundle();
                    b.putString(TagFinal.ALBUM_SINGE_URI,uris.get(index));
                    intent.putExtras(b);
                    mContext.startActivity(intent);
                }
            });
        }
    }
    /**
     * 设置上拉加载状态
     *
     * @param loadState 1.正在加载 2.加载完成 3.加载到底
     */
    public void setLoadState(int loadState) {
        notifyDataSetChanged();
    }





    public interface SealChoice{
        void refresh(KeyValue bean, int position_index);
    }

    public SealChoice sealChoice;

    public void setSealChoice(SealChoice sealChoice) {
        this.sealChoice = sealChoice;
    }
}
