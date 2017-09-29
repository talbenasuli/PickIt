package pickit.com.pickit.UI.Screens;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIActivity;

/**
 * Created by Tal on 15/03/2017.
 */

public class PILoginActivity extends PIActivity
    implements PILoginFragment.PILoginFragmentListener
{

    private static final String TAG = "PILoginActivity";
    PIRegistrationFragment registrationFragment;
    PILoginFragment loginFragment;
    PIForgotYourPasswordFragment forgotYourPasswordFragment;


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
                .replace(R.id.loginContainerFrame, registrationFragment, PIRegistrationFragment.TAG).addToBackStack(null)
                .commit();
    }

    @Override
    public void openForgotYourPasswordView() {
        forgotYourPasswordFragment = PIForgotYourPasswordFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.loginContainerFrame, forgotYourPasswordFragment, PIForgotYourPasswordFragment.TAG).addToBackStack(null)
                .commit();
    }

    public void moveToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
