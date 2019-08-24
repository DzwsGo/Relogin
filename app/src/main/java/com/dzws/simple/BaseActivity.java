package com.dzws.simple;

import android.support.v7.app.AppCompatActivity;

import com.dzws.relogin_annotation.ReloadInheritable;

@ReloadInheritable(reloadMethod = "get")
public abstract class BaseActivity extends AppCompatActivity {
    public void get(){}
}
