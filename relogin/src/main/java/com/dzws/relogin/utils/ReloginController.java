package com.dzws.relogin.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-01 17:57
 */
public class ReloginController {
  private String RELOGIN = "RE_LOGIN";
  private String TAG = getClass().getSimpleName();
  private String mCurrentActivityClassName;
  private String mLoginClassName;
  private int mNeedLoginCode;
  private boolean isRelogin = false;

  private HashMap<String,String> reloadMethodMap = new HashMap<>();

  HashMap<String, String> getReloadMethodMap() {
    return reloadMethodMap;
  }

  void setReloadMethodMap(HashMap<String, String> reloadMethodMap) {
    this.reloadMethodMap = reloadMethodMap;
  }

  boolean isRelogin() {
    return isRelogin;
  }

  private static class ReloginControllerHolder {
    static ReloginController mReloginController = new ReloginController();
  }

  public static ReloginController getInstance() {
    return ReloginControllerHolder.mReloginController;
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
    Log.d(TAG, "ReloginController loginClassName : " + loginClassName);
    this.mLoginClassName = loginClassName;
  }

  /**
   * 去登陆
   */
  public void toLogin() {
    try {
      Class<?> loginClass = Class.forName(mLoginClassName);
      Intent intent = new Intent(Relogin.getApp(), loginClass);
      intent.putExtra(RELOGIN, true);
      intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
      Relogin.getApp().startActivity(intent);
      isRelogin = true;
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
  void setReloginCode(int code) {
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

  void onRelogin(Activity activity) {
    Log.d("ReloginController","onRelogin clazzName : " + mCurrentActivityClassName);
    isRelogin = false;
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
      Log.e(TAG,"onRelogin e : "  + e);
    }
  }
}
