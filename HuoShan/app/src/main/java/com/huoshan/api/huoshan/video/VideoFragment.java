package com.huoshan.api.huoshan.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/28.
 */

public class VideoFragment extends Fragment implements VideoApi{
    private ImageView img;
    private RecyclerView lv;
    private VideoPresenter videoPresenter;
    private List<String> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPresenter = new VideoPresenter(this);
        videoPresenter.getVideo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment,container,false);
        img =view.findViewById(R.id.vf_tiimg);
        lv =view.findViewById(R.id.vf_rv);


        return view;
    }

    @Override
    public void show(VideoBeans videoBeans) {
         //设置
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        lv.setLayoutManager(gridLayoutManager);
        //设置值--创建适配器
        List<VideoBeans.DataBeanX> data = videoBeans.getData();
        VideoAdapter videoAdapter = new VideoAdapter(data, getActivity());
        lv.setAdapter(videoAdapter);
        videoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), VideoingActivity.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });
    }
}
