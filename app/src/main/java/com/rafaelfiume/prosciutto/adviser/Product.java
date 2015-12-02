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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
