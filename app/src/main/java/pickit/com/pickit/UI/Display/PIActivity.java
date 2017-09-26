package pickit.com.pickit.UI.Display;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pickit.com.pickit.R;
import pickit.com.pickit.UI.Screens.PILoadingFragment;

/**
 * Created by or on 22/09/2017.
 */

public class PIActivity extends AppCompatActivity {

    PILoadingFragment loadingFragment = new PILoadingFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void showLoadingFragment(int containerID, Boolean isBackgroundClear){
        loadingFragment.isBackgroundClear(isBackgroundClear);
        getSupportFragmentManager().beginTransaction()
                .add(containerID,loadingFragment, null).addToBackStack(null)
                .commit();

    }

    public void hideLoadingFragment(){
        getSupportFragmentManager().beginTransaction().remove(loadingFragment).commit();
    }
}
