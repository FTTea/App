package com.huoshan.api.huoshan.Login;

import android.text.TextUtils;
import android.view.View;


import com.huoshan.api.huoshan.Bean.BaseBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by lenovo on 2017/12/28.
 */

public class LoginPresenter {
    private LoginApi loginApi;
    private final LoginModel lm;

    public LoginPresenter( LoginApi loginApi) {
        this.loginApi = loginApi;
        lm = new LoginModel();
    }
    public  void getOnLogin(String mob,String pwd){
        if(checkPwd(pwd)&&chekckMobile(mob)){
           lm.OnImm(mob, pwd, new Observer<BaseBean>() {
               @Override
               public void onSubscribe(Disposable d) {

               }

               @Override
               public void onNext(BaseBean baseBean) {
                  loginApi.showData(baseBean.getMsg());
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
    public boolean chekckMobile(String mob){
        if(TextUtils.isEmpty(mob)){
            loginApi.showData("用户名不能为空");
            return false;
        }
        if(!isMobileNO(mob)){
            loginApi.showData("手机号格式错误");
            return false;
        }

        return  true;
    }
    public boolean checkPwd(String pwd){
        if(TextUtils.isEmpty(pwd)){
            loginApi.showData("密码不能为空");
            return false;
        }
        if(pwd.length()<3){
            loginApi.showData("密码必须大于3位数");
            return  false;
        }
        return  true;
    }
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

}
