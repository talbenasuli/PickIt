package pickit.com.pickit.Data;

import java.util.ArrayList;

/**
 * Created by or on 19/09/2017.
 */

public class PIUserData {
    String firstName;
    String lastName;
    String email;
    ArrayList<PIGenreData> favoriteGeners;

    public PIUserData(String firstName, String lastName, String email, ArrayList<PIGenreData> favoriteGeners) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.favoriteGeners = favoriteGeners;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<PIGenreData> getFavoriteGeners() {
        return favoriteGeners;
    }

    public void setFavoriteGeners(ArrayList<PIGenreData> favoriteGeners) {
        this.favoriteGeners = favoriteGeners;
    }


}
