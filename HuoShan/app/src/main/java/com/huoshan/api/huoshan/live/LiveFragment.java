package com.huoshan.api.huoshan.live;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.Bean.LiveBean;
import com.huoshan.api.huoshan.MainActivity;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.LiveAdater;
import com.huoshan.api.huoshan.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2017/12/28.
 */

public class LiveFragment extends Fragment implements LiveApi{
    private View view;
    private Banner mLBanner;
    private RecyclerView mLRv;
    private List<String> images=new ArrayList<>();

    private ViewFlipper flipper;
    private List testList;
    private int count;
    private TextView big_img ;
    private TextView small_img;
    private LivePresenter lp;
    private SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_fragment, container, false);


        initView(view);
        /**
         *  轮播展示
         */
        mLBanner .setImageLoader(new GlideImageLoader());
        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3384628415,3416006593&fm=27&gp=0.jpg");
        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3331814356,2659951730&fm=27&gp=0.jpg");
        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3291318311,3527351425&fm=11&gp=0.jpg");
        //设置图片集合
        mLBanner .setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mLBanner .start();
        lp = new LivePresenter(this);
        lp.getLivePresenter();
        //跑马灯效果
        runLight();
        //切换布局
        big_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                mLRv.setLayoutManager(gridLayoutManager);

            }
        });
        small_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                mLRv.setLayoutManager(gridLayoutManager);

            }
        });
        //上拉刷新
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lp.getLivePresenter();
                        //停止刷新
                        swipe.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        return view;
    }

    private void runLight() {
        testList = new ArrayList();
        testList.add(0, "小时榜");
        testList.add(1, "上一小时榜");
        testList.add(2, "金主周日榜");
        testList.add(3, "金主日榜");
        testList.add(4, "金主昨日榜");
        count = testList.size();
        for (int i = 0; i < count; i++) {
            final View ll_content = View.inflate(getContext(), R.layout.item_flipper, null);
            TextView tv_content = (TextView) ll_content.findViewById(R.id.tv_content);
            LinearLayout iv_closebreak =ll_content.findViewById(R.id.iv_closebreak);
            SimpleDraweeView img1=ll_content.findViewById(R.id.if_img1);
            SimpleDraweeView img2=ll_content.findViewById(R.id.if_img2);
            SimpleDraweeView img3=ll_content.findViewById(R.id.if_img3);
            //图片一
            Uri uri = Uri.parse("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2450994032,3525797548&fm=27&gp=0.jpg");

            img1.setImageURI(uri);
            //图片二
            Uri uri2 = Uri.parse("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3746075707,1914896074&fm=27&gp=0.jpg");
            img2.setImageURI(uri2);
            //图片三
            Uri uri3 = Uri.parse("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1018364764,1223529536&fm=27&gp=0.jpg");
           img3.setImageURI(uri3);
            tv_content.setText(testList.get(i).toString());
            //点击
            iv_closebreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //对当前显示的视图进行移除
                    flipper.removeView(ll_content);
                    count--;
                    //当删除后仅剩 一条 新闻时，则取消滚动
                    if (count == 1) {
                        flipper.stopFlipping();
                    }
                }
            });
            flipper.addView(ll_content);
        }
    }

    private void initView(View view) {
        mLBanner = (Banner) view.findViewById(R.id.l_banner);
        mLRv = (RecyclerView) view.findViewById(R.id.l_rv);
        flipper=view.findViewById(R.id.flipper);
        big_img = view.findViewById(R.id.big_img);
        small_img = view.findViewById(R.id.small_img);
        swipe = view.findViewById(R.id.l_switch);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mLRv.setLayoutManager(gridLayoutManager);
    }


    @Override
    public void showData(LiveBean liveBean) {
        List<LiveBean.DataBeanX> data = liveBean.getData();
        LiveAdater la=new LiveAdater(data,getActivity());
        mLRv.setAdapter(la);
    }
}
