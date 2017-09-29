package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIMultiSelectionSpinner;

/**
 * Created by Tal on 26/09/2017.
 */

public class PISongSuggestFragment extends Fragment implements View.OnClickListener, PIModel.PISendSongSuggestListener, PIModel.PIGetPlaceNameListener {

    EditText songNameEditText;
    EditText artistEditText;
    EditText youtubeLinkEditText;
    Button sendButton;
    PIMultiSelectionSpinner spinner;
    String placeName;

    List<String> list;
    String[] genres;

    public static final String TAG = "PISongSuggestFragment";

    public static PISongSuggestFragment newInstance() {

        Bundle args = new Bundle();

        PISongSuggestFragment fragment = new PISongSuggestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View songSuggestView  = inflater.inflate(R.layout.pi_fragment_song_recomendation, container, false);
        return songSuggestView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PIModel.getInstance().getPlaceName(this);
        songNameEditText = (EditText) view.findViewById(R.id.songSuggestSongNameEditText);
        artistEditText = (EditText) view.findViewById(R.id.songSuggestArtistEditText);
        youtubeLinkEditText = (EditText) view.findViewById(R.id.songSuggestyoutubeLinkEditText);
        spinner = (PIMultiSelectionSpinner) view.findViewById(R.id.songSuggestGenreSpinner);

        genres = getResources().getStringArray(R.array.genreArray);
        list = new ArrayList<String>();
        for (String g : genres) {
            list.add(g);
        }
        spinner.initialize(list);

        sendButton = (Button) view.findViewById(R.id.songSuggestSendButton);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == sendButton) {
            if(songNameEditText.getText().toString().equals("")) {
                Toast.makeText(getContext(),"You must fil the name field",Toast.LENGTH_LONG).show();
            }

            else {
                PIModel.getInstance().sendSongSuggest(this,songNameEditText.getText().toString()
                        , artistEditText.getText().toString()
                        , youtubeLinkEditText.getText().toString()
                        ,spinner.getselectedItems());
            }
        }
    }

    @Override
    public void sendSongSuggestOnSuccess() {
        String message = "Thank you, " + placeName + " got your suggestion";
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void placeNameOnResponse(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public void placeNameOnCancel(VolleyError error) {}
}
