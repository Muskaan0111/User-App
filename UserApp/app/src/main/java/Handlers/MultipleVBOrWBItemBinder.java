package Handlers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.userapp.MainActivity;
import com.example.userapp.databinding.MultipleVbAndWbItemsBinding;

import Models.Cart;
import Models.Product;
import Pickers.VariantPickerDialog;
import Pickers.WeightPickerDialog;

public class MultipleVBOrWBItemBinder {
    MultipleVbAndWbItemsBinding b;
    Cart cart;
    Product product;

    public void bindItems(MultipleVbAndWbItemsBinding b, Product product , Cart cart){
        this.b=b;
        this.product=product;
        this.cart=cart;

        b.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(product.type==Product.WEIGHT_BASED){
                    showWeightPicker();
                }
                else{
                    showVariantPicker();
                }

            }
        });


        b.editQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.type==Product.WEIGHT_BASED){
                    showWeightPicker();
                }
                else{
                    showVariantPicker();
                }


            }
        });



    }

    private void showVariantPicker() {

        Context context = b.getRoot().getContext();

        new VariantPickerDialog().show(context, cart, product, new VariantPickerDialog.OnVariantsSelectedListener() {
            @Override
            public void onQtyUpdated(int qty) {
                updateQtyVB(qty + "");

            }

            @Override
            public void onRemoved() {
                 hideQty();
            }
        });








    }

    private void updateQtyVB(String s) {

        b.qtyGroup.setVisibility(View.VISIBLE);
        b.addButton.setVisibility(View.GONE);

        b.quantity.setText(s);
        updateCart();
    }

    private void showWeightPicker() {

        Context context = b.getRoot().getContext();

        new WeightPickerDialog()
                .show(context, cart, product, new WeightPickerDialog.OnWeightSelectedListener() {
                    @Override
                    public void onWeightPicked(int kg, int g) {
                        updateQty(kg,g);
                    }

                    @Override
                    public void onCancelled() {
                        hideQty();

                    }
                });






    }

    private void hideQty() {

        b.qtyGroup.setVisibility(View.GONE);
        b.addButton.setVisibility(View.VISIBLE);
        updateCart();
    }

    private void updateQty(int kg, int g) {

        b.qtyGroup.setVisibility(View.VISIBLE);
        b.addButton.setVisibility(View.GONE);

        if(g==0){

            b.quantity.setText(kg+"kg");
        }
        else if(kg==0){
            b.quantity.setText(g+"g");
        }

        else{
            b.quantity.setText(kg+"kg "+g+"g");

        }

updateCart();



    }

    private void updateCart() {
        Context context = b.getRoot().getContext();
        if(context instanceof MainActivity){
            ((MainActivity) context).getCartSummary();
        } else {
            Toast.makeText(context, "oops!", Toast.LENGTH_SHORT).show();
        }
    }
    }



