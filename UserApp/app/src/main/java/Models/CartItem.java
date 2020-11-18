package Models;

public class CartItem {

    String name;
    float qty;
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


}

