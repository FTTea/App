package com.huoshan.api.huoshan.video;

import com.huoshan.api.huoshan.Bean.BaseBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;

import io.reactivex.Observer;

/**
 * Created by geting on 2018/1/1.
 */

public interface IVideoModel {
    public void getVideoItem(Observer<VideoBeans> observer);
}
