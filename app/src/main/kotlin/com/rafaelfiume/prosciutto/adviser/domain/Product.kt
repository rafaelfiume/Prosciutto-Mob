package com.rafaelfiume.prosciutto.adviser.domain

import android.os.Parcel
import android.os.Parcelable

class Product : Parcelable {

    private val name: String
    private val price: String
    private val reputation: String
    private val fatPercentage: String
    private val variety: String
    private val imageUrl: String
    private val descriptionUrl: String

    constructor(name: String,
                variety: String,
                price: String,
                reputation: String,
                fatPercentage: String,
                imageUrl: String,
                descriptionUrl: String) {
        this.name = name
        this.variety = variety
        this.price = price
        this.reputation = reputation
        this.fatPercentage = fatPercentage
        this.imageUrl = imageUrl
        this.descriptionUrl = descriptionUrl
    }

    private constructor(`in`: Parcel) {
        this.name = `in`.readString()
        this.variety = `in`.readString()
        this.price = `in`.readString()
        this.reputation = `in`.readString()
        this.fatPercentage = `in`.readString()
        this.imageUrl = `in`.readString()
        this.descriptionUrl = `in`.readString()
    }

    fun name(): String {
        return name
    }

    fun variety(): String {
        return variety
    }

    fun price(): String {
        return price
    }

    fun reputation(): String {
        return reputation
    }

    fun fatPercentage(): String {
        return fatPercentage
    }

    fun imageUrl(): String {
        return imageUrl
    }

    fun descriptionUrl(): String {
        return descriptionUrl
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(this.name)
        out.writeString(this.variety)
        out.writeString(this.price)
        out.writeString(this.reputation)
        out.writeString(this.fatPercentage)
        out.writeString(this.imageUrl)
        out.writeString(this.descriptionUrl)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Product> = object : Parcelable.Creator<Product> {
            override fun createFromParcel(`in`: Parcel): Product {
                return Product(`in`)
            }

            override fun newArray(size: Int): Array<Product?> {
                return arrayOfNulls(size)
            }
        }
    }
}
