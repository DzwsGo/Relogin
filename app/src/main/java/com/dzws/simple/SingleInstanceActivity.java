package com.dzws.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dzws.relogin.utils.ReloginController;

public class SingleInstanceActivity extends StandardActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    public void get() {
        Toast.makeText(SingleInstanceActivity.this, TAG + " Get " + this, Toast.LENGTH_SHORT).show();
    }

    public void toRelogin(View view) {
        ReloginController.getInstance().toLogin();
    }

    public void toStandard(View view) {
        Intent intent = new Intent(SingleInstanceActivity.this, StandardActivity.class);
        startActivity(intent);
    }

    public void toSingleTop(View view) {
    }

    public void toSingleTask(View view) {

    }

    public void toSingleInstance(View view) {

    }
}
