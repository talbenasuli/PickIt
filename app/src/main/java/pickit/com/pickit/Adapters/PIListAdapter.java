package pickit.com.pickit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.R;

/**
 * Created by Tal on 03/03/2017.
 */

public class PIListAdapter extends PIBaseAdapter {

    public PIListAdapter(Context context, int resource){
        super(context , resource);
    }

    private static class PIListViewHolder extends BaseViewHolder {
        //parameters:
        private TextView rightTextView;
        private ImageButton rightImageButton;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PIListViewHolder viewHolder = null;

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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PIListViewHolder) convertView.getTag();
        }

        PIListRowData data =(PIListRowData) getItem(position);
        viewHolder.topTextView.setText(data.topText);
        viewHolder.bottomTextView.setText(data.bottomText);
        viewHolder.rightTextView.setText(String.valueOf(data.rightText));
        viewHolder.position.setText(String.valueOf(position + 1));

        return convertView;
    }
}
