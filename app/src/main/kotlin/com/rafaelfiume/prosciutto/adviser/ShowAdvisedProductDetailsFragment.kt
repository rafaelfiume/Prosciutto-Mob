package com.rafaelfiume.prosciutto.adviser

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription
import com.rafaelfiume.prosciutto.adviser.integration.ProductDescriptionQuery
import kotlinx.android.synthetic.main.content_show_advised_product_details.view.*
import kotlinx.android.synthetic.main.fragment_show_advised_product_details.view.*

/**
 * Use the [ShowAdvisedProductDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowAdvisedProductDetailsFragment : Fragment() {

    // TODO private lateinit var product: Product
    private var product: Product = Product("", "", "", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            product = arguments.getParcelable(PRODUCT_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_show_advised_product_details, container, false)
        loadScreen(product, view)

        return view
    }

    fun updateProduct(product: Product) {
        loadScreen(product, view!!)
    }

    private fun loadScreen(product: Product, view: View) {
        view.collapsing_toolbar.title = product.name

        Glide.with(this).load(product.imageUrl).centerCrop().into(view.backdrop)

        view.p_detail_name.text = product.name
        view.p_detail_price.text = product.price
        view.p_detail_reputation.text = product.reputation
        view.p_detail_fat.text = product.fatPercentage
        loadProductDescription(product, view)
    }

    private fun loadProductDescription(product: Product, view: View) {
        GetProductDescription(product, view).execute()
    }

    internal inner class GetProductDescription(
            private val product: Product,
            private val view: View) : AsyncTask<Void, Void, ProductDescription>() {

        private val descriptionQuery = ProductDescriptionQuery()

        override fun doInBackground(vararg nothing: Void): ProductDescription {
            try {
                return descriptionQuery.query(product.descriptionUrl)

            } catch (e: Exception) {
                return ProductDescription.empty()
            }
        }

        override fun onPostExecute(description: ProductDescription) {
            view.description_label.text = "About the ${product.variety} Variety:"
            view.p_detail_description.text = description.value
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match  the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val PRODUCT_PARAM = "product_param"

        // TODO: Rename and change types and number of parameters
        fun newInstance(product: Product): ShowAdvisedProductDetailsFragment {
            val fragment = ShowAdvisedProductDetailsFragment()
            val args = Bundle()
            args.putParcelable(PRODUCT_PARAM, product)
            fragment.arguments = args
            return fragment
        }

    }
}
