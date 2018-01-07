package com.huoshan.api.huoshan.mine;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.TuiJianAdapter;
import com.huoshan.api.huoshan.erweimautils.MipcaActivityCapture;
import com.huoshan.api.huoshan.searchUtils.SearchActivity;
import com.huoshan.api.huoshan.utils.WebViewActivity;
import com.huoshan.api.huoshan.video.VideoApi;
import com.huoshan.api.huoshan.video.VideoPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteActivity extends AppCompatActivity implements VideoApi {
    private VideoPresenter videoPresenter;
    private ImageView acin_back;
      private ImageView acin_sou,iv;
      private LinearLayout acin_yao,acin_erwei,acin_sao,acin_tongxun,acin_xinlang;
      private RecyclerView acin_lv;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPresenter = new VideoPresenter(this);
        setContentView(R.layout.activity_invite);
        videoPresenter.getVideo();
        //初始化组件
        acin_back = findViewById(R.id.acin_back);
        acin_sou = findViewById(R.id.acin_sou);
        acin_yao = findViewById(R.id.acin_yao);
        acin_erwei = findViewById(R.id.acin_erwei);
        acin_sao = findViewById(R.id.acin_sao);
        acin_tongxun = findViewById(R.id.acin_tongxun);
        acin_xinlang = findViewById(R.id.acin_xinlang);
        acin_lv = findViewById(R.id.acin_lv);
        iv = findViewById(R.id.iv);
        //点击返回
        acin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviteActivity.this.finish();
            }
        });
        //点击搜索
        acin_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InviteActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        //点击邀请好友吧
        acin_yao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InviteActivity.this, InviteChildActivity.class);
                startActivity(intent);
            }
        });
        //生成二维码
        acin_erwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //跳转到二维码界面
                Intent intent = new Intent(InviteActivity.this, ErWeiMaActivity.class);
                startActivity(intent);
            }
        });
        //扫一扫--扫描二维码
        acin_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫描二维码
                Intent intent = new Intent(InviteActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
            }
        });
        //点击通讯录
        acin_tongxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击选择通讯录
                AlertDialog.Builder builder = new AlertDialog.Builder(InviteActivity.this);
                builder.setTitle("提示：").setMessage("确定从通讯录选择联系人？").setCancelable(true)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                //跳转到展示联系人的界面
                                Intent intent = new Intent(InviteActivity.this, AddressListActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        //跳转到新浪微博
        acin_xinlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //显示推荐的用户

    }

    //二维码的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCANNIN_GREQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                Log.i("xxx", result.toString());
                Intent intent = new Intent(InviteActivity.this, WebViewActivity.class);
                intent.putExtra("url", result);
                startActivity(intent);
            }
        }
    }
    //推荐用户展示
    @Override
    public void show(VideoBeans videoBeans) {
        List<VideoBeans.DataBeanX> list = videoBeans.getData();
        acin_lv.setLayoutManager(new LinearLayoutManager(this));
        TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(list, this);
        acin_lv.setAdapter(tuiJianAdapter);
    }
}
