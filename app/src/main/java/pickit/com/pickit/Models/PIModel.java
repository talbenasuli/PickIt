package pickit.com.pickit.Models;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.URLUtil;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Data.PIPlaceData;
import pickit.com.pickit.Data.PIUserData;
import pickit.com.pickit.Networking.Requests.PIGetSongImagePathRequest;

/**
 * Created by Tal Ben Asuli on 18/09/2017.
 */

public class PIModel {
    private static final PIModel ourInstance = new PIModel();
    PIFireBaseModel modelFireBase;
    PIFilesModel modelFiles;
    PIRequestModel requestModel;
    PISongImageModel songImageModel;

    public static PIModel getInstance() {
        return ourInstance;
    }

    private PIModel() {
        requestModel = new PIRequestModel(PIMyApplication.getMyContext());
        songImageModel = new PISongImageModel(PIMyApplication.getMyContext());
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
        public void onPickIt(String songId, int songPickIts);
        public void onSongEnds(String songId, PIListRowData songData);
    }

    public void registerServerUpdates(PISocketIORequestListener listener) {
        requestModel.registerServerUpdates(listener);
    }

    public interface PIGetPlaceNameListener {
        public void placeNameOnResponse(String placeName);
        public void placeNameOnCancel(VolleyError error);
    }

    public void getPlaceName(PIGetPlaceNameListener listener) {
        requestModel.getPlaceName(listener);
    }

    public String getUserId() {
        return modelFireBase.getCurrentUserId();
    }

    public interface PIGetUserPickitsListener {
        public void getUserPickitsOnResponse(List<String> userPickits);
        public void getUserPickitsOnCancel(VolleyError error);
    }


    public void getUserPickits(PIGetUserPickitsListener listener) {
        requestModel.getUserPickits(listener);
    }

    public interface PISendSongSuggestListener {
        public void sendSongSuggestOnSuccess();
    }

    public void sendSongSuggest(PISendSongSuggestListener listener, String songName, String artist, String youtubeLink,
                                ArrayList<String> generes) {
        requestModel.sendSongSuggestRequest(listener,songName,artist,youtubeLink,generes);
    }

    public interface PIGetSongPathImageRequestListener {
        public void getSongImageRequestOnResponse(String imagePath);
        public void getSongImageRequestOnErrorResponse(VolleyError error);
    }

    public interface PIGetSongImageListener {
        public void onResponse(Bitmap image);
        public void onError(VolleyError error);
    }

    public void getSongImage( String artist, PIGetSongImageListener listener) {
        if(artist != null) {
            songImageModel.getSongImageRequest(listener,artist);
        }
    }


    //*******************fireBase*******************************


    //registration
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

    //login

    public interface LoginListener {
        public void loginOnComplete();
        public void loginOnCancel(String errorMessage);
    }

    public void login(String email, String password, LoginListener callback) {
        modelFireBase.login(email,password,callback);
    }

    //songs selections list manegment

    public void saveSelectedSong(String songName){
        modelFireBase.saveSelectedSong(songName);
    }


    public interface GetUserLastPickitsListener {
        public void getUserLastPickitsOnComplete(ArrayList<PIBaseData> lastPickitsList);
    }
    public void getUserLastPickits(GetUserLastPickitsListener listener){
        modelFireBase.getUserLastPickits(listener);
    }

    //last visited palces list manegment

    public void saveLastVisitedPlace(String placeName){
        modelFireBase.saveLastPlaceVisited(placeName);
    }

    public interface GetLastVisitedPlacesListener {
        public void getLastVisitedPlacesOnComplete(ArrayList<PIBaseData> lastVisitedPlacesList);
    }

    public void getLastVisitedPlaces(GetLastVisitedPlacesListener listener){
        modelFireBase.getLastvisitedPlaces(listener);
    }

    //image handeling

    public interface SaveImageListener{
        public void saveImageListenerOnComplete(String url);
    }

    public void saveImage(final Bitmap imageBmp, final String name, final SaveImageListener listener, final Activity activity) {
        modelFireBase.saveImage(imageBmp, name, new SaveImageListener() {
            @Override
            public void saveImageListenerOnComplete(String url) {
                String fileName = URLUtil.guessFileName(url, null, null);
                modelFiles.saveImageToFile(imageBmp,fileName, activity);
                listener.saveImageListenerOnComplete(url);
            }
        });
    }

    public interface GetImageListener{
        void getImageListenerOnSuccess(Bitmap image);
        void getImageListenerOnFail();
    }
    public void getImage(final Activity activity, final GetImageListener listener) {
        //check if image exsist localy

        getProfileImageUrl(new GetProfileImageUrlListener() {
            @Override
            public void profileImageUrlListenerOnComplete(final String url) {

                final String fileName = URLUtil.guessFileName(url, null, null);
                modelFiles.loadImageFromFileAsynch(fileName, new LoadImageFromFileAsynch() {
                    @Override
                    public void loadImageFromFileAsynchOnComplete(Bitmap bitmap) {
                        if (bitmap != null){
                            listener.getImageListenerOnSuccess(bitmap);
                        }else {
                            modelFireBase.getImage(url, new GetImageListener() {
                                @Override
                                public void getImageListenerOnSuccess(Bitmap image) {
                                    String fileName = URLUtil.guessFileName(url, null, null);
                                    Log.d("TAG","getImage from FB success " + fileName);
                                    modelFiles.saveImageToFile(image,fileName, activity);
                                    listener.getImageListenerOnSuccess(image);
                                }

                                @Override
                                public void getImageListenerOnFail() {
                                    Log.d("TAG","getImage from FB fail ");
                                    listener.getImageListenerOnFail();
                                }
                            });

                        }
                    }
                });
            }

            @Override
            public void profileImageUrlListenerOnFail() {
                listener.getImageListenerOnFail();
            }
        });

    }

    interface LoadImageFromFileAsynch{
        void loadImageFromFileAsynchOnComplete(Bitmap bitmap);
    }

    public interface GetProfileImageUrlListener {
        void profileImageUrlListenerOnComplete(String url);
        void profileImageUrlListenerOnFail();
    }
    public void getProfileImageUrl(GetProfileImageUrlListener listener){
        modelFireBase.getProfileImageUrl(listener);
    }

    public interface PIRestPasswordWithEmailListener {
        public void resetPasswordOnComplete();
    }

    public void sendRestPasswordWithEmailRequest(PIRestPasswordWithEmailListener listener, String emailAddress) {
        modelFireBase.sendRestPasswordWithEmail(listener, emailAddress);
    }

    //manage list of places that are working with pickit

    public interface getAllWorkingPlacesInRangeListener{
        void getAllWorkingPlacesInRangeListenerOnComplete(ArrayList<PIPlaceData> places);
    }

    public void getAllWorkingPlacesInRange(int range , getAllWorkingPlacesInRangeListener listener ){
        modelFireBase.getAllWorkingPlacesInRange(range , listener);
    }
}
