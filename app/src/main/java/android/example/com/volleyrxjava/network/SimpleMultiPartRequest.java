package android.example.com.volleyrxjava.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by dkocian on 11/23/2014.
 */
public class SimpleMultiPartRequest extends MultiPartRequest<String> {

    private final Response.Listener<String> mListener;

    public SimpleMultiPartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorlistener) {
        super(Method.POST, url, listener, errorlistener);
        mListener = listener;
    }

    public SimpleMultiPartRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorlistener) {
        super(method, url, listener, errorlistener);
        mListener = listener;
    }

    @Override
    protected void deliverResponse(String response) {
        if (null != mListener) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
