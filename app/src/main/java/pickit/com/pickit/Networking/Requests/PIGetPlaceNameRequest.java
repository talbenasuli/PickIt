package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.Parsers.PIGetPlaceNameParser;

/**
 * Created by Omer on 23/09/2017.
 */

public class PIGetPlaceNameRequest extends PIBaseRequest {
    PIModel.PIGetPlaceNameListener listener;
    String placeName;

    public PIGetPlaceNameRequest(Context context) {
        super(context);
        urlSufix = "getPlaceName";
    }

    @Override
    protected void parseData(JSONObject response) {
        PIGetPlaceNameParser parser = new PIGetPlaceNameParser();
        placeName = parser.parse(response);
    }

    @Override
    protected void notifyError(VolleyError error) {
        listener.placeNameOnCancel(error);
    }

    @Override
    protected void notifySuccess() {
        listener.placeNameOnResponse(placeName);
    }

    public void setListener(PIModel.PIGetPlaceNameListener listener) {
        this.listener = listener;
    }
}
