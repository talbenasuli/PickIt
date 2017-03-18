package pickit.com.pickit.UI.Screens;


import android.os.Handler;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pickit.com.pickit.R;


public class SplashActivity extends AppCompatActivity {

    private int delayTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        moveToNextActivityByTime();
    }

    protected void moveToNextActivity(Class nextActivity){
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
        finish();
    }

    //Moving to the next activity after delayTime.
    private void moveToNextActivityByTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            //This is the function that happens after delayTime.
            public void run() {
                moveToNextActivity(PILoginActivity.class);
            }
        }, delayTime);
    }
}
