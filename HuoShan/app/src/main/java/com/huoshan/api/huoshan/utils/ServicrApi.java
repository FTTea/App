package com.huoshan.api.huoshan.utils;



import com.huoshan.api.huoshan.Bean.BaseBean;
import com.huoshan.api.huoshan.Bean.CityBean;
import com.huoshan.api.huoshan.Bean.LiveBean;
import com.huoshan.api.huoshan.Bean.SearchBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;

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
    //视频
    @GET(UrlUtils.VIDEO_PATH)
    Call<VideoBeans> getVideoItem();
    //同城
    @GET(UrlUtils.CITY_PATH)
    Call<CityBean> getCityItem();

    @GET(UrlUtils.SEARCH_PATH)
    Call<SearchBean> getSearch();
}
