package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pickit.com.pickit.R;


/**
 * Created by or on 22/09/2017.
 */

public class PILoadingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View loadingFragment  = inflater.inflate(R.layout.pi_fragment_loading, container, false);
        return loadingFragment;
    }
}
