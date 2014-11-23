package example.com.volleyrxjava;

import android.app.Application;
import android.content.Context;
import example.com.volleyrxjava.network.OkHttpStack;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dkocian on 11/23/2014.
 */
public class MyApplication extends Application {
    private static Context context;

    public static void addToRequestQueue(Request request) {
        RequestQueueHolder.requestQueue.add(request);
    }

    @Override
    public void onCreate() {
        context = this;
    }

    private static class RequestQueueHolder {
        private static final RequestQueue requestQueue = Volley.newRequestQueue(context, new OkHttpStack());
    }
}
