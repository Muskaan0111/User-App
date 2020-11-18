package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.userapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.ProductAdapter;
import Models.Cart;
import Models.Product;
import Models.Variant;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding b;
    private Cart cart = new Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setupProductsList();


    }

    private void setupProductsList() {

        ArrayList<Product> products = new ArrayList<>(Arrays.asList(

                new Product("Bread", Arrays.asList(
                        new Variant("big", 10)
                        , new Variant("small", 20)
                        , new Variant("medium", 30)
                ))
                , new Product("Apple", 30, 1)
                , new Product("Kiwi", Arrays.asList(
                        new Variant("1kg", 100)
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


    }


}