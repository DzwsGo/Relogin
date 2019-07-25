package com.dzws.relogin.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.lang.reflect.Method;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

  private HashMap<String,String> reloadMethodMap = new HashMap<>();

  HashMap<String, String> getReloadMethodMap() {
    return reloadMethodMap;
  }

  void setReloadMethodMap(HashMap<String, String> reloadMethodMap) {
    this.reloadMethodMap = reloadMethodMap;
  }

  boolean isReLogin() {
    return isReLogin;
  }

  private static class ReLoginControllerHolder {
    static ReLoginController mReLoginController = new ReLoginController();
  }

  public static ReLoginController getInstance() {
    return ReLoginControllerHolder.mReLoginController;
  }

  void putCurrentActivityClassName(String className) {
    this.mCurrentActivityClassName = className;
  }

  public String getCurrentActivityClassName() {
    return mCurrentActivityClassName;
  }

  /**
   * 在Application中调用
   */
  void setLoginClassName(String loginClassName) {
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
      intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
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
  String getLoginClassName() {
    return mLoginClassName;
  }

  /**
   * 设置需要重新登陆的code
   */
  void setReLoginCode(int code) {
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

  void onReLogin(Activity activity) {
    Log.d("ReLoginController","onReLogin clazzName : " + mCurrentActivityClassName);
    isReLogin = false;
    //反射获取当前正在运行的class，执行reload操作
    try {
      String reLoadMethod = reloadMethodMap.get(mCurrentActivityClassName);
      if(TextUtils.isEmpty(reLoadMethod) || reLoadMethod == null) {
        return;
      }

      Method method = activity.getClass().getMethod(reLoadMethod);
      method.invoke(activity);
    } catch (Exception e) {
      e.printStackTrace();
      Log.e(TAG,"onReLogin e : "  + e);
    }
  }
}
