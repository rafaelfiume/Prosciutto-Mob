package com.rafaelfiume.prosciutto.adviser

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.rafaelfiume.prosciutto.adviser.domain.Product

import java.util.ArrayList

open class ProductAdapter(context: Context, private val factory: OnSuggestedProductClickListenerFactory) : ArrayAdapter<Product>(context, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)

        val product = getItem(position)
        val tvName = itemView.findViewById(R.id.product_name_text) as TextView
        tvName.text = product!!.name
        val tvPrice = itemView.findViewById(R.id.product_price_text) as TextView
        tvPrice.text = product.price

        itemView.setOnClickListener(factory.newOnClickListenerFor(product))

        return itemView
    }

    internal fun content(): ArrayList<out Parcelable> {
        // Annoying not having a similar functionality returning a copy of the product list :(
        // Android, you forced me to do that!!
        val copy = ArrayList<Product>()
        for (i in 0..count - 1) {
            copy.add(getItem(i))
        }
        return copy
    }
}
