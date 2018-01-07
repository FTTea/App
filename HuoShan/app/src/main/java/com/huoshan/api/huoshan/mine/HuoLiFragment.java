package com.huoshan.api.huoshan.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huoshan.api.huoshan.R;

/**
 * Created by geting on 2018/1/6.
 */

public class HuoLiFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout mVideoHuo;
    private LinearLayout mLiveHuo;
    private LinearLayout mOtherHuo;
    private RelativeLayout mYinghangka;
    private RelativeLayout mZhifubao;
    private RelativeLayout mWeixin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.huoli_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mVideoHuo = (LinearLayout) view.findViewById(R.id.video_huo);
        mVideoHuo.setOnClickListener(this);
        mLiveHuo = (LinearLayout) view.findViewById(R.id.live_huo);
        mLiveHuo.setOnClickListener(this);
        mOtherHuo = (LinearLayout) view.findViewById(R.id.other_huo);
        mOtherHuo.setOnClickListener(this);
        mYinghangka = (RelativeLayout) view.findViewById(R.id.yinghangka);
        mYinghangka.setOnClickListener(this);
        mZhifubao = (RelativeLayout) view.findViewById(R.id.zhifubao);
        mZhifubao.setOnClickListener(this);
        mWeixin = (RelativeLayout) view.findViewById(R.id.weixin);
        mWeixin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.video_huo:
                break;
            case R.id.live_huo:
                break;
            case R.id.other_huo:
                break;
            case R.id.yinghangka:
                break;
            case R.id.zhifubao:
                break;
            case R.id.weixin:
                break;
        }
    }
}
