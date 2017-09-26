package pickit.com.pickit.UI.Screens;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PICustomSwipeAdapter;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Models.PIMyApplication;
import pickit.com.pickit.R;

/**
 * Created by or on 19/03/2017.
 */

public class PILoginFragment extends Fragment implements View.OnClickListener, PIModel.LoginListener {

    public static final String TAG = "PILoginFragment";
    List<Integer> imagesList;
    ViewPager viewPager;
    ImageButton loginButton;
    ImageButton registerButton;
    PICustomSwipeAdapter adapter;
    EditText emailEditText;
    EditText passwordEditText;

    private PILoginFragmentListener listener;


    public interface PILoginFragmentListener {
        void onRegistrationClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = ((PILoginFragmentListener) context);
        } catch (ClassCastException e) {
            throw  new ClassCastException("Activity " + getActivity().getClass().getName() + " must implement PILoginFragmentListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = ((PILoginFragmentListener) activity);
        } catch (ClassCastException e) {
            throw  new ClassCastException("Activity " + getActivity().getClass().getName() + " must implement PILoginFragmentListener");
        }
    }

    public static PILoginFragment newInstance() {
        Bundle args = new Bundle();
        PILoginFragment fragment = new PILoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View loginFragment  = inflater.inflate(R.layout.pi_login_fragment, container, false);
        return loginFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagesList = new ArrayList<Integer>();
        imagesList.add( R.drawable.pickit1);
        imagesList.add( R.drawable.pickit2);
        imagesList.add( R.drawable.pickit3);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        adapter = new PICustomSwipeAdapter(getContext(), imagesList);
        viewPager.setAdapter(adapter);

        loginButton = (ImageButton) view.findViewById(R.id.loginImageButton);
        loginButton.setOnClickListener(this);

        registerButton = (ImageButton)view.findViewById(R.id.pickItImageButton);
        registerButton.setOnClickListener(this);

        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
    }

    protected void onLoginSuccess(){
        ((PILoginActivity)getActivity()).moveToMainActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }


    @Override
    public void onClick(View view) {
        if(view == loginButton){

            ((PILoginActivity)getActivity()).showLoadingFragment(R.id.loginContainerFrame, true);
            //try to log in
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(email.equals("")  || password.equals("")){
                ((PILoginActivity)getActivity()).hideLoadingFragment();
                //TODO:toast
                Toast.makeText(getContext(), "enter email and password", Toast.LENGTH_LONG).show();
            }
            else {
                PIModel.getInstance().login(email, password, this);
            }

        }
        else if(view == registerButton){
            listener.onRegistrationClicked();
        }
    }

    @Override
    public void loginOnComplete() {
        onLoginSuccess();
    }

    @Override
    public void loginOnCancel(String errorMessage) {
        ((PILoginActivity)getActivity()).hideLoadingFragment();
        Toast.makeText(PIMyApplication.getMyContext(), errorMessage , Toast.LENGTH_SHORT);
    }
}
