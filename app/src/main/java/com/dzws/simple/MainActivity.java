package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dzws.relogin.utils.ReLoginController;
import com.dzws.relogin_annotation.ReLoad;

public class MainActivity extends AppCompatActivity {
  private String TAG = getClass().getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ((TextView)findViewById(R.id.tv_name)).setText(TAG + this);
  }

  @ReLoad
  public void comeOn() {
    Log.d(TAG,"MainActivity Get : " + this);
    Toast.makeText(MainActivity.this, TAG + " start " + this, Toast.LENGTH_SHORT).show();
  }

  public void toReLogin(View view) {
    ReLoginController.getInstance().toLogin();
  }

  public void toCurrent(View view) {
    Intent intent = new Intent(MainActivity.this, CurrentActivity.class);
    startActivity(intent);
  }

  public void toA(View view) {
  }
}
