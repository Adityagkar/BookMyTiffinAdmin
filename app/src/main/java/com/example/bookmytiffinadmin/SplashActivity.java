package com.example.bookmytiffinadmin;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 4000;
    String permissions[] = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS
    ,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECEIVE_SMS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  initApp();
                } 

            }
        }, SPLASH_TIME_OUT);
    }

    void initApp(){
        Intent i;

            i = new Intent(SplashActivity.this, MainActivity.class);

        startActivity(i);
        finish();
    }


}
