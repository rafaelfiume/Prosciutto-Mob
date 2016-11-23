package com.rafaelfiume.prosciutto.adviser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rafaelfiume.prosciutto.adviser.domain.Product

private const val EXTRA_SUGGESTED_PRODUCT = "com.rafaelfiume.prosciutto.adviser.ShowProductDetail.extra.suggestion"

class ShowAdvisedProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_advised_product_details)

        // If we're being restored from a previous state, then we don't need to do anything and should return
        // or else we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return
        }

        val product = intent.getParcelableExtra<Product>(EXTRA_SUGGESTED_PRODUCT)
        val fragment = ShowAdvisedProductDetailsFragment.newInstance(product)
        fragmentManager.beginTransaction()
                .add(R.id.show_advised_product_fragment_container, fragment).commit()
    }

    companion object {

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
