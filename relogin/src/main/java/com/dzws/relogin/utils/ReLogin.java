package com.dzws.relogin.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.dzws.relogin_compiler.IReLoginHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
  private static HashMap<String,Activity> activityMap = new HashMap<>();
  public static void init(Application application) {

    try {
      IReLoginHelper reLoginHelper =
          (IReLoginHelper) Class.forName("com.dzws.relogin.ReLoginHelper")
              .getConstructor()
              .newInstance();
      ReLoginController.getInstance().setLoginClassName(reLoginHelper.getReLoginClassName());
      ReLoginController.getInstance().setReLoginCode(reLoginHelper.getReLoginResponseCode());
      ReLoginController.getInstance().setReloadMethodMap(reLoginHelper.getReloadMethodMap());
    } catch (Exception e) {
      e.printStackTrace();
    }

    mApplication = application;
    mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityMap.put(activity.getClass().getCanonicalName(),activity);
      }

      @Override public void onActivityStarted(Activity activity) {

      }

      @Override public void onActivityResumed(Activity activity) {
        setCurrentActivityName(activity);
      }

      @Override public void onActivityPaused(Activity activity) {

      }

      @Override public void onActivityStopped(Activity activity) {

      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override public void onActivityDestroyed(Activity activity) {
        String currentSimpleName = activity.getClass().getCanonicalName();
        Log.d(TAG,"onActivityDestroyed : " + currentSimpleName + " getCurrentActivityClassName : " + ReLoginController.getInstance().getCurrentActivityClassName());
        activityMap.remove(currentSimpleName);

        if(TextUtils.equals(currentSimpleName, ReLoginController.getInstance().getLoginClassName()) && ReLoginController.getInstance().isReLogin()) {
          Set<Map.Entry<String, Activity>> entries = activityMap.entrySet();
          for (Map.Entry<String, Activity> entry : entries) {
            if(TextUtils.equals(ReLoginController.getInstance().getCurrentActivityClassName(),entry.getKey())) {
              ReLoginController.getInstance().onReLogin(entry.getValue());
            }
          }
        }
      }
    });
  }

  private static void setCurrentActivityName(Activity activity) {
    String currentSimpleName = activity.getClass().getCanonicalName();
    Log.d(TAG,"onActivityCreated : " + currentSimpleName);
    if(!TextUtils.equals(currentSimpleName, ReLoginController.getInstance().getLoginClassName())) {
      //不是LoginActivity
      ReLoginController.getInstance().putCurrentActivityClassName(currentSimpleName);
    }
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
