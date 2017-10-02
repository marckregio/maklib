package com.marckregio.makunatlib.http;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by makregio on 12/08/16.
 */
public class REST {

    private static final String requestContent = "application/json; charset=utf-8";
    private static final MediaType JSON = MediaType.parse(requestContent);

    public static void postOkHttp(String url, JSONObject json, final OkHttp listener){
        OkHttpClient ok = new OkHttpClient();
        RequestBody requestBody= RequestBody.create(JSON, json.toString());
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    listener.onResponse(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface OkHttp {

        void onFailure(Request request, IOException e);
        void onResponse(Response response) throws Exception;
    }
}
