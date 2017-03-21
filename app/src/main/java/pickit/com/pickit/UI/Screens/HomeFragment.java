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

/**
 * Created by or on 17/01/2017.
 */

public class HomeFragment extends Fragment {

    //Parameters:
    public static final String TAG = "HomeFragment";
    List<PIBaseData> songList;
    ListView songsTableListView;
    PIListAdapter listAdapter;

    /**
     * creating new instance of HomeFragment
     * @return the instance of Home Fragment.
     */
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeFragmentView = inflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        return homeFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        songList = new ArrayList<PIBaseData>();
        //************ only for test in future we will get this data from server******************
        PIListRowData song = new PIListRowData();
        song.topText = "tal hagever";
        song.bottomText = "yotal";
        song.songId = 1;
        song.rightText = "67";

        PIListRowData song2 = new PIListRowData();
        song2.topText = "tal hagever";
        song2.bottomText = "yotal";
        song2.songId = 2;
        song2.rightText = "67";

        PIListRowData song3 = new PIListRowData();
        song3.topText = "tal hagever";
        song3.bottomText = "yotal";
        song3.songId = 2;
        song3.rightText = "67";

        PIListRowData song4 = new PIListRowData();
        song4.topText = "tal hagever";
        song4.bottomText = "yotal";
        song4.songId = 2;
        song4.rightText = "67";

        PIListRowData song5 = new PIListRowData();
        song5.topText = "tal hagever";
        song5.bottomText = "yotal";
        song5.songId = 2;
        song5.rightText = "67";

        PIListRowData song6 = new PIListRowData();
        song6.topText = "tal hagever";
        song6.bottomText = "yotal";
        song6.songId = 2;
        song6.rightText = "67";

        PIListRowData song7 = new PIListRowData();
        song7.topText = "tal hagever";
        song7.bottomText = "yotal";
        song7.songId = 2;
        song7.rightText = "67";

        songList.add(song);
        songList.add(song2);
        songList.add(song3);
        songList.add(song4);
        songList.add(song5);
        songList.add(song6);
        songList.add(song7);

        songsTableListView = (ListView)view.findViewById(R.id.songList);
        listAdapter = new PIListAdapter(getContext(), R.layout.pi_list_row);
        listAdapter.setDataList(songList);
        songsTableListView.setAdapter(listAdapter);
    }

}
