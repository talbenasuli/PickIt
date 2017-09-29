package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PIListAdapter;
import pickit.com.pickit.Adapters.PIPlacesAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Data.PIPlaceData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIMultiSelectionSpinner;

/**
 * Created by or on 17/01/2017.
 */

public class RecommendationsFragment extends Fragment implements PIListAdapter.PIListAdapterListener, PIModel.getAllWorkingPlacesInRangeListener {
    public static final String TAG = "RecommendationsFragment";
    PIMultiSelectionSpinner placeTypesSpinner;
    ListView placesTableListView;
    PIPlacesAdapter listAdapter;
    ArrayList<String> placeTypesList;
    ArrayList<PIPlaceData> placesList;



    public static RecommendationsFragment newInstance() {

        Bundle args = new Bundle();

        RecommendationsFragment fragment = new RecommendationsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.pi_fragment_recommendations, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeTypesSpinner = (PIMultiSelectionSpinner)view.findViewById(R.id.placesSpinner);

        String[] placeTypes = getResources().getStringArray(R.array.placeTypesArray);
        placeTypesList = new ArrayList<String>();
        for(String p : placeTypes){
            placeTypesList.add(p);
        }

        placeTypesSpinner.initialize(placeTypesList);
        placesList = new ArrayList();
        PIModel.getInstance().getAllWorkingPlacesInRange(0 , this);

        //assigning the data in to the listView
        placesTableListView = (ListView)view.findViewById(R.id.placesListView);
        listAdapter = new PIPlacesAdapter(getContext(), R.layout.pi_list_row, R.drawable.places_icon);
        listAdapter.setDataList(placesList);
        placesTableListView.setAdapter(listAdapter);
    }

    @Override
    public void onClickRightButton(int position) {
    }

    @Override
    public void getAllWorkingPlacesInRangeListenerOnComplete(ArrayList<PIPlaceData> places) {
        placesList = places;
        listAdapter.setDataList(places);
        listAdapter.notifyDataSetChanged();
    }
}
