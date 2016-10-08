package com.rafaelfiume.prosciutto.adviser.domain

import android.os.Parcel
import android.os.Parcelable

data class Product(val name: String,
                   val variety: String,
                   val price: String,
                   val reputation: String,
                   val fatPercentage: String,
                   val imageUrl: String,
                   val descriptionUrl: String): Parcelable {

    private constructor(`in`: Parcel) : this(
            `in`.readString(),
            `in`.readString(),
            `in`.readString(),
            `in`.readString(),
            `in`.readString(),
            `in`.readString(),
            `in`.readString()){
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
