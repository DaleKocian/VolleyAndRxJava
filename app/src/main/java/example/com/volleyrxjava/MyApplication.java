package example.com.volleyrxjava;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import example.com.volleyrxjava.network.OkHttpStack;

/**
 * Created by dkocian on 11/23/2014.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static Context context;

    public static synchronized void addToRequestQueue(Request request) {
        request.setTag(TAG);
        RequestQueueHolder.requestQueue.add(request);
    }

    public static synchronized void addToRequestQueue(Request request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", request.getUrl());
        RequestQueueHolder.requestQueue.add(request);
    }

    public static synchronized void cancelPendingRequests(Object tag) {
        RequestQueueHolder.requestQueue.cancelAll(tag);
    }

    @Override
    public void onCreate() {
        context = this;
    }

    private static class RequestQueueHolder {
        private static final RequestQueue requestQueue = Volley.newRequestQueue(context, new OkHttpStack());
    }
}
