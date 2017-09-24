package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.Parsers.PIGetUserPickitsParser;

/**
 * Created by Omer on 23/09/2017.
 */

public class PIGetUserPickItsRequest extends PIBaseRequest {

    PIModel.PIGetUserPickitsListener listener;
    List<String> userPickitsList;

    public PIGetUserPickItsRequest(Context context) {
        super(context);
        urlSufix = "getUserPickits";
        userPickitsList = new ArrayList<>();
        queryParams.put("userId", PIModel.getInstance().getUserId());
    }

    @Override
    protected void parseData(JSONObject response) {
        PIGetUserPickitsParser parser = new PIGetUserPickitsParser();
        userPickitsList = parser.parse(response);
    }

    @Override
    protected void notifyError(VolleyError error) {
        listener.getUserPickitsOnCancel(error);
    }

    @Override
    protected void notifySuccess() {
        listener.getUserPickitsOnResponse(userPickitsList);
    }

    public void setListener(PIModel.PIGetUserPickitsListener listener) {
        this.listener = listener;
    }
}
