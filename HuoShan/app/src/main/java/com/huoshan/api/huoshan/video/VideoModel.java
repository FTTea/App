package com.huoshan.api.huoshan.video;

import com.huoshan.api.huoshan.Bean.BaseBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.utils.RetrofitUtils;
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
 * Created by geting on 2018/1/1.
 */

public class VideoModel implements IVideoModel{
    @Override
    public void getVideoItem(final Observer<VideoBeans> observer) {
        Observable.create(new ObservableOnSubscribe<VideoBeans>() {
            @Override
            public void subscribe(ObservableEmitter<VideoBeans> e) throws Exception {
                ServicrApi si= RetrofitUtils2.getServicrApi();
                Call<VideoBeans> login = si.getVideoItem();
                login.enqueue(new Callback<VideoBeans>() {
                    @Override
                    public void onResponse(Call<VideoBeans> call, Response<VideoBeans> response) {
                        observer.onNext(response.body());
                        observer.onComplete();
                    }

                    @Override
                    public void onFailure(Call<VideoBeans> call, Throwable t) {

                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
