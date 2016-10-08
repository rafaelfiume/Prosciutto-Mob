package com.rafaelfiume.prosciutto.adviser

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.RadioGroup

import com.rafaelfiume.prosciutto.adviser.domain.Product
import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery

import java.util.ArrayList

import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.EXPERT
import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.GOURMET
import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.HEALTHY
import com.rafaelfiume.prosciutto.adviser.integration.ProductAdviserQuery.MAGIC

class AdviserActivity : AppCompatActivity() {

    private var query: ProductAdviserQuery? = null

    private var adapter: ProductAdapter? = null

    fun onMagicRadioButtonClicked(v: View) {
        this.query = MAGIC
    }

    fun onHealthyRadioButtonClicked(v: View) {
        this.query = HEALTHY
    }

    fun onExpertRadioButtonClicked(v: View) {
        this.query = EXPERT
    }

    fun onGourmetRadioButtonClicked(v: View) {
        this.query = GOURMET
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adviser)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setTitle(R.string.activity_title)

        val listView = findViewById(R.id.suggested_products_list) as ListView
        this.adapter = ProductAdapter(this, OnSuggestedProductClickListenerFactory(this))
        listView.adapter = this.adapter

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { GetProductAdvice().execute() }

        setMagicOptionSelectedByDefault()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_adviser, menu)
        return true
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putParcelableArrayList(LIST_OF_RECOMMENDED_PRODUCTS, adapter!!.content())
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        this.adapter!!.addAll(savedInstanceState.getParcelableArrayList<Product>(LIST_OF_RECOMMENDED_PRODUCTS)!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setMagicOptionSelectedByDefault() {
        val options = findViewById(R.id.profile_options) as RadioGroup
        options.check(R.id.magic_option)

        this.query = MAGIC
    }

    internal inner class GetProductAdvice : AsyncTask<Void, Void, List<Product>>() {

        private val requestingAdviceMessage: Snackbar
        private val failedMessage: Snackbar

        private var taskFailed = false

        init {
            this.requestingAdviceMessage = Snackbar.make(findViewById(R.id.fab), "Asking for advice...", Snackbar.LENGTH_INDEFINITE)
            this.failedMessage = Snackbar.make(findViewById(R.id.fab), "Failed", Snackbar.LENGTH_LONG).setAction("Retry") { GetProductAdvice().execute() } // display option for user to retry
        }

        override fun onPreExecute() {
            cleanSuggestedProductsList()
            requestingAdviceMessage.show()
        }

        override fun doInBackground(vararg nothing: Void): List<Product> {
            try {
                return query!!.suggestedProducts()

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
            adapter!!.addAll(products)
        }

        private fun cleanSuggestedProductsList() {
            adapter!!.clear()
        }
    }

    companion object {

        private val LIST_OF_RECOMMENDED_PRODUCTS = "recommended_products"
    }

}
