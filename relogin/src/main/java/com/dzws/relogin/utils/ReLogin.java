package com.dzws.relogin.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.dzws.relogin.ReLoginController;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-20 11:22
 */
public class ReLogin {
  private static String TAG = "ReLogin";
  private ReLogin(){}
  private static Application mApplication;
  public static void init(Application application) {
    mApplication = application;
    mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String currentSimpleName = activity.getClass().getSimpleName();
        Log.d(TAG,"onActivityCreated : " + currentSimpleName);

        if(!TextUtils.equals(currentSimpleName, ReLoginController.getInstance().getLoginClassName())) {
          //不是LoginActivity
          ReLoginController.getInstance().putCurrentActivityClassName(currentSimpleName);
        }else {
          //正在登陆
        }
      }

      @Override public void onActivityStarted(Activity activity) {

      }

      @Override public void onActivityResumed(Activity activity) {

      }

      @Override public void onActivityPaused(Activity activity) {

      }

      @Override public void onActivityStopped(Activity activity) {

      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override public void onActivityDestroyed(Activity activity) {
        String currentSimpleName = activity.getClass().getSimpleName();
        if(TextUtils.equals(currentSimpleName, ReLoginController.getInstance().getLoginClassName()) && ReLoginController.getInstance().isReLogin()) {
          ReLoginController.getInstance().onReLoginDestroy();
        }
      }
    });
  }
  public static ReLogin getInstance() {
    return ReLoginUtilsHolder.reLogin;
  }
  private static class ReLoginUtilsHolder {
    private static ReLogin reLogin = new ReLogin();
  }

  public static Application getApp() {
    return mApplication;
  }
}
