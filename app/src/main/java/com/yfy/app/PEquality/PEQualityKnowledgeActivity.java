package com.yfy.app.PEquality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.base.Base;


import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class PEQualityKnowledgeActivity extends BaseActivity {
    private static final String TAG = PEQualityKnowledgeActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_e_knowledge_main);
        getData();
        initSQToolbar();

    }


    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);

    }





    @OnClick(R.id.p_e_knowledge_library)
    void knowledgeLibrary(){
        Intent intent=new Intent(mActivity,PEQualityKnowledgeLibraryActivity.class);
        intent.putExtra(Base.title,"健康知识库");
        startActivity(intent);
    }
    @OnClick(R.id.p_e_knowledge_answer_start)
    void knowledgeAnswerStart(){

        Intent intent=new Intent(mActivity,PEQualityKnowledgeAnswerActivity.class);
        intent.putExtra(Base.title,"健康知识库");
        intent.putExtra(Base.type,TagFinal.TRUE);
        startActivity(intent);

    }
    @OnClick(R.id.p_e_knowledge_answer_end)
    void knowledgeAnswerEnd(){

        Intent intent=new Intent(mActivity,PEQualityKnowledgeAnswerActivity.class);
        intent.putExtra(Base.title,"健康知识库");
        intent.putExtra(Base.type,TagFinal.FALSE);
        startActivity(intent);

    }

    /**
     * ----------------------------retrofit-----------------------
     */


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
