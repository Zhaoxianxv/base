package com.yfy.app.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.video_jcv.JCVideoPlayer;
import com.yfy.video_jcv.JCVideoPlayerStandard;

import butterknife.Bind;


/**
 * Created by Nathen on 16/7/31.
 */
public class PlayDirectlyActivity extends AppCompatActivity {



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
        JCVideoPlayerStandard.startFullscreen(this, JCVideoPlayerStandard.class, url, title);



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
