package com.huoshan.api.huoshan.erweimautils.net;

/**
 * Created by peng on 2017/10/30.
 */

public class HttpUtils {
    //    private static HttpUtils httpUtils = new HttpUtils();//饿汉式，直接创建对象
//    public static HttpUtils getHttpUtils() {
//        return httpUtils;
//    }
    private static HttpUtils httpUtils;

    private HttpUtils() {
    }

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

}
