package com.huoshan.api.huoshan.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.ContentAdapter;

import java.util.List;

public class ContentFragment extends Fragment implements VideoApi{
    private VideoView videoView ;
    private VideoPresenter videoPresenter;
    private TextView tv,fc_name,fc_desc;
    private Button fc_guan;
    private SimpleDraweeView fc_img;
    private SwipeRefreshLayout refreshLayout;
    public ContentFragment() {
    }

    public static Fragment newInstance(String title, int position) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPresenter = new VideoPresenter(this);
        videoPresenter.getVideo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        videoView = view.findViewById(R.id.videoView);
        tv = view.findViewById(R.id.ving_tv);
       /* fc_img = view.findViewById(R.id.fc_img);
        fc_name = view.findViewById(R.id.fc_name);
        fc_guan = view.findViewById(R.id.fc_guan);
        fc_desc = view.findViewById(R.id.fc_desc);*/

        tv.setText("火力400");
        /*//按钮的点击事件
        fc_guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Toast.makeText(getActivity(),"关注成功",Toast.LENGTH_SHORT).show();
                  fc_guan.setVisibility(View.GONE);
            }
        });*/
        return view;
    }



    public String getTitle() {
        return getArguments().getString("title");
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }

    @Override
    public void show(VideoBeans videoBeans) {
        List<VideoBeans.DataBeanX> data = videoBeans.getData();
        String s = data.get(0).getData().getVideo().getUrl_list().get(0);
        MediaController mediaController = new MediaController(getContext());
        //播放视频
        mediaController.setVisibility(View.INVISIBLE);
        //设置视频控制器
        videoView.setMediaController(mediaController);
        //这里用相对布局包裹videoview 实现视频全屏播放
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        videoView.setLayoutParams(layoutParams);
        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(Uri.parse(s));

        //开始播放视频
        videoView.start();
        //设置值
        String s1 = data.get(0).getData().getAuthor().getAvatar_jpg().getUrl_list().get(0);
       /* Uri uri = Uri.parse(s1);
        fc_img.setImageURI(uri);
        String nickname = data.get(0).getData().getAuthor().getNickname();
        fc_name.setText(nickname);
        String ti = data.get(0).getData().getTitle();
        fc_desc.setText(ti);*/

    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( getActivity(), "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

}