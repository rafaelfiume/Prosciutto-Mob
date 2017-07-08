package com.rafaelfiume.prosciutto.adviser

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.rafaelfiume.prosciutto.adviser.ChooseProfileAndProductFragment.OnFetchingProductsListener
import com.rafaelfiume.prosciutto.adviser.ChooseProfileAndProductFragment.OnProductSelectedListener
import com.rafaelfiume.prosciutto.adviser.domain.Product

class AdviserActivity : AppCompatActivity(), OnProductSelectedListener, OnFetchingProductsListener {

    private var requestingAdviceMessage: Snackbar? = null
    private var failedMessage: Snackbar? = null

    override fun onFragmentInteraction(product: Product) {
        val showDetailsFrag = supportFragmentManager.findFragmentById(R.id.fragment_show_advised_product_details)

        if (showDetailsFrag is ShowAdvisedProductDetailsFragment) { // If article frag is available, we're in two-pane layout...
            showDetailsFrag.updateProduct(product)

        } else { // Here we're in the one-pane layout and must swap frags...
            val newFragment = ShowAdvisedProductDetailsFragment.newInstance(product)
            supportFragmentManager
                    .beginTransaction()
                        .replace(R.id.fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onStarted()   { requestingAdviceMessage?.show()    }
    override fun onCompleted() { requestingAdviceMessage?.dismiss() }
    override fun onFailure()   { failedMessage?.show()              }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        if (findViewById(R.id.fragment_container) != null) { // Check to see it we are in a small screen
            // However, if we're being restored from a previous state,
            // then we don't need to do anything or else we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return
            }
            supportFragmentManager
                    .beginTransaction()
                        .add(R.id.fragment_container, ChooseProfileAndProductFragment.newInstanceForSinglePaneMode(), "CHOOSE_PROF_PROD_FRAG")
                    .commit()

        } else { // Configure the toolbar and other stuff ONLY if in dual pane mode
            val toolbar = findViewById(R.id.main_toolbar) as Toolbar
            this.setSupportActionBar(toolbar)
            toolbar.setTitle(R.string.main_toolbar_title)

            val fab = findViewById(R.id.fab) as FloatingActionButton
            fab.setOnClickListener { FetchSalumesAction().perform() }

            this.requestingAdviceMessage = Snackbar
                    .make(findViewById(R.id.coordinator_layout), "Asking for advice...", Snackbar.LENGTH_INDEFINITE)

            this.failedMessage = Snackbar
                    .make(findViewById(R.id.coordinator_layout), "Failed", Snackbar.LENGTH_LONG)
                    .setAction("Retry") { FetchSalumesAction().perform() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_adviser, menu)
        return true
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

    inner class FetchSalumesAction {
        fun perform() {
            val frag = supportFragmentManager.findFragmentByTag("CHOOSE_PROF_PROD_FRAG") as ChooseProfileAndProductFragment
            frag.fetchSalumes()
        }

    }
}
