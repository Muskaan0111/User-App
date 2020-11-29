package Pickers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.userapp.databinding.WeightPickerDialogBinding;

import Models.Cart;
import Models.Product;

public class WeightPickerDialog {

    Context context;
    Cart cart;
    Product product;



    WeightPickerDialogBinding b;

    public interface OnWeightSelectedListener{
      void onWeightPicked(int kg, int g);
      void onCancelled();
    }

    public void show(Context context, Cart cart , Product product , OnWeightSelectedListener listener){

       //inflating layout
        b = WeightPickerDialogBinding.inflate(LayoutInflater.from(context));

        this.cart=cart;
        this.product=product;


        new AlertDialog.Builder(context)
                .setTitle("Pick Weight")
                .setView(b.getRoot())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int kg = b.numberPickerKg.getValue();
                        int g = b.numberPickerG.getValue()*50;

                        if(kg==0&&g==0){
                            return;
                        }

                       float value =  (kg+ (g/1000f));
                        cart.updateWeightBasedQty(product,value);
                        listener.onWeightPicked(kg,g);

                    }
                })
                .setNegativeButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cart.removeWeightBasedQty(product);
                        listener.onCancelled();
                        Toast.makeText(context, "Cancelled!", Toast.LENGTH_SHORT).show();

                    }
                })
                .show();

                setNumberPickers();
                showPreviousQty();

    }

    private void showPreviousQty() {
        if(cart.mapOfItems.containsKey(product.name)){

            float qty = cart.mapOfItems.get(product.name).qty;

            b.numberPickerKg.setValue((int) qty);
            b.numberPickerG.setValue((int) ((qty - (int) qty) * 1000/50) );
        }
    }

    private void setNumberPickers() {

        float quantity = product.minQty;
        b.numberPickerKg.setMinValue((int) (quantity / 1000));
        b.numberPickerKg.setMaxValue(10);
        b.numberPickerG.setMinValue(((int) (quantity % 1000)) / 50);
        b.numberPickerG.setMaxValue(19);

        b.numberPickerKg.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " " + "kg";
            }
        });

        b.numberPickerG.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return (value * 50) + " " + "g";
            }
        });

        updateFirstItemViewInNumberPicker(b.numberPickerKg);
        updateFirstItemViewInNumberPicker(b.numberPickerG);

    }

    private void updateFirstItemViewInNumberPicker(NumberPicker numberPicker) {

        View firstItem = numberPicker.getChildAt(0);
        if (firstItem != null) {
            firstItem.setVisibility(View.INVISIBLE);
        }
    }




}
