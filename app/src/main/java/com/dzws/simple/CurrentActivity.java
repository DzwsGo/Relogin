package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dzws.relogin.utils.ReLoginController;
import com.dzws.relogin_annotation.ReLoad;
import com.dzws.relogin_annotation.ReLogin;

import java.lang.annotation.Inherited;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-22 11:34
 */
public class CurrentActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "CurrentActivity : " + this);
        ((TextView) findViewById(R.id.tv_name)).setText(TAG + this);
    }

    @Override
    public void get() {
        Log.d(TAG, "CurrentActivity Get : " + this);
        Toast.makeText(CurrentActivity.this, TAG + " start " + this, Toast.LENGTH_SHORT).show();
    }

    public void toReLogin(View view) {
        ReLoginController.getInstance().toLogin();
    }

    public void toCurrent(View view) {
        Intent intent = new Intent(CurrentActivity.this, CurrentActivity.class);
        startActivity(intent);
    }

    public void toA(View view) {
        Intent intent = new Intent(CurrentActivity.this, AActivity.class);
        startActivity(intent);
    }
}
