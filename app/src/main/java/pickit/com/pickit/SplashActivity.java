package pickit.com.pickit;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private int delayTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        moveToNextActivity();
    }

    //Moving to the next activity after delayTime.
    private void moveToNextActivity(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            //This is the function that happens after delayTime.
            public void run() {

            }
        }, delayTime);
    }
}
