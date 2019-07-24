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
  }
}
