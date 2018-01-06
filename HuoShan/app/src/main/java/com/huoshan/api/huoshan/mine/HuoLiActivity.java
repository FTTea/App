package com.huoshan.api.huoshan.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.live.LiveFragment;
import com.huoshan.api.huoshan.utils.ViewPagerIndicator;
import com.huoshan.api.huoshan.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class HuoLiActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mHuoacBack;
    /**
     * 火力
     */
    private TextView mHuoacHuoli;
    /**
     * 钻石
     */
    private TextView mHuoacZuan;
    private ViewPagerIndicator mHuoliacIndicator;
    private ImageView mHuoacZhangdan;

    private ViewPager mHuoacPager;
    private List<Fragment> mFragmentList;
    private TextView currentItem;


    private FragmentPagerAdapter orderPickingAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo_li);
        initView();
        mHuoacPager.setAdapter(orderPickingAdapter);
        mHuoacPager.addOnPageChangeListener(onPageChangeListener);
        mHuoacPager.setCurrentItem(0);
        currentItem = mHuoacHuoli;
        currentItem.setTextColor(Color.parseColor("#ffba00"));
    }

    private void initView() {
        mHuoacBack = (ImageView) findViewById(R.id.huoac_back);
        mHuoacBack.setOnClickListener(this);
        mHuoacHuoli = (TextView) findViewById(R.id.huoac_huoli);
        mHuoacHuoli.setOnClickListener(this);
        mHuoacZuan = (TextView) findViewById(R.id.huoac_zuan);
        mHuoacZuan.setOnClickListener(this);
        mHuoliacIndicator = (ViewPagerIndicator) findViewById(R.id.huoliac_indicator);
        mHuoacZhangdan = (ImageView) findViewById(R.id.huoac_zhangdan);
        mHuoacZhangdan.setOnClickListener(this);
        mHuoacPager = (ViewPager) findViewById(R.id.huoac_pager);
        //初始化viewpager的item，并添加到list中
        mFragmentList = new ArrayList<>();
        HuoLiFragment lf = new HuoLiFragment();
        ZuanShiFragment vf = new ZuanShiFragment();
        mFragmentList.add(lf);
        mFragmentList.add(vf);

        orderPickingAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public Fragment getItem(int i) {
                return mFragmentList.get(i);
            }
        };
        //设置ViewPager监听事件
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            //滑动时，indicator下面的横线跟着滑动
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                mHuoliacIndicator.scroll(i, v);
            }

            //选中监听，改变indicator文字颜色
            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        if (currentItem == mHuoacHuoli)
                            return;
                        mHuoacHuoli.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = mHuoacHuoli;
                        break;
                    case 1:
                        if (currentItem == mHuoacZuan)
                            return;
                        mHuoacZuan.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = mHuoacZuan;
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
            case R.id.huoac_back:
                HuoLiActivity.this.finish();
                break;
            case R.id.huoac_huoli:
                mHuoacPager.setCurrentItem(0);
                break;
            case R.id.huoac_zuan:
                mHuoacPager.setCurrentItem(1);
                break;
            case R.id.huoac_zhangdan:
                break;
        }
    }
}
