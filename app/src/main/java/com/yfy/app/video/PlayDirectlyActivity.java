package com.yfy.app.video;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.video_jcv.JCVideoPlayer;
import com.yfy.video_jcv.JCVideoPlayerStandard;


/**
 * Created by Nathen on 16/7/31.
 */
public class PlayDirectlyActivity extends BaseActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_main);
        getData();
    }

    public void getData(){
        Intent intent=getIntent();
        String url=intent.getStringExtra(Base.value);
        String title=intent.getStringExtra(Base.title);
        initSQToolbar(title);
        JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, url, title);



    }


    private void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);

    }



    //配置video_jvc
    @Override
    public void onBackPressed() {
        Logger.e("onBackPressed");
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.e("onPause");
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.e("onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
