package pickit.com.pickit.Networking.Requests.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tal Ben Asuli on 19/09/2017.
 */

public class PISocketIOSongIDParser {

    public String parse(String jsonObject) {
        String id = "-1";
        for(int i =0 ; i < jsonObject.length(); i++) {
            if(Character.isDigit(jsonObject.charAt(i))) {
                id = String.valueOf(jsonObject.charAt(i));
            }
        }
        return id;
    }
}
