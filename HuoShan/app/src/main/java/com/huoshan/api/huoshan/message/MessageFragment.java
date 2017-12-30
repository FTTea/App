package com.huoshan.api.huoshan.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huoshan.api.huoshan.R;


/**
 * Created by lenovo on 2017/12/29.
 */

public class MessageFragment  extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.message_fragment,container,false);
        return  view;

    }
}
