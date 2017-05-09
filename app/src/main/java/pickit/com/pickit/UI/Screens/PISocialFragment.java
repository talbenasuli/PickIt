package pickit.com.pickit.UI.Screens;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Adapters.PIRecyclerViewAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.R;

/**
 * Created by or on 27/03/2017.
 */

public class PISocialFragment extends Fragment {

    RecyclerView recentPicksRecyclerView;
    PIRecyclerViewAdapter recentPicksadapter;

    RecyclerView mostPickedSongsRecyclerView;
    PIRecyclerViewAdapter mostPickedSongsPicksadapter;

    public static final String TAG = "PISocialFragment";

    public static PISocialFragment newInstance() {

        Bundle args = new Bundle();

        PISocialFragment fragment = new PISocialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.pi_fragment_social, container, false);
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recentPicksRecyclerView =(RecyclerView) view.findViewById(R.id.recentPicksRecyclerView);
        recentPicksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mostPickedSongsRecyclerView =(RecyclerView) view.findViewById(R.id.mostPickedSongsRecyclerView);
        mostPickedSongsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<PIBaseData> dataList = new ArrayList<PIBaseData>();

        PIBaseData ss1 = new PIBaseData();
        ss1.topText = "Eyal Golan";
        ss1.bottomText = "this is how we do !!!!";

        PIBaseData ss = new PIBaseData();
        ss.topText = "Eyal Golan";
        ss.bottomText = "this is how we do !!!!";

        PIBaseData dd = new PIBaseData();
        dd.topText = "Eyal Golan";
        dd.bottomText = "this is how we do !!!!";
        dataList.add(ss1);

        PIBaseData ff = new PIBaseData();
        ff.topText = "Eyal Golan";
        ff.bottomText = "this is how we do !!!!";

        dataList.add(ss1);
        dataList.add(ss);
        dataList.add(dd);
        dataList.add(ff);


        recentPicksadapter = new PIRecyclerViewAdapter();
        recentPicksadapter.setData(dataList);
        recentPicksRecyclerView.setAdapter(recentPicksadapter);

        mostPickedSongsPicksadapter = new PIRecyclerViewAdapter();
        mostPickedSongsPicksadapter.setData(dataList);
        mostPickedSongsRecyclerView.setAdapter(mostPickedSongsPicksadapter);
    }
}
