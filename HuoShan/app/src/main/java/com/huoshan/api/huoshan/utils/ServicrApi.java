package com.huoshan.api.huoshan.utils;



import com.huoshan.api.huoshan.Bean.BaseBean;
import com.huoshan.api.huoshan.Bean.LiveBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2017/12/28.
 */

public interface ServicrApi {
    @GET("user/login")
    Call<BaseBean> login(@Query("mobile") String mob, @Query("password") String pwd);

    //直播
    @GET(UrlUtils.LIVE_PATH)
    Call<LiveBean> getLiveItem();
}
