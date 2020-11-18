package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userapp.databinding.MultipleVbAndWbItemsBinding;
import com.example.userapp.databinding.SingleVbItemBinding;

import java.util.ArrayList;

import Handlers.MultipleVBOrWBItemBinder;
import Handlers.SingleVBItemBinder;
import Models.Cart;
import Models.CartItem;
import Models.Product;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
       private static final int SINGLE_VB_ITEM = 0, MULTIPLE_VB_AND_WB_ITEMS = 1;

    private Context context;
    ArrayList<Product> productsList;
    Cart cart;

    public ProductAdapter(Context context, ArrayList<Product> productsList, Cart cart) {
        this.context = context;
        this.productsList = productsList;
        this.cart = cart;
    }

    @Override
    public int getItemViewType(int position) {
        Product product = productsList.get(position);

        if(product.type== Product.WEIGHT_BASED || product.variants.size()>1)
            return MULTIPLE_VB_AND_WB_ITEMS;

        return SINGLE_VB_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==SINGLE_VB_ITEM){

            SingleVbItemBinding b = SingleVbItemBinding.inflate(LayoutInflater.from(context),parent,false);
               return new SingleVBItem(b);

        }
        else
        {

            MultipleVbAndWbItemsBinding b = MultipleVbAndWbItemsBinding.inflate(LayoutInflater.from(context),parent,false);
            return  new MultipleVOrWBItem(b);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Product product = productsList.get(position);

        if(getItemViewType(position)==SINGLE_VB_ITEM){
            SingleVBItem svb = (SingleVBItem)holder;
            svb.b.name.setText(product.name+" "+product.variants.get(0).name);
            svb.b.price.setText(product.getPrice());

            //buttons

            new SingleVBItemBinder().bindItems(svb.b,product,cart);


        }

        else{

            MultipleVOrWBItem mvb = (MultipleVOrWBItem)holder;
            mvb.b.name.setText(product.name+" ");
            mvb.b.price.setText(product.getPrice());

            new MultipleVBOrWBItemBinder();
        }


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


public class SingleVBItem extends RecyclerView.ViewHolder{
       SingleVbItemBinding b;

    public SingleVBItem(@NonNull SingleVbItemBinding b) {
        super(b.getRoot());
        this.b = b;
    }
}

    public class MultipleVOrWBItem extends RecyclerView.ViewHolder{
        MultipleVbAndWbItemsBinding b;

        public MultipleVOrWBItem(@NonNull MultipleVbAndWbItemsBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }


}
