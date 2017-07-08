package com.rafaelfiume.prosciutto.adviser

import android.support.v4.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
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
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

private const val LIST_OF_RECOMMENDED_PRODUCTS = "recommended_products"

/**
 * Use the [ChooseProfileAndProductFragment.newInstanceForSinglePaneMode] factory method to
 * create an instance of this fragment.
 */
class ChooseProfileAndProductFragment() : Fragment() {

    private var paneMode: PaneMode = PaneMode(single = false)

    constructor(paneMode: PaneMode) : this() {
        this.paneMode = paneMode
    }

    interface OnProductSelectedListener {
        fun onFragmentInteraction(product: Product)
    }

    interface OnFetchingProductsListener {
        fun onStarted()
        fun onCompleted()
        fun onFailure()
    }

    private var productSelectedListener: OnProductSelectedListener? = null
    private var fetchingProductsListener: OnFetchingProductsListener? = null
    private var adapter: ProductAdapter? = null
    lateinit private var query: ProductAdviserQuery

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO 05/07/2017 : Issue #11 : move this two ugly checks to a unit test
        ensureActivityHasImplementedProductSelectListenerInterface(context)
        ensureActivityHasImplementedFetchingProductsListenerInterface(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_profile_and_product, container, false)

        val listView = view.findViewById(R.id.suggested_products_list) as ListView
        if (this.adapter == null) {
            this.adapter = ProductAdapter(this.activity, OnSuggestedProductClickListenerFactory(this.productSelectedListener!!))
        }
        listView.adapter = this.adapter

        val profileOptions = view.findViewById(R.id.profile_options) as RadioGroup
        profileOptions.setOnCheckedChangeListener { _, checkedId ->
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
        savedInstanceState.putParcelable("pane_mode", paneMode)
        savedInstanceState.putParcelableArrayList(LIST_OF_RECOMMENDED_PRODUCTS, adapter!!.content())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val savedListOfProducts = savedInstanceState?.getParcelableArrayList<Product>(LIST_OF_RECOMMENDED_PRODUCTS)
        if (savedListOfProducts != null) {
            this.adapter!!.addAll(savedListOfProducts)
        }
        val paneMode = savedInstanceState?.getParcelable<PaneMode>("pane_mode")
        if (paneMode != null) {
            this.paneMode = paneMode
        }

        // adjustSinglePaneModeLayout
        if (this.paneMode.single) {
            val toolbar = view!!.findViewById(R.id.main_toolbar) as Toolbar
            val appCompatActivity = (activity!! as AppCompatActivity) // this smells like... smell
            appCompatActivity.setSupportActionBar(toolbar)
            toolbar.setTitle(R.string.main_toolbar_title)

            val fab = view!!.findViewById(R.id.fab) as FloatingActionButton
            fab.setOnClickListener { fetchSalumes() }
        }
    }

    override fun onDetach() {
        super.onDetach()
        productSelectedListener = null
        fetchingProductsListener = null
    }

    fun fetchSalumes() {
        GetProductAdvice(fetchingProductsListener).execute()
    }

    private fun ensureActivityHasImplementedProductSelectListenerInterface(context: Context) {
        try {
            productSelectedListener = context as OnProductSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnProductSelectedListener")
        }
    }

    private fun ensureActivityHasImplementedFetchingProductsListenerInterface(context: Context) {
        try {
            fetchingProductsListener = context as OnFetchingProductsListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnFetchingProductsListener")
        }
    }

    private fun setMagicOptionSelectedByDefault(v: View) {
        val options = v.findViewById(R.id.profile_options) as RadioGroup
        options.check(R.id.magic_option)
        this.query = MAGIC
    }

    inner class GetProductAdvice(val fetchingProductsListener: OnFetchingProductsListener?) : AsyncTask<Void, Void, List<Product>>() {

        private var taskFailed = false

        private val requestingAdviceMessage: Snackbar?
        private val failedMessage: Snackbar?

        init {
            // initialise sanackbars if in single pane mode
            if (paneMode.single) {
                this.requestingAdviceMessage =
                        Snackbar.make(view!!.findViewById(R.id.coordinator_layout), "Asking for advice...", Snackbar.LENGTH_INDEFINITE)
                this.failedMessage = Snackbar
                        .make(view!!.findViewById(R.id.coordinator_layout), "Failed", Snackbar.LENGTH_LONG)
                        .setAction("Retry") { fetchSalumes() }
            }
            else {
                this.requestingAdviceMessage = null
                this.failedMessage = null
            }
        }

        override fun onPreExecute() {
            cleanSuggestedProductsList()
            if (paneMode.single) requestingAdviceMessage!!.show() else fetchingProductsListener?.onStarted()
        }

        override fun doInBackground(vararg nothing: Void): List<Product> {
            try {
                return query.suggestedProducts()

            } catch (e: Exception) {
                Log.e("CHOOSE_PROF_PROD_FRAG", "error when querying salume supplier", e)
                this.taskFailed = true
                return ArrayList()
            }
        }

        override fun onPostExecute(products: List<Product>) {
            if (paneMode.single) requestingAdviceMessage!!.dismiss() else fetchingProductsListener?.onCompleted()
            updateSuggestedProductsList(products)
            if (taskFailed) {
                if (paneMode.single) failedMessage!!.show() else fetchingProductsListener?.onFailure()
            }
        }

        private fun updateSuggestedProductsList(products: List<Product>) {
            cleanSuggestedProductsList()
            adapter!!.addAll(products)
        }

        private fun cleanSuggestedProductsList() {
            adapter!!.clear()
        }
    }

    companion object {

        fun newInstanceForSinglePaneMode() = ChooseProfileAndProductFragment(PaneMode(single = true))
    }
}
