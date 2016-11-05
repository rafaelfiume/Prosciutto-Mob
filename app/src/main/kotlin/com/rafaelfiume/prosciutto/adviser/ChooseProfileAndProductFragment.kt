package com.rafaelfiume.prosciutto.adviser

import android.app.Fragment
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_INDEFINITE
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioGroup
import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery
import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.*
import java.util.*

private const val LIST_OF_RECOMMENDED_PRODUCTS = "recommended_products"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChooseProfileAndProductFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChooseProfileAndProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseProfileAndProductFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    lateinit private var query: ProductAdviserQuery

    lateinit private var adapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_profile_and_product, container, false)

        val listView = view.findViewById(R.id.suggested_products_list) as ListView
        this.adapter = ProductAdapter(this.activity, OnSuggestedProductClickListenerFactory(this.activity))
        listView.adapter = this.adapter

        val fab = view.findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { GetProductAdvice(view).execute() }

        val profileOptions = view.findViewById(R.id.profile_options) as RadioGroup
        profileOptions.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.magic_option -> this.query = MAGIC
                R.id.healthy_option -> this.query = HEALTHY
                R.id.expert_option -> this.query = EXPERT
                R.id.gourmet_option -> this.query = GOURMET
            }
        }

        setMagicOptionSelectedByDefault(view)

        return view
    }

     override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putParcelableArrayList(LIST_OF_RECOMMENDED_PRODUCTS, adapter.content())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) { // equivalent to Activity#onRestoreInstanceState
        super.onActivityCreated(savedInstanceState)

        val parcelable = savedInstanceState?.getParcelableArrayList<Product>(LIST_OF_RECOMMENDED_PRODUCTS)
        if (parcelable != null) {
            this.adapter.addAll(parcelable)
        }
    }

    private fun setMagicOptionSelectedByDefault(v: View) {
        val options = v.findViewById(R.id.profile_options) as RadioGroup
        options.check(R.id.magic_option)

        this.query = MAGIC
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    internal inner class GetProductAdvice(view: View) : AsyncTask<Void, Void, List<Product>>() {

        private val requestingAdviceMessage: Snackbar
        private val failedMessage: Snackbar

        private var taskFailed = false

        init {
            this.requestingAdviceMessage = Snackbar
                    .make(view.findViewById(R.id.fab), "Asking for advice...", LENGTH_INDEFINITE)

            this.failedMessage = Snackbar
                    .make(view.findViewById(R.id.fab), "Failed", LENGTH_LONG)
                    .setAction("Retry") { GetProductAdvice(view).execute() }
        }

        override fun onPreExecute() {
            cleanSuggestedProductsList()
            requestingAdviceMessage.show()
        }

        override fun doInBackground(vararg nothing: Void): List<Product> {
            try {
                return query.suggestedProducts()

            } catch (e: Exception) {
                Log.e(AdviserActivity::class.java.name, "error when querying salume supplier", e)
                this.taskFailed = true
                return ArrayList()
            }
        }

        override fun onPostExecute(products: List<Product>) {
            requestingAdviceMessage.dismiss()
            updateSuggestedProductsList(products)
            if (taskFailed) {
                failedMessage.show()
            }
        }

        private fun updateSuggestedProductsList(products: List<Product>) {
            cleanSuggestedProductsList()
            adapter.addAll(products)
        }

        private fun cleanSuggestedProductsList() {
            adapter.clear()
        }
    }

    companion object {

        fun newInstance() = ChooseProfileAndProductFragment()
    }
}
