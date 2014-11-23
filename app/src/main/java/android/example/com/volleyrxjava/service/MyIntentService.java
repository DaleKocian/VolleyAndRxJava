package android.example.com.volleyrxjava.service;

import android.app.IntentService;
import android.content.Intent;
import android.example.com.volleyrxjava.MyApplication;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by dkocian on 11/23/2014.
 */
public class MyIntentService extends IntentService {
    public static final String RESULT = "result";
    public static final String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
    public static final String TAG = MyIntentService.class.getSimpleName();
    public static final String NOTIFICATION = "android.example.com.volleyrxjava";

    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, future, future);
        MyApplication.addToRequestQueue(jsonObjectRequest);
        try {
            JSONObject response = future.get();
            publishResults(response);
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        } catch (ExecutionException e) {
            VolleyError cause = (VolleyError) e.getCause();
            String s = new String(cause.networkResponse.data);
            Log.e(TAG, s);
            Log.e(TAG, cause.networkResponse.toString());
        }
    }

    private void publishResults(JSONObject result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result.toString());
        sendBroadcast(intent);
    }
}
