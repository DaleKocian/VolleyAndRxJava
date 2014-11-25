package example.com.volleyrxjava.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by dkocian on 11/24/2014.
 */
public class JsonObjectObservableRequest {
    private PublishSubject<Observable<JSONObject>> publishSubject = PublishSubject.create();
    private JsonObjectRequest jsonObjectRequest;

    public JsonObjectObservableRequest(int method, String url, JSONObject request) {
        jsonObjectRequest = new JsonObjectRequest(method, url, request, getResponseListener(), getResponseErrorListener());
    }

    private Response.Listener<JSONObject> getResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                publishSubject.onNext(Observable.just(response));
            }
        };
    }

    private Response.ErrorListener getResponseErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Observable<JSONObject> myError = Observable.error(error);
                publishSubject.onNext(myError);
            }
        };
    }

    public JsonObjectRequest getJsonObjectRequest() {
        return jsonObjectRequest;
    }

    public Observable<JSONObject> getObservable() {
        return publishSubject.flatMap(new Func1<Observable<JSONObject>, Observable<JSONObject>>() {
            @Override
            public Observable<JSONObject> call(Observable<JSONObject> jsonObjectObservable) {
                return jsonObjectObservable;
            }
        });
    }
}
