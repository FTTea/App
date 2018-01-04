package com.huoshan.api.huoshan.frist.city;

import com.huoshan.api.huoshan.Bean.CityBean;
import com.huoshan.api.huoshan.Bean.LiveBean;

import io.reactivex.Observer;

/**
 * Created by geting on 2018/1/4.
 */

public interface ICityModel {
    public void getCity(Observer<CityBean> observer);
}
