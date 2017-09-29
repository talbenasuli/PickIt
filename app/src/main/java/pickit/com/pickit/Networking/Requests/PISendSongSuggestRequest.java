package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import pickit.com.pickit.Models.PIModel;

/**
 * Created by Tal on 26/09/2017.
 */

public class PISendSongSuggestRequest extends PIBaseRequest {

    PIModel.PISendSongSuggestListener listener;

    public PISendSongSuggestRequest(Context context, String songName, String artist, String youtubeLink,
                                    ArrayList<String> generes) {
        super(context);
        urlSufix = "sendSongSuggest";
        queryParams.put("songName",songName);
        queryParams.put("artist", artist);
        queryParams.put("youtubeLink", youtubeLink);
    }

    @Override
    protected void parseData(JSONObject response) {}

    @Override
    protected void notifyError(VolleyError error) {}

    @Override
    protected void notifySuccess() {
        listener.sendSongSuggestOnSuccess();
    }

    public void setListener(PIModel.PISendSongSuggestListener listener) {
        this.listener = listener;
    }
}
