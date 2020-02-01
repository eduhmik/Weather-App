package com.example.weatherapp.activities;

import android.os.Bundle;
import android.os.Handler;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseActivity;

import androidx.annotation.Nullable;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNewActivity(MainActivity.class);
                SplashActivity.this.finish();
            }
        }, 3000);

    }
}
