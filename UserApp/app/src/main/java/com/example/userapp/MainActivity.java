package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.userapp.databinding.ActivityMainBinding;
import com.example.userapp.databinding.SummaryDialogBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.ProductAdapter;
import Models.Cart;
import Models.Product;
import Models.Variant;
import Pickers.SummaryDialog;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding b;
    private Cart cart = new Cart();
    SummaryDialogBinding x ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setupProductsList();


    }

    private void setupProductsList() {

        ArrayList<Product> products = new ArrayList<>(Arrays.asList(

                new Product("Stawberry", Arrays.asList(
                        new Variant("1kg", 100)
                        , new Variant("2kg", 200)
                        , new Variant("3kg", 300)
                ))
                , new Product("Onion", 80, 1)
                , new Product("Pineapple", Arrays.asList(
                        new Variant("1kg", 200)
                ))


        ));

        ProductAdapter adapter = new ProductAdapter(this, products, cart);


        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        b.recyclerView.setAdapter(adapter);




    }

    public void getCartSummary() {
        if(cart.noOfTotalItems==0){

            b.cartViewer.setVisibility(View.GONE);
        }
          String cartDetails= "Rs: " + cart.subTotal+"\n"+cart.noOfTotalItems+" items";
        b.cartViewer.setVisibility(View.VISIBLE);
        b.cartPreview.setText(cartDetails);

        configureButtons();



    }

    private void configureButtons() {

        b.summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new SummaryDialog().show(MainActivity.this, cart, new SummaryDialog.OnOkSelectedListener() {
                        @Override
                        public void onOkSelected() {

                        }
                    });


            }
        });

    }


}