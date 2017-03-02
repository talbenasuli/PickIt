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

    public static final String TAG = "HomeFragment";

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
        PISong song = new PISong();
        song.songName = "or hagever";
        song.authorName = "yotal";
        song.songId = 1;
        song.picksCount = 67;

        songList.add(song);

        View view = inflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        ListView list = (ListView)view.findViewById(R.id.songList);
        PISongsListAdapter adapter = new PISongsListAdapter(getContext(), R.layout.pi_songs_list_cell);
        adapter.setSongsList(songList);
        list.setAdapter(adapter);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


}
