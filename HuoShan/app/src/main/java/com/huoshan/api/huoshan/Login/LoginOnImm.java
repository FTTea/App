package com.huoshan.api.huoshan.Login;



import com.huoshan.api.huoshan.Bean.BaseBean;

import io.reactivex.Observer;

/**
 * Created by lenovo on 2017/12/28.
 */

public interface LoginOnImm  {
    void OnImm(String mob, String pwd, Observer<BaseBean> observer);
}
