package Models;

import java.sql.Timestamp;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    int status;
    String orderId;
    Timestamp orderPlacedTs;

    String userName, userPhoneNo, userAddress;

    List<CartItem> cartItems;
    int subTotal;

    public Order(String userName, String userPhoneNo, String userAddress) {
        this.userName = userName;
        this.userPhoneNo = userPhoneNo;
        this.userAddress = userAddress;
    }


    public Order(int status, String orderId, Timestamp orderPlacedTs, String userName, String userPhoneNo, String userAddress, List<CartItem> cartItems, int subTotal) {
        this.status = status;
        this.orderId = orderId;
        this.orderPlacedTs = orderPlacedTs;
        this.userName = userName;
        this.userPhoneNo = userPhoneNo;
        this.userAddress = userAddress;
        this.cartItems = cartItems;
        this.subTotal = subTotal;
    }


    public Order() {
    }

    public Order(int subTotal, List<CartItem> cartItems, Timestamp ts, String name, String phoneNumber, String address) {
        this.subTotal = subTotal;
        this.cartItems = cartItems;
        this.orderPlacedTs = ts;
        this.userName = name;
        this.userPhoneNo = phoneNumber;
        this.userAddress = address;



    }

    public String getName() {
        return userName;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public int getStatus() {
        return status;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public Timestamp getOrderPlacedTs() {
        return orderPlacedTs;
    }

    public String getOrderId() {



        return orderId;
    }

    public List<CartItem> cartItems() {
        return cartItems;
    }


    public static class OrderStatus {

        public static final int PLACED = 1;

    }


    }
