package com.example.userapp;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



public class SendFCM {

    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send"
            , KEY_STRING = "key=AAAAkRWzpUc:APA91bFw8vRgz4X4DahLaybWj-NHF2sIXbL_3jqF8NyQdxOnEVUYNkI4elpKoNl5T_cOSliFVn9GiCo7Z9HpYZSQyxF6CmMsUlW-bsLKAOvGi6jFGnp6rkkjET1KiEAKMWVkEhSeZKeo";

    OkHttpClient client = new OkHttpClient();

    public void send(String message, Callback callback){
        RequestBody reqBody = RequestBody.create(message
                , MediaType.get("application/json"));


        Request request = new Request.Builder()
                .url(FCM_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", KEY_STRING)
                .post(reqBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }








}
