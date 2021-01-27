package com.yfy.app.duty_evaluate;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.TermBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.dialog.ConfirmDateWindow;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.greendao.KeyValueDb;
import com.yfy.greendao.NormalDataSaveTools;
import com.yfy.view.SQToolBar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;

public class PracticeAddActivity extends BaseActivity {
    private static final String TAG = PracticeAddActivity.class.getSimpleName();


    @BindView(R.id.p_e_honor_add_choose_date)
    AppCompatTextView select_date_tv;
    @BindView(R.id.p_e_honor_add_choose_course)
    AppCompatTextView select_rank_tv;
    @BindView(R.id.public_type_txt_edit_value)
    EditText honor_name_et;
    @BindView(R.id.practice_add_icon)
    AppCompatImageView honnor_icon_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_practice_add);
        selected_date_bean =new DateBean();
        selected_date_bean.setValue_long(System.currentTimeMillis(),true);
        select_date_tv.setText(selected_date_bean.getName());


        Logger.e(TAG);
        initSQToolbar();

        initDateDialog();
        initDialogList();
    }




    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("上传奖状");
        toolbar.addMenuText(TagFinal.ONE_INT,"提交");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {

                saveHonor();
            }
        });

    }


    private ConfirmDateWindow date_dialog;
    private void initDateDialog(){
        date_dialog = new ConfirmDateWindow(mActivity);
        date_dialog.setOnPopClickListenner(new ConfirmDateWindow.OnPopClickListenner() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.set:
                        selected_date_bean.setName(date_dialog.getTimeName());
                        selected_date_bean.setValue(date_dialog.getTimeValue());
                        select_date_tv.setText(selected_date_bean.getName());
                        select_date_tv.setTextColor(ColorRgbUtil.getBaseText());
                        date_dialog.dismiss();
                        break;
                    case R.id.cancel:
                        date_dialog.dismiss();
                        break;
                }

            }
        });
    }




    private CPWListBeanView cpwListBeanView;
    List<CPWBean> cpwBeans=new ArrayList<>();
    private void setCPWlListBeanData(){
        if (StringJudge.isEmpty(cpwBeans)){
            cpwBeans.add(new CPWBean("学校运动会",""));
            cpwBeans.add(new CPWBean("校区联赛",""));
            cpwBeans.add(new CPWBean("县级比赛",""));
            cpwBeans.add(new CPWBean("市级比赛",""));
            cpwBeans.add(new CPWBean("省级比赛",""));
            cpwBeans.add(new CPWBean("国家级比赛",""));
        }
        cpwListBeanView.setDatas(cpwBeans);

    }


    private void initDialogList(){
        cpwListBeanView = new CPWListBeanView(mActivity);
        cpwListBeanView.setOnPopClickListenner(new CPWListBeanView.OnPopClickListenner() {
            @Override
            public void onClick(CPWBean cpwBean,String type) {
                selected_rank=cpwBean.getName();
                select_rank_tv.setText(cpwBean.getName());
                select_rank_tv.setTextColor(ColorRgbUtil.getBaseText());
                cpwListBeanView.dismiss();
            }
        });
    }


    @OnClick(R.id.p_e_honor_add_choose_course)
    void setCourse(){
        setCPWlListBeanData();
        cpwListBeanView.showAtCenter();
    }
    @OnClick(R.id.p_e_honor_add_choose_date)
    void setDate(){
        date_dialog.showAtBottom();

    }






    public TermBean selected_term_bean;
    public DateBean selected_date_bean;
    public String selected_rank;
    public String honor_name_s;
    public String image_path;


    private void saveHonor(){


        if (StringJudge.isEmpty(selected_rank)){
            ViewTool.showToastShort(mActivity,"未选择获奖等级");
            return;
        }
        honor_name_s=honor_name_et.getText().toString().trim();
        if (StringJudge.isEmpty(honor_name_s)){
            ViewTool.showToastShort(mActivity,"获奖项目未填写！");
            return;
        }
        if (StringJudge.isEmpty(image_path)){
            ViewTool.showToastShort(mActivity,"电子奖状未上传！");
            return;
        }



        KeyValueDb keyValue = new KeyValueDb();
        keyValue.setFile_path(image_path);
        keyValue.setName(honor_name_s);
        NormalDataSaveTools.getInstance().saveHonor(keyValue);
    }

}
