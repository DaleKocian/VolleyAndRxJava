package example.com.volleyrxjava.activitiy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.nio.charset.Charset;

import butterknife.ButterKnife;
import butterknife.InjectView;
import example.com.volleyrxjava.MyApplication;
import example.com.volleyrxjava.R;
import example.com.volleyrxjava.network.JsonObjectObservableRequest;
import example.com.volleyrxjava.service.MyIntentService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dkocian on 11/24/2014.
 */
public class MainActivity5 extends FragmentActivity {
    private static final String TAG = MainActivity4.class.getSimpleName();
    @InjectView(R.id.helloWorld)
    protected TextView helloWorld;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        JsonObjectObservableRequest jsonObjectObservableRequest = new JsonObjectObservableRequest(Request.Method
                .GET, MyIntentService.URL, null);
        JsonObjectObservableRequest jsonObjectObservableRequest2 = new JsonObjectObservableRequest(Request.Method
                .GET, MyIntentService.URL, null);
        Observable<JSONObject> observable1 = jsonObjectObservableRequest.getObservable();
        Observable<JSONObject> observable2 = jsonObjectObservableRequest2.getObservable();
        MyApplication.addToRequestQueue(jsonObjectObservableRequest.getJsonObjectRequest());
        MyApplication.addToRequestQueue(jsonObjectObservableRequest2.getJsonObjectRequest());
        observable1.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "Completed");
            }

            @Override
            public void onError(Throwable e) {
                VolleyError cause = (VolleyError) e.getCause();
                String s = new String(cause.networkResponse.data, Charset.forName("UTF-8"));
                Log.e(TAG, s);
                Log.e(TAG, cause.toString());
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                Log.e(TAG, "onNext1");
                helloWorld.setText(jsonObject.toString());
            }
        });
        observable2.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "Completed2");
            }

            @Override
            public void onError(Throwable e) {
                VolleyError cause = (VolleyError) e.getCause();
                String s = new String(cause.networkResponse.data, Charset.forName("UTF-8"));
                Log.e(TAG, s);
                Log.e(TAG, cause.toString());
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                Log.e(TAG, "onNext2");
                helloWorld.setText(jsonObject.toString());
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
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
