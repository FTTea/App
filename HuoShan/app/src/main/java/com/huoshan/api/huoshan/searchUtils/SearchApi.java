package com.huoshan.api.huoshan.searchUtils;

import com.huoshan.api.huoshan.Bean.SearchBean;

import java.util.List;

/**
 * Created by lenovo on 2018/1/6.
 */

public interface SearchApi {
    void showUser(List<SearchBean.DataBean.RecommendUserBean> User);
    void showHotWords(List<SearchBean.DataBean.HotWordsBean> words);
}
