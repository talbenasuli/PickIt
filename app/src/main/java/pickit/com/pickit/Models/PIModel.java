package pickit.com.pickit.Models;

import com.android.volley.VolleyError;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Data.PIUserData;
import pickit.com.pickit.UI.Screens.HomeFragment;

/**
 * Created by Tal Ben Asuli on 18/09/2017.
 */

public class PIModel {
    private static final PIModel ourInstance = new PIModel();
    PIFireBaseModel modelFireBase;

    public static PIModel getInstance() {
        return ourInstance;
    }
    PIRequestModel requestModel;

    private PIModel() {
        requestModel = new PIRequestModel(PIMyApplication.getMyContext());
        modelFireBase = new PIFireBaseModel();
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
        public void onPickIt(String songId);
        public void onSongEnds(String songId, PIListRowData songData);
    }

    public void registerServerUpdates(PISocketIORequestListener listener) {
        requestModel.registerServerUpdates(listener);
    }


    //*******************fireBase*******************************

    public interface PIRegisterListener {
        public void registerOnComplete(FirebaseUser user);
        public void registerOnCancel();
    }

    public void register(String email, String password, PIRegisterListener callback) {
        modelFireBase.register(email,password,callback);
    }


    public interface PISaveUserDataListener {
        public void saveUserDataOnComplete();
        public void saveUserDataOnCancel();
    }
    public void saveUserDetailsAfterRegistration(PIUserData userData, PISaveUserDataListener listener ) {
        modelFireBase.saveUserDetailsAfterRegistration(userData, listener);
    }
}
