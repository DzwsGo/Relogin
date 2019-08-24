package com.dzws.relogin.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;
import com.dzws.relogin_compiler.IReloginHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-20 11:22
 */
public class Relogin implements ReloginController.ReloginListener {
  private static String TAG = "Relogin";
  private Relogin(){
    ReloginController.getInstance().setReloginListener(this);
  }
  private static Application mApplication;
  private static HashMap<String, List<Activity>> activityMap = new HashMap<>();
  public static void init(Application application) {
    try {
      IReloginHelper reLoginHelper =
          (IReloginHelper) Class.forName("com.dzws.relogin.ReloginHelper")
              .getConstructor()
              .newInstance();
      ReloginController.getInstance().setLoginClassName(reLoginHelper.getReloginClassName());
      ReloginController.getInstance().setReloginCode(reLoginHelper.getReloginResponseCode());
      ReloginController.getInstance().setReloadMethodMap(reLoginHelper.getReloadMethodMap());
    } catch (Exception e) {
      e.printStackTrace();
    }

    mApplication = application;
    mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        List<Activity> activities = activityMap.get(activity.getClass().getCanonicalName());
        if(activities == null) {
          activities = new ArrayList<>();
        }
        if(!activities.contains(activity)) {
          activities.add(activity);
        }
        activityMap.put(activity.getClass().getCanonicalName(),activities);
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
        Log.d(TAG,"onActivityDestroyed : " + currentSimpleName + " getCurrentActivityClassName : " + ReloginController.getInstance().getCurrentActivityClassName());
        notifyActivityMap(activity, currentSimpleName);
        doReload(currentSimpleName);
      }
    });
  }

  /**
   * onResume 先于 onActivityDestroyed 方法执行
   * 1、找到当前map中根据类名查找activity list
   * 2、取Activity List 中最后一个对象进行reload操作
   * @param currentSimpleName
   */
  private static void doReload(String currentSimpleName) {
    if(TextUtils.equals(currentSimpleName, ReloginController.getInstance().getLoginClassName()) && ReloginController.getInstance().isRelogin()) {
      Set<Map.Entry<String, List<Activity>>> entries = activityMap.entrySet();
      for (Map.Entry<String, List<Activity>> entry : entries) {
        if (TextUtils.equals(ReloginController.getInstance().getCurrentActivityClassName(),
            entry.getKey())) {
          List<Activity> value = entry.getValue();
          if (value != null && value.size() > 0) {
            Activity act = value.get(value.size() - 1);
            Log.d(TAG, "onActivityDestroyed : " + act);
            if (!act.isFinishing()) {
              ReloginController.getInstance().onReloginDoReload(act);
            }
          }
        }
      }
    }
  }

  private static void notifyActivityMap(Activity activity, String currentSimpleName) {
    List<Activity> activities = activityMap.get(currentSimpleName);
    if(activities == null || activities.size() == 0) {
      activityMap.remove(currentSimpleName);
    }else {
      activities.remove(activity);
      activityMap.put(currentSimpleName,activities);
    }
  }

  public static final int MODEL_FINISH = 0;
  public static final int MODEL_REFRESH = 1;

  @Override public void onRelogin() {
    do {

      if(reloginModel == MODEL_FINISH) {
        finishActivitiesWithoutLoginActivity();
        break;
      }
      if(reloginModel == MODEL_REFRESH) {
        break;
      }

    }while (false);
  }

  private void finishActivitiesWithoutLoginActivity() {
    Set<Map.Entry<String, List<Activity>>> entries = activityMap.entrySet();
    for (Map.Entry<String, List<Activity>> entry : entries) {
      String key = entry.getKey();
      List<Activity> value = entry.getValue();
      if(!TextUtils.equals(key, ReloginController.getInstance().getLoginClassName())) {
        for (Activity activity : value) {
          activity.finish();
        }
      }
    }
  }

  @IntDef({MODEL_FINISH,MODEL_REFRESH})
  @Retention(RetentionPolicy.SOURCE)
  public @interface ReloginModel{}

  private int reloginModel = MODEL_FINISH;

  public void setModel(@ReloginModel int model) {
    reloginModel = model;
  }

  private static void setCurrentActivityName(Activity activity) {
    String currentSimpleName = activity.getClass().getCanonicalName();
    Log.d(TAG,"onActivityCreated : " + currentSimpleName);
    if(!TextUtils.equals(currentSimpleName, ReloginController.getInstance().getLoginClassName())) {
      //不是LoginActivity
      ReloginController.getInstance().putCurrentActivityClassName(currentSimpleName);
    }
  }

  public static Relogin getInstance() {
    return ReloginUtilsHolder.relogin;
  }
  private static class ReloginUtilsHolder {
    private static Relogin relogin = new Relogin();
  }

  public static Application getApp() {
    return mApplication;
  }
}
