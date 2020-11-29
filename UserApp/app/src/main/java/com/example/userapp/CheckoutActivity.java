package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userapp.databinding.ActivityCheckoutBinding;

import java.io.Serializable;

import Models.Cart;
import Models.Product;

public class CheckoutActivity extends AppCompatActivity  {
ActivityCheckoutBinding b;
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
        Toast.makeText(this,toChange,Toast.LENGTH_LONG).show();



    }
}