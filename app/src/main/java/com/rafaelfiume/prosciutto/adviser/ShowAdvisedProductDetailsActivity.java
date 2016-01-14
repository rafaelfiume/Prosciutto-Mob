package com.rafaelfiume.prosciutto.adviser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rafaelfiume.prosciutto.adviser.domain.Product;
import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription;
import com.rafaelfiume.prosciutto.adviser.integration.ProductDescriptionQuery;

import static java.lang.String.format;

public class ShowAdvisedProductDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SUGGESTED_PRODUCT = "com.rafaelfiume.prosciutto.adviser.ShowProductDetail.extra.suggestion";

    public static void navigate(Activity callingActivity, Product product) {
        callingActivity.startActivity(newIntent(callingActivity, product));

        // TODO RF 06/12/15 This is pretty cool. See https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition
        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        //ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static Intent newIntent(Context context, Product product) {
        final Intent intent = new Intent(context, ShowAdvisedProductDetailsActivity.class);
        intent.putExtra(EXTRA_SUGGESTED_PRODUCT, product);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advised_product_details);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        final Product product = intent.getParcelableExtra(EXTRA_SUGGESTED_PRODUCT);

        loadProductDescription(product);
        setValueFor(R.id.p_detail_name, product.name());
        setValueFor(R.id.p_detail_price, product.price());
        setValueFor(R.id.p_detail_reputation, product.reputation());
        setValueFor(R.id.p_detail_fat, product.fatPercentage());

        final CollapsingToolbarLayout  collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(product.name());
        // collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.holo_red_dark));
        collapsingToolbarLayout.setContentScrimColor(android.R.color.background_dark);

        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(product.imageUrl()).centerCrop().into(imageView);
    }

    private void setValueFor(int viewId, String value) {
        TextView tvName = (TextView) findViewById(viewId);
        tvName.setText(value);
    }

    private void loadProductDescription(Product product) {
        new GetProductProductDescription(product).execute();
    }

    class GetProductProductDescription extends AsyncTask<Void, Void, ProductDescription> {

        private final ProductDescriptionQuery descriptionQuery = new ProductDescriptionQuery();
        private final Product product;

        GetProductProductDescription(Product product) {
            this.product = product;
        }

        @Override
        protected ProductDescription doInBackground(Void... nothing) {
            try {
                return descriptionQuery.query(product.descriptionUrl());

            } catch (Exception e) {
                return ProductDescription.empty();
            }
        }

        @Override
        protected void onPostExecute(ProductDescription description) {
            setValueFor(R.id.description_label, format("About the %s Variety:", product.variety()));
            setValueFor(R.id.p_detail_description, description.value());
        }

    }

}
