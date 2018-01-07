package com.huoshan.api.huoshan.searchUtils;

import com.huoshan.api.huoshan.Bean.SearchBean;

import io.reactivex.Observer;

/**
 * Created by lenovo on 2018/1/6.
 */

public interface SearchOnImm {
    void OnImm(Observer<SearchBean> observer);
}
