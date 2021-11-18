package com.yfy.app.PEquality.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yfy.app.album.SingePicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.tool_textwatcher.MyWatcher;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressLint("NonConstantResourceId")
public class PETeaWorkAddAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    public List<KeyValue> getDataList() {
        return dataList;
    }


    public PETeaWorkAddActivity mContext;
    public PETeaWorkAddAdapter(PETeaWorkAddActivity mContext){
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
        if (viewType == TagFinal.TYPE_IMAGE) {
            View view = inflater.inflate(R.layout.public_type_multi_add_interval, parent, false);
            return new MultiHolder(view);
        }
        return new ErrorHolder(parent);
    }
    @Override
    public void bindHolder(ReViewHolder holder, int position) {

        if (holder instanceof DateHolder) {
            DateHolder dateH = (DateHolder) holder;
            dateH.index_position=position;
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
                long_edit.apply_edit.setText("");
            }else{
                long_edit.apply_edit.setText(long_edit.bean.getValue());
            }
        }

        if (holder instanceof TxtEditHolder) {
            TxtEditHolder txt_edit = (TxtEditHolder) holder;
            txt_edit.index=position;
            txt_edit.bean=dataList.get(position);
            txt_edit.setView();
            txt_edit.apply_name.setText(StringUtils.getTextJoint("%1$s\t",txt_edit.bean.getTitle()));
            if (StringJudge.isEmpty(txt_edit.bean.getValue())){
                txt_edit.apply_edit.setHint(txt_edit.bean.getKey());
                txt_edit.apply_edit.setText("");
            }else{
                txt_edit.apply_edit.setText(txt_edit.bean.getValue());
            }


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
            ViewTool.editControlInputType(apply_edit,"numberSigned");
        }
    }
    private class MultiHolder extends ReViewHolder {
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
            album_select.setOnPopClickListener(new NoFastClickListener() {
                @Override
                public void fastPopClick(View view) {
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



    public interface SealChoice{
        void refresh(KeyValue bean, int position_index);
    }

    public SealChoice sealChoice;

    public void setSealChoice(SealChoice sealChoice) {
        this.sealChoice = sealChoice;
    }











    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }
}
