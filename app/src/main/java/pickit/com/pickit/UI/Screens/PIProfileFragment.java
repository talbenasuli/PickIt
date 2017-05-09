package pickit.com.pickit.UI.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PIRecyclerViewAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.R;

/**
 * Created by Tal on 21/03/2017.
 */

public class PIProfileFragment extends Fragment implements View.OnClickListener {

    RecyclerView songsRecyclerView;
    PIRecyclerViewAdapter adapter;
    ImageButton settingsImageButton;
    RecyclerView placesVisitedRecyclerView;

    public static final String TAG = "PIProfileFragment";

    private PIProfileFragmentListener listener;

    public interface PIProfileFragmentListener {
        void onSettingsImageButtonClicked();
    }

    public static PIProfileFragment newInstance() {

        Bundle args = new Bundle();

        PIProfileFragment fragment = new PIProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.pi_fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsImageButton = (ImageButton)view.findViewById(R.id.settingsImageButton);
        settingsImageButton.setOnClickListener(this);

        songsRecyclerView =(RecyclerView) view.findViewById(R.id.myPickitsRecyclerView);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        placesVisitedRecyclerView = (RecyclerView) view.findViewById(R.id.placesVisitedRecyclerView);
        placesVisitedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<PIBaseData> dataList = new ArrayList<PIBaseData>();

        PIBaseData firsData = new PIBaseData();
        firsData.topText = "Eyal Golan";
        firsData.bottomText = "this is how we do !!!!";

        PIBaseData ss = new PIBaseData();
        ss.topText = "Eyal Golan";
        ss.bottomText = "this is how we do !!!!";

        PIBaseData dd = new PIBaseData();
        dd.topText = "Eyal Golan";
        dd.bottomText = "this is how we do !!!!";
        dataList.add(firsData);

        PIBaseData ff = new PIBaseData();
        ff.topText = "Eyal Golan";
        ff.bottomText = "this is how we do !!!!";

        dataList.add(firsData);
        dataList.add(ss);
        dataList.add(dd);
        dataList.add(ff);


        adapter = new PIRecyclerViewAdapter();
        adapter.setData(dataList);
        songsRecyclerView.setAdapter(adapter);
        placesVisitedRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = ((PIProfileFragmentListener) activity);
        } catch (ClassCastException e) {
            throw  new ClassCastException("Activity " + getActivity().getClass().getName() + " must implement PILoginFragmentListener");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == this.settingsImageButton){
            listener.onSettingsImageButtonClicked();
        }
    }
}


