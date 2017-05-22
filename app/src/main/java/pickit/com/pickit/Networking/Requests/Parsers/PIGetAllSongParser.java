package pickit.com.pickit.Networking.Requests.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;

/**
 * Created by Tal on 16/05/2017.
 */

public class PIGetAllSongParser {

    public List<PIBaseData> parse(JSONObject object) {

        JSONArray jsonArray;
        List<PIBaseData> songList = new ArrayList<>();

        try {
            jsonArray = object.getJSONArray("songs");

            for (int i = 0; i < jsonArray.length(); i++) {
                PIListRowData data = new PIListRowData();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data.songId = Integer.parseInt(jsonObject.getString("songID"));
                data.topText = jsonObject.getString("songName");
                data.bottomText = jsonObject.getString("artist");
                data.rightText = jsonObject.getString("pickIts");
                songList.add(data);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return songList;
    }
}
