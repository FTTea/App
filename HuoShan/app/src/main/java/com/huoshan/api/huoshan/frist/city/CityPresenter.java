package com.huoshan.api.huoshan.frist.city;

import com.huoshan.api.huoshan.Bean.CityBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by geting on 2018/1/4.
 */

public class CityPresenter {
    private CityApi cityApi;
    private CityModel cityModel;

    public CityPresenter(CityApi cityApi) {
        this.cityApi = cityApi;
        cityModel = new CityModel();
    }
    public void getCity(){
        cityModel.getCity(new Observer<CityBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CityBean videoBeans) {
                cityApi.show(videoBeans);
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
