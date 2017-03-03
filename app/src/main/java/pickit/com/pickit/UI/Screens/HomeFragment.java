package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PISongsListAdapter;
import pickit.com.pickit.Data.PISong;
import pickit.com.pickit.R;

/**
 * Created by or on 17/01/2017.
 */

public class HomeFragment extends Fragment {

    //Parameters:
    public static final String TAG = "HomeFragment";

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
        List<PISong> songList = new ArrayList<PISong>();
        //************ only for test******************
        PISong song = new PISong();
        song.songName = "tal hagever";
        song.authorName = "yotal";
        song.songId = 1;
        song.picksCount = 67;

        PISong song2 = new PISong();
        song2.songName = "tal hagever";
        song2.authorName = "yotal";
        song2.songId = 2;
        song2.picksCount = 67;

        PISong song3 = new PISong();
        song3.songName = "yotam hagever";
        song3.authorName = "yotal";
        song3.songId = 3;
        song3.picksCount = 67;

        PISong song4 = new PISong();
        song4.songName = "yossi hagever";
        song4.authorName = "yotal";
        song4.songId = 4;
        song4.picksCount = 67;

        PISong song5 = new PISong();
        song5.songName = "shomob hagever";
        song5.authorName = "yotal";
        song5.songId = 5;
        song5.picksCount = 67;

        PISong song6 = new PISong();
        song6.songName = "hhhh hagever";
        song6.authorName = "yotal";
        song6.songId = 6;
        song6.picksCount = 67;

        PISong song7 = new PISong();
        song7.songName = "oaaar hagever";
        song7.authorName = "yotal";
        song7.songId = 7;
        song7.picksCount = 67;

        songList.add(song);
        songList.add(song2);
        songList.add(song3);
        songList.add(song4);
        songList.add(song5);
        songList.add(song6);
        songList.add(song7);
        //*******************************

        View homeFragmentView = inflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        ListView songsTableListView = (ListView)homeFragmentView.findViewById(R.id.songList);
        PISongsListAdapter listAdapter = new PISongsListAdapter(getContext(), R.layout.pi_songs_list_cell);
        listAdapter.setSongsList(songList);
        songsTableListView.setAdapter(listAdapter);

        return homeFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
