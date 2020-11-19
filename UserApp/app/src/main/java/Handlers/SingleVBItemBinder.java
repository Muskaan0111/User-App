package Handlers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.userapp.MainActivity;
import com.example.userapp.databinding.SingleVbItemBinding;

import Models.Cart;
import Models.Product;

public class SingleVBItemBinder {

    SingleVbItemBinding b;

    public void bindItems(SingleVbItemBinding b, Product product, Cart cart){

        this.b = b;

        b.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addToCart(product,product.variants.get(0));
                updateViews(1);
            }
        });

        b.incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int quantity =  cart.addToCart(product,product.variants.get(0));
                updateViews(quantity);
            }
        });

        b.decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity =  cart.removeFromCart(product,product.variants.get(0));
                updateViews(quantity);
            }
        });







    }

    private void updateViews(int qty) {

        if(qty==1){
            b.addButton.setVisibility(View.GONE);
            b.qtyGroup.setVisibility(View.VISIBLE);

        }

        else if(qty==0){
            b.addButton.setVisibility(View.VISIBLE);
            b.qtyGroup.setVisibility(View.GONE);

        }

        b.quantity.setText(qty+" ");
        updateCart();


    }

    private void updateCart() {

        Context context = b.getRoot().getContext();
        if(context instanceof MainActivity){

            ( (MainActivity)context).getCartSummary();
        }
        else{
            Toast.makeText(context, "OOPS , try again !", Toast.LENGTH_SHORT).show();
        }



    }


}
