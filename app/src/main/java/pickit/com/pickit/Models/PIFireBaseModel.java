package pickit.com.pickit.Models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import pickit.com.pickit.Data.PIUserData;

/**
 * Created by or on 19/09/2017.
 */

public class PIFireBaseModel {

    private static String dataBaseName = "usersData";
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

}
