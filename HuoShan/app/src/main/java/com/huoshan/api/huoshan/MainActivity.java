package com.huoshan.api.huoshan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.huoshan.api.huoshan.utils.ViewPagerIndicator;

import com.huoshan.api.huoshan.Login.LoginApi;
import com.huoshan.api.huoshan.Login.LoginPresenter;
import com.huoshan.api.huoshan.LoginAfter.LoginAfterActivity;
import com.huoshan.api.huoshan.live.LiveFragment;
import com.huoshan.api.huoshan.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoginApi {



    //声明
    private List<Fragment> mFragmentList;
    private FragmentPagerAdapter orderPickingAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private TextView currentItem;
    private ViewPager m_pager;
    private TextView m_live;
    private TextView m_video;
    private ViewPagerIndicator indicator;
    private TextView mLr;
    private PopupWindow ppw;
    private EditText mob;
    private EditText pwd;
    private LoginPresenter lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         init();
        m_pager.setAdapter(orderPickingAdapter);
        m_pager.addOnPageChangeListener(onPageChangeListener);
        m_pager.setCurrentItem(0);
        currentItem = m_live;
        currentItem.setTextColor(Color.parseColor("#ffba00"));
        //实例化presenter
        lp = new LoginPresenter(this);
        //一进入应用查询数据库
        //查询数据库
        SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        String state = sp.getString("state", "1");
        //判断是否登录
        if("登录成功".equals(state)){
            //跳转到另外一个布局
            Intent intent = new Intent(MainActivity.this, LoginAfterActivity.class);
            startActivity(intent);
        }
    }

    private void init() {
        m_pager = findViewById(R.id.m_pager);

        m_live = findViewById(R.id.m_live);
        m_video = findViewById(R.id.m_video);
        indicator = findViewById(R.id.m_indicator);
        mLr = findViewById(R.id.m_lr);
        m_live.setOnClickListener(this);
        m_video.setOnClickListener(this);
        mLr.setOnClickListener(this);
        //初始化viewpager的item，并添加到list中
        mFragmentList = new ArrayList<>();
        LiveFragment lf = new LiveFragment();
        VideoFragment vf = new VideoFragment();
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
                indicator.scroll(i, v);
            }

            //选中监听，改变indicator文字颜色
            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        if (currentItem == m_live)
                            return;
                        m_live.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = m_live;
                        break;
                    case 1:
                        if (currentItem == m_video)
                            return;
                        m_video.setTextColor(Color.parseColor("#ffba00"));
                        currentItem.setTextColor(Color.parseColor("#646464"));
                        currentItem = m_video;
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
        switch (v.getId()){
            case R.id.m_live:
                m_pager.setCurrentItem(0);
                break;
            case R.id.m_video:
                m_pager.setCurrentItem(1);
                break;
            case R.id.m_lr:
                PopuInit();
                break;
        }
    }

    private void PopuInit() {
        View contentview= LayoutInflater.from(this).inflate(R.layout.login_popuwindow_layout,null);
        ppw = new PopupWindow(contentview,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ppw.setContentView(contentview);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）

        ppw.setWidth(width);

        TextView login=contentview.findViewById(R.id.l_login);
        TextView reg=contentview.findViewById(R.id.l_reg);
        mob = contentview.findViewById(R.id.l_mob);
        pwd = contentview.findViewById(R.id.l_pwd);
        //存入到数据库当中
        SharedPreferences mSharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String s = mob.getText().toString().trim();
        String p = pwd.getText().toString().trim();
        editor.putString("name",s);
        editor.putString("pwd",p);
        editor.commit();

        ImageView cha=contentview.findViewById(R.id.l_cha);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lp.getOnLogin(mob.getText().toString(), pwd.getText().toString());
            }
        });
        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ppw.dismiss();
            }
        });
        ppw.setContentView(contentview);
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        ppw.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showData(String str) {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        if(str.equals("登录成功")){
            //创建一个数据库并且存放到数据库当中
            SharedPreferences sharedPreferences = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putString("state", "登录成功");
            editor.commit();//提交修改

            Intent intent=new Intent(this,LoginAfterActivity.class);
            startActivity(intent);
            finish();

        }else{
           return;
        }
    }
}




