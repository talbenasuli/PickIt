package pickit.com.pickit.Networking.Requests.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tal on 05/06/2017.
 */

public class PIGetSongImageParser {

    public String parse(JSONObject jsonObject) {
        JSONArray jsonArray;
        String imagePath = "";

        try {
            JSONObject obj = jsonObject.getJSONObject("artist");
            jsonArray = obj.getJSONArray("image");
            obj = jsonArray.getJSONObject(4);
            imagePath = obj.getString("#text");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imagePath;
    }
}
