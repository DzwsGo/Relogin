package com.dzws.relogin;

import android.content.Intent;
import android.util.Log;
import com.dzws.relogin.utils.ReLogin;
import com.dzws.relogin.utils.RxBus;
import io.reactivex.Observable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-01 17:57
 */
public class ReLoginController {
  private String RELOGIN = "RE_LOGIN";
  private String TAG = getClass().getSimpleName();
  private String mCurrentActivityClassName;
  private String mLoginClassName;
  private int mNeedLoginCode;
  private boolean isReLogin = false;

  public boolean isReLogin() {
    return isReLogin;
  }

  private static class ReLoginControllerHolder {
    static ReLoginController mReLoginController = new ReLoginController();
  }

  public static ReLoginController getInstance() {
    return ReLoginControllerHolder.mReLoginController;
  }

  public void putCurrentActivityClassName(String className) {
    this.mCurrentActivityClassName = className;
  }

  /**
   * 登陆完成后由Login页面调用
   */
  public void sendLoginEvent() {
    Log.d(TAG, "ReLoginController mCurrentActivityClassName : " + mCurrentActivityClassName);
    RxBus.get().sendEvent(new ReLoginBean(mCurrentActivityClassName));
  }

  /**
   * 获取重新登陆后的事件
   */
  public Observable<ReLoginBean> getLoginEvent() {
    return RxBus.get().getEvent(ReLoginBean.class);
  }

  /**
   * 在Application中调用
   */
  public void setLoginClassName(String loginClassName) {
    Log.d(TAG, "ReLoginController loginClassName : " + loginClassName);
    this.mLoginClassName = loginClassName;
  }

  /**
   * 去登陆
   */
  public void toLogin() {
    try {
      Class<?> loginClass = Class.forName(mLoginClassName);
      Intent intent = new Intent(ReLogin.getApp(), loginClass);
      intent.putExtra(RELOGIN, true);
      ReLogin.getApp().startActivity(intent);
      isReLogin = true;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      Log.e(TAG, "toLogin mLoginClassName : " + mLoginClassName + " ClassNotFoundException");
    }
  }

  /**
   * 获取登陆类名
   */
  public String getLoginClassName() {
    return mLoginClassName;
  }

  /**
   * 设置需要重新登陆的code
   */
  public void setReLoginCode(int code) {
    this.mNeedLoginCode = code;
  }

  /**
   * 设置响应code
   */
  public void setResponseCode(int code) {
    if (mNeedLoginCode == code) {
      toLogin();
    }
  }

  public void onReLoginDestroy() {
    Log.d("mainClassName","onReLoginDestroy");
    isReLogin = false;
    //反射获取当前正在运行的class，执行reload操作

    try {
      Class<?> mainClassName = Class.forName("com.dzws.simple.MainActivity");
      Method comeOn = mainClassName.getMethod("comeOn");
      Object o = mainClassName.newInstance();
      Log.d("mainClassName","mainClassName o : " + o);
      comeOn.invoke(o);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
