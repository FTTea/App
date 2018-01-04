package com.huoshan.api.huoshan.frist.city;

import android.content.Intent;
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

import com.huoshan.api.huoshan.Bean.CityBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.CityAdapter;
import com.huoshan.api.huoshan.adapter.VideoAdapter;
import com.huoshan.api.huoshan.video.VideoPresenter;
import com.huoshan.api.huoshan.video.VideoingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/29.
 */

public class CityFragment extends Fragment implements CityApi,SwipeRefreshLayout.OnRefreshListener{
    private CityPresenter cityPresenter;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;
    private RecyclerView lv;
    private List<String> list = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;
    private GridLayoutManager mLayoutManager;
    private CityAdapter cityAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<CityBean.DataBeanX> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityPresenter = new CityPresenter(this);
        cityPresenter.getCity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_city,container,false);
        refreshLayout = view.findViewById(R.id.refreshLayout1);
        lv = view.findViewById(R.id.fc_lv);
        initRefreshLayout();
         return view;
    }
    //加载更多
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void show(CityBean cityBean) {
        //设置
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        lv.setLayoutManager(mLayoutManager);
        //设置值--创建适配器
        data = cityBean.getData();
        cityAdapter = new CityAdapter(data, getActivity(),true);
        lv.setAdapter(cityAdapter);
        lv.setItemAnimator(new DefaultItemAnimator());
        cityAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CityingActivity.class);
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
                    if (cityAdapter.isFadeTips() == false && lastVisibleItem + 1 == cityAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(cityAdapter.getRealLastPosition(), cityAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (cityAdapter.isFadeTips() == true && lastVisibleItem + 2 == cityAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(cityAdapter.getRealLastPosition(), cityAdapter.getRealLastPosition() + PAGE_COUNT);
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
    public void updateRecyclerView(int fromIndex, int toIndex) {
        //List<VideoBeans.DataBeanX> datas = getDatas(fromIndex, toIndex);
        cityPresenter.getCity();

        /*if (datas.size() > 0) {*/
           /* videoAdapter.updateList(datas, true);*/
       /* } else {
            videoAdapter.updateList(null, false);
        }*/
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        cityAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
