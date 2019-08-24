package com.dzws.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dzws.annotation.Reload;

public class SingleTaskActivity extends StandardActivity {
    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Reload
    public void comeOn() {
        Log.d(TAG, "MainActivity comeOn : " + this);
        Toast.makeText(SingleTaskActivity.this, TAG + " comeOn " + this, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void get() {
        super.get();
        Log.d(TAG,"SingleTaskActivity Get : " + this);
        Toast.makeText(SingleTaskActivity.this, TAG + " Get : " + this, Toast.LENGTH_SHORT).show();
    }
}
