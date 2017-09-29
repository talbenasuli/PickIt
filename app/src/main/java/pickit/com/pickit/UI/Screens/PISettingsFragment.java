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
 * Created by Tal on 21/03/2017.
 */

public class PISettingsFragment extends Fragment implements View.OnClickListener, PIModel.PIChangePaswordListener {

    EditText newPasswordEditText;
    EditText rePasswordEditText;
    EditText oldPasswordEditText;
    ImageButton saveButton;

    public static final String TAG = "PISettingsFragment";

    public static PISettingsFragment newInstance() {

        Bundle args = new Bundle();

        PISettingsFragment fragment = new PISettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.pi_fragment_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newPasswordEditText = (EditText) view.findViewById(R.id.settingsNewPasswordEditText);
        rePasswordEditText = (EditText) view.findViewById(R.id.settingRePasswordEditText);
        oldPasswordEditText = (EditText) view.findViewById(R.id.settingOldPasswordEditText);

        saveButton = (ImageButton) view.findViewById(R.id.settingSaveButton);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == saveButton) {
            String newPassword = newPasswordEditText.getText().toString();
            String oldPassword = oldPasswordEditText.getText().toString();
            String rePassword = rePasswordEditText.getText().toString();

            if(newPassword.isEmpty() || oldPassword.isEmpty() || rePassword.isEmpty()) {
                Toast.makeText(getContext(),"Please Fill ALL The Fields",Toast.LENGTH_LONG).show();
            }

            else if(!newPassword.equals(rePassword)){
                Toast.makeText(getContext(),"The Password And The ReEnter Password Are Not Match",Toast.LENGTH_LONG).show();
            }

            else {
                PIModel.getInstance().changeUserPassword(this,oldPassword,newPassword);
            }
        }
    }

    //changeUserPasswordListener
    @Override
    public void changePasswordOnComplete() {
        Toast.makeText(getContext(),"Password Changed Successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void changePasswordOnFail() {
        Toast.makeText(getContext(),"We Have Got A Problem To Change Your Password Please Try Later",Toast.LENGTH_LONG).show();
    }

    @Override
    public void changePasswordCredentialFail() {
        Toast.makeText(getContext(),"Old Password Wrong",Toast.LENGTH_LONG).show();
    }
}
