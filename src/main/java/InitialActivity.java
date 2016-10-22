package com.example.javcoz.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
            }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);



        Animation fall = AnimationUtils.loadAnimation(this, R.anim.fall);
        ImageView imageView = (ImageView) findViewById(R.id.initial);
        imageView.startAnimation(fall);
        fall.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent theIntent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(theIntent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
                //imageView.startAnimation(fadeOut);
    }
/*
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent theIntent = new Intent(InitialActivity.this, MainActivity.class);
            startActivity(theIntent);
        }
        return true;
    }
*/


}
