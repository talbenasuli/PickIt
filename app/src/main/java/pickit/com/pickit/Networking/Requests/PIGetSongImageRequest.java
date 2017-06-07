package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import pickit.com.pickit.Networking.Requests.Parsers.PIGetSongImageParser;

/**
 * Created by Tal on 05/06/2017.
 */

public class PIGetSongImageRequest extends PIBaseRequest {

    public interface PIGetSongImageRequestListener {
        public void getSongImageRequestOnResponse(String imagePath);
        public void getSongImageRequestOnErrorResponse(VolleyError error);
    }

    private String artistName;
    private String apiKey = "a16256d94a11ccbaecb710a24d549ee2";
    private String format = "json";
    private String imagePath;
    private PIGetSongImageRequestListener listener;

    public PIGetSongImageRequest(Context context, String imagePath) {
        super(context);
        this.artistName = imagePath;
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

    public void setListener(PIGetSongImageRequestListener listener) {
        this.listener = listener;
    }
}
