package com.huoshan.api.huoshan.live.gift.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.live.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * 该Fragment用于对直播或观看直播的初始化
 * 直播的控件的创建以及销毁等等都可以在这里进行操作，这样就与我们自己的交互代码分离了
 *
 * Success is the sum of small efforts, repeated day in and day out.
 * 成功就是日复一日那一点点小小努力的积累。
 * AndroidGroup：158423375
 * Author：Johnny
 * AuthorQQ：956595454
 * AuthorWX：Qiang_it
 * AuthorPhone：nothing
 * Created by 2016/9/22.
 */
public class LiveViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=  inflater.inflate(R.layout.fragment_liveview, container, false);
        IjkVideoView mLiveView=view.findViewById(R.id.live_view);
        Bundle arguments = getArguments();
        String mVideoPath = arguments.getString("path");

// init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        if (mVideoPath != null) {
            mLiveView.setVideoPath(mVideoPath);
        }
        mLiveView.start();

        return view;
    }
}