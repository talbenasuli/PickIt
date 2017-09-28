package pickit.com.pickit.UI.Screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PIRecyclerViewAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;

/**
 * Created by Tal on 21/03/2017.
 */

public class PIProfileFragment extends Fragment implements View.OnClickListener, PIModel.GetUserLastPickitsListener, PIModel.GetLastVisitedPlacesListener, PIModel.SaveImageListener, PIModel.GetImageListener {

    RecyclerView songsRecyclerView;
    PIRecyclerViewAdapter lastSongsAdapter;
    PIRecyclerViewAdapter lastVisitedPlacesAdapter;
    ImageButton settingsImageButton;
    ImageButton cameraImageButton;
    ImageView profilePictureImageView;
    Bitmap profileImageBitmap;
    RecyclerView placesVisitedRecyclerView;
    private int requestsCounter = 0;

    public static final String TAG = "PIProfileFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private PIProfileFragmentListener listener;

    public interface PIProfileFragmentListener {
        void onSettingsImageButtonClicked();
    }

    public static PIProfileFragment newInstance() {

        Bundle args = new Bundle();

        PIProfileFragment fragment = new PIProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.pi_fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsImageButton = (ImageButton)view.findViewById(R.id.settingsImageButton);
        settingsImageButton.setOnClickListener(this);

        cameraImageButton = (ImageButton) view.findViewById(R.id.openCameraImageButton);
        cameraImageButton.setOnClickListener(this);

        profilePictureImageView = (ImageView) view.findViewById(R.id.profilePictureImageView);
        requestsCounter++;
        PIModel.getInstance().getImage(getActivity(),this);

        songsRecyclerView =(RecyclerView) view.findViewById(R.id.myPickitsRecyclerView);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        placesVisitedRecyclerView = (RecyclerView) view.findViewById(R.id.placesVisitedRecyclerView);
        placesVisitedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<PIBaseData> dataList = new ArrayList<PIBaseData>();

        PIBaseData firsData = new PIBaseData();
        firsData.topText = "Eyal Golan";
        firsData.bottomText = "this is how we do !!!!";

        PIBaseData ss = new PIBaseData();
        ss.topText = "Eyal Golan";
        ss.bottomText = "this is how we do !!!!";

        PIBaseData dd = new PIBaseData();
        dd.topText = "Eyal Golan";
        dd.bottomText = "this is how we do !!!!";
        dataList.add(firsData);

        PIBaseData ff = new PIBaseData();
        ff.topText = "Eyal Golan";
        ff.bottomText = "this is how we do !!!!";

        dataList.add(firsData);
        dataList.add(ss);
        dataList.add(dd);
        dataList.add(ff);


        lastSongsAdapter = new PIRecyclerViewAdapter();
        lastSongsAdapter.setData(dataList);
        lastVisitedPlacesAdapter = new PIRecyclerViewAdapter();
        lastVisitedPlacesAdapter.setData(dataList);
        songsRecyclerView.setAdapter(lastSongsAdapter);
        placesVisitedRecyclerView.setAdapter(lastVisitedPlacesAdapter);

        requestsCounter++;
        PIModel.getInstance().getUserLastPickits(this);
        requestsCounter++;
        PIModel.getInstance().getLastVisitedPlaces(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = ((PIProfileFragmentListener) activity);
        } catch (ClassCastException e) {
            throw  new ClassCastException("Activity " + getActivity().getClass().getName() + " must implement PILoginFragmentListener");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == this.settingsImageButton){
            listener.onSettingsImageButtonClicked();
        }
        else if(v == cameraImageButton){
            openCamera();
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePictureImageView.setImageBitmap(imageBitmap);
            this.profileImageBitmap = imageBitmap;

            PIModel.getInstance().saveImage(imageBitmap, PIModel.getInstance().getUserId() + ".jpeg",this, getActivity());
        }
    }


    @Override
    public void getUserLastPickitsOnComplete(ArrayList<PIBaseData> lastPickitsList) {
        lastSongsAdapter.setData(lastPickitsList);
        lastSongsAdapter.notifyDataSetChanged();
        requestsCounter--;
        if (requestsCounter == 0){
            ((MainActivity)getActivity()).hideLoadingFragment();
        }
    }

    @Override
    public void getLastVisitedPlacesOnComplete(ArrayList<PIBaseData> lastVisitedPlacesList) {
        lastVisitedPlacesAdapter.setData(lastVisitedPlacesList);
        lastVisitedPlacesAdapter.notifyDataSetChanged();
        requestsCounter--;
        if (requestsCounter == 0){
            ((MainActivity)getActivity()).hideLoadingFragment();
        }

    }

    @Override
    public void saveImageListenerOnComplete(String url) {

    }

    @Override
    public void getImageListenerOnSuccess(Bitmap image) {
        profilePictureImageView.setImageBitmap(image);
        requestsCounter--;

        if(requestsCounter == 0){
            ((MainActivity)getActivity()).hideLoadingFragment();
        }
    }

    @Override
    public void getImageListenerOnFail() {
        requestsCounter--;
        if(requestsCounter == 0){
            ((MainActivity)getActivity()).hideLoadingFragment();
        }
    }
}


