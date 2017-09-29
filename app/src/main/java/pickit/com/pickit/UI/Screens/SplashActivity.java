package pickit.com.pickit.UI.Screens;


import android.content.SharedPreferences;
import android.os.Handler;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Models.PIMyApplication;
import pickit.com.pickit.Networking.Requests.PIGetSongImageRequest;
import pickit.com.pickit.R;


public class SplashActivity extends AppCompatActivity implements PIModel.LoginListener {

    private int delayTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //moveToNextActivityByTime();
        SharedPreferences userDefaults = PIMyApplication.getMyContext().getSharedPreferences("userDefaults", 0);
        String email = userDefaults.getString("email","");
        String password = userDefaults.getString("password","");
        if(!email.isEmpty() && !password.isEmpty()) {
            PIModel.getInstance().login(email, password, this);
        }
        else {
            moveToNextActivityByTime();
        }
    }

    protected void moveToNextActivity(Class nextActivity){
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginOnComplete() {
        moveToNextActivity(MainActivity.class);
    }

    @Override
    public void loginOnCancel(String errorMessage) {
        moveToNextActivity(PILoginActivity.class);
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
