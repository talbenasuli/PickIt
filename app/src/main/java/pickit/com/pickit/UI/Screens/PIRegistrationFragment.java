package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Data.PIUserData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PIMultiSelectionSpinner;

/**
 * Created by or on 18/03/2017.
 */

public class PIRegistrationFragment extends Fragment implements View.OnClickListener, PIModel.PIRegisterListener, PIModel.PISaveUserDataListener {
    public static final String TAG = "RegistrationFragment";
    PIMultiSelectionSpinner spinner;
    String[] genres;
    List<String> list;
    Button registerButton;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;

    public static PIRegistrationFragment newInstance() {
        Bundle args = new Bundle();
        PIRegistrationFragment fragment = new PIRegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View registrationFragmentView = inflater.from(getContext()).inflate(R.layout.pi_fragment_registration, container, false);
        return registrationFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        genres = getResources().getStringArray(R.array.genreArray);
        list = new ArrayList<String>();
        for (String g : genres) {
            list.add(g);
        }
        spinner = (PIMultiSelectionSpinner) view.findViewById(R.id.genreSpinner);
        spinner.initialize(list);

        registerButton = (Button) view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        firstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) view.findViewById(R.id.lastNameEditText);

        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);

    }

    @Override
    public void onClick(View view) {
        if(view == registerButton) {
            ((PILoginActivity)getActivity()).showLoadingFragment(R.id.loginContainerFrame, true);
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(!email.equals("") || !password.equals("")) {
                PIModel.getInstance().register(email,password,this);
            }
            else {
                ((PILoginActivity)getActivity()).hideLoadingFragment();
                Toast.makeText(getContext(),"Please Enter Your Details",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void registerOnComplete(FirebaseUser user) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        ArrayList<String> favoriteGeners = spinner.getselectedItems();

        PIUserData userData = new PIUserData(firstName, lastName, email, favoriteGeners);
        PIModel.getInstance().saveUserDetailsAfterRegistration(userData, this);
    }

    @Override
    public void registerOnCancel() {
        ((PILoginActivity)getActivity()).hideLoadingFragment();
        //TODO: notidy the user with toast
    }

    @Override
    public void saveUserDataOnComplete() {
        ((PILoginActivity)getActivity()).moveToMainActivity();
    }

    @Override
    public void saveUserDataOnCancel() {
        //TODO: notidy the user with toast and remove the new user from firebase
    }
}