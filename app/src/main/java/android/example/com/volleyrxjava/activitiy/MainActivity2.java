package android.example.com.volleyrxjava.activitiy;

import android.example.com.volleyrxjava.MyApplication;
import android.example.com.volleyrxjava.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity2 extends FragmentActivity {

    public static final String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
    public static final String TAG = MainActivity2.class.getSimpleName();
    TextView _tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _tvHello = (TextView) findViewById(R.id.helloWorld);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, future, future);
        MyApplication.addToRequestQueue(jsonObjectRequest);
        /*rxGetFutureObject()
                .observeOn(Schedulers.io())
                .subscribe(new Action1<RequestFuture<JSONObject>>() {
                    @Override
                    public void call(RequestFuture<JSONObject> jsonObjectRequestFuture) {
                        Log.e(TAG, Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.getMessage());
                        }
                        try {
                            JSONObject jsonObject = jsonObjectRequestFuture.get();
                            Log.e(TAG, jsonObject.toString());
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.getMessage());
                        } catch (ExecutionException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                });
        rxGetFutureObject()
                .observeOn(Schedulers.io())
                .subscribe(new Action1<RequestFuture<JSONObject>>() {
                    @Override
                    public void call(RequestFuture<JSONObject> jsonObjectRequestFuture) {
                        Log.e(TAG + "2", Thread.currentThread().getName());
                        try {
                            JSONObject jsonObject = jsonObjectRequestFuture.get();
                            Log.e(TAG + "2", jsonObject.toString());
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.getMessage());
                        } catch (ExecutionException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                });*/
        Observable.zip(rxGetFutureObject2(), rxGetFutureObject2(), new Func2<RequestFuture<JSONObject>, RequestFuture<JSONObject>, JSONObject>() {
            @Override
            public JSONObject call(RequestFuture<JSONObject> jsonObjectRequestFuture, RequestFuture<JSONObject> jsonObjectRequestFuture2) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = jsonObjectRequestFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    VolleyError cause = (VolleyError) e.getCause();
                    String s = new String(cause.networkResponse.data);
                    Log.e(TAG, s);
                    Log.e(TAG, cause.networkResponse.toString());
                }
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2 = jsonObjectRequestFuture2.get();
                } catch (InterruptedException | ExecutionException e) {
                    VolleyError cause = (VolleyError) e.getCause();
                    String s = new String(cause.networkResponse.data);
                    Log.e(TAG, s);
                    Log.e(TAG, cause.networkResponse.toString());
                }
                Log.e(TAG + " : jsonObjectRequestFuture1", jsonObject.toString());
                Log.e(TAG + " : jsonObjectRequestFuture2", jsonObject2.toString());
                return jsonObject;
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<JSONObject>() {
                    @Override
                    public void call(JSONObject o) {
                        Log.e(TAG, "SETTING TEXT");
                        _tvHello.setText("apple");
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public Observable<RequestFuture<JSONObject>> rxGetFutureObject() {
        return Observable.create(new Observable.OnSubscribe<RequestFuture<JSONObject>>() {
            @Override
            public void call(Subscriber<? super RequestFuture<JSONObject>> subscriber) {
                RequestFuture<JSONObject> future = RequestFuture.newFuture();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, future, future);
                MyApplication.addToRequestQueue(jsonObjectRequest);
                subscriber.onNext(future);
            }
        });
    }

    public Observable<RequestFuture<JSONObject>> rxGetFutureObject2() {
        final RequestFuture<JSONObject> future = RequestFuture.newFuture();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, future, future);
        MyApplication.addToRequestQueue(jsonObjectRequest);
        Observable<JSONObject> from = Observable.from(future, AndroidSchedulers.mainThread());
        return from.observeOn(Schedulers.io()).create(new Observable.OnSubscribe<RequestFuture<JSONObject>>() {
            @Override
            public void call(Subscriber<? super RequestFuture<JSONObject>> subscriber) {
                Log.e(TAG + " HELLO", Thread.currentThread().getName());
                try {
                    JSONObject jsonObject = future.get();
                    Log.e(TAG + "HELLO", jsonObject.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(future);
            }
        });
    }
}
