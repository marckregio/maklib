package com.marckregio.makunatlib.util;

import android.content.Context;

import com.marckregio.makunatlib.AsyncRequest;

import org.json.JSONObject;

/**
 * Created by makregio on 07/07/2017.
 */

public class OliveShare extends AsyncRequest {

    private static final String SHARE_URL = "https://olive-share.smedia.com.au/sharer?";
    private static final String STATE = "state=";
    private static final String PUBCODE = "&pubcode=";
    private static final String DATE = "&date=";
    private static final String PAGE = "&page=";
    private static final String ARTICLE = "&ar=";

    public static final String SHAREURL = "share_url";

    public OliveShare(Context context, String state, String pubcode, String date, String page, String ar) {
        super(context, SHARE_URL +
                STATE + state +
                PUBCODE + pubcode +
                DATE + date +
                PAGE + page +
                ARTICLE + ar);
    }

    @Override
    protected void requestObject(Object params, String request, Object locker) {
        client.requestJSONObj((JSONObject) params, super.request, locker);
    }
}
