package com.dzws.simple;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import com.dzws.relogin.utils.ReLogin;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-20 11:38
 */
public class BaseApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    ReLogin.init(this);
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
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

      }
    });
  }
}
