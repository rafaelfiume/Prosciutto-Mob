package com.rafaelfiume.prosciutto.adviser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View itemView = (convertView == null)
                ? LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false)
                : convertView;

        final Product product = getItem(position);
        TextView tvName = (TextView) itemView.findViewById(R.id.product_name_text);
        tvName.setText(product.name());

        return itemView;
    }
}
