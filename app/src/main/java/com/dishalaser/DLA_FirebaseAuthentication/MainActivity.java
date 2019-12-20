package com.dishalaser.DLA_FirebaseAuthentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    //Probably for the purpose of splash screen.
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //This method is used so that your splash activity will cover the entire screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Bind your MainActivity.class file with activity_signin.
        setContentView(R.layout.activity_signin);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);

                //The current activity will finish
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
