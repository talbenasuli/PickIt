package pickit.com.pickit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pickit.com.pickit.Data.PISong;
import pickit.com.pickit.R;

/**
 * Created by or on 02/03/2017.
 */

public class PISongsListAdapter extends ArrayAdapter<PISong> {

    /**
     * Holds the layout components after inflation
     * this class stints the times of the searching and inflating actions,
     * after preforming those actions once it wont be repeated again.
     */
    private static class ViewHolder{
        private ImageView image;
        private TextView songName;
        private TextView authorName;
        private ImageButton pickitButton;
        private TextView pickitsCounter;
        private TextView position;
    }

    //Parameters:
    List<PISong> songsList ;
    private Context context;
    private int resource;

    /**
     *
     * @param context the context of the system.
     * @param resource the id of the layout that we want to present.
     */
    public PISongsListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return songsList.size();
    }

    public void setSongsList(List<PISong> songsList) {
        this.songsList = songsList;
    }

    @Nullable
    @Override
    /**
     * @param postion the index of the row.
     */
    public PISong getItem(int position) {
        return songsList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.songImageView);
            viewHolder.songName = (TextView) convertView.findViewById(R.id.songNameTextView);
            viewHolder.authorName = (TextView) convertView.findViewById(R.id.authorNameTextView);
            viewHolder.pickitButton = (ImageButton) convertView.findViewById(R.id.pickitButton);
            viewHolder.pickitsCounter = (TextView) convertView.findViewById(R.id.pickitCounterTextView);
            viewHolder.position = (TextView) convertView.findViewById(R.id.position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PISong song = getItem(position);
        viewHolder.songName.setText(song.songName);
        viewHolder.authorName.setText(song.authorName);
        viewHolder.pickitsCounter.setText(String.valueOf(song.picksCount));
        viewHolder.position.setText(String.valueOf(position + 1));

        return convertView;
    }
}
