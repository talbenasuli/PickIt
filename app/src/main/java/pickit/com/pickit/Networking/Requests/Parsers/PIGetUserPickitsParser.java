package pickit.com.pickit.Networking.Requests.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omer on 23/09/2017.
 */

public class PIGetUserPickitsParser {
    public List<String> parse(JSONObject jsonObject) {
        List<String> pickItList = new ArrayList<>();
        try {
            JSONArray userPickisJsonArray = jsonObject.getJSONArray("userPickits");
            for(int i = 0; i < userPickisJsonArray.length(); i++) {
                String songId = userPickisJsonArray.getString(i);
                pickItList.add(songId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pickItList;
    }
}
