package com.huoshan.api.huoshan.live;



import com.huoshan.api.huoshan.Bean.LiveBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lenovo on 2017/12/29.
 */

public class LivePresenter {
    private LiveApi la;
    private LiveModel lm;

    public LivePresenter(LiveApi la) {
        this.la = la;
        lm=new LiveModel();
    }
    public void getLivePresenter(){
        lm.OnImm(new Observer<LiveBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LiveBean liveBean) {
                la.showData(liveBean);
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
