package pickit.com.pickit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by or on 12/01/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load fragment
        getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmets_container, HomeFragment.newInstance(), HomeFragment.TAG)
                                    .commit();
    }
}
