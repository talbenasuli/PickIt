package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import pickit.com.pickit.Models.PIModel;

/**
 * Created by Tal on 16/05/2017.
 */

public class PIUpdatePickItRequest extends PIBaseRequest {

    public PIModel.PIUpdatePickItRequestListener listener;

    public PIUpdatePickItRequest(Context context, String songID) {
        super(context);

        urlSufix = "updatePickIt";
        queryParams.put("id", songID);
        queryParams.put("userId",  PIModel.getInstance().getUserId());
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

    public void setListener(PIModel.PIUpdatePickItRequestListener listener) {
        this.listener = listener;
    }
}
