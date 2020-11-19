package Pickers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.userapp.databinding.MultipleVbAndWbItemsBinding;
import com.example.userapp.databinding.VariantItemBinding;
import com.example.userapp.databinding.VariantPickerDialogBinding;

import Models.Cart;
import Models.Product;
import Models.Variant;

public class VariantPickerDialog {

    VariantPickerDialogBinding b;
    MultipleVbAndWbItemsBinding x;
    Context context;
    Product product;
    Cart cart;



    public interface OnVariantsSelectedListener{
        void onQtyUpdated(int qty);
        void onRemoved();
    }


    public void show(Context context,Cart cart, Product product,OnVariantsSelectedListener listener){
         this.context=context;
        b = VariantPickerDialogBinding.inflate(LayoutInflater.from(context));

        this.cart=cart;
        this.product=product;

        new AlertDialog.Builder(context)
                .setTitle("Pick Variants")
                .setView(b.getRoot())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int qty = cart.allVariantsQty.get(product.name);


                        if(qty>0)
                       listener.onQtyUpdated(qty);


                        else
                            listener.onRemoved();


                    }
                })
                .setNegativeButton("REMOVE ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cart.removeEveryVariant(product);
                        listener.onRemoved();



                    }
                })
                .show();


        setVariants();



    }

    private void setVariants() {

        for(Variant variant: product.variants){

            VariantItemBinding vb = VariantItemBinding.inflate(LayoutInflater.from(context),b.getRoot(),true);

            vb.variantName.setText(variant.nameString());

            showPreviousDetails(variant,vb);
            setButtons(variant,vb);




        }



    }

    private void setButtons(Variant variant, VariantItemBinding vb) {

        vb.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = cart.addToCart(product,variant);


                if(qty==1){

                    vb.decrement.setVisibility(View.VISIBLE);
                    vb.qty.setVisibility(View.VISIBLE);

                }

                vb.qty.setText(qty+" ");


            }
        });

        vb.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = cart.removeFromCart(product, variant);
                vb.qty.setText(qty + " ");


                if(qty == 0){
                    vb.decrement.setVisibility(View.GONE);
                    vb.qty.setVisibility(View.GONE);
                }


            }
        });



    }

//    private void checkDetails( VariantItemBinding vb, MultipleVbAndWbItemsBinding x) {
//        int num = Integer.parseInt(vb.qty.getText().toString().trim());
//        int numCheck = Integer.parseInt(x.quantity.getText().toString().trim());
//
//        if(CHECK==0&&num!=numCheck){
//
//            vb.qty.setText(numCheck+"");
//
//        }
//
//
//
//    }

    private void showPreviousDetails(Variant variant, VariantItemBinding vb) {

        int qty = cart.getVariantQty(product, variant);



            if(qty > 0){
                vb.decrement.setVisibility(View.VISIBLE);
                vb.qty.setVisibility(View.VISIBLE);

                vb.qty.setText(qty + "");
            }




    }




}
