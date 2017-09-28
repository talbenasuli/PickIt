package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.Parsers.PIGetSongImageParser;

/**
 * Created by Tal on 05/06/2017.
 */

public class PIGetSongImagePathRequest extends PIBaseRequest {

    private String artistName;
    private String apiKey = "a16256d94a11ccbaecb710a24d549ee2";
    private String format = "json";
    private String imagePath;
    private PIModel.PIGetSongPathImageRequestListener listener;

    public PIGetSongImagePathRequest(Context context, String artistName) {
        super(context);
        this.artistName = artistName;
        url = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artistName + "&api_key=" + apiKey + "&format=" + format;
    }

    @Override
    protected void parseData(JSONObject response) {
        PIGetSongImageParser parser = new PIGetSongImageParser();
        imagePath = parser.parse(response);
    }

    @Override
    protected void notifyError(VolleyError error) {
        listener.getSongImageRequestOnErrorResponse(error);
    }

    @Override
    protected void notifySuccess() {
       listener.getSongImageRequestOnResponse(imagePath);
    }

    public void setListener(PIModel.PIGetSongPathImageRequestListener listener) {
        this.listener = listener;
    }
}
