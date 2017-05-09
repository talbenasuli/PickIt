package pickit.com.pickit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

public class PIListAdapter extends PIBaseAdapter implements View.OnClickListener {

    private int rightImageButtonValue;

    public interface PIListAdapterListener {
        public void onClickRightButton(int position);
    }

    public PIListAdapterListener listener;

    public PIListAdapter(Context context, int resource){
        super(context , resource);
    }

    public PIListAdapter(Context context, int resource, int rightImageButtonValue ){
        super(context , resource);
        this.rightImageButtonValue = rightImageButtonValue;
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
            viewHolder.layout = convertView.findViewById(R.id.listRowLayout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PIListViewHolder) convertView.getTag();
        }

        PIListRowData data =(PIListRowData) getItem(position);
        viewHolder.image.setImageResource(data.image);
        viewHolder.topTextView.setText(data.topText);
        viewHolder.bottomTextView.setText(data.bottomText);
        viewHolder.rightTextView.setText(String.valueOf(data.rightText));
        viewHolder.rightImageButton.setTag(position);
        viewHolder.rightImageButton.setOnClickListener(this);
        viewHolder.position.setText(String.valueOf(position + 1));


        if (rightImageButtonValue != 0) {
            viewHolder.rightImageButton.setImageResource(rightImageButtonValue);
        }

        int backgroundColor = position % 2 == 0 ? R.color.lightPurple : R.color.purpleSVG;
        viewHolder.layout.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        return convertView;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        listener.onClickRightButton(position);
    }
}
