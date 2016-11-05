package com.rafaelfiume.prosciutto.adviser

import android.app.Fragment
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
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShowAdvisedProductDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShowAdvisedProductDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowAdvisedProductDetailsFragment : Fragment() {

    private lateinit var product: Product

    // TODO private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            product = arguments.getParcelable(PRODUCT_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_show_advised_product_details, container, false)

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        val appCompatActivity = (activity!! as AppCompatActivity) // this smells like... smell
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        loadProductDescription(product, view)
        setValueFor(R.id.p_detail_name, product.name, view)
        setValueFor(R.id.p_detail_price, product.price, view)
        setValueFor(R.id.p_detail_reputation, product.reputation, view)
        setValueFor(R.id.p_detail_fat, product.fatPercentage, view)

        val collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = product.name
        // collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.holo_red_dark));
        collapsingToolbarLayout.setContentScrimColor(android.R.color.background_dark)

        val imageView = view.findViewById(R.id.backdrop) as ImageView
        Glide.with(this).load(product.imageUrl).centerCrop().into(imageView)

        return view
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(activity) // TODO
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        mListener = null
//    }

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



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }

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
