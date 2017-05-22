package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Tal on 16/05/2017.
 */

public class PIUpdatePickItRequest extends PIBaseRequest {

    public interface PIUpdatePickItRequestListener {
        void updatePickItRequestOnResponse();
        void updatePickItRequestOnErrorResponse(VolleyError error);
    }

    public PIUpdatePickItRequestListener listener;

    public PIUpdatePickItRequest(Context context, String songID) {
        super(context);

        urlSufix = "updatePickIt";
        queryParams.put("id", songID);
    }

    @Override
    protected void parseData(JSONObject response) {

    }

    @Override
    protected void notifyError(VolleyError error) {
        listener.updatePickItRequestOnErrorResponse(error);
    }

    @Override
    protected void notifySuccess() {
        listener.updatePickItRequestOnResponse();
    }
}
