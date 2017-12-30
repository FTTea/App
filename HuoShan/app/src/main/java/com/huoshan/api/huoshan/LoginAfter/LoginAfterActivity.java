package com.huoshan.api.huoshan.LoginAfter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hjm.bottomtabbar.BottomTabBar;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.add.AddFragment;
import com.huoshan.api.huoshan.care.CareFragment;
import com.huoshan.api.huoshan.frist.FristFragment;
import com.huoshan.api.huoshan.message.MessageFragment;
import com.huoshan.api.huoshan.mine.MineFragment;

public class LoginAfterActivity extends AppCompatActivity {

    private BottomTabBar mBottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_after);
        initView();
        mBottomTabBar=(BottomTabBar)findViewById(R.id.bottom_tab_bar);

        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50,50)
                .setFontSize(8)
                .setTabPadding(4,6,10)
                .setChangeColor(Color.RED,Color.DKGRAY)
                .addTabItem("首页",R.mipmap.aeb, FristFragment.class)
                .addTabItem("关注", R.mipmap.ae_, CareFragment.class)
                .addTabItem("+",R.drawable.a9v, AddFragment.class)
                .addTabItem("消息",R.mipmap.aed, MessageFragment.class)
                .addTabItem("我的",R.mipmap.aef, MineFragment.class)

                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {

                    }
                });

    }


    private void initView() {
        mBottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
    }
}
