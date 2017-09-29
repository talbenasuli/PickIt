package pickit.com.pickit.Networking.Requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import pickit.com.pickit.Models.PIModel;

/**
 * Created by Tal on 27/09/2017.
 */

public class PIGetSongImageRequest implements Response.Listener<Bitmap>, Response.ErrorListener {

    RequestQueue requestQueue;
    String imagePath;
    PIModel.PIGetSongImageListener listener;

    public PIGetSongImageRequest(Context context, String imagePath) {
        requestQueue = Volley.newRequestQueue(context);
        this.imagePath = imagePath;
    }

    public void sendRequest() {
        ImageRequest request = new ImageRequest(imagePath,this, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,this);
        requestQueue.add(request);
    }

    @Override
    public void onResponse(Bitmap response) {
        listener.onResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        listener.onError(error);
    }

    public void setListener(PIModel.PIGetSongImageListener listener) {
        this.listener = listener;
    }
}
