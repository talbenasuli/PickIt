package pickit.com.pickit.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIGenreData;
import pickit.com.pickit.Data.PIPlaceData;
import pickit.com.pickit.Data.PIUserData;
import pickit.com.pickit.UI.Screens.MainActivity;

/**
 * Created by or on 19/09/2017.
 */

public class PIFireBaseModel {

    private static String usersDataBaseName = "usersData";
    private static String workingPlacesDataBaseName = "working places";
    private final int maxLastSongsListSize = 20;
    private final int maxLastVisitedPlacesListSize = 10;
    public static FirebaseUser currentUser;

    public void register(String email, String password, final PIModel.PIRegisterListener callback) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    currentUser = user;
                    callback.registerOnComplete(user);

                } else {
                    // If sign in fails, display a message to the user.
                    callback.registerOnCancel();
                }
            }
        });
    }

    public void saveUserDetailsAfterRegistration(PIUserData userData, final PIModel.PISaveUserDataListener listener ){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(usersDataBaseName);
        Map<String, Object> value = new HashMap<>();

        value.put("first name", userData.getFirstName());
        value.put("last name", userData.getLastName());
        value.put("email", userData.getEmail());
        value.put("generes", userData.getFavoriteGeners());

        Map<String, Object> lastSelectedSongsList = new HashMap<>();
        lastSelectedSongsList.put("listSize" , 0);

        value.put("lastSelectedSongsList" , lastSelectedSongsList);

        myRef.child(currentUser.getUid()).setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.saveUserDataOnComplete();
            }
        });
    }

    public void login(String email, String password, final PIModel.LoginListener callback) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            currentUser = user;
                            callback.loginOnComplete();
                        } else {
                            // If sign in fails, display a message to the user.
                            callback.loginOnCancel("error");
                        }
                    }
                });
    }

    public String getCurrentUserId() {
        return currentUser.getUid();
    }

    public void saveSelectedSong( final String songName){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(usersDataBaseName + "/" + currentUser.getUid() + "/" + "lastSelectedSongsList" );



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int listSize = dataSnapshot.child("listSize").getValue(int.class);

                if(listSize < maxLastSongsListSize){
                    listSize++;
                    myRef.child("listSize").setValue(listSize);
                    myRef.child(String.valueOf(listSize)).setValue(songName);
                }
                else {
                    Map<String, Object> value = new HashMap<>();
                    value.put("listSize" , listSize);
                    for (int i = 2 ; i <= maxLastSongsListSize ; i++){
                        String name = dataSnapshot.child(String.valueOf(i)).getValue(String.class);
                        value.put(String.valueOf(i-1) , name);
                    }
                    value.put(String.valueOf(maxLastSongsListSize) , songName);
                    myRef.setValue(value);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUserLastPickits(final PIModel.GetUserLastPickitsListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(usersDataBaseName + "/" + currentUser.getUid() + "/" + "lastSelectedSongsList" );

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PIBaseData> lastPickitsList = new ArrayList<>();
                int listSize = dataSnapshot.child("listSize").getValue(int.class);

                for (int i = 1 ; i < listSize ; i++){
                    PIBaseData song = new PIBaseData();
                    song.topText = dataSnapshot.child(String.valueOf(i)).getValue(String.class);
                    song.bottomText = String.valueOf(i);
                    lastPickitsList.add(0, song);
                }
                listener.getUserLastPickitsOnComplete(lastPickitsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveLastPlaceVisited( final String PlaceName){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(usersDataBaseName + "/" + currentUser.getUid() + "/" + "lastPlacesVisited" );



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long listSize = dataSnapshot.getChildrenCount();

                String lastVisitedPlace = dataSnapshot.child(String.valueOf(listSize)).getValue(String.class);

                if(!lastVisitedPlace.equals(PlaceName)) {

                    if(listSize < maxLastVisitedPlacesListSize){
                        myRef.child(String.valueOf(listSize + 1)).setValue(PlaceName);
                    }
                    else {
                        Map<String, Object> value = new HashMap<>();
                        for (int i = 2 ; i <= maxLastVisitedPlacesListSize ; i++){
                            String name = dataSnapshot.child(String.valueOf(i)).getValue(String.class);
                            value.put(String.valueOf(i-1) , name);
                        }
                        value.put(String.valueOf(maxLastVisitedPlacesListSize) , PlaceName);
                        myRef.setValue(value);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getLastvisitedPlaces(final PIModel.GetLastVisitedPlacesListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(usersDataBaseName + "/" + currentUser.getUid() + "/" + "lastPlacesVisited" );

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PIBaseData> lastPickitsList = new ArrayList<>();
                long listSize = dataSnapshot.getChildrenCount();

                for (int i = 1 ; i <= listSize ; i++){
                    PIBaseData place = new PIBaseData();
                    place.topText = dataSnapshot.child(String.valueOf(i)).getValue(String.class);
                    place.bottomText = String.valueOf(i);
                    lastPickitsList.add(0, place);
                }
                listener.getLastVisitedPlacesOnComplete(lastPickitsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveImage(Bitmap imageBmp, String id, final PIModel.SaveImageListener listener){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagesRef = storage.getReference().child("images").child(id);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                saveImageUrl(downloadUrl.toString() , listener);
            }
        });
    }

    private void saveImageUrl(final String url , final PIModel.SaveImageListener listener ){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(usersDataBaseName + "/" + currentUser.getUid() + "/profileImageUrl");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.saveImageListenerOnComplete(url);
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.setValue(url);
    }

    public void getImage(String url, final PIModel.GetImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.getImageListenerOnSuccess(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                listener.getImageListenerOnFail();
            }
        });
    }

    public void getProfileImageUrl(final PIModel.GetProfileImageUrlListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(usersDataBaseName + "/" + currentUser.getUid() + "/profileImageUrl");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);
                if(url != null){
                    listener.profileImageUrlListenerOnComplete(url);
                }
                else{
                    listener.profileImageUrlListenerOnFail();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendRestPasswordWithEmail(final PIModel.PIRestPasswordWithEmailListener listener, String emailAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.resetPasswordOnComplete();
                        }
                    }
                });
    }
    //manage list of places that are working with pickit

    public void getAllWorkingPlacesInRange(int range , final PIModel.getAllWorkingPlacesInRangeListener listener ){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(workingPlacesDataBaseName);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PIPlaceData> placesList = new ArrayList();

                for (DataSnapshot placeShot : dataSnapshot.getChildren()){
                    PIPlaceData place = new PIPlaceData();
                    place.topText = placeShot.getKey();
                    place.genresList = getGenersArrayFromSnapShot(placeShot.child("genres"));
                    place.bottomText = getStringFromGenresArray(place.genresList);
                    place.placeTypesList = getTypesArrayFromSnapShot(placeShot.child("types"));
                    placesList.add(place);
                }
                listener.getAllWorkingPlacesInRangeListenerOnComplete(placesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String getStringFromGenresArray(ArrayList<PIGenreData> genres){
        StringBuilder sb = new StringBuilder();
        Boolean isFirst = true;
        for (PIGenreData genre : genres) {
            if(!isFirst){
                sb.append(" | ");
            }
            isFirst = false;
            sb.append(genre.name);
        }

        return sb.toString();
    }

    private ArrayList<PIGenreData> getGenersArrayFromSnapShot(DataSnapshot snapShot){
        ArrayList<PIGenreData> genres = new ArrayList();

        for(DataSnapshot genreShot : snapShot.getChildren()){
            PIGenreData genre = new PIGenreData();

            genre.name = genreShot.getKey();
            genre.percentage = genreShot.getValue(long.class);
            genres.add(genre);
        }

        return genres;
    }

    private ArrayList<String> getTypesArrayFromSnapShot(DataSnapshot snapShot){
        ArrayList<String> types = new ArrayList();

        for(DataSnapshot genreShot : snapShot.getChildren()){
           String type = genreShot.getValue(String.class);
            types.add(type);
        }

        return types;
    }

}
