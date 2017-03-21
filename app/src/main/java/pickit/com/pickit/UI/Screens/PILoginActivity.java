package pickit.com.pickit.UI.Screens;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;

import java.util.List;

import pickit.com.pickit.Adapters.PICustomSwipeAdapter;
import pickit.com.pickit.R;

/**
 * Created by Tal on 15/03/2017.
 */

public class PILoginActivity extends AppCompatActivity
    implements PILoginFragment.PILoginFragmentListener
{

    private static final String TAG = "PILoginActivity";
    PIRegistrationFragment registrationFragment;
    PILoginFragment loginFragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pi_login_activity);
        loginFragment = PILoginFragment.newInstance();
        registrationFragment = PIRegistrationFragment.newInstance();

        //loading login fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loginContainerFrame, loginFragment, PILoginFragment.TAG)
                .commit();
    }

    @Override
    public void onRegistrationClicked() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.loginContainerFrame, registrationFragment, PIRegistrationFragment.TAG).addToBackStack(null)
                .commit();
    }
}
