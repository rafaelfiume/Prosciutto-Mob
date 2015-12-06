package com.rafaelfiume.prosciutto.adviser;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    public static final String EXTRA_MESSAGE = "com.rafaelfiume.prosciutto.adviser.ShowProductDetail";

    public ProductAdapter(Context context) {
        super(context, 0);
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getContext(), ShowAdvisedProductDetails.class);
                intent.putExtra(EXTRA_MESSAGE, product);
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }

    ArrayList<? extends Parcelable> content() {
        // this is ugly! annoying not having a similar functionality returning a copy of the product list :(
        // you forced me to do that!!
        final ArrayList<Product> copy = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            copy.add(getItem(i));
        }
        return copy;
    }
}
