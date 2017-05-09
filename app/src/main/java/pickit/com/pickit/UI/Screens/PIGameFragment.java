package pickit.com.pickit.UI.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pickit.com.pickit.Adapters.PIGridViewAdapter;
import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.R;
import pickit.com.pickit.UI.Display.PITimerView;

public class PIGameFragment extends Fragment {

    private PITimerView timerView = null;
    private GridView gridView;
    private PIGridViewAdapter adapter;
    private int gameMinutes = 1;
    private List<PIBaseData> dataList;

    public static final String TAG = "PIGameFragment";

    public static PIGameFragment newInstance() {

        Bundle args = new Bundle();

        PIGameFragment fragment = new PIGameFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.pi_fragment_game, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new PIGridViewAdapter(getContext(),R.layout.pi_recycler_grid_cell);

        dataList = new ArrayList<>();
        createData();

        adapter.setDataList(dataList);
        gridView.setAdapter(adapter);

        timerView = (PITimerView) view.findViewById(R.id.timer);
        if(timerView != null) {
            timerView.setOnTickListener(new PITimerView.OnTickListener() {
                @Override
                public String getText(long timeRemaining) {
                    int seconds = (int) (timeRemaining / 1000) % 60;
                    int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
                    int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
                    int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
                    boolean hasDays = days > 0;
                    return String.format("%3$02d%6$s",
                            hasDays ? days : hours,
                            hasDays ? hours : minutes,
                            hasDays ? minutes : seconds,
                            hasDays ? "d" : "h",
                            hasDays ? "h" : "m",
                            hasDays ? "m" : "s");
                }
            });
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, gameMinutes);
        end.add(Calendar.SECOND, 0);

        Calendar start = Calendar.getInstance();
        if (timerView != null) {
            timerView.start(start, end);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        timerView.stop();
    }

    private void createData() {
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

        PIBaseData t1 = new PIBaseData();
        t1.topText = "Eyal Golan";
        t1.bottomText = "this is how we do !!!!";

        PIBaseData t2 = new PIBaseData();
        t2.topText = "Eyal Golan";
        t2.bottomText = "this is how we do !!!!";

        dataList.add(firsData);
        dataList.add(ss);
        dataList.add(dd);
        dataList.add(t1);
        dataList.add(t2);
    }
}