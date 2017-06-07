package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Networking.Requests.Parsers.PIGetAllSongParser;

/**
 * Created by Tal on 04/06/2017.
 */

public class PIGetPlayingSongRequest extends PIBaseRequest {

    public interface PIGetPlayingSongListener {
        void getPlayingSongOnResponse(PIBaseData songData);
        void getPlayingSongOnErrorResponse(VolleyError error);
    }

    PIBaseData songData;
    private PIGetPlayingSongListener listener;

    public PIGetPlayingSongRequest(Context context) {
        super(context);

        urlSufix = "getCurrentPlayingSong";
    }

    @Override
    protected void parseData(JSONObject response) {
        PIGetAllSongParser parser = new PIGetAllSongParser();
        List<PIBaseData> songList = parser.parse(response);

        if(songList.size() > 0) {
            songData = songList.get(0);
        }
    }

    @Override
    protected void notifyError(VolleyError error) {
        listener.getPlayingSongOnErrorResponse(error);
    }

    @Override
    protected void notifySuccess() {
        listener.getPlayingSongOnResponse(songData);
    }

    public void setListener(PIGetPlayingSongListener listener) {
        this.listener =listener;
    }
}
