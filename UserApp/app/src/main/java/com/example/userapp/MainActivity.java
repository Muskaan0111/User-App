package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.example.userapp.databinding.ActivityMainBinding;
import com.example.userapp.databinding.SummaryDialogBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import Adapters.ProductAdapter;
import Models.Cart;
import Models.Inventory;
import Models.Product;

import Pickers.SummaryDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {
ActivityMainBinding b;
    private Cart cart = new Cart();
    private ArrayList<Product> product;

    SummaryDialogBinding x;
    private App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

     //   setupProductsList();


        app = (App) getApplicationContext();
        loadData();
        setupCheckout();


        FirebaseMessaging.getInstance().subscribeToTopic("users");
        sendNotification();

    }

    public void sendNotification() {
        String message = FormattingMessage
                .getSampleMessage("users", "Welcome", "Welcome to the User App");

        new SendFCM()
                .send(message
                        , new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Failure")
                                                .setMessage(e.toString())
                                                .show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Success")
                                                .setMessage(response.toString()+"Hello Guys!")
                                                .show();
                                    }
                                });


                            }
                        });

    }


    private void loadData() {
        if(app.isOffline()){
            app.showToast(this, "You are offline! Check Again");
            return;
        }

        app.showLoadingDialog(this);

        app.db.collection("inventory")
                .document("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Inventory inventory = documentSnapshot.toObject(Inventory.class);
                            product = inventory.products;
                        }
                        else
                        { product = new ArrayList<>();}
                        setupProductsList();
                        app.hideLoadingDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        app.hideLoadingDialog();
                        app.showToast(MainActivity.this, e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private void setupCheckout() {
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


    private void setupProductsList() {

//         products = new ArrayList<>(Arrays.asList(
//
//                new Product("Stawberry", Arrays.asList(
//                        new Variant("1kg", 100)
//                        , new Variant("2kg", 200)
//                        , new Variant("3kg", 300)
//                ))
//                , new Product("Onion", 80, 1)
//                , new Product("Pineapple", Arrays.asList(
//                        new Variant("1kg", 200)
//                ))
//
//
//        ));

        ProductAdapter adapter = new ProductAdapter(this, product, cart);


        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        b.recyclerView.setAdapter(adapter);


    }

    public void getCartSummary() {
        if (cart.noOfTotalItems == 0) {

            b.cartViewer.setVisibility(View.GONE);
        }
        String cartDetails = "Rs: " + cart.subTotal + "\n" + cart.noOfTotalItems + " items";
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


        b.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCheckoutActivity(v);
            }
        });


    }

    public void launchCheckoutActivity(View view) {


        Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);

      intent.putExtra("MyCart",cart);


        startActivity(intent);



    }
}

