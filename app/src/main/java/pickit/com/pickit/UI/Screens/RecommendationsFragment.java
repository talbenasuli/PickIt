package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import pickit.com.pickit.Adapters.PIListAdapter;
import pickit.com.pickit.Adapters.PIPlacesAdapter;
import pickit.com.pickit.Adapters.PIPlacesComperator;
import pickit.com.pickit.Data.PIGenreData;
import pickit.com.pickit.Data.PIPlaceData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIMultiSelectionSpinner;

/**
 * Created by or on 17/01/2017.
 */

public class RecommendationsFragment extends Fragment implements PIListAdapter.PIListAdapterListener, PIModel.getAllWorkingPlacesInRangeListener, PIMultiSelectionSpinner.multiSelectionSpinnerListener {
    public static final String TAG = "RecommendationsFragment";
    PIMultiSelectionSpinner placeTypesSpinner;
    ListView placesTableListView;
    PIPlacesAdapter listAdapter;
    ArrayList<PIPlaceData> filteredList;
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
        placeTypesSpinner.listener = this;
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

    @Override
    public void multiSelectionSpinnerListenerFinishSelection() {
        ArrayList<String> typesToSortBy = placeTypesSpinner.getselectedItems();
        ArrayList<PIPlaceData> listToShow;
        if(typesToSortBy.size() > 0){
            listToShow = filterListByTypes(typesToSortBy);
        }
        else {
            listToShow = placesList;
        }

        sortPlacesByUserFavoritGenres(listToShow);
    }

    private ArrayList<PIPlaceData> filterListByTypes(ArrayList<String> typesToSortBy){
        filteredList = new ArrayList();

        for(PIPlaceData place : placesList){
            for(String type :typesToSortBy){
                if(place.placeTypesList.indexOf(type) != -1){
                    filteredList.add(place);
                    break;
                }
            }
        }

        return filteredList;
    }

    private void sortPlacesByUserFavoritGenres(final ArrayList<PIPlaceData> listToSort){

        PIModel.getInstance().getUserFavoriteGenres(new PIModel.getUserFavoriteGenresListener() {
            @Override
            public void getUserFavoriteGenresOnComplete(ArrayList<PIGenreData> userFavoriteGenres) {
                Collections.sort(listToSort , new PIPlacesComperator(userFavoriteGenres));
                listAdapter.setDataList(listToSort);
                listAdapter.notifyDataSetChanged();
            }
        });
      //  Collections.sort(listToSort , new PIPlacesComperator());
    }
}
