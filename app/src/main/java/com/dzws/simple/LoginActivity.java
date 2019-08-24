package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dzws.relogin_annotation.Relogin;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-22 11:34
 */
@Relogin(reloginCode = 401)
public class LoginActivity extends BaseActivity {
  private String TAG = getClass().getSimpleName();
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Intent intent = getIntent();
    if(intent != null) {
      boolean reLogin = intent.getBooleanExtra("RE_LOGIN", false);
      Log.d(TAG,"reLogin : " + reLogin);
    }
  }

  @Override
  public void get() {
    super.get();

  }
}
