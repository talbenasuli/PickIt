package pickit.com.pickit.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pickit.com.pickit.Data.PIBaseData;

/**
 * Created by or on 02/03/2017.
 */

public class PIBaseAdapter extends ArrayAdapter<PIBaseData> {

    /**
     * Holds the layout components after inflation
     * this class stints the times of the searching and inflating actions,
     * after preforming those actions once it wont be repeated again.
     */
    public static class BaseViewHolder{
        public ImageView image;
        public TextView topTextView;
        public TextView bottomTextView;
        public TextView position;
    }

    //Parameters:
    protected List<PIBaseData> dataList ;
    protected Context context;
    protected int resource;

    /**
     *
     * @param context the context of the system.
     * @param resource the id of the layout that we want to present.
     */
    public PIBaseAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void setDataList(List<PIBaseData> dataList) {
        this.dataList = dataList;
    }

    @Nullable
    @Override
    /**
     * @param postion the index of the row.
     */
    public PIBaseData getItem(int position) {

        return dataList.get(position);
    }
}
