package com.dzws.simple;

import android.support.v7.app.AppCompatActivity;

import com.dzws.relogin_annotation.ReLogin;

import java.lang.annotation.Inherited;

@ReLogin(reLoginCode = 401,reLoginMethodName = "get",reLoginClassName = "com.dzws.simple.LoginActivity")
public abstract class BaseActivity extends AppCompatActivity {
    public void get(){}
}
