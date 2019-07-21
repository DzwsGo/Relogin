package com.dzws.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dzws.relogin_annotation.ReLoad;
import com.dzws.relogin_annotation.ReLogin;

@ReLogin(reLoginCode = 401)
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @ReLoad
  public void comeOn() {

  }
  @ReLoad
  public void baby(){}
}
