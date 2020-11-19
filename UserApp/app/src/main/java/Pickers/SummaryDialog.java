package Pickers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.example.userapp.databinding.SummaryDialogBinding;
import com.example.userapp.databinding.WeightPickerDialogBinding;

import Models.Cart;
import Models.Product;

public class SummaryDialog {


    Context context;
    Cart cart;
    Product product;

    SummaryDialogBinding b;

    public interface OnOkSelectedListener {
        void onOkSelected();
    }

    public void show(Context context, Cart cart, OnOkSelectedListener onOkSelectedListener) {

        //inflating layout
        b = SummaryDialogBinding.inflate(LayoutInflater.from(context));

        this.cart = cart;



        new AlertDialog.Builder(context)
                .setTitle("View Your Summary")
                .setView(b.getRoot())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

        getSummaryData();

    }

    private void getSummaryData() {
       String summary =  cart.mapOfItems.toString();

       b.summary.setText(summary);


    }
}