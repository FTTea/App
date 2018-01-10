package com.huoshan.api.huoshan.owner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huoshan.api.huoshan.R;

/**
 * Created by lenovo on 2018/1/8.
 */

public class MyLiveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mylive_fragment,null);
        return view;
    }
}
