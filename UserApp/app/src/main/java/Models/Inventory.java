package Models;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public ArrayList<Product> products;

    public Inventory() {
    }

    public Inventory(ArrayList<Product> products) {
        this.products = products;
    }
}
