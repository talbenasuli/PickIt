package pickit.com.pickit.UI.Screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import pickit.com.pickit.R;


/**
 * Created by or on 22/09/2017.
 */

public class PILoadingFragment extends Fragment {

    private Boolean isBackgroundClear;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View loadingFragment  = inflater.inflate(R.layout.pi_fragment_loading, container, false);

        if(!isBackgroundClear){
            loadingFragment.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        return loadingFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.purpleDark), PorterDuff.Mode.MULTIPLY);
    }

    public void isBackgroundClear(Boolean isBackgroundClear){
        this.isBackgroundClear = isBackgroundClear;
    }
}
