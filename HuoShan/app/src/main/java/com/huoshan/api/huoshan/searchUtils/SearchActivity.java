package com.huoshan.api.huoshan.searchUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.WorkSource;
import android.provider.UserDictionary;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huoshan.api.huoshan.Bean.SearchBean;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.adapter.RecommendUserAdatpter;
import com.huoshan.api.huoshan.erweimautils.MipcaActivityCapture;
import com.huoshan.api.huoshan.mine.AddressListActivity;
import com.huoshan.api.huoshan.mine.ErWeiMaActivity;
import com.huoshan.api.huoshan.utils.WebViewActivity;
import com.zhy.autolayout.AutoLayoutActivity;
import  com.huoshan.api.huoshan.searchUtils.utils.XCFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AutoLayoutActivity implements View.OnClickListener, SearchApi {

    /**
     * 搜索用户名或者火山号
     */
    private EditText mMsSs;
    private List<String> mNames=new ArrayList<>();
    /**
     * 取消
     */
    private TextView mMsQx;
    private LinearLayout mMsYao;
    private LinearLayout mMsErwei;
    private LinearLayout mMsSao;
    private RecyclerView mMsRv;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private RelativeLayout mMsPhone;
    /**
     * 搜索用户名或者火山号
     */
    private EditText mSs;
    /**
     * 取消
     */
    private TextView mCancle;
    private LinearLayout mYao;
    private LinearLayout mErwei;
    private LinearLayout mSao;

    private RelativeLayout mPhone;
    private RecyclerView mRv;
    /**
     * 取消
     */
    private TextView mMsCancle;
    private XCFlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search);
        initView();

        SearchPresenter sp = new SearchPresenter(this);
        sp.getSearch();


    }

    //流失布局
    private void initChildViews() {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for(int i = 0; i < mNames.size(); i ++) {
            TextView view = new TextView(this);
            view.setText(mNames.get(i));
            view.setTextSize(20f);
            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            mFlowLayout.addView(view, lp);
        }
    }

    private void initView() {

        mSs = findViewById(R.id.ms_ss);
        mSs.setOnClickListener(this);
        mCancle = findViewById(R.id.ms_cancle);
        mCancle.setOnClickListener(this);
        mYao = findViewById(R.id.ms_yao);
        mYao.setOnClickListener(this);
        mErwei = findViewById(R.id.ms_erwei);
        mErwei.setOnClickListener(this);
        mSao = findViewById(R.id.ms_sao);
        mSao.setOnClickListener(this);

        mPhone = findViewById(R.id.ms_phone);
        mPhone.setOnClickListener(this);
        mRv = findViewById(R.id.ms_rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mMsSs = (EditText) findViewById(R.id.ms_ss);
        mMsCancle = (TextView) findViewById(R.id.ms_cancle);
        mMsYao = (LinearLayout) findViewById(R.id.ms_yao);
        mMsErwei = (LinearLayout) findViewById(R.id.ms_erwei);
        mMsSao = (LinearLayout) findViewById(R.id.ms_sao);
        mFlowLayout= (XCFlowLayout) findViewById(R.id.ms_xc);

        mMsPhone = (RelativeLayout) findViewById(R.id.ms_phone);
        mMsRv = (RecyclerView) findViewById(R.id.ms_rv);
    }


    private void getPhone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：").setMessage("确定从通讯录选择联系人？").setCancelable(true)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //跳转到展示联系人的界面
                        Intent intent = new Intent(SearchActivity.this, AddressListActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    //二维码的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCANNIN_GREQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                Log.i("xxx", result.toString());
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", result);
                startActivity(intent);
            }
        }
    }

    @Override
    public void showUser(List<SearchBean.DataBean.RecommendUserBean> list) {
        RecommendUserAdatpter adatpter = new RecommendUserAdatpter(list, this);
        mRv.setAdapter(adatpter);
    }

    @Override
    public void showHotWords(List<SearchBean.DataBean.HotWordsBean> words) {
        for (int i=0;i<words.size();i++){

            SearchBean.DataBean.HotWordsBean hotWordsBean = words.get(i);
            mNames.add(hotWordsBean.getWord());
        }
        initChildViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ms_ss:
                break;
            case R.id.ms_cancle:
                this.finish();
                break;
            case R.id.ms_yao:
                break;
            case R.id.ms_erwei:
                Intent intent = new Intent(this, ErWeiMaActivity.class);
                startActivity(intent);
                break;
            case R.id.ms_sao:
                Intent intent2 = new Intent(this, MipcaActivityCapture.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent2, 1);
                break;
            case R.id.ms_phone:
                getPhone();
                break;
        }
    }
}
