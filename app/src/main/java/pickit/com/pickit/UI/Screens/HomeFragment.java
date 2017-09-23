package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PIListAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.PIGetAllSongsRequest;
import pickit.com.pickit.Networking.Requests.PIGetPlayingSongRequest;
import pickit.com.pickit.Networking.Requests.PISocketIORequest;
import pickit.com.pickit.Networking.Requests.PIUpdatePickItRequest;
import pickit.com.pickit.R;

/**
 * Created by or on 17/01/2017.
 */

public class HomeFragment extends Fragment implements PIListAdapter.PIListAdapterListener, View.OnClickListener
        , PIModel.getAllSongsRequestListener, PIModel.PIGetPlayingSongListener, PIModel.PIUpdatePickItRequestListener,
        PIModel.PISocketIORequestListener {

    //Parameters:
    public static final String TAG = "HomeFragment";
    List<PIBaseData> songList;
    List<PIBaseData> searchSongList;
    ListView songsTableListView;
    ListView searchSongListView;
    PIListAdapter listAdapter;
    PIListAdapter searchListAdapter;
    ImageButton searchButton;
    EditText searchEditText;
    View playingNowView;

    // Plating song view parameters:
    ImageButton playingNowRightImageButton;
    ImageView playingNowImageView;
    TextView playingNowPosition;
    TextView playingNowTopTextView;
    TextView playingNowBottomTextView;
    TextView PlayingNowRightTexView;

    /**
     * creating new instance of HomeFragment
     *
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

        PIModel.getInstance().registerServerUpdates(this);

        songList = new ArrayList<PIBaseData>();
        searchButton = (ImageButton) view.findViewById(R.id.searchIconButton);
        searchEditText = (EditText) view.findViewById(R.id.mainSearchEditText);
        searchButton.setOnClickListener(this);

        playingNowView = view.findViewById(R.id.homeFragmentSongPlayer);
        playingNowRightImageButton = (ImageButton) playingNowView.findViewById(R.id.rightImageButton);
        playingNowImageView = (ImageView) playingNowView.findViewById(R.id.listRowImage);
        playingNowPosition = (TextView) playingNowView.findViewById(R.id.position);
        playingNowTopTextView = (TextView) playingNowView.findViewById(R.id.topTextView);
        playingNowBottomTextView = (TextView) playingNowView.findViewById(R.id.bottomTextView);
        PlayingNowRightTexView = (TextView) playingNowView.findViewById(R.id.rightTextView);

        playingNowRightImageButton.setImageResource(R.drawable.speaker);
        ((ViewGroup) playingNowPosition.getParent()).removeView(playingNowPosition);
        ((ViewGroup) PlayingNowRightTexView.getParent()).removeView(PlayingNowRightTexView);

        PIModel.getInstance().getPlayingSong(this);
        PIModel.getInstance().getAllSongs(this);

        songsTableListView = (ListView) view.findViewById(R.id.songList);
        searchSongListView = (ListView) view.findViewById(R.id.searchSongList);

        listAdapter = new PIListAdapter(getContext(), R.layout.pi_list_row, R.drawable.like);
        searchListAdapter = new PIListAdapter(getContext(),R.layout.pi_list_row);
        listAdapter.listener = this;
        listAdapter.setDataList(songList);
        songsTableListView.setAdapter(listAdapter);
    }

    @Override
    public void onClickRightButton(int position) {
        PIListRowData presentData = (PIListRowData) songList.get(position);
        int id = presentData.songId;
        PIModel.getInstance().updatePickIt(String.valueOf(id), this);
    }

    @Override
    public void onClick(View view) {
        if (view == searchButton) {
            searchEditText.requestFocus();
            searchSongListView.setVisibility(view.VISIBLE);
        }
    }

    // PIGetAllSongsRequestListener
    @Override
    public void getAllSongsRequestOnResponse(List<PIBaseData> songList) {
        this.songList = songList;
        listAdapter.setDataList(songList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllSongsRequestOnError(VolleyError error) {
        Toast.makeText(getContext(),"Could not load the song list",Toast.LENGTH_LONG);
    }

    // PIUpdatePickItRequestListener
    @Override
    public void updatePickItRequestOnResponse() {
        // function not in use because every update goes to should update list method.
    }

    @Override
    public void updatePickItRequestOnErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Could not update pickIt",Toast.LENGTH_LONG);
    }

    // PISocketIORequestListener
    @Override
    public void onPickIt(String songId) {
        int songIndex = getSongIndexById(Integer.valueOf(songId));
        PIListRowData song = (PIListRowData) songList.get(songIndex);
        song.rightText = String.valueOf(Integer.valueOf(song.rightText) + 1);
        updateListIfNeeded(songIndex);
    }

    @Override
    public void onSongEnds(String songId, PIListRowData songData) {
        PIModel.getInstance().getPlayingSong(this);
        songList.add(songData);
        updateList();
    }

    // PIGetPlayingSongRequest
    @Override
    public void getPlayingSongOnResponse(PIBaseData songData) {
        playingNowTopTextView.setText(songData.topText);
        playingNowBottomTextView.setText(songData.bottomText);
    }

    @Override
    public void getPlayingSongOnErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Could not load the playing song name",Toast.LENGTH_LONG);
    }

    // Helpers Methods
    private int getSongIndexById(int songId) {
        int indexToReturn = -1; // -1 is not a valid index so is that function return -1 it's an error.
        for (int i = 0; i < songList.size(); i++) {
            PIListRowData rowData = (PIListRowData) songList.get(i);
            if (rowData.songId == songId) {
                indexToReturn = i;
                break;
            }
        }
        return indexToReturn;
    }

    private void updateList() {
        if(songList != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listAdapter.setDataList(songList);
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void updateListIfNeeded(int songIndex) {
        final List<PIBaseData> songList = this.songList;
        for (int i = songIndex; i > 0; i--) {
            PIListRowData firstSong = (PIListRowData) songList.get(i);
            PIListRowData secondSong = (PIListRowData) songList.get(i - 1);
            if (Integer.valueOf(firstSong.rightText) > Integer.valueOf(secondSong.rightText)) {
                PIBaseData temp = songList.get(i);
                songList.set(i, secondSong);
                songList.set(i - 1, temp);
            }
        }
        updateList();
    }
}
