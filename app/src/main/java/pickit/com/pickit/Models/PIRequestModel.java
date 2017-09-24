package pickit.com.pickit.Models;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Networking.Requests.PIGetAllSongsRequest;
import pickit.com.pickit.Networking.Requests.PIGetPlaceNameRequest;
import pickit.com.pickit.Networking.Requests.PIGetPlayingSongRequest;
import pickit.com.pickit.Networking.Requests.PIGetUserPickItsRequest;
import pickit.com.pickit.Networking.Requests.PISocketIORequest;
import pickit.com.pickit.Networking.Requests.PIUpdatePickItRequest;

/**
 * Created by Tal Ben Asuli on 18/09/2017.
 */

public class PIRequestModel {

    Context context;
    public PIRequestModel(Context context) {
        this.context = context;
    }

    public void getAllSongs(final PIModel.getAllSongsRequestListener listener) {
        PIGetAllSongsRequest allSongsRequest = new PIGetAllSongsRequest(context);
        allSongsRequest.setListener(new PIModel.getAllSongsRequestListener() {
            @Override
            public void getAllSongsRequestOnResponse(List<PIBaseData> songList) {
                listener.getAllSongsRequestOnResponse(songList);
            }

            @Override
            public void getAllSongsRequestOnError(VolleyError error) {
                listener.getAllSongsRequestOnError(error);
            }
        });
        allSongsRequest.sendRequest();
    }

    public void getPlayingSong(final PIModel.PIGetPlayingSongListener listener) {
        PIGetPlayingSongRequest playingSongRequest = new PIGetPlayingSongRequest(context);
        playingSongRequest.setListener(new PIModel.PIGetPlayingSongListener() {
            @Override
            public void getPlayingSongOnResponse(PIBaseData songData) {
                listener.getPlayingSongOnResponse(songData);
            }

            @Override
            public void getPlayingSongOnErrorResponse(VolleyError error) {
                listener.getPlayingSongOnErrorResponse(error);
            }
        });
        playingSongRequest.sendRequest();
    }

    public void updatePickIt(String songID, final PIModel.PIUpdatePickItRequestListener listener) {
        PIUpdatePickItRequest updatePickItRequest = new PIUpdatePickItRequest(context, songID);
        updatePickItRequest.setListener(new PIModel.PIUpdatePickItRequestListener() {
            @Override
            public void updatePickItRequestOnResponse() {
                listener.updatePickItRequestOnResponse();
            }

            @Override
            public void updatePickItRequestOnErrorResponse(VolleyError error) {
                listener.updatePickItRequestOnErrorResponse(error);
            }
        });
        updatePickItRequest.sendRequest();
    }

    public void registerServerUpdates(final PIModel.PISocketIORequestListener listener) {
        PISocketIORequest registerServerUpdatesRequest = new PISocketIORequest();
        registerServerUpdatesRequest.setListener(new PIModel.PISocketIORequestListener() {
            @Override
            public void onPickIt(String songId) {
                listener.onPickIt(songId);
            }

            public void onSongEnds(String songId, PIListRowData songData) {
                listener.onSongEnds(songId, songData);
            }
        });
        registerServerUpdatesRequest.sendSocketIOConnectRequest();
    }

    public void getPlaceName(final PIModel.PIGetPlaceNameListener listener) {
        PIGetPlaceNameRequest request = new PIGetPlaceNameRequest(context);
        request.setListener(new PIModel.PIGetPlaceNameListener() {
            @Override
            public void placeNameOnResponse(String placeName) {
                listener.placeNameOnResponse(placeName);
            }

            @Override
            public void placeNameOnCancel(VolleyError error) {
                listener.placeNameOnCancel(error);
            }
        });

        request.sendRequest();
    }

    public void getUserPickits(final PIModel.PIGetUserPickitsListener listener) {
        PIGetUserPickItsRequest request = new PIGetUserPickItsRequest(context);
        request.setListener(new PIModel.PIGetUserPickitsListener() {
            @Override
            public void getUserPickitsOnResponse(List<String> userPickits) {
                listener.getUserPickitsOnResponse(userPickits);
            }

            @Override
            public void getUserPickitsOnCancel(VolleyError error) {
                listener.getUserPickitsOnCancel(error);
            }
        });

        request.sendRequest();
    }


}
