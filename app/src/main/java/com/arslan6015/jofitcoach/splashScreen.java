package com.arslan6015.jofitcoach;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class splashScreen extends AppCompatActivity {

    // splash screen timer
    private static int SPLASH_TIME_OUT = 3500;
    ImageView splashLogoFirst;
//    ,splashLogoSecond,splashLogoThird;
    Animation fade_in,fade_out;


    private int[] imageArray;
    private int currentIndex;
    private int startIndex;
    private int endIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_splash_screen);

        //load animation
        fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,R.anim.fade_out);

        //inilization
        splashLogoFirst = findViewById(R.id.splashLogoFirst);
//        splashLogoSecond = findViewById(R.id.splashLogoSecond);
//        splashLogoThird = findViewById(R.id.splashLogoThird);

        imageArray = new int[3];
        imageArray[0] = R.drawable.firstlogo;
        imageArray[1] = R.drawable.secondlogo;
        imageArray[2] = R.drawable.thirdlogo;

        startIndex = 0;
        endIndex = 2;
        nextImage();



//        splashLogoFirst.setAnimation(fade_in);
//        splashLogoSecond.setVisibility(View.VISIBLE);
//        splashLogoSecond.setAnimation(fade_out);

//        splashLogoThird.setVisibility(View.VISIBLE);
//        splashLogoThird.setAnimation(fade_out);

//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(splashScreen.this, MainActivity.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);


    }

    private void nextImage() {
//        splashLogoFirst.setImageResource(null);
        splashLogoFirst.setImageResource(imageArray[currentIndex]);
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        splashLogoFirst.startAnimation(rotateimage);
        currentIndex++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentIndex>endIndex){
                    Intent i = new Intent(splashScreen.this, MainActivity.class);
                    startActivity(i);
                }else{
                    nextImage();
                }
            }
        },1800);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}