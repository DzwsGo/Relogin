package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dzws.relogin.utils.ReloginController;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-22 11:34
 */
public class StandardActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "StandardActivity : " + this);
        ((TextView) findViewById(R.id.tv_name)).setText(TAG + this);
    }

    @Override
    public void get() {
        Log.d(TAG, "StandardActivity Get : " + this);
        Toast.makeText(StandardActivity.this, TAG + " Get " + this, Toast.LENGTH_SHORT).show();
    }

    public void toRelogin(View view) {
        ReloginController.getInstance().toLogin();
    }

    public void toStandard(View view) {
        Intent intent = new Intent(StandardActivity.this, StandardActivity.class);
        startActivity(intent);
    }

    public void toSingleTop(View view) {
        Intent intent = new Intent(StandardActivity.this, SingleTopActivity.class);
        startActivity(intent);
    }

    public void toSingleTask(View view) {
        Intent intent = new Intent(StandardActivity.this, SingleTaskActivity.class);
        startActivity(intent);
    }

    public void toSingleInstance(View view) {
        Intent intent = new Intent(StandardActivity.this, SingleInstanceActivity.class);
        startActivity(intent);
    }
}
