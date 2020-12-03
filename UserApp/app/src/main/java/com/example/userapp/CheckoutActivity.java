package com.example.userapp;

import androidx.annotation.NonNull;
import java.sql.Timestamp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.databinding.ActivityCheckoutBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Cart;
import Models.CartItem;
import Models.Order;
import Models.Product;

public class CheckoutActivity extends AppCompatActivity  {
ActivityCheckoutBinding b;
    private App getApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      b = ActivityCheckoutBinding.inflate(getLayoutInflater());
      setContentView(b.getRoot());

      getValuesFromIntent();



    }



    private void getValuesFromIntent() {
        Intent intent=getIntent();


        Cart cart =(Cart) getIntent().getSerializableExtra("MyCart");


        setUpSummary(cart);

       // Toast.makeText(this,cart.mapOfItems.toString(),Toast.LENGTH_LONG).show();
    }

    private void setUpSummary(Cart cart) {


         String toChange = cart.mapOfItems.toString();
        toChange.replaceFirst("^[a-z,]+$","");
       // Toast.makeText(this,toChange,Toast.LENGTH_LONG).show();





        b.proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataOnFirebase(cart);

            }
        });





    }

    private void saveDataOnFirebase(Cart cart) {
        getApp = (App) getApplicationContext();
        if(getApp.isOffline()){
            getApp.showToast(this, "Unable to save. You are offline!");
            return;
        }

        getApp.showLoadingDialog(this);
        String name =  b.entername.getText().toString();
        String phoneNumber = b.enterphone.getText().toString().trim();

        String address = b.enteradress.getText().toString();
        int subTotal = cart.subTotal;
        List<CartItem> cartItems = new ArrayList<CartItem>(cart.mapOfItems.values());

        Date date= new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);



        Map<String, Order> object1 = new HashMap<>();

       String orderId= subTotal+25*5000/2+" ";



        object1.put("order", new Order(Order.OrderStatus.PLACED,orderId,ts,name,phoneNumber,address,cartItems,subTotal));



        getApp.db.collection("Orders")
                .add(object1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CheckoutActivity.this, "Order Placed!", Toast.LENGTH_SHORT).show();
                        getApp.hideLoadingDialog();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CheckoutActivity.this, "Order Not Placed!", Toast.LENGTH_SHORT).show();
                        getApp.hideLoadingDialog();
                        finish();
                    }
                });







    }


        public static boolean isPhoneValid(String s)
        {


            Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

            // Pattern class contains matcher() method
            // to find matching between given number
            // and regular expression
            Matcher m = p.matcher(s);
            return (m.find() && m.group().equals(s));
        }

}