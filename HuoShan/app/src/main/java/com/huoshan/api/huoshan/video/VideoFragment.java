package com.huoshan.api.huoshan.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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

public class VideoFragment extends Fragment implements VideoApi,SwipeRefreshLayout.OnRefreshListener{
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;
    private RecyclerView lv;
    private VideoPresenter videoPresenter;
    private List<String> list = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    private  VideoAdapter videoAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<VideoBeans.DataBeanX> data;

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

        lv =view.findViewById(R.id.vf_rv);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        initRefreshLayout();
        //initRecyclerView();
        return view;
    }
    //加载更多
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }


   @Override
    public void show(VideoBeans videoBeans) {
         //设置
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        lv.setLayoutManager(mLayoutManager);
        //设置值--创建适配器
         data = videoBeans.getData();
        videoAdapter = new VideoAdapter(data, getActivity(),true);
        lv.setAdapter(videoAdapter);
        lv.setItemAnimator(new DefaultItemAnimator());
        videoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), VideoingActivity.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });
        //设置加载更多
        lv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (videoAdapter.isFadeTips() == false && lastVisibleItem + 1 == videoAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(videoAdapter.getRealLastPosition(), videoAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (videoAdapter.isFadeTips() == true && lastVisibleItem + 2 == videoAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(videoAdapter.getRealLastPosition(), videoAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
   }
   /* private List<VideoBeans.DataBeanX> getDatas(final int firstIndex, final int lastIndex) {
       videoPresenter.getVideo();

        return resList;
    }*/

    public void updateRecyclerView(int fromIndex, int toIndex) {
        //List<VideoBeans.DataBeanX> datas = getDatas(fromIndex, toIndex);
         videoPresenter.getVideo();

        /*if (datas.size() > 0) {*/
           /* videoAdapter.updateList(datas, true);*/
       /* } else {
            videoAdapter.updateList(null, false);
        }*/
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        videoAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}