package pickit.com.pickit.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.R;
/**
 * Created by Tal on 03/03/2017.
 */

public class PIListAdapter extends PIBaseAdapter implements View.OnClickListener{

    private int rightImageButtonValue;
    private PIListViewHolder viewHolder;
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
        viewHolder = null;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new PIListViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.listRowImage);
            viewHolder.topTextView = (TextView) convertView.findViewById(R.id.topTextView);
            viewHolder.bottomTextView = (TextView) convertView.findViewById(R.id.bottomTextView);;
            viewHolder.position = (TextView) convertView.findViewById(R.id.position);
            viewHolder.rightImageButton = (ImageButton) convertView.findViewById(R.id.rightImageButton);
            viewHolder.rightTextView = (TextView) convertView.findViewById(R.id.rightTextView);
            viewHolder.layout = convertView.findViewById(R.id.listRowLayout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PIListViewHolder) convertView.getTag();
        }

        PIListRowData data =(PIListRowData) getItem(position);
        songId = data.songId;
        viewHolder.topTextView.setText(data.topText);
        viewHolder.bottomTextView.setText(data.bottomText);
        viewHolder.rightTextView.setText(String.valueOf(data.rightText));
        viewHolder.rightImageButton.setTag(position);
        viewHolder.rightImageButton.setOnClickListener(this);
        viewHolder.position.setText(String.valueOf(position + 1));

//        if (data.bottomText != null || !data.bottomText.equals("") && imageCache.get(data.songId) == null) {
//            PIGetSongImageRequest songImageRequest = new PIGetSongImageRequest(getContext(), data.bottomText);
//            songImageRequest.setListener(this);
//            songImageRequest.sendRequest();
//        }
//
//        else if(imageCache.get(position) != null) {
//            viewHolder.image.setImageBitmap(imageCache.get(songId));
//        }
//
//        else {
//            // TODO: add default image.
//        }

        if (rightImageButtonValue != 0) {
            viewHolder.rightImageButton.setImageResource(rightImageButtonValue);
        }

        int backgroundColor = position % 2 == 0 ? R.color.gray2 : R.color.gray1;
        viewHolder.layout.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        return convertView;
    }

    @Override
    public void onClick(View view) {
        listener.onClickRightButton(songId);
    }

//    @Override
//    public void getSongImageRequestOnResponse(String imagePath) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//
//        ImageRequest imgRequest = new ImageRequest(imagePath, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                viewHolder.image.setImageBitmap(response);
//                imageCache.put(songId, response);
//            }
//        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //do stuff
//                    }
//                });
//
//        requestQueue.add(imgRequest);
//
//    }
//
//    @Override
//    public void getSongImageRequestOnErrorResponse(VolleyError error) {
//
//    }
}
