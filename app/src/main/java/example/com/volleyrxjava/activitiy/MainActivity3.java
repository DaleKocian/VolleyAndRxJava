package example.com.volleyrxjava.activitiy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import example.com.volleyrxjava.MyApplication;
import example.com.volleyrxjava.R;
import example.com.volleyrxjava.model.WeatherData;
import example.com.volleyrxjava.network.GsonRequest;
import example.com.volleyrxjava.service.MyIntentService;
import rx.Observable;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity3 extends FragmentActivity {
    private static final String TAG = MainActivity3.class.getSimpleName();
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                WeatherData weatherData = (WeatherData) bundle.getSerializable(MyIntentService.RESULT);
                helloWorld.setText(weatherData.toString());
                Log.e(TAG, weatherData.toString());
            }
        }
    };
    @InjectView(R.id.helloWorld)
    protected TextView helloWorld;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mCompositeSubscription.add(newGetWeatherData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        VolleyError cause = (VolleyError) e.getCause();
                        String s = new String(cause.networkResponse.data, Charset.forName("UTF-8"));
                        Log.e(TAG, s);
                        Log.e(TAG, cause.toString());
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        helloWorld.setText(weatherData.toString());
                        Log.e(TAG, weatherData.toString());
                    }
                }));
        /*Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);*/
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
        registerReceiver(receiver, new IntentFilter(MyIntentService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    private WeatherData getWeatherData() throws ExecutionException, InterruptedException {
        RequestFuture<WeatherData> future = RequestFuture.newFuture();
        GsonRequest<WeatherData> jsonObjectRequest = new GsonRequest<>(MyIntentService.URL, WeatherData.class, null, future,
                future);
        MyApplication.addToRequestQueue(jsonObjectRequest);
        return future.get();
    }

    public Observable<WeatherData> newGetWeatherData() {
        return Observable.defer(new Func0<Observable<WeatherData>>() {
            @Override
            public Observable<WeatherData> call() {
                Exception exception;
                try {
                    return Observable.just(getWeatherData());
                } catch (InterruptedException | ExecutionException e) {
                    Log.e(TAG, e.getMessage());
                    return Observable.error(e);
                }
            }
        });
    }

    public void checkNetworkChange() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        AndroidObservable.fromBroadcast(this, filter)
                .subscribe(new Action1<Intent>() {
                    @Override
                    public void call(Intent intent) {
                        handleConnectivityChange(intent);
                    }
                });
    }

    public void handleConnectivityChange(Intent intent) {
    }
}
