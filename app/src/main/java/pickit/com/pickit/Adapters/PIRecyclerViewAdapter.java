package pickit.com.pickit.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.R;

/**
 * Created by Tal on 21/03/2017.
 */

public class PIRecyclerViewAdapter extends RecyclerView.Adapter<PIRecyclerViewAdapter.ViewHolder> {

    private List<PIBaseData> dataList;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topText;
        public TextView bottomText;
        public Image image;

        public ViewHolder(View view) {
            super(view);
            topText = (TextView) view.findViewById(R.id.auothorName);
            bottomText = (TextView) view.findViewById(R.id.songName);
        }
    }

    public void setData(List<PIBaseData> dataList) {
        this.dataList = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pi_recycler_grid_cell, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PIBaseData data = dataList.get(position);
        holder.topText.setText(data.topText);
        holder.bottomText.setText(data.bottomText);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}