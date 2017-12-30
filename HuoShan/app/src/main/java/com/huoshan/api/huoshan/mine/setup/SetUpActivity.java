package com.huoshan.api.huoshan.mine.setup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huoshan.api.huoshan.MainActivity;
import com.huoshan.api.huoshan.R;

public class SetUpActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;
    private RelativeLayout mNumber;
    private RelativeLayout mErweima;
    private RelativeLayout mYaoqingzhuan;
    private RelativeLayout mLook;
    private RelativeLayout mTongzhi;
    private RelativeLayout mYinsi;
    private RelativeLayout mZhanghao;
    private RelativeLayout mRenzheng;
    private RelativeLayout mHuancun;
    private RelativeLayout mMainliu;
    private RelativeLayout mHuoshan;
    /**
     * 退出登录
     */
    private Button mTuichu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mNumber = (RelativeLayout) findViewById(R.id.se_number);
        mNumber.setOnClickListener(this);
        mErweima = (RelativeLayout) findViewById(R.id.se_erweima);
        mErweima.setOnClickListener(this);
        mYaoqingzhuan = (RelativeLayout) findViewById(R.id.se_yaoqingzhuan);
        mYaoqingzhuan.setOnClickListener(this);
        mLook = (RelativeLayout) findViewById(R.id.se_look);
        mLook.setOnClickListener(this);
        mTongzhi = (RelativeLayout) findViewById(R.id.se_tongzhi);
        mTongzhi.setOnClickListener(this);
        mYinsi = (RelativeLayout) findViewById(R.id.se_yinsi);
        mYinsi.setOnClickListener(this);
        mZhanghao = (RelativeLayout) findViewById(R.id.se_zhanghao);
        mZhanghao.setOnClickListener(this);
        mRenzheng = (RelativeLayout) findViewById(R.id.se_renzheng);
        mRenzheng.setOnClickListener(this);
        mHuancun = (RelativeLayout) findViewById(R.id.se_huancun);
        mHuancun.setOnClickListener(this);
        mMainliu = (RelativeLayout) findViewById(R.id.se_mainliu);
        mMainliu.setOnClickListener(this);
        mHuoshan = (RelativeLayout) findViewById(R.id.se_huoshan);
        mHuoshan.setOnClickListener(this);
        mTuichu = (Button) findViewById(R.id.se_tuichu);
        mTuichu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                //返回上一个界面
                SetUpActivity.this.finish();
                break;
            case R.id.se_number:
                break;
            case R.id.se_erweima:
                break;
            case R.id.se_yaoqingzhuan:
                break;
            case R.id.se_look:
                break;
            case R.id.se_tongzhi:
                break;
            case R.id.se_yinsi:
                break;
            case R.id.se_zhanghao:
                break;
            case R.id.se_renzheng:
                break;
            case R.id.se_huancun:
                break;
            case R.id.se_mainliu:
                break;
            case R.id.se_huoshan:
                break;
            case R.id.se_tuichu:
                //点击退出弹出框
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                AlertDialog al = alertDialogBuilder.setTitle("确定要退出吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //清楚数据库--一会在那边退出登录的时候清楚数据库
                                SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit(); editor.clear(); editor.commit();
                                //跳转到没有登录的首页
                                Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                SetUpActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .create();
                al.show();//将dialog显示出来
                break;
        }
    }
}
