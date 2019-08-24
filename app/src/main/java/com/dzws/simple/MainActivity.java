package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dzws.relogin.utils.ReloginController;
import com.dzws.relogin_annotation.Reload;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv_name)).setText(TAG + this);
    }

    @Reload
    public void comeOn() {
        Log.d(TAG, "MainActivity comeOn : " + this);
        Toast.makeText(MainActivity.this, TAG + " comeOn " + this, Toast.LENGTH_SHORT).show();
    }

    public void toRelogin(View view) {
        ReloginController.getInstance().toLogin();
    }

    public void toStandard(View view) {
        Intent intent = new Intent(MainActivity.this, StandardActivity.class);
        startActivity(intent);
    }


    public void toSingleTop(View view) {
        Intent intent = new Intent(MainActivity.this, SingleTopActivity.class);
        startActivity(intent);
    }

    public void toSingleTask(View view) {
        Intent intent = new Intent(MainActivity.this, SingleTaskActivity.class);
        startActivity(intent);
    }

    public void toSingleInstance(View view) {
        Intent intent = new Intent(MainActivity.this, SingleInstanceActivity.class);
        startActivity(intent);
    }
}
