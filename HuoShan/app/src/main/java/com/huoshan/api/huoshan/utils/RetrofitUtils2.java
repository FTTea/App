package com.huoshan.api.huoshan.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

/**
 * Created by lenovo on 2017/12/28.
 */

public class RetrofitUtils2 {
    private static ServicrApi servicrApi;
    public static <T> T createApi(Class<T> tClass,String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(tClass);
    }
    public static ServicrApi getServicrApi(){
        if(servicrApi==null){
            synchronized (RetrofitUtils2.class){
                if(servicrApi==null){
                    servicrApi= RetrofitUtils2.createApi(ServicrApi.class, UrlUtils.VIDEO_HOST);
                }
            }
        }
        return  servicrApi;
    }
}
