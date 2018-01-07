package com.huoshan.api.huoshan.searchUtils;

import com.huoshan.api.huoshan.Bean.SearchBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lenovo on 2018/1/6.
 */

public class SearchPresenter {
   private SearchApi sa;
   private SearchModel sm;

    public SearchPresenter(SearchApi sa) {
        this.sa = sa;
        sm=new SearchModel();
    }
    public void getSearch(){
        sm.OnImm(new Observer<SearchBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SearchBean searchBean) {
               sa.showUser(searchBean.getData().getRecommend_user());
               sa.showHotWords(searchBean.getData().getHot_words());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
