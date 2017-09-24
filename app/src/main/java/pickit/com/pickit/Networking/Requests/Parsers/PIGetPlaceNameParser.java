package pickit.com.pickit.Networking.Requests.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Omer on 23/09/2017.
 */

public class PIGetPlaceNameParser {
    String placeName;
    public String parse(JSONObject jsonObject) {
        try {
            placeName = jsonObject.getString("placeName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeName;
    }
}
