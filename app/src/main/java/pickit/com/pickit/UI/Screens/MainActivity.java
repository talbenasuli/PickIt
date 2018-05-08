package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import pickit.com.pickit.Models.PIMyApplication;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIActivity;


/**
 * Created by or on 12/01/2017.
 */

public class MainActivity extends PIActivity implements View.OnClickListener, PIProfileFragment.PIProfileFragmentListener {//implements View.OnClickListener,PIProfileFragment.PIProfileFragmentListener

    //buttons:
    ImageButton navBarHomeButton;
    ImageButton navBarProfileButton;
    ImageButton navBarRecommedationImageButton;

    ImageButton menuImageButton;

    //fragments:
    HomeFragment homeFragment;
    RecommendationsFragment recommendations;
    PIProfileFragment profileFragment;
    PISettingsFragment settingsFragment;


    View menuView;

    //variables
    public String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuView = findViewById(R.id.menuView);
        menuView.setOnClickListener(this);

        //fragments instances creation
        homeFragment = HomeFragment.newInstance();
        settingsFragment = PISettingsFragment.newInstance();


        //loading home fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmets_container, homeFragment)
                .commit();

        menuImageButton = (ImageButton) findViewById(R.id.mainMenuImageButton);
        menuImageButton.setOnClickListener(this);

        //creating navigation bars buttons and setting its listeners
        navBarHomeButton = (ImageButton)findViewById(R.id.home_button);
        navBarHomeButton.setOnClickListener(this);

        navBarProfileButton = (ImageButton)findViewById(R.id.profile_button);
        navBarProfileButton.setOnClickListener(this);

        navBarRecommedationImageButton = (ImageButton) findViewById(R.id.reccomendations_button);
        navBarRecommedationImageButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.mainMenuImageButton) {
            menuOpenAnimation();
        }

        else if (v == menuView) {
            menuCloseAnimation();
        }

        else {
            Fragment fragment = new Fragment();
            String tag  = new String();
            switch (v.getId()) {

                case R.id.home_button:
                    if (homeFragment == null) {
                        fragment = HomeFragment.newInstance();
                    }
                    else{
                        fragment = homeFragment;
                    }
                    tag = HomeFragment.TAG;
                    break;

                case R.id.reccomendations_button:
                    if (recommendations == null){
                        fragment = RecommendationsFragment.newInstance();
                    }
                    else{
                        fragment = recommendations;
                    }
                    tag = RecommendationsFragment.TAG;
                    break;

                case R.id.profile_button:
                    if (profileFragment == null){
                        fragment = PIProfileFragment.newInstance();
                    }
                    else{
                        fragment = profileFragment;
                    }
                    tag = profileFragment.TAG;
                    break;

                default:
                    return;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmets_container, fragment)
                    .commit();

            menuCloseAnimation();
        }

        if (v.getId() == R.id.profile_button || v.getId() == R.id.reccomendations_button) {
            showLoadingFragment(R.id.fragmets_container, false);
        }
    }

    private void menuOpenAnimation() {
        Animation menuOpenAnimation = AnimationUtils.loadAnimation(PIMyApplication.getMyContext(), R.anim.menu_open_animation);
        menuView.startAnimation(menuOpenAnimation);
        menuView.setVisibility(View.VISIBLE);
    }

    private void menuCloseAnimation() {
        Animation menuCloaseAnimation = AnimationUtils.loadAnimation(PIMyApplication.getMyContext(), R.anim.menu_close_animation);
        menuView.startAnimation(menuCloaseAnimation);
        menuCloaseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onSettingsImageButtonClicked() {
        if(settingsFragment.TAG == null){
            settingsFragment = PISettingsFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmets_container, settingsFragment, settingsFragment.TAG).addToBackStack(null)
                .commit();
    }
}




