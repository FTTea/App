package com.huoshan.api.huoshan.live;





import com.huoshan.api.huoshan.Bean.LiveBean;
import com.huoshan.api.huoshan.utils.LiveRetrofit;
import com.huoshan.api.huoshan.utils.ServicrApi;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2017/12/29.
 */

public class LiveModel implements LiveOnImm {
    @Override
    public void OnImm(final Observer<LiveBean> observer) {
        Observable.create(new ObservableOnSubscribe<LiveBean>() {
            @Override
            public void subscribe(ObservableEmitter<LiveBean> e) throws Exception {
                ServicrApi sa= LiveRetrofit.getServicrApi();
                Call<LiveBean> liveItem = sa.getLiveItem();
                liveItem.enqueue(new Callback<LiveBean>() {
                    @Override
                    public void onResponse(Call<LiveBean> call, Response<LiveBean> response) {
                        observer.onNext(response.body());
                        observer.onComplete();
                    }

                    @Override
                    public void onFailure(Call<LiveBean> call, Throwable t) {

                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( observer);
    }
}
