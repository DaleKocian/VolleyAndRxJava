package example.com.volleyrxjava.utils;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by dkocian on 11/23/2014.
 */
public class Utils {

    public static final int CACHE_HIT_BUT_REFRESHED = 3 * 60 * 1000;// in 3 minutes cache will be hit, but also refreshed on background
    public static final int CACHE_EXPIRED = 24 * 60 * 60 * 1000;// in 24 hours this cache entry expires completely
    public static final String HEADER_DATE = "Date";
    public static final String E_TAG = "ETag";

    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String headerValue = headers.get(HEADER_DATE);
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }
        String serverEtag = headers.get(E_TAG);
        final long softExpire = now + CACHE_HIT_BUT_REFRESHED;
        final long ttl = now + CACHE_EXPIRED;
        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;
        return entry;
    }
}
