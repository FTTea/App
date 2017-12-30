package com.huoshan.api.huoshan.Login;


import com.huoshan.api.huoshan.Bean.BaseBean;
import com.huoshan.api.huoshan.utils.RetrofitUtils;
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
 * Created by lenovo on 2017/12/28.
 */

public class LoginModel implements LoginOnImm {
    @Override
    public void OnImm(final String mob, final String pwd, final Observer<BaseBean> observer) {
        Observable.create(new ObservableOnSubscribe<BaseBean>() {
            @Override
            public void subscribe(ObservableEmitter<BaseBean> e) throws Exception {
                ServicrApi si= RetrofitUtils.getServicrApi();
                Call<BaseBean> login = si.login(mob, pwd);
                login.enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        observer.onNext(response.body());
                        observer.onComplete();
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {

                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }
}
