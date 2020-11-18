package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    // contains all items
    public Map<String, CartItem> mapOfItems = new HashMap<>();

    // targets the variant based items
    public Map<String,Integer> allVariantsQty = new HashMap<>();

    public int subTotal,noOfTotalItems;

    //adds a variant based product to cart
    public  int addToCart(Product product, Variant variant){

        String detector =  product.name+" "+variant.name;
        if(mapOfItems.containsKey(detector)){
            CartItem cartItem = mapOfItems.get(detector);
          cartItem.qty++;
          cartItem.price += variant.price;



        }else
        {
            mapOfItems.put(detector,new CartItem(detector, variant.price));

        }

        subTotal += variant.price;
        noOfTotalItems++;


        if(allVariantsQty.containsKey(product.name)){
            int qty = allVariantsQty.get(product.name) + 1;
            allVariantsQty.put(product.name, qty);


        }
        else{

            allVariantsQty.put(product.name, 1);

        }

        return (int) mapOfItems.get(detector).qty;
    }

    public int removeFromCart(Product product, Variant variant) {

        String detector = product.name + " " + variant.name;
        if(mapOfItems.containsKey(detector)){
            CartItem cartItem = mapOfItems.get(detector);
            mapOfItems.get(detector).qty--;
            cartItem.price-= variant.price;

            if (mapOfItems.get(detector).qty == 0) {
                mapOfItems.remove(detector);
            }

            subTotal -= variant.price;
            noOfTotalItems--;

            int qty = allVariantsQty.get(product.name) - 1;
            allVariantsQty.put(product.name, qty);


            if (allVariantsQty.get(product.name) == 0)
            {allVariantsQty.remove(product.name);}

        }
        return mapOfItems.containsKey(detector)?(int) mapOfItems.get(detector).qty : 0;


    }

    public void updateWeightBasedQty(Product product, float qty){

        int newPrice = (int) (product.pricePerKg*qty);

        if(mapOfItems.containsKey(product.name)){
            int oldPrice = mapOfItems.get(product.name).price;

            subTotal = subTotal - oldPrice + newPrice;
            System.out.println(subTotal);



        }
        else{

            noOfTotalItems++;
            subTotal += newPrice;
            System.out.println(subTotal);
            System.out.println(noOfTotalItems);
        }

        mapOfItems.put(product.name,new CartItem(product.name, qty,newPrice));


    }

    public void removeWeightBasedQty(Product product){

        if(mapOfItems.containsKey(product.name)){
            noOfTotalItems--;
            System.out.println(noOfTotalItems);
            subTotal -= mapOfItems.get(product.name).price;
            System.out.println(subTotal);
            mapOfItems.remove(product.name);
        }

    }

    public void removeEveryVariant(Product product){

        for(Variant variant : product.variants){

            String detector = product.name + " " + variant.name;

            if(mapOfItems.containsKey(detector)){

                subTotal -= mapOfItems.get(detector).price;
                noOfTotalItems -= mapOfItems.get(detector).qty;
                mapOfItems.remove(detector);
            }
        }

        if(allVariantsQty.containsKey(product.name))
        {   allVariantsQty.remove(product.name);}




    }


    public ArrayList<String> getData(Product product){

        ArrayList<String> information = new ArrayList<>();
        information.add("Name Of Product = "+ product.name);
        information.add("Subtotal = "+ subTotal);
        information.add("Number Of Total Items = "+ noOfTotalItems);



        return information;



    }






}


