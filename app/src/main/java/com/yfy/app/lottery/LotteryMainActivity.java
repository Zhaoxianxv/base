package com.yfy.app.lottery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yfy.app.SelectedClassActivity;
import com.yfy.app.SelectedModeTypeActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.app.duty_evaluate.DutyEvaluateTeaHonorMainActivity;
import com.yfy.app.view.ViewTypeSelectActivity;
import com.yfy.base.App;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.ConfirmContentWindow;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.greendao.tool.NormalDataSaveTools;
import com.yfy.upload.UploadDataService;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("NonConstantResourceId")
public class LotteryMainActivity extends BaseActivity {
    private static final String TAG = LotteryMainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottery_main);
        Logger.e(TAG);


        initSQToolbar();

    }


    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("选择用户类型");
        toolbar.addMenuText(TagFinal.ONE_INT,"zxx");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastMenuClick(View view, int position){
                startActivity(new Intent(mActivity, SelectedModeTypeActivity.class));
            }
        });


    }





    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }




}
