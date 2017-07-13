package com.rafaelfiume.prosciutto.adviser

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.rafaelfiume.prosciutto.adviser.ChooseProfileAndProductFragment.OnProductSelectedListener
import com.rafaelfiume.prosciutto.adviser.domain.Product

class AdviserActivity : AppCompatActivity(), OnProductSelectedListener {

    private lateinit var chooseFragmentTag: String
    private lateinit var listener: OnFetchingContentListener

    override fun onSelected(product: Product) {
        val isLargeScreen = whenInLargeScreenShowDetailsOf(product)

        if (!isLargeScreen) {
            goToDetailsScreenAndShow(product)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        this.chooseFragmentTag = resources.getString(R.string.tag_choose_fragment)

        if (isSmallScreen()) {
            if (areWeBeingRestoredFromPrevious(savedInstanceState)) {
                return // do nothing or else we could end up with overlapping fragments
            }
            showChooseProfileAndProductScreen()

        } else { // large screen
            configureLargeScreenLayout()
        }
    }

    private fun areWeBeingRestoredFromPrevious(savedInstanceState: Bundle?) = savedInstanceState != null

    private fun isSmallScreen() = findViewById(R.id.fragment_container) != null

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

    fun listener() = this.listener

    private fun whenInLargeScreenShowDetailsOf(product: Product): Boolean {
        val showDetailsFrag = supportFragmentManager.findFragmentById(R.id.fragment_show_advised_product_details)
        if (showDetailsFrag is ShowAdvisedProductDetailsFragment) { // If frag is available, we're in two-pane layout...
            showDetailsFrag.updateProduct(product)
            return true
        }
        return false
    }

    private fun goToDetailsScreenAndShow(product: Product) {
        val newFragment = ShowAdvisedProductDetailsFragment.newInstance(product)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()
    }

    private fun showChooseProfileAndProductScreen() {
        val fragment = ChooseProfileAndProductFragment.newInstanceForSmallScreen()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, chooseFragmentTag)
                .commit()
    }

    private fun configureLargeScreenLayout() {
        val toolbar = findViewById(R.id.main_toolbar) as Toolbar
        this.setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.main_toolbar_title)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { FetchSalumesAction().perform() }

        val snackbarView = findViewById(R.id.coordinator_layout)
        this.listener = OnFetchingContentListener(snackbarView, View.OnClickListener { FetchSalumesAction().perform() })
    }

    inner class FetchSalumesAction {
        fun perform() {
            val frag = supportFragmentManager.findFragmentByTag(chooseFragmentTag) as ChooseProfileAndProductFragment
            frag.fetchSalumes()
        }

    }
}
