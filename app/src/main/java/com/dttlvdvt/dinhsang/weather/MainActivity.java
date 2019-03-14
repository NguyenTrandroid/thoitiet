package com.dttlvdvt.dinhsang.weather;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView logo;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.textlogo);
        Animation animationl = AnimationUtils.loadAnimation(this,R.anim.logo);
        logo.startAnimation(animationl);
        intent = new Intent(MainActivity.this,Main2Activity.class);
        CountDownTimer countDownTimer = new CountDownTimer(2500,1000) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
            startActivity(intent);
            finish();
            }
        };
        countDownTimer.start();
    }
}
