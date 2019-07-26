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
        Toast.makeText(CurrentActivity.this, TAG + " Get " + this, Toast.LENGTH_SHORT).show();
    }

    public void toRelogin(View view) {
        ReloginController.getInstance().toLogin();
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
