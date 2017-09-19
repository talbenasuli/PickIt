package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.Parsers.PIGetAllSongParser;

/**
 * Created by Tal on 14/05/2017.
 */

public class PIGetAllSongsRequest extends PIBaseRequest {

    public PIModel.getAllSongsRequestListener listener;
    private List<PIBaseData> songList;

    public PIGetAllSongsRequest(Context context) {
        super(context);
        urlSufix = "getAllSongs";
        songList = new ArrayList<>();
    }

    @Override
    public void parseData(JSONObject response) {
        PIGetAllSongParser parser = new PIGetAllSongParser();
        songList = parser.parse(response);
    }

    @Override
    protected void notifyError(VolleyError error) {
        listener.getAllSongsRequestOnError(error);
    }

    @Override
    protected void notifySuccess() {
        if(!songList.isEmpty()) {
            listener.getAllSongsRequestOnResponse(songList);
        }
    }

    public void setListener(PIModel.getAllSongsRequestListener listener) {
        this.listener = listener;
    }
}