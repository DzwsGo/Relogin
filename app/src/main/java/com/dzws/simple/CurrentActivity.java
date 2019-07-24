package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.dzws.relogin.utils.ReLoginController;
import com.dzws.relogin_annotation.ReLoad;
import com.dzws.relogin_annotation.ReLogin;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-22 11:34
 */
public class CurrentActivity extends AppCompatActivity {
  private String TAG = getClass().getSimpleName();
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent intent = getIntent();
    if(intent != null) {
      boolean reLogin = intent.getBooleanExtra("RE_LOGIN", false);
      Log.d(TAG,"reLogin : " + reLogin);
    }
  }

  @ReLoad
  public void get() {
    Toast.makeText(CurrentActivity.this, TAG + " start", Toast.LENGTH_SHORT).show();
  }

  public void toReLogin(View view) {
    ReLoginController.getInstance().toLogin();
  }
  public void toCurrent(View view) {
    Intent intent = new Intent(CurrentActivity.this, CurrentActivity.class);
    startActivity(intent);
  }
}
