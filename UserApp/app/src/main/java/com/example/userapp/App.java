package com.example.userapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {


    public FirebaseFirestore db;
    private AlertDialog dialog;
    private ConnectivityManager connectivityManager;



    @Override
    public void onCreate() {
        super.onCreate();

        setup();
    }

    private void setup() {
        db = FirebaseFirestore.getInstance();
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    public void showLoadingDialog(Context context){
        dialog = new AlertDialog.Builder(context)
                .setTitle("Loading...")
                .setMessage("Please wait!")
                .show();
    }

    public void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean isOffline(){
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return !(wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected());
    }

    public void hideLoadingDialog(){
        if(dialog != null)
            dialog.dismiss();
    }
}
