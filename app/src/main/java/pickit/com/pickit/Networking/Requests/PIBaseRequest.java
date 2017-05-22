package pickit.com.pickit.Networking.Requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tal on 14/05/2017.
 */

public abstract class PIBaseRequest implements PIBaseNetworkingRequest, Response.Listener<JSONObject>, Response.ErrorListener{

    String url;
    String urlSufix;
    String fullPath;
    Map queryParams;
    Context context;

    public PIBaseRequest (Context context) {
        url = "http://10.0.0.1:9999/";
        urlSufix = null;
        queryParams = new HashMap();
        this.context = context;
    }

    @Override
    public void sendRequest() {

        fullPath = url + urlSufix;

        addQueryParamsIfNeeded();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest  request = new JsonObjectRequest(Request.Method.GET, fullPath, new String(), this, this);
        requestQueue.add(request);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        notifyError(error);
    }

    @Override
    public void onResponse(JSONObject response) {
        parseData(response);
        notifySuccess();
    }

    protected abstract void parseData(JSONObject response);

    protected abstract void notifyError(VolleyError error);

    protected abstract void notifySuccess();

    private void addQueryParamsIfNeeded() {

        if(queryParams != null  && !queryParams.isEmpty()) {
            Iterator<Map.Entry<Integer, Integer>> iterator = queryParams.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<Integer, Integer> query = iterator.next();
                fullPath = fullPath + "?" + query.getKey() + "=" + query.getValue();
            }
        }
    }


}