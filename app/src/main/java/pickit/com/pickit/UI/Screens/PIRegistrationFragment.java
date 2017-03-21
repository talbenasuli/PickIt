package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIMultiSelectionSpinner;

/**
 * Created by or on 18/03/2017.
 */

public class PIRegistrationFragment extends Fragment {
    public static final String TAG = "RegistrationFragment";

    public static PIRegistrationFragment newInstance(){
        Bundle args = new Bundle();
        PIRegistrationFragment fragment = new PIRegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View registrationFragmentView = inflater.from(getContext()).inflate(R.layout.pi_fragment_registration, container, false);
        String[] geners = getResources().getStringArray(R.array.genereArray);
        List<String> list = new ArrayList<String>();
        for(String g : geners){
            list.add(g);
        }
        PIMultiSelectionSpinner spinner = (PIMultiSelectionSpinner)registrationFragmentView.findViewById(R.id.genreSpinner);
        spinner.setPrompt("aaaaaaa");

        spinner.setItems(list);
        return registrationFragmentView;
    }
}
