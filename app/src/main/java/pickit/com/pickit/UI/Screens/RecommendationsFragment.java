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
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIMultiSelectionSpinner;

/**
 * Created by or on 17/01/2017.
 */

public class RecommendationsFragment extends Fragment {
    public static final String TAG = "RecommendationsFragment";
    PIMultiSelectionSpinner genreSpinner;
    PIMultiSelectionSpinner placeTypesSpinner;
    ListView placesTableListView;
    PIListAdapter listAdapter;
    List<String> genreList;
    List<String> placeTypesList;
    List<PIBaseData> placesList;



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
        genreSpinner =(PIMultiSelectionSpinner)view.findViewById(R.id.genreSpinner);
        placeTypesSpinner = (PIMultiSelectionSpinner)view.findViewById(R.id.placesSpinner);

        //creating the genre and places types lists from the resource
        String[] geners = getResources().getStringArray(R.array.genreArray);
        genreList = new ArrayList<String>();
        for(String g : geners){
            genreList.add(g);
        }

        String[] placeTypes = getResources().getStringArray(R.array.placeTypesArray);
        placeTypesList = new ArrayList<String>();
        for(String p : placeTypes){
            placeTypesList.add(p);
        }

        //assigning the lists to the spinners
        genreSpinner.initialize(genreList);

        placeTypesSpinner.initialize(placeTypesList);

        //only for tests creating the places list (in future we will get the list from the server)
        placesList = new ArrayList<PIBaseData>();

        PIListRowData place1 = new PIListRowData();
        place1.topText = "Japanika";
        place1.bottomText = "Resturant/Bar";
        place1.rightText = "5 km";

        PIListRowData place2 = new PIListRowData();
        place2.topText = "Jackie O";
        place2.bottomText = "resturant/Dance Bar/Pub";
        place2.rightText = "7 km";

        PIListRowData place3 = new PIListRowData();
        place3.topText = "BBB";
        place3.bottomText = "resturant/bar";
        place3.rightText = "2 km";

        PIListRowData place4 = new PIListRowData();
        place4.topText = "Holiday In";
        place4.bottomText = "Hotel";
        place4.rightText = "13 km";

        placesList.add(place1);
        placesList.add(place2);
        placesList.add(place3);
        placesList.add(place4);

        //assigning the data in to the listView
        placesTableListView = (ListView)view.findViewById(R.id.placesListView);
        listAdapter = new PIListAdapter(getContext(), R.layout.pi_list_row);
        listAdapter.setDataList(placesList);
        placesTableListView.setAdapter(listAdapter);
    }
}
