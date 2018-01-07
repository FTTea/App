package com.huoshan.api.huoshan.searchUtils;


import com.huoshan.api.huoshan.Bean.SearchBean;
import com.huoshan.api.huoshan.utils.RetrofitUtils2;
import com.huoshan.api.huoshan.utils.ServicrApi;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2018/1/6.
 */

public class SearchModel implements SearchOnImm {
    @Override
    public void OnImm(final Observer<SearchBean> observer) {
        Observable.create(new ObservableOnSubscribe<SearchBean>() {
            @Override
            public void subscribe(ObservableEmitter<SearchBean> e) throws Exception {
                ServicrApi sa=RetrofitUtils2.getServicrApi();
                Call<SearchBean> search = sa.getSearch();
                search.enqueue(new Callback<SearchBean>() {
                    @Override
                    public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                        observer.onNext(response.body());
                        observer.onComplete();
                    }

                    @Override
                    public void onFailure(Call<SearchBean> call, Throwable t) {

                    }
                });

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }
}
