package pickit.com.pickit.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.HashMap;

import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Models.PIMyApplication;
import pickit.com.pickit.Networking.Requests.PIGetSongImageRequest;
import pickit.com.pickit.R;

/**
 * Created by Tal on 03/03/2017.
 */

public class PIListAdapter extends PIBaseAdapter {

    private int rightImageButtonValue;
    private int songId;
    private HashMap<Integer,Bitmap> imageCache;

    public interface PIListAdapterListener {
        public void onClickRightButton(int songId);
    }

    public PIListAdapterListener listener;

    public PIListAdapter(Context context, int resource){
        super(context , resource);
    }

    public PIListAdapter(Context context, int resource, int rightImageButtonValue ){
        super(context , resource);
        this.rightImageButtonValue = rightImageButtonValue;
        imageCache = new HashMap<>();
    }


    private static class PIListViewHolder extends BaseViewHolder {
        //parameters:
        private TextView rightTextView;
        private ImageButton rightImageButton;
        private View layout;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PIListViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new PIListViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.listRowImage);
            viewHolder.topTextView = (TextView) convertView.findViewById(R.id.topTextView);
            viewHolder.bottomTextView = (TextView) convertView.findViewById(R.id.bottomTextView);;
            viewHolder.rightImageButton = (ImageButton) convertView.findViewById(R.id.rightImageButton);
            viewHolder.rightTextView = (TextView) convertView.findViewById(R.id.rightTextView);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.listViewProgressBar);
            viewHolder.progressBar.getIndeterminateDrawable().setColorFilter(convertView.getResources().getColor(R.color.purpleDark), PorterDuff.Mode.MULTIPLY);
            viewHolder.layout = convertView.findViewById(R.id.listRowLayout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PIListViewHolder) convertView.getTag();
        }

        final PIListRowData data =(PIListRowData) getItem(position);
        viewHolder.image.setTag(data.bottomText);
        songId = data.songId;
        viewHolder.topTextView.setText(data.topText);
        viewHolder.bottomTextView.setText(data.bottomText);
        viewHolder.rightTextView.setText(String.valueOf(data.rightText));
        viewHolder.rightImageButton.setTag(position);
        viewHolder.rightImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickRightButton(data.songId);
            }
        });
        viewHolder.rightImageButton.setEnabled(!data.didPickit);

        viewHolder.image.setVisibility(View.GONE);
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        if (data.bottomText != null && !data.bottomText.equals("") && !data.bottomText.isEmpty()) {
            PIModel.getInstance().getSongImage(data.bottomText, new PIModel.PIGetSongImageListener() {
                @Override
                public void onResponse(Bitmap image) {
                    if (data.bottomText.equals(viewHolder.image.getTag())) {
                        viewHolder.image.setImageBitmap(image);
                        viewHolder.image.setVisibility(View.VISIBLE);
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }
                    else {
                        viewHolder.image.setImageResource(R.drawable.song_general_image);
                        viewHolder.image.setVisibility(View.VISIBLE);
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    viewHolder.image.setImageResource(R.drawable.song_general_image);
                    viewHolder.image.setVisibility(View.VISIBLE);
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            });
        }
        else {
            viewHolder.image.setImageResource(R.drawable.song_general_image);
            viewHolder.image.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.GONE);
        }

        if (rightImageButtonValue != 0) {
            viewHolder.rightImageButton.setImageResource(rightImageButtonValue);
        }

        int backgroundColor = position % 2 == 0 ? R.color.gray2 : R.color.gray1;
        viewHolder.layout.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        return convertView;
    }
}
