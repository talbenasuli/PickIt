package pickit.com.pickit.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;

/**
 * Created by Tal on 21/03/2017.
 */

public class PIRecyclerViewAdapter extends RecyclerView.Adapter<PIRecyclerViewAdapter.ViewHolder> {

    public enum PIRecyclerViewAdapterType {
        PLACES,SONGS
    }

    private List<PIBaseData> dataList;
    private PIRecyclerViewAdapterType type;

    public PIRecyclerViewAdapter(PIRecyclerViewAdapterType type) {
        this.type = type;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topText;
        public TextView bottomText;
        public ImageView image;
        public ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            topText = (TextView) view.findViewById(R.id.topText);
            bottomText = (TextView) view.findViewById(R.id.bottomText);
            image = (ImageView) view.findViewById(R.id.image);
            progressBar = (ProgressBar) view.findViewById(R.id.recyclerViewProgressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(view.getResources().getColor(R.color.purpleDark), PorterDuff.Mode.MULTIPLY);

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PIBaseData data = dataList.get(position);
        holder.topText.setText(data.topText);
        holder.bottomText.setText(data.bottomText);
        holder.bottomText.setTag(data.bottomText);

        switch (type) {
            case PLACES:
                //TODO: download places images from firebase.
                // Just for now
                holder.image.setImageResource(R.drawable.song_general_image);
                holder.progressBar.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);

                break;
            case SONGS:
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.GONE);
                if(data.bottomText != null && !data.bottomText.isEmpty()) {
                    PIModel.getInstance().getSongImage(data.bottomText, new PIModel.PIGetSongImageListener() {
                        @Override
                        public void onResponse(Bitmap image) {
                            if(data.bottomText.equals(holder.bottomText.getTag().toString())) {
                                holder.image.setImageBitmap(image);
                                holder.progressBar.setVisibility(View.GONE);
                                holder.image.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            holder.image.setImageResource(R.drawable.song_general_image);
                            holder.progressBar.setVisibility(View.GONE);
                            holder.image.setVisibility(View.VISIBLE);
                        }
                    });
                }

                else {
                    holder.image.setImageResource(R.drawable.song_general_image);
                    holder.progressBar.setVisibility(View.GONE);
                    holder.image.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}