package com.example.vendor.Seeds;

public class SeedOrdersModel {
    String Order_Product_address;
    String Order_Product_amount;
    String Order_Product_count;
    String Order_Product_id;
    String Order_Product_pincode;

    public SeedOrdersModel(String order_Product_address, String order_Product_amount, String order_Product_count, String order_Product_id, String order_Product_pincode) {
        Order_Product_address = order_Product_address;
        Order_Product_amount = order_Product_amount;
        Order_Product_count = order_Product_count;
        Order_Product_id = order_Product_id;
        Order_Product_pincode = order_Product_pincode;
    }

    public SeedOrdersModel() {
    }

    public String getOrder_Product_address() {
        return Order_Product_address;
    }

    public void setOrder_Product_address(String order_Product_address) {
        Order_Product_address = order_Product_address;
    }

    public String getOrder_Product_amount() {
        return Order_Product_amount;
    }

    public void setOrder_Product_amount(String order_Product_amount) {
        Order_Product_amount = order_Product_amount;
    }

    public String getOrder_Product_count() {
        return Order_Product_count;
    }

    public void setOrder_Product_count(String order_Product_count) {
        Order_Product_count = order_Product_count;
    }

    public String getOrder_Product_id() {
        return Order_Product_id;
    }

    public void setOrder_Product_id(String order_Product_id) {
        Order_Product_id = order_Product_id;
    }

    public String getOrder_Product_pincode() {
        return Order_Product_pincode;
    }

    public void setOrder_Product_pincode(String order_Product_pincode) {
        Order_Product_pincode = order_Product_pincode;
    }
}
