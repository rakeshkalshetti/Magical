package com.magical.adapter;

/**
 * Created by Rakesh on 26/06/17.
 */

public class ProductItem {
    private int id;
    private String name;
    private int price;

    private boolean productState;

    public ProductItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isProductState() {
        return productState;
    }

    public void setProductState(boolean productState) {
        this.productState = productState;
    }
}
