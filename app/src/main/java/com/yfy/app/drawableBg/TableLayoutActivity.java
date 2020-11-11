package com.yfy.app.drawableBg;

import android.os.Bundle;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.List;

import butterknife.OnClick;


public class TableLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_table_layout);
    }

    @OnClick(R.id.p_e_standard_title)
    void setOn(){
        List<String> yearlist=StringUtils.listToStringSplitCharacters("9月,10月,11月,12月,8月",",");
        List<String> list=MathTool.randomLIstAtList(yearlist,3,2);
        Logger.e(String.valueOf(list.size())+"个");
//        for (String s:list){
////            Logger.e(s);
//        }
    }
}
