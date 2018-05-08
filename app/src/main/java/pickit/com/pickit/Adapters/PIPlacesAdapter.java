package pickit.com.pickit.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;

import pickit.com.pickit.Data.PIBaseData;
import pickit.com.pickit.Data.PIPlaceData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.R;

/**
 * Created by or on 29/09/2017.
 */

public class PIPlacesAdapter extends ArrayAdapter<PIPlaceData> {

    //Parameters:
    protected List<PIPlaceData> dataList ;
    protected Context context;
    protected int resource;
    protected int rightImageButtonValue;


    public static class ViewHolder{
        public ImageView image;
        public TextView topTextView;
        public TextView bottomTextView;
        public TextView position;
        public ProgressBar progressBar;
        private TextView rightTextView;
        private ImageButton rightImageButton;
        private View layout;
    }

    public PIPlacesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    public PIPlacesAdapter(Context context, int resource, int rightImageButtonValue ){
        super(context , resource);
        this.rightImageButtonValue = rightImageButtonValue;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public PIPlaceData getItem(int position) {
        return dataList.get(position);
    }

    public void setDataList(List<PIPlaceData> dataList) {
        this.dataList = dataList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.listRowImage);
            viewHolder.topTextView = (TextView) convertView.findViewById(R.id.topTextView);
            viewHolder.bottomTextView = (TextView) convertView.findViewById(R.id.bottomTextView);;
            viewHolder.rightImageButton = (ImageButton) convertView.findViewById(R.id.rightImageButton);
            viewHolder.rightTextView = (TextView) convertView.findViewById(R.id.rightTextView);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.listViewProgressBar);
            viewHolder.progressBar.getIndeterminateDrawable().setColorFilter(convertView.getResources().getColor(R.color.purpleDark), PorterDuff.Mode.MULTIPLY);
            viewHolder.layout = convertView.findViewById(R.id.listRowLayout);

            viewHolder.rightImageButton.setVisibility(View.GONE);
            viewHolder.rightTextView.setVisibility(View.GONE);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (PIPlacesAdapter.ViewHolder) convertView.getTag();
        }

        final PIPlaceData data = (PIPlaceData)getItem(position);
        viewHolder.image.setTag(data.imageURL);
        viewHolder.topTextView.setText(data.topText);
        viewHolder.bottomTextView.setText(data.bottomText);
        viewHolder.rightTextView.setText(String.valueOf(15));
        viewHolder.rightImageButton.setTag(position);
        viewHolder.rightImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(data.imageURL != null  && !data.imageURL.isEmpty()) {
            PIModel.getInstance().getImageByURL(data.imageURL, new PIModel.GetImageListener() {
                @Override
                public void getImageListenerOnSuccess(Bitmap image) {
                    if(data.imageURL.equals(viewHolder.image.getTag().toString())) {
                        viewHolder.image.setImageBitmap(image);
                        viewHolder.progressBar.setVisibility(View.GONE);
                        viewHolder.image.setVisibility(View.VISIBLE);
                    }

                    else {
                        viewHolder.image.setImageResource(R.drawable.song_general_image);
                        viewHolder.progressBar.setVisibility(View.GONE);
                        viewHolder.image.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void getImageListenerOnFail() {
                    viewHolder.image.setImageResource(R.drawable.song_general_image);
                    viewHolder.progressBar.setVisibility(View.GONE);
                    viewHolder.image.setVisibility(View.VISIBLE);
                }
            });
        }

        else {
            viewHolder.image.setImageResource(R.drawable.song_general_image);
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.VISIBLE);
        }

        if (rightImageButtonValue != 0) {
            viewHolder.rightImageButton.setImageResource(rightImageButtonValue);
        }

        int backgroundColor = position % 2 == 0 ? R.color.gray2 : R.color.gray1;
        viewHolder.layout.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        return convertView;
    }

}
