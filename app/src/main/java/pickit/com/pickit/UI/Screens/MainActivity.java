package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pickit.com.pickit.R;


/**
 * Created by or on 12/01/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //buttons:
    Button navBarHomeButton;
    Button navBarGameButton;
    Button navBarRecommendationButton;
    Button navBarSocialButton;
    Button navBarProfileButton;

    //fragments:
    HomeFragment homeFragment;
    PIGameFragment gameFragment;
    RecommendationsFragment recommendations;
    PISocialFragment socialFragment;
    PIProfileFragment profileFragment;

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

        //loading home fragment
        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmets_container, homeFragment, HomeFragment.TAG)
                                    .commit();

        //creating navigation bars buttons and setting its listeners
        navBarHomeButton = (Button)findViewById(R.id.home_button);
        navBarHomeButton.setOnClickListener(this);

        navBarGameButton = (Button)findViewById(R.id.game_button);
        navBarGameButton.setOnClickListener(this);

        navBarRecommendationButton = (Button)findViewById(R.id.reccomendations_button);
        navBarRecommendationButton.setOnClickListener(this);

        navBarSocialButton = (Button)findViewById(R.id.social_button);
        navBarSocialButton.setOnClickListener(this);

        navBarProfileButton =(Button)findViewById(R.id.profile_button);
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
            case R.id.social_button:
                if (PISocialFragment.TAG == null) {
                    fragment = PISocialFragment.newInstance();
                }
                else{
                    fragment = socialFragment;
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
    }


}
