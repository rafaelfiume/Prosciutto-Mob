package com.rafaelfiume.prosciutto.adviser;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.rafaelfiume.prosciutto.adviser.R;

import static com.rafaelfiume.prosciutto.adviser.ProductAdapter.EXTRA_MESSAGE;

public class ShowAdvisedProductDetails extends AppCompatActivity {

    public static void navigate(Context context, Product product) {
        final Intent intent = new Intent(context, ShowAdvisedProductDetails.class);
        intent.putExtra(EXTRA_MESSAGE, product);
        context.startActivity(intent);

        // TODO RF 06/12/15 This is pretty cool. See https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition
        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        //ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advised_product_details);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        final Product product = intent.getParcelableExtra(EXTRA_MESSAGE);

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

    private void setValueFor(int p_detail_name, String name) {
        TextView tvName = (TextView) findViewById(p_detail_name);
        tvName.setText(name);
    }

}
