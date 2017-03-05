package com.rafaelfiume.prosciutto.adviser

import android.support.v4.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.domain.ProductDescription
import com.rafaelfiume.prosciutto.adviser.integration.ProductDescriptionQuery

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
        val view = activity.findViewById(R.id.fragment_show_advised_product_details)
        loadScreen(product, view)
    }

    private fun loadScreen(product: Product, view: View) {
        val collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = product.name

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        val appCompatActivity = (activity!! as AppCompatActivity) // this smells like... smell
        appCompatActivity.setSupportActionBar(toolbar)
        //appCompatActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true) // TODO

        val imageView = view.findViewById(R.id.backdrop) as ImageView
        Glide.with(this).load(product.imageUrl).centerCrop().into(imageView)

        setValueFor(R.id.p_detail_name, product.name, view)
        setValueFor(R.id.p_detail_price, product.price, view)
        setValueFor(R.id.p_detail_reputation, product.reputation, view)
        setValueFor(R.id.p_detail_fat, product.fatPercentage, view)
        loadProductDescription(product, view)
    }

    private fun loadProductDescription(product: Product, view: View) {
        GetProductDescription(product, view).execute()
    }

    private fun setValueFor(viewId: Int, value: String, view: View) {
        val tvName = view.findViewById(viewId) as TextView
        tvName.text = value
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
            setValueFor(R.id.description_label, "About the ${product.variety} Variety:", view)
            setValueFor(R.id.p_detail_description, description.value, view)
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
