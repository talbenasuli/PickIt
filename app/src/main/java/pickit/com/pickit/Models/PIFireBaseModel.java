package pickit.com.pickit.Models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIUserData;

/**
 * Created by or on 19/09/2017.
 */

public class PIFireBaseModel {

    private static String dataBaseName = "usersData";
    private final int maxLastSongsListSize = 20;
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
        DatabaseReference myRef = database.getReference(dataBaseName);
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
        final DatabaseReference myRef = database.getReference(dataBaseName + "/" + currentUser.getUid() + "/" + "lastSelectedSongsList" );



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

    public void getUserLastPickits(final PIModel.getUserLastPickitsListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(dataBaseName + "/" + currentUser.getUid() + "/" + "lastSelectedSongsList" );

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PIBaseData> lastPickitsList = new ArrayList<>();
                int listSize = dataSnapshot.child("listSize").getValue(int.class);

                for (int i = 1 ; i < listSize ; i++){
                    PIBaseData song = new PIBaseData();
                    song.topText = dataSnapshot.child(String.valueOf(i)).getValue(String.class);
                    song.bottomText = String.valueOf(i);
                    lastPickitsList.add(song);
                }
                listener.getUserLastPickitsOnComplete(lastPickitsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
