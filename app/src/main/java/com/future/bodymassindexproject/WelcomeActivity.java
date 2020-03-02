package com.future.bodymassindexproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class WelcomeActivity extends AppCompatActivity {

    ImageView iv2;
    TextView tvWelcome;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        iv2 = (ImageView)findViewById(R.id.iv2);
        tvWelcome = (TextView)findViewById(R.id.tvWelcome);
        animation = AnimationUtils.loadAnimation(this, R.anim.a1);
        iv2.startAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent i = new Intent(WelcomeActivity.this, CustomerLoginActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

}
