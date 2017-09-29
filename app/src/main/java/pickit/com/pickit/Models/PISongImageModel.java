package pickit.com.pickit.Models;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import pickit.com.pickit.Networking.Requests.PIGetSongImagePathRequest;
import pickit.com.pickit.Networking.Requests.PIGetSongImageRequest;

/**
 * Created by Tal on 28/09/2017.
 */

public class PISongImageModel {

    private Map<String,Bitmap> imagesCache;

    private Context context;
    public PISongImageModel(Context context) {
        this.context = context;
        imagesCache = new HashMap<String,Bitmap>();
    }

    private void getSongImagePathRequest(final PIModel.PIGetSongPathImageRequestListener listener, String artist) {
        PIGetSongImagePathRequest request = new PIGetSongImagePathRequest(context,artist);
        request.setListener(new PIModel.PIGetSongPathImageRequestListener() {
            @Override
            public void getSongImageRequestOnResponse(String imagePath) {
                listener.getSongImageRequestOnResponse(imagePath);
            }

            @Override
            public void getSongImageRequestOnErrorResponse(VolleyError error) {
                listener.getSongImageRequestOnErrorResponse(error);
            }
        });
        request.sendRequest();
    }

    public void getSongImageRequest(final PIModel.PIGetSongImageListener listener, final String artist) {
        if(imagesCache.get(artist) != null) {
            Bitmap image = imagesCache.get(artist);
            listener.onResponse(image);
        }
        else {
            getSongImagePathRequest(new PIModel.PIGetSongPathImageRequestListener() {
                @Override
                public void getSongImageRequestOnResponse(String imagePath) {
                    PIGetSongImageRequest request = new PIGetSongImageRequest(context,imagePath);
                    request.setListener(new PIModel.PIGetSongImageListener() {
                        @Override
                        public void onResponse(Bitmap image) {
                            imagesCache.put(artist,image);
                            listener.onResponse(image);
                        }

                        @Override
                        public void onError(VolleyError error) {
                            listener.onError(error);
                        }
                    });
                    request.sendRequest();
                }

                @Override
                public void getSongImageRequestOnErrorResponse(VolleyError error) {}
            },artist);
        }
    }

}
