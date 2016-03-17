package com.simonluo.daidai_weather.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.simonluo.daidai_weather.R;

import java.lang.annotation.Target;

/**
 * Created by 333 on 2016/3/1.
 */
public class LaunchActivity extends AppCompatActivity {
    private static final int LAUNCH_DURATION = 3000;
    private static final String TAG = "LaunchActivity";
    private ImageView img_launch;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        img_launch = (ImageView) findViewById(R.id.img_launch);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        Glide.with(this).load(R.mipmap.launch).crossFade(1500).into(img_launch);
        startAppDelay();
    }

    private void startAppDelay(){
        img_launch.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, LAUNCH_DURATION);
    }

    private void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.support.v7.appcompat.R.anim.abc_grow_fade_in_from_bottom, android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom);
        finish();
    }
}
