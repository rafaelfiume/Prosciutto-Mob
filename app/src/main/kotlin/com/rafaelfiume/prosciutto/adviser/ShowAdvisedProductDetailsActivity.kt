package com.rafaelfiume.prosciutto.adviser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription
import com.rafaelfiume.prosciutto.adviser.integration.ProductDescriptionQuery
import java.lang.String.format

class ShowAdvisedProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_advised_product_details)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val product = intent.getParcelableExtra<Product>(EXTRA_SUGGESTED_PRODUCT)

        loadProductDescription(product)
        setValueFor(R.id.p_detail_name, product.name)
        setValueFor(R.id.p_detail_price, product.price)
        setValueFor(R.id.p_detail_reputation, product.reputation)
        setValueFor(R.id.p_detail_fat, product.fatPercentage)

        val collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.setTitle(product.name)
        // collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.holo_red_dark));
        collapsingToolbarLayout.setContentScrimColor(android.R.color.background_dark)

        val imageView = findViewById(R.id.backdrop) as ImageView
        Glide.with(this).load(product.imageUrl).centerCrop().into(imageView)
    }

    private fun setValueFor(viewId: Int, value: String) {
        val tvName = findViewById(viewId) as TextView
        tvName.text = value
    }

    private fun loadProductDescription(product: Product) {
        GetProductProductDescription(product).execute()
    }

    internal inner class GetProductProductDescription(private val product: Product) : AsyncTask<Void, Void, ProductDescription>() {

        private val descriptionQuery = ProductDescriptionQuery()

        override fun doInBackground(vararg nothing: Void): ProductDescription {
            try {
                return descriptionQuery.query(product.descriptionUrl)

            } catch (e: Exception) {
                return ProductDescription.empty()
            }
        }

        override fun onPostExecute(description: ProductDescription) {
            setValueFor(R.id.description_label, format("About the %s Variety:", product.variety))
            setValueFor(R.id.p_detail_description, description.value())
        }

    }

    companion object {

        val EXTRA_SUGGESTED_PRODUCT = "com.rafaelfiume.prosciutto.adviser.ShowProductDetail.extra.suggestion"

        fun navigate(callingActivity: Activity, product: Product) {
            callingActivity.startActivity(newIntent(callingActivity, product))

            // TODO RF 06/12/15 This is pretty cool. See https://github.com/codepath/android_guides/wiki/Shared-Element-Activity-Transition
            //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
            //ActivityCompat.startActivity(activity, intent, options.toBundle());
        }

        fun newIntent(context: Context, product: Product): Intent {
            val intent = Intent(context, ShowAdvisedProductDetailsActivity::class.java)
            intent.putExtra(EXTRA_SUGGESTED_PRODUCT, product)
            return intent
        }
    }

}
