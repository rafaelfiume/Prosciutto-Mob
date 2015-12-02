package com.rafaelfiume.prosciutto.adviser;

public class Product {

    private final String name;
    private final String price;
    private final String reputation;
    private final String fatPercentage;

    public Product(String name, String price, String reputation, String fatPercentage) {
        this.name = name;
        this.price = price;
        this.reputation = reputation;
        this.fatPercentage = fatPercentage;
    }

    public String name() {
        return name;
    }

    public String price() {
        return price;
    }

    public String reputation() {
        return reputation;
    }

    public String fatPercentage() {
        return fatPercentage;
    }

}
