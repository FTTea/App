package com.huoshan.api.huoshan.frist.city;

import com.huoshan.api.huoshan.Bean.CityBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;
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
 * Created by geting on 2018/1/4.
 */

public class CityModel implements ICityModel{
    @Override
    public void getCity(final Observer<CityBean> observer) {
        Observable.create(new ObservableOnSubscribe<CityBean>() {
            @Override
            public void subscribe(ObservableEmitter<CityBean> e) throws Exception {
                ServicrApi si= RetrofitUtils2.getServicrApi();
                Call<CityBean> login = si.getCityItem();
                login.enqueue(new Callback<CityBean>() {
                    @Override
                    public void onResponse(Call<CityBean> call, Response<CityBean> response) {
                        observer.onNext(response.body());
                        observer.onComplete();
                    }

                    @Override
                    public void onFailure(Call<CityBean> call, Throwable t) {

                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    }

