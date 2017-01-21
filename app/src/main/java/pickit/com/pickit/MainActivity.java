package pickit.com.pickit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by or on 12/01/2017.
 */

public class MainActivity extends AppCompatActivity {

    //buttons:
    Button navBarHomeButton;
    Button navBarGameButton;
    Button navBarRecommendationButton;
    Button navBarSocialButton;
    Button navBarProfileButton;

    //fragments:
    HomeFragment homeFragment;
    GameFragment gameFragment;
    ReccomendationsFragment recommendations;
    SocialFragment socialFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragments instances creation
        homeFragment = HomeFragment.newInstance();
        gameFragment = GameFragment.newInstance();
        socialFragment = SocialFragment.newInstance();
        recommendations = ReccomendationsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        //loading home fragment
        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmets_container, homeFragment, HomeFragment.TAG)
                                    .commit();

        //creating navigation bars buttons and setting its listeners
        navBarHomeButton = (Button)findViewById(R.id.home_button);
        navBarHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("HOME");
            }
        });

        navBarGameButton = (Button)findViewById(R.id.game_button);
        navBarGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("GAME");
            }
        });

        navBarRecommendationButton = (Button)findViewById(R.id.reccomendations_button);
        navBarRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("RECOMMENDATIONS");
            }
        });

        navBarSocialButton = (Button)findViewById(R.id.social_button);
        navBarSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("SOCIAL");
            }
        });

        navBarProfileButton =(Button)findViewById(R.id.profile_button);
        navBarProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("PROFILE");
            }
        });
    }

    private void openFragment(String key){
        Fragment fragment = new Fragment();
        String tag  = new String();
        switch (key) {
            case "HOME":
                if (HomeFragment.TAG == null) {
                    fragment = HomeFragment.newInstance();
                }
                else{
                    fragment = homeFragment;
                }
                tag = HomeFragment.TAG;
                break;
            case "GAME":
                if (GameFragment.TAG == null){
                    fragment = GameFragment.newInstance();
                }
                else{
                    fragment = gameFragment;
                }
                tag = GameFragment.TAG;
                break;
            case "RECOMMENDATIONS":
                if (ReccomendationsFragment.TAG == null){
                    fragment = ReccomendationsFragment.newInstance();
                }
                else{
                    fragment = recommendations;
                }
                tag = ReccomendationsFragment.TAG;
                break;
            case "SOCIAL":
                if (SocialFragment.TAG == null) {
                    fragment = SocialFragment.newInstance();
                }
                else{
                    fragment = socialFragment;
                }
                tag = SocialFragment.TAG;
                break;
            case "PROFILE":
                if (ProfileFragment.TAG == null){
                    fragment = ProfileFragment.newInstance();
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
