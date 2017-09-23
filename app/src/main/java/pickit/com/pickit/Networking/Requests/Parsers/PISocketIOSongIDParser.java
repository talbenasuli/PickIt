package pickit.com.pickit.Networking.Requests.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Data.PISocketIOData;

/**
 * Created by Tal Ben Asuli on 19/09/2017.
 */

public class PISocketIOSongIDParser {

    public PISocketIOData parse(JSONObject jsonObject) {

        String action = null;
        String songId = null;
        PIListRowData songData = null;

        try {
            action = jsonObject.getString("action");
            songId = jsonObject.getString("songId");
            JSONObject songJsonObject = jsonObject.getJSONObject("songData");
            PISongParser songParser = new PISongParser();
            songData = songParser.parse(songJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new PISocketIOData(action,songId,songData);
    }
}
