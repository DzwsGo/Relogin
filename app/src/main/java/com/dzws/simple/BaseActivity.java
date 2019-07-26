package com.dzws.simple;

import android.support.v7.app.AppCompatActivity;

import com.dzws.relogin_annotation.Relogin;

@Relogin(reloginCode = 401, reloginMethodName = "get", reloginClassName = "com.dzws.simple.LoginActivity")
public abstract class BaseActivity extends AppCompatActivity {
    public void get(){}
}
