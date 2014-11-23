package example.com.volleyrxjava.activitiy;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import example.com.volleyrxjava.MyApplication;
import example.com.volleyrxjava.R;
import example.com.volleyrxjava.model.WeatherData;
import example.com.volleyrxjava.network.GsonRequest;
import example.com.volleyrxjava.service.MyIntentService;
import rx.Observable;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class MainActivity4 extends FragmentActivity {
    private static final String TAG = MainActivity4.class.getSimpleName();
    @InjectView(R.id.helloWorld)
    protected TextView helloWorld;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        try {
            getWeatherData();
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    private void getWeatherData() throws ExecutionException, InterruptedException {
        final PublishSubject<Observable<WeatherData>> publishSubject = PublishSubject.create();
        final PublishSubject<Observable<WeatherData>> publishSubject2 = PublishSubject.create();
        Response.Listener<WeatherData> listener = new Response.Listener<WeatherData>() {
            @Override
            public void onResponse(WeatherData response) {
                publishSubject.onNext(Observable.just(response));
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Observable<WeatherData> myError = Observable.error(error);
                publishSubject.onNext(myError);
            }
        };
        Response.Listener<WeatherData> listener2 = new Response.Listener<WeatherData>() {
            @Override
            public void onResponse(WeatherData response) {
                publishSubject2.onNext(Observable.just(response));
            }
        };
        Response.ErrorListener errorListener2 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Observable<WeatherData> myError = Observable.error(error);
                publishSubject2.onNext(myError);
            }
        };
        Observable<WeatherData> weatherDataObservable1 = publishSubject.flatMap(new Func1<Observable<WeatherData>, Observable<WeatherData>>() {
            @Override
            public Observable<WeatherData> call(Observable<WeatherData> weatherDataObservable) {
                return weatherDataObservable;
            }
        });
        Observable<WeatherData> weatherDataObservable2 = publishSubject2.flatMap(new Func1<Observable<WeatherData>,
                Observable<WeatherData>>() {
            @Override
            public Observable<WeatherData> call(Observable<WeatherData> weatherDataObservable) {
                return weatherDataObservable;
            }
        });
        GsonRequest<WeatherData> jsonObjectRequest =
                new GsonRequest<>(MyIntentService.URL, WeatherData.class, null, listener, errorListener);
        GsonRequest<WeatherData> jsonObjectRequest2 =
                new GsonRequest<>(MyIntentService.URL2, WeatherData.class, null, listener2, errorListener2);
        MyApplication.addToRequestQueue(jsonObjectRequest);
        MyApplication.addToRequestQueue(jsonObjectRequest2);
        mCompositeSubscription.add(Observable.zip(weatherDataObservable1, weatherDataObservable2, new Func2<WeatherData,
                WeatherData, String>() {
            @Override
            public String call(WeatherData weatherData, WeatherData weatherData2) {
                Log.e(TAG, weatherData.toString());
                Log.e(TAG, weatherData2.toString());
                return weatherData.toString() + "\n\n\n" + weatherData2.toString();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String weatherData) {
                Log.e(TAG, weatherData);
                helloWorld.setText(weatherData);
            }
        }));
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
