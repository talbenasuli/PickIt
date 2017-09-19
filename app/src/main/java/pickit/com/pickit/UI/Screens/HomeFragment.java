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
    ListView songsTableListView;
    PIListAdapter listAdapter;
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

        playingNowView                 = view.findViewById(R.id.homeFragmentSongPlayer);
        playingNowRightImageButton     = (ImageButton) playingNowView.findViewById(R.id.rightImageButton);
        playingNowImageView            = (ImageView) playingNowView.findViewById(R.id.listRowImage);
        playingNowPosition             = (TextView) playingNowView.findViewById(R.id.position);
        playingNowTopTextView          = (TextView) playingNowView.findViewById(R.id.topTextView);
        playingNowBottomTextView       = (TextView) playingNowView.findViewById(R.id.bottomTextView);
        PlayingNowRightTexView         = (TextView) playingNowView.findViewById(R.id.rightTextView);

        playingNowRightImageButton.setImageResource(R.drawable.speaker);
        ((ViewGroup) playingNowPosition.getParent()).removeView(playingNowPosition);
        ((ViewGroup) PlayingNowRightTexView.getParent()).removeView(PlayingNowRightTexView);

        PIModel.getInstance().getPlayingSong(this);
        PIModel.getInstance().getAllSongs(this);

        songsTableListView = (ListView) view.findViewById(R.id.songList);
        listAdapter = new PIListAdapter(getContext(), R.layout.pi_list_row, R.drawable.like);
        listAdapter.listener = this;
        listAdapter.setDataList(songList);
        songsTableListView.setAdapter(listAdapter);
    }

    @Override
    public void onClickRightButton(int position) {
        PIListRowData presentData = (PIListRowData) songList.get(position);
        int id = presentData.songId;
        PIModel.getInstance().updatePickIt(String.valueOf(id),this);
    }

    @Override
    public void onClick(View view) {
        if (view == searchButton) {
            searchEditText.requestFocus();
        }
    }

    // PIGetAllSongsRequestListener
    @Override
    public void getAllSongsRequestOnResponse(List <PIBaseData> songList) {
        this.songList = songList;
        listAdapter.setDataList(songList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllSongsRequestOnError(VolleyError error) {

    }

    // PIUpdatePickItRequestListener
    @Override
    public void updatePickItRequestOnResponse( ) {
        //getAllSongsRequest.sendRequest();
    }

    @Override
    public void updatePickItRequestOnErrorResponse(VolleyError error) {
        //TODO: dialog Box.
    }

    @Override
    public void shouldUpdateList() {
        //getAllSongsRequest.sendRequest();
        //getPlayingSongRequest.sendRequest();
    }

    @Override
    public void getPlayingSongOnResponse(PIBaseData songData) {
        playingNowTopTextView.setText(songData.topText);
        playingNowBottomTextView.setText(songData.bottomText);
    }

    @Override
    public void getPlayingSongOnErrorResponse(VolleyError error) {
        //TODO: add dialogView.
    }

//    @Override
//    public void getSongImageRequestOnResponse(String imagePath, final PIBaseData songData) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//
//        ImageRequest imgRequest = new ImageRequest(imagePath, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//              songData.image = response;
//                listAdapter.notifyDataSetChanged();
//            }
//        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //do stuff
//                    }
//                });
//
//        requestQueue.add(imgRequest);
//
//    }
//
//
//
//    @Override
//    public void getSongImageRequestOnErrorResponse(VolleyError error) {
//        // TODO: dialogBox.
//    }
}



////************ only for test in future we will get this data from server******************


//        PIListRowData song = new PIListRowData();
//        song.topText = "Money";
//        song.bottomText = "Pink Floyd";
//        song.songId = 1;
//        song.rightText = "60";
//        song.image = R.drawable.pink;
//
//        PIListRowData song2 = new PIListRowData();
//        song2.topText = "Dancing Queen";
//        song2.bottomText = "ABBA";
//        song2.songId = 2;
//        song2.rightText = "55";
//        song2.image = R.drawable.abba;
//
//        PIListRowData song3 = new PIListRowData();
//        song3.topText = "Smoke On The Water";
//        song3.bottomText = "AC-DC";
//        song3.songId = 2;
//        song3.rightText = "42";
//        song3.image = R.drawable.acdc;
//
//        PIListRowData song4 = new PIListRowData();
//        song4.topText = "Lose Yourself";
//        song4.bottomText = "Eminem";
//        song4.songId = 2;
//        song4.rightText = "40";
//        song4.image = R.drawable.eminem_lose;
//
//        PIListRowData song5 = new PIListRowData();
//        song5.topText = "When I'm Gone";
//        song5.bottomText = "Eminem";
//        song5.songId = 2;
//        song5.rightText = "36";
//        song5.image = R.drawable.eminem;
//
//        PIListRowData song6 = new PIListRowData();
//        song6.topText = "We Are The Champions";
//        song6.bottomText = "Queen";
//        song6.songId = 2;
//        song6.rightText = "15";
//        song6.image = R.drawable.queen;
//
//        PIListRowData song7 = new PIListRowData();
//        song7.topText = "All You Need Is Love";
//        song7.bottomText = "The Beatles";
//        song7.songId = 2;
//        song7.rightText = "12";
//        song7.image = R.drawable.beatless;
//
//        songList.add(song);
//        songList.add(song2);
//        songList.add(song3);
//        songList.add(song4);
//        songList.add(song5);
//        songList.add(song6);
//        songList.add(song7);


//    test onclicked righButton:
//    PIListRowData presentData = (PIListRowData) songList.get(position);
//    Integer rightText = Integer.parseInt(presentData.rightText) + 1;
//    presentData.rightText = rightText.toString();
//
//
//        while (position != 1 && position != 0 && Integer.parseInt(((PIListRowData) songList.get(position)).rightText) > Integer.parseInt(((PIListRowData) songList.get(position - 1)).rightText)) {
//
//        PIListRowData temp = (PIListRowData) songList.get(position - 1);
//        songList.set(position - 1, songList.get(position));
//        songList.set(position, temp);
//        position--;
//        }
//
//        listAdapter.notifyDataSetChanged();