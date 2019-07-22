package com.dzws.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.dzws.relogin.ReLoginController;
import com.dzws.relogin_annotation.ReLoad;
import com.dzws.relogin_annotation.ReLogin;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d("mainClassName","mainClassName o : " + this);

  }

  @ReLoad
  public void comeOn() {
    Log.d("mainClassName","comeOn mainClassName o : " + this);
  }
  @ReLoad
  public void baby(){}

  public void toReLogin(View view) {
    ReLoginController.getInstance().toLogin();
  }
}
