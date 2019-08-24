package com.dzws.simple;

import android.app.Application;
import com.dzws.relogin.utils.Relogin;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-20 11:38
 */
public class BaseApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Relogin.init(this);
    Relogin.getInstance().setModel(Relogin.MODEL_FINISH);
  }
}
