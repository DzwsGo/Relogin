package com.dzws.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.dzws.relogin.ReLoginController;
import com.dzws.relogin_annotation.ReLogin;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-22 11:34
 */
@ReLogin(reLoginCode = 401)
public class LoginActivity extends AppCompatActivity {
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
}
