package com.rafaelfiume.prosciutto.adviser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rafaelfiume.prosciutto.adviser.R;

import static com.rafaelfiume.prosciutto.adviser.ProductAdapter.EXTRA_MESSAGE;

public class ShowAdvisedProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advised_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final Product product = (Product) intent.getSerializableExtra(EXTRA_MESSAGE);

        setValueFor(R.id.p_detail_name, product.name());
        setValueFor(R.id.p_detail_price, product.price());
        setValueFor(R.id.p_detail_reputation, product.reputation());
        setValueFor(R.id.p_detail_fat, product.fatPercentage());
    }

    private void setValueFor(int p_detail_name, String name) {
        TextView tvName = (TextView) findViewById(p_detail_name);
        tvName.setText(name);
    }

}
