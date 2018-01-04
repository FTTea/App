package com.huoshan.api.huoshan.live;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.live.gift.ui.fragment.LiveViewFragment;
import com.huoshan.api.huoshan.live.gift.ui.fragment.MainDialogFragment;
import com.huoshan.api.huoshan.live.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LiveActivity extends AppCompatActivity {

    private IjkVideoView mLiveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        String  mVideoPath = getIntent().getStringExtra("stream_addr");
        String  nickname = getIntent().getStringExtra("name");

         //直播
        LiveViewFragment liveViewFragment = new LiveViewFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle=new Bundle();
        bundle.putString("path",mVideoPath);
        bundle.putString("name",nickname);
        liveViewFragment.setArguments(bundle);
        transaction.add(R.id.flmain, liveViewFragment);
        transaction.commit();

        new MainDialogFragment().show(getSupportFragmentManager(),"MainDialogFragment");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
