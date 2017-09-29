package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;

/**
 * Created by Omer on 29/09/2017.
 */

public class PIForgotYourPasswordFragment extends Fragment implements View.OnClickListener, PIModel.PIRestPasswordWithEmailListener {
    public static final String TAG = "PIForgotYourPasswordFragment";

    //forgot your password views
    View forgotYoutPasswordView;
    EditText forgotYourPasswordEditText;
    ImageButton forgotYourPasswordConfirmImageButton;

    public static PIForgotYourPasswordFragment newInstance() {
        Bundle args = new Bundle();
        PIForgotYourPasswordFragment fragment = new PIForgotYourPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View forgotYourPasswordView = inflater.from(getContext()).inflate(R.layout.pi_forgot_your_password_fragment, container, false);
        return forgotYourPasswordView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        forgotYoutPasswordView = view.findViewById(R.id.forgotYourPasswordView);
        forgotYourPasswordEditText = (EditText) forgotYoutPasswordView.findViewById(R.id.forgotYourMailEditText);
        forgotYourPasswordConfirmImageButton = (ImageButton) forgotYoutPasswordView.findViewById(R.id.forgotYourPasswordSendImageButton);

        forgotYourPasswordConfirmImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == forgotYourPasswordConfirmImageButton) {
            String email = forgotYourPasswordEditText.getText().toString();
            if (email == null || email.equals("")) {
                Toast.makeText(getContext(), "Please Enter Your Mail", Toast.LENGTH_LONG).show();
            } else {
                PIModel.getInstance().sendRestPasswordWithEmailRequest(this, email);
            }
        }
    }

    // sendRestPasswordWithEmailRequest
    @Override
    public void resetPasswordOnComplete() {
        Toast.makeText(getContext(),"You Have Got A Mail To Reset Your Password",Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
