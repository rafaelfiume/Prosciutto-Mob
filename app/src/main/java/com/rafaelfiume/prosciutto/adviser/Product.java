package com.rafaelfiume.prosciutto.adviser;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public static final String SALUME_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/b/b5/Formaggi_e_salumi_sardi.jpg";

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

    private Product(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
        this.reputation = in.readString();
        this.fatPercentage = in.readString();
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

    public String imageUrl() {
        return SALUME_IMAGE_URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.name);
        out.writeString(this.price);
        out.writeString(this.reputation);
        out.writeString(this.fatPercentage);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
