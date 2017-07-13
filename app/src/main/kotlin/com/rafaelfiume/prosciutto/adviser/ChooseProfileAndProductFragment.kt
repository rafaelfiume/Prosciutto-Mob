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
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View.OnClickListener

private const val LIST_OF_RECOMMENDED_PRODUCTS = "recommended_products"

/**
 * Use the [ChooseProfileAndProductFragment.newInstanceForSmallScreen] factory method to
 * create an instance of this fragment.
 */
class ChooseProfileAndProductFragment() : Fragment() {

    interface OnProductSelectedListener {
        fun onSelected(product: Product)
    }

    private lateinit var chooseFragmentTag: String
    private var paneMode: PaneMode = PaneMode(single = false)
    private var productSelectedListener: OnProductSelectedListener? = null
    private var adapter: ProductAdapter? = null
    private lateinit var query: ProductAdviserQuery

    constructor(paneMode: PaneMode) : this() {
        this.paneMode = paneMode
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        setUpProductSelectedListener(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_profile_and_product, container, false)

        this.chooseFragmentTag = resources.getString(R.string.tag_choose_fragment)

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
    }

    fun fetchSalumes() {
        GetProductAdvice(getOnFetchContentListener()).execute()
    }

    private fun setUpProductSelectedListener(context: Context) {
        this.productSelectedListener =
                context as? OnProductSelectedListener
                        ?: throw ClassCastException(context.toString() + " must implement OnProductSelectedListener")
    }

    private fun setMagicOptionSelectedByDefault(v: View) {
        val options = v.findViewById(R.id.profile_options) as RadioGroup
        options.check(R.id.magic_option)
        this.query = MAGIC
    }

    private fun getOnFetchContentListener(): OnFetchingContentListener {
        if (paneMode.single) { // small screen
            val snackbarView = view!!.findViewById(R.id.coordinator_layout)
            return OnFetchingContentListener(snackbarView, OnClickListener { fetchSalumes() })

        } else {
            return (context as? AdviserActivity)?.listener()
                    ?: throw ClassCastException(context.toString() + " must implement OnFetchingContentListener")
        }
    }

    inner class GetProductAdvice(val listener: OnFetchingContentListener) : AsyncTask<Void, Void, List<Product>>() {

        private var taskFailed = false

        override fun onPreExecute() {
            cleanSuggestedProductsList()
            listener.onStarted()
        }

        override fun doInBackground(vararg nothing: Void): List<Product> = try {
            query.suggestedProducts()

        } catch (e: Exception) {
            Log.e(chooseFragmentTag, "error when querying salume supplier", e)
            this.taskFailed = true
            ArrayList()
        }

        override fun onPostExecute(products: List<Product>) {
            listener.onCompleted()
            updateSuggestedProductsList(products)

            if (taskFailed) {
                listener.onFailure()
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

        fun newInstanceForSmallScreen() = ChooseProfileAndProductFragment(PaneMode(single = true))
    }
}
