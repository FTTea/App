package com.huoshan.api.huoshan.video;

import com.huoshan.api.huoshan.Bean.VideoBeans;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by geting on 2018/1/1.
 */

public class VideoPresenter {
    private VideoApi videoApi;
    private VideoModel videoModel;

    public VideoPresenter(VideoApi videoApi) {
        this.videoApi = videoApi;
        videoModel = new VideoModel();
    }
    public void getVideo(){
        videoModel.getVideoItem(new Observer<VideoBeans>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(VideoBeans videoBeans) {
                videoApi.show(videoBeans);
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
