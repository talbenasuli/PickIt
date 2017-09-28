package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIActivity;


/**
 * Created by or on 12/01/2017.
 */

public class MainActivity extends PIActivity
        implements View.OnClickListener,PIProfileFragment.PIProfileFragmentListener {

    //buttons:
    ImageButton navBarHomeButton;
    ImageButton navBarGameButton;
    ImageButton navBarRecommendationButton;
    ImageButton navBarSocialButton;
    ImageButton navBarProfileButton;

    //fragments:
    HomeFragment homeFragment;
    PIGameFragment gameFragment;
    PISongSuggestFragment songSuggestFragment;
    RecommendationsFragment recommendations;
    PISocialFragment socialFragment;
    PIProfileFragment profileFragment;
    PISettingsFragment settingsFragment;

    //variables
    public String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragments instances creation
        homeFragment = HomeFragment.newInstance();
        socialFragment = PISocialFragment.newInstance();
        gameFragment = PIGameFragment.newInstance();
        recommendations = RecommendationsFragment.newInstance();
        profileFragment = PIProfileFragment.newInstance();
        settingsFragment = PISettingsFragment.newInstance();
        songSuggestFragment = PISongSuggestFragment.newInstance();

        //loading home fragment
        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmets_container, homeFragment, HomeFragment.TAG)
                                    .commit();

        //creating navigation bars buttons and setting its listeners
        navBarHomeButton = (ImageButton)findViewById(R.id.home_button);
        navBarHomeButton.setOnClickListener(this);

        navBarGameButton = (ImageButton)findViewById(R.id.game_button);
        navBarGameButton.setOnClickListener(this);

        navBarRecommendationButton = (ImageButton)findViewById(R.id.reccomendations_button);
        navBarRecommendationButton.setOnClickListener(this);

        navBarSocialButton = (ImageButton)findViewById(R.id.songSuggest_button);
        navBarSocialButton.setOnClickListener(this);

        navBarProfileButton =(ImageButton)findViewById(R.id.profile_button);
        navBarProfileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new Fragment();
        String tag  = new String();
        switch (v.getId()) {
            case R.id.home_button:
                if (HomeFragment.TAG == null) {
                    fragment = HomeFragment.newInstance();
                }
                else{
                    fragment = homeFragment;
                }
                tag = HomeFragment.TAG;
                break;
            case R.id.game_button:
                if (PIGameFragment.TAG == null){
                    fragment = PIGameFragment.newInstance();
                }
                else{
                    fragment = gameFragment;
                }
                tag = PIGameFragment.TAG;
                break;
            case R.id.reccomendations_button:
                if (RecommendationsFragment.TAG == null){
                    fragment = RecommendationsFragment.newInstance();
                }
                else{
                    fragment = recommendations;
                }
                tag = RecommendationsFragment.TAG;
                break;
            case R.id.songSuggest_button:
                if (PISongSuggestFragment.TAG == null) {
                    fragment = PISongSuggestFragment.newInstance();
                }
                else{
                    fragment = songSuggestFragment;
                }
                tag = PISocialFragment.TAG;
                break;
            case R.id.profile_button:
                if (PIProfileFragment.TAG == null){
                    fragment = PIProfileFragment.newInstance();
                }
                else{
                    fragment = profileFragment;
                }
                tag = profileFragment.TAG;
            default:
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmets_container, fragment, tag)
                .commit();

        if(v.getId() == R.id.profile_button){
            showLoadingFragment(R.id.fragmets_container , false);
        }
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
