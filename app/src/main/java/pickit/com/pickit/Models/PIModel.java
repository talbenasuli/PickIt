package pickit.com.pickit.Models;

import com.android.volley.VolleyError;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.UI.Screens.HomeFragment;

/**
 * Created by Tal Ben Asuli on 18/09/2017.
 */

public class PIModel {
    private static final PIModel ourInstance = new PIModel();

    public static PIModel getInstance() {
        return ourInstance;
    }
    PIRequestModel requestModel;

    private PIModel() {
        requestModel = new PIRequestModel(PIMyApplication.getMyContext());
    }

    public interface getAllSongsRequestListener {
        public void getAllSongsRequestOnResponse(List<PIBaseData> songList);
        public void getAllSongsRequestOnError(VolleyError error);
    }

    public void getAllSongs(getAllSongsRequestListener listener) {
        requestModel.getAllSongs(listener);
    }

    public interface PIGetPlayingSongListener {
        void getPlayingSongOnResponse(PIBaseData songData);
        void getPlayingSongOnErrorResponse(VolleyError error);
    }

    public void getPlayingSong(PIGetPlayingSongListener listener) {
        requestModel.getPlayingSong(listener);
    }


    public interface PIUpdatePickItRequestListener {
        void updatePickItRequestOnResponse();
        void updatePickItRequestOnErrorResponse(VolleyError error);
    }

    public void updatePickIt(String songID, PIUpdatePickItRequestListener listener) {
        requestModel.updatePickIt(songID,listener);
    }

    public interface PISocketIORequestListener {
        public void updateList(String songId);
    }

    public void registerServerUpdates(PISocketIORequestListener listener) {
        requestModel.registerServerUpdates(listener);
    }

}
