package pickit.com.pickit.UI.Screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PIListAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.PISocketIORequest;
import pickit.com.pickit.R;

/**
 * Created by or on 17/01/2017.
 */

public class HomeFragment extends Fragment implements PIListAdapter.PIListAdapterListener, View.OnClickListener
        , PIModel.getAllSongsRequestListener, PIModel.PIGetPlayingSongListener, PIModel.PIUpdatePickItRequestListener,
        PIModel.PISocketIORequestListener, TextWatcher, PIModel.PIGetPlaceNameListener, PIModel.PIGetUserPickitsListener {


    private String placeName;

    //Parameters:
    public static final String TAG = "HomeFragment";
    List<PIBaseData> songList;
    List<PIBaseData> searchSongList;
    List<String> userPickits;
    ListView songsTableListView;
    ListView searchSongListView;
    PIListAdapter listAdapter;
    PIListAdapter searchListAdapter;
    ImageButton searchButton;
    EditText searchEditText;
    View playingNowView;
    TextView placeNameTextView;
    ImageButton searchXButton;
    boolean isDetached = false;

    // Plating song view parameters:
    ImageButton playingNowRightImageButton;
    ImageView playingNowImageView;
    TextView playingNowPosition;
    TextView playingNowTopTextView;
    TextView playingNowBottomTextView;
    TextView PlayingNowRightTexView;
    ProgressBar playinNowProgressBar;

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
        searchSongList = new ArrayList<>();
        searchButton = (ImageButton) view.findViewById(R.id.searchIconButton);
        searchEditText = (EditText) view.findViewById(R.id.mainSearchEditText);
        searchEditText.addTextChangedListener(this);
        searchButton.setOnClickListener(this);

        playingNowView = view.findViewById(R.id.homeFragmentSongPlayer);
        playingNowRightImageButton = (ImageButton) playingNowView.findViewById(R.id.rightImageButton);
        playingNowImageView = (ImageView) playingNowView.findViewById(R.id.listRowImage);
        playingNowPosition = (TextView) playingNowView.findViewById(R.id.position);
        playingNowTopTextView = (TextView) playingNowView.findViewById(R.id.topTextView);
        playingNowBottomTextView = (TextView) playingNowView.findViewById(R.id.bottomTextView);
        PlayingNowRightTexView = (TextView) playingNowView.findViewById(R.id.rightTextView);
        playinNowProgressBar = (ProgressBar) playingNowView.findViewById(R.id.listViewProgressBar);
        playinNowProgressBar.getIndeterminateDrawable().setColorFilter(view.getResources().getColor(R.color.purpleDark), PorterDuff.Mode.MULTIPLY);


        playingNowRightImageButton.setImageResource(R.drawable.speaker);
        ((ViewGroup) playingNowPosition.getParent()).removeView(playingNowPosition);
        ((ViewGroup) PlayingNowRightTexView.getParent()).removeView(PlayingNowRightTexView);

        PIModel.getInstance().getPlayingSong(this);
        PIModel.getInstance().getAllSongs(this);

        placeNameTextView = (TextView) view.findViewById(R.id.homePlaceNameTextView);
        if(placeName == null){
            PIModel.getInstance().getPlaceName(this);
        }
        else {
            placeNameTextView.setText(placeName);
        }

        searchXButton = (ImageButton) view.findViewById(R.id.homeSearchXButton);
        searchXButton.setOnClickListener(this);

        songsTableListView = (ListView) view.findViewById(R.id.songList);
        searchSongListView = (ListView) view.findViewById(R.id.homeSearchSongList);

        listAdapter = new PIListAdapter(getContext(), R.layout.pi_list_row, R.drawable.like);
        searchListAdapter = new PIListAdapter(getContext(),R.layout.pi_list_row, R.drawable.like);

        listAdapter.listener = this;
        searchListAdapter.listener = this;

        listAdapter.setDataList(songList);
        searchListAdapter.setDataList(searchSongList);

        songsTableListView.setAdapter(listAdapter);
        searchSongListView.setAdapter(searchListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchSongListView.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isDetached = false;
        updateList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isDetached = true;
        PISocketIORequest.disconnectSocketIO();
    }

    @Override
    public void onClickRightButton(int songId) {
        PIModel.getInstance().updatePickIt(String.valueOf(songId), this);
        int songIndex = getSongIndexById(songId,songList);
        PIListRowData songFromSongList = (PIListRowData) songList.get(songIndex);
        songFromSongList.didPickit = true;

        if(searchSongList.size() != 0 && searchSongList != null && searchSongListView.getVisibility() == View.VISIBLE) {
            int songIndexInSearchList = getSongIndexById(songId,searchSongList);
            PIListRowData songInSearchList = (PIListRowData) searchSongList.get(songIndexInSearchList);
            songInSearchList.didPickit = songFromSongList.didPickit;
        }
        String songName = songList.get(getSongIndexById(songId , songList)).topText;
        PIModel.getInstance().saveSelectedSong(songName);
    }

    @Override
    public void onClick(View view) {
        if (view == searchButton) {
            searchEditText.requestFocus();
        }

        else if(view == searchXButton) {
            searchEditText.setText("");
        }
    }

    // PIGetAllSongsRequestListener
    @Override
    public void getAllSongsRequestOnResponse(List<PIBaseData> songList) {
        this.songList = songList;
        PIModel.getInstance().getUserPickits(this);
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
    public void onPickIt(String songId, int songPickIts) {
        int songIdAsInt = Integer.valueOf(songId);
        int songIndexAtSongList = getSongIndexById(Integer.valueOf(songId),songList);
        PIListRowData song = (PIListRowData) songList.get(songIndexAtSongList);
        song.rightText = String.valueOf(songPickIts);

        if(searchSongList.size() != 0 && searchSongList != null && searchSongListView.getVisibility() == View.VISIBLE) {
            int songIndexInSearchList = getSongIndexById(songIdAsInt,searchSongList);
            PIListRowData songInSearchList = (PIListRowData) searchSongList.get(songIndexInSearchList);
            songInSearchList.rightText = song.rightText;
        }

        updateListIfNeeded(songIndexAtSongList);
        updateSearchList();
    }

    @Override
    public void onSongEnds(String songId, PIListRowData songData) {
        PIModel.getInstance().getPlayingSong(this);
        songList.remove(0);
        songList.add(songData);
        updateList();
    }

    // PIGetPlayingSongRequest
    private void setPlayinSongInitialImage() {
        playingNowImageView.setImageResource(R.drawable.song_general_image);
        playinNowProgressBar.setVisibility(View.GONE);
        playingNowImageView.setVisibility(View.VISIBLE);
    }
    @Override
    public void getPlayingSongOnResponse(PIBaseData songData) {
        playingNowTopTextView.setText(songData.topText);
        playingNowBottomTextView.setText(songData.bottomText);
        if( songData.bottomText == null || songData.bottomText.isEmpty()) {
            setPlayinSongInitialImage();
        }
        PIModel.getInstance().getSongImage(songData.bottomText, new PIModel.PIGetSongImageListener() {
            @Override
            public void onResponse(Bitmap image) {
                playingNowImageView.setImageBitmap(image);
                playinNowProgressBar.setVisibility(View.GONE);
                playingNowImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(VolleyError error) {
                setPlayinSongInitialImage();
            }
        });
    }

    @Override
    public void getPlayingSongOnErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Could not load the playing song name",Toast.LENGTH_LONG);
    }

    // Helpers Methods
    private int getSongIndexById(int songId, List<PIBaseData> list) {
        int indexToReturn = -1; // -1 is not a valid index so is that function return -1 it's an error.
        for (int i = 0; i < list.size(); i++) {
            PIListRowData rowData = (PIListRowData) list.get(i);
            if (rowData.songId == songId) {
                indexToReturn = i;
                break;
            }
        }
        return indexToReturn;
    }

    private void updateList() {
        if(songList != null && !isDetached) {
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

    private void updateSearchList() {
        if(searchSongList != null && !isDetached) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchListAdapter.setDataList(searchSongList);
                    searchListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void searchSongByName(String name) {
        searchSongList = new ArrayList<>();
        for (PIBaseData data : songList) {
            PIListRowData song = (PIListRowData) data;
            if(song.topText.contains(name)) {
                searchSongList.add(song);
            }
        }
    }

    private void updateDataWithPickits() {

        if(userPickits == null) {
            return;
        }

        for(int i = 0; i<songList.size();i++) {
            for(int j = 0; j<userPickits.size();j++) {
                PIListRowData songData = (PIListRowData) songList.get(i);
                if(songData.songId == Integer.parseInt(userPickits.get(j))) {
                    songData.didPickit = true;
                }
            }
        }
        listAdapter.setDataList(songList);
        listAdapter.notifyDataSetChanged();
    }

    // PIGetPlaceNameRequest
    @Override
    public void placeNameOnResponse(String placeName) {
        this.placeName = placeName;
        placeNameTextView.setText(placeName);
        PIModel.getInstance().saveLastVisitedPlace(placeName);
    }

    @Override
    public void placeNameOnCancel(VolleyError error) {


    }

    @Override
    public void getUserPickitsOnResponse(List<String> userPickits) {
        this.userPickits = userPickits;
        updateDataWithPickits();
    }

    @Override
    public void getUserPickitsOnCancel(VolleyError error) {}

    // SearchTextFieldsListeners
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchSongListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchSongByName(charSequence.toString());
        updateSearchList();
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
