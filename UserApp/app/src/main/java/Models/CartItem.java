package Models;

import java.io.Serializable;

public class CartItem implements Serializable {

    String name;
    public float qty;
    int price;

    //in case of new addition to cart
    public CartItem(String name, int price) {
        this.name = name;
        this.price = price;
        qty = 1;
    }


    //in case of already existing product
    public CartItem(String name, float qty, int price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\n" +"[" +
                "Name=" + name + "\n" +
                ", price=" + price +"\n"+
                ", qty=" + qty +
                ']';
    }

}

