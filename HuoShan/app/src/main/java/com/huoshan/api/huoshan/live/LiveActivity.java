package com.huoshan.api.huoshan.live;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.live.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LiveActivity extends AppCompatActivity {

    private IjkVideoView mLiveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initView();
        String  mVideoPath = getIntent().getStringExtra("stream_addr");

// init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        if (mVideoPath != null) {
            mLiveView.setVideoPath(mVideoPath);
        }
        mLiveView.start();
    }

    private void initView() {
        mLiveView = (IjkVideoView) findViewById(R.id.live_view);
    }
}
