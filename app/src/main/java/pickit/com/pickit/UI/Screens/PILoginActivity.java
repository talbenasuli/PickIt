package pickit.com.pickit.UI.Screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PICustomSwipeAdapter;
import pickit.com.pickit.R;

/**
 * Created by Tal on 15/03/2017.
 */

public class PILoginActivity extends AppCompatActivity {
    ViewPager viewPager;
    PICustomSwipeAdapter adapter;
    List<Integer> imagesList;
    ImageButton loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pi_login);
        imagesList = new ArrayList<Integer>();
        imagesList.add( R.drawable.pickit1);
        imagesList.add( R.drawable.pickit2);
        imagesList.add( R.drawable.pickit3);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PICustomSwipeAdapter(this, imagesList);
        viewPager.setAdapter(adapter);

        loginButton = (ImageButton) findViewById(R.id.loginImageButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextActivity(MainActivity.class);
            }
        });
    }

    protected void moveToNextActivity(Class nextActivity){
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
        finish();
    }
}
