package com.huoshan.api.huoshan.frist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.frist.city.CityFragment;
import com.huoshan.api.huoshan.live.LiveFragment;
import com.huoshan.api.huoshan.utils.ViewPagerIndicator;
import com.huoshan.api.huoshan.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/29.
 */

public class FristFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView mSou;
    /**
     * 直播
     */
    private TextView mLive;
    /**
     * 视频
     */
    private TextView mVideo ;
    /**
     * 同城
     */
    private TextView mTongcheng;
    private ViewPagerIndicator mIndicator;
    private ViewPager mIdVp;
    private List<Fragment> mFragmentList;
    private FragmentPagerAdapter orderPickingAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private TextView currentItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frist_fragment, container, false);
        initView(view);
        mIdVp.setAdapter(orderPickingAdapter);
        mIdVp.addOnPageChangeListener(onPageChangeListener);
        mIdVp.setCurrentItem(0);
        currentItem = mLive;
        currentItem.setTextColor(Color.parseColor("#ffba00"));
        return view;
    }

    private void initView(View view) {
        mSou = (ImageView) view.findViewById(R.id.f_sou);
        mLive = (TextView) view.findViewById(R.id.f_live);
        mLive.setOnClickListener(this);
        mVideo = (TextView) view.findViewById(R.id.f_video);
        mVideo.setOnClickListener(this);
        mTongcheng = (TextView) view.findViewById(R.id.f_city);
        mTongcheng.setOnClickListener(this);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.f_indicator);
        mIdVp = (ViewPager) view.findViewById(R.id.f_view);

        //初始化viewpager的item，并添加到list中
        mFragmentList = new ArrayList<>();
        LiveFragment liveFragment =
                new LiveFragment();
        VideoFragment videoFragment =
                new VideoFragment();
      CityFragment loginedTongFragment = new CityFragment();
        mFragmentList.add(liveFragment);
        mFragmentList.add(videoFragment);
        mFragmentList.add(loginedTongFragment);

        orderPickingAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return  mFragmentList.size();
            }
        };
        //设置ViewPager监听事件
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            //滑动时，indicator下面的横线跟着滑动
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                mIndicator.scroll(i, v);
            }

            //选中监听，改变indicator文字颜色
            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        if (currentItem == mLive)
                            return;
                        mLive.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = mLive;
                        break;
                    case 1:
                        if (currentItem == mVideo)
                            return;
                        mVideo.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = mVideo;
                        break;
                    case 2:
                        if (currentItem == mTongcheng)
                            return;
                        mTongcheng.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = mTongcheng;
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.f_live:
                mIdVp.setCurrentItem(0);
                break;
            case R.id.f_video:
                mIdVp.setCurrentItem(1);
                break;
            case R.id.f_city:
                mIdVp.setCurrentItem(2);
                break;
        }
    }
}
