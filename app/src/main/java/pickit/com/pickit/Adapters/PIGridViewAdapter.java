package pickit.com.pickit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pickit.com.pickit.R;

/**
 * Created by Tal on 27/03/2017.
 */

public class PIGridViewAdapter extends PIBaseAdapter{

    /**
     * @param context  the context of the system.
     * @param resource the id of the layout that we want to present.
     */
    public PIGridViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(resource, null);

            TextView topText = (TextView) gridView.findViewById(R.id.topText);
            TextView bottomText = (TextView) gridView.findViewById(R.id.bottomText);

            topText.setText(dataList.get(position).topText);
            bottomText.setText(dataList.get(position).bottomText);
        }
        else {
            gridView = convertView;
        }
        return gridView;
        }
    }