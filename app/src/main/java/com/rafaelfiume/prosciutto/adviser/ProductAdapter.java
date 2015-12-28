package com.rafaelfiume.prosciutto.adviser;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final OnSuggestedProductClickListenerFactory factory;

    public ProductAdapter(Context context, OnSuggestedProductClickListenerFactory factory) {
        super(context, 0);
        this.factory = factory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View itemView = (convertView == null)
                ? LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false)
                : convertView;

        final Product product = getItem(position);
        final TextView tvName = (TextView) itemView.findViewById(R.id.product_name_text);
        tvName.setText(product.name());
        final TextView tvPrice = (TextView) itemView.findViewById(R.id.product_price_text);
        tvPrice.setText(product.price());

        itemView.setOnClickListener(factory.newOnClickListenerFor(product));

        return itemView;
    }

    ArrayList<? extends Parcelable> content() {
        // Annoying not having a similar functionality returning a copy of the product list :(
        // Android, you forced me to do that!!
        final ArrayList<Product> copy = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            copy.add(getItem(i));
        }
        return copy;
    }
}
