package com.huoshan.api.huoshan.owner;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.owner.fragment.MyLiveFragment;
import com.huoshan.api.huoshan.owner.fragment.MyVideoFragment;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

public class UserOwnerActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 昵称
     */
    private TextView mNick;
    private SimpleDraweeView mAvatar;
    /**
     * 49.2万
     */
    private TextView mFs;
    /**
     * 79
     */
    private TextView mCare;
    /**
     * 4.5万
     */
    private TextView mFire;
    /**
     * 唐山
     */
    private TextView mCity;
    /**
     * 男
     */
    private TextView mSex;
    /**
     * 85后
     */
    private TextView mYear;
    /**
     * 0
     */
    private TextView mSend;
    /**
     * decorition
     */
    private TextView mTitle;
    private LinearLayout mVideo;
    private LinearLayout mLive;
    private ViewPager mPager;
    private List<Fragment> flist = new ArrayList<>();
    private MyLiveFragment liveFragment = new MyLiveFragment();
    private MyVideoFragment videoFragment = new MyVideoFragment();
    private SharedPreferences sp;
    private ImageView mUoBack;
    /**
     * 昵称
     */
    private TextView mUoNick;
    private SimpleDraweeView mRiAvatar;
    /**
     * 49.2万
     */
    private TextView mUoFs;
    /**
     * 79
     */
    private TextView mUoCare;
    /**
     * 4.5万
     */
    private TextView mUoFire;
    /**
     * 关注
     */
    private Button mBtncare;
    /**
     * 唐山
     */
    private TextView mUoCity;
    /**
     * 男
     */
    private TextView mUoSex;
    /**
     * 85后
     */
    private TextView mUoYear;
    /**
     * 0
     */
    private TextView mUoSend;
    /**
     * decorition
     */
    private TextView mUoTitle;
    private LinearLayout mUoVideo;
    private LinearLayout mUoLive;
    private ViewPager mUoPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_owner);
        initView();
        flist.add(liveFragment);
        flist.add(videoFragment);
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return flist.get(position);
            }

            @Override
            public int getCount() {
                return flist.size();
            }
        });


        //赋值
        valueMethod();


    }

    private void valueMethod() {
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String nick = sp.getString("nick", null);
        mNick.setText(nick);
        String avatar = sp.getString("avatar", null);
        mAvatar.setImageURI(Uri.parse(avatar));
        String city = sp.getString("city", null);
        mCity.setText(city);
        String title = sp.getString("title", null);
        mTitle.setText(title);
        String birthday = sp.getString("birthday", null);
        mYear.setText(birthday);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.uo_back);
        mBack.setOnClickListener(this);
        mNick = (TextView) findViewById(R.id.uo_nick);
        mAvatar = (SimpleDraweeView) findViewById(R.id.ri_avatar);
        mFs = (TextView) findViewById(R.id.uo_fs);
        mCare = (TextView) findViewById(R.id.uo_care);
        mFire = (TextView) findViewById(R.id.uo_fire);
        mCity = (TextView) findViewById(R.id.uo_city);
        mSex = (TextView) findViewById(R.id.uo_sex);
        mYear = (TextView) findViewById(R.id.uo_year);
        mSend = (TextView) findViewById(R.id.uo_send);
        mTitle = (TextView) findViewById(R.id.uo_title);
        mVideo = (LinearLayout) findViewById(R.id.uo_video);
        mVideo.setOnClickListener(this);
        mLive = (LinearLayout) findViewById(R.id.uo_live);
        mLive.setOnClickListener(this);
        mPager = (ViewPager) findViewById(R.id.uo_pager);
        mUoBack = (ImageView) findViewById(R.id.uo_back);
        mUoNick = (TextView) findViewById(R.id.uo_nick);
        mRiAvatar = (SimpleDraweeView) findViewById(R.id.ri_avatar);
        mUoFs = (TextView) findViewById(R.id.uo_fs);
        mUoCare = (TextView) findViewById(R.id.uo_care);
        mUoFire = (TextView) findViewById(R.id.uo_fire);
        mBtncare = (Button) findViewById(R.id.uo_btncare);
        mBtncare.setOnClickListener(this);
        mUoCity = (TextView) findViewById(R.id.uo_city);
        mUoSex = (TextView) findViewById(R.id.uo_sex);
        mUoYear = (TextView) findViewById(R.id.uo_year);
        mUoSend = (TextView) findViewById(R.id.uo_send);
        mUoTitle = (TextView) findViewById(R.id.uo_title);
        mUoVideo = (LinearLayout) findViewById(R.id.uo_video);
        mUoLive = (LinearLayout) findViewById(R.id.uo_live);
        mUoPager = (ViewPager) findViewById(R.id.uo_pager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.uo_back:
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("nick");
                edit.remove("avatar");
                edit.remove("city");
                edit.remove("title");
                edit.remove("birthday");
                edit.commit();
                this.finish();
                break;
            case R.id.uo_video:
                mPager.setCurrentItem(0);
                break;
            case R.id.uo_live:
                mPager.setCurrentItem(1);
                break;
            case R.id.uo_btncare:
                String trim = mBtncare.getText().toString().trim();
                if(trim.equals("关注")){
                   mBtncare.setText("已关注");
                }else if(trim.equals("已关注")){
                    mBtncare.setText("关注");
                }
                break;
        }
    }
}
