package com.rafaelfiume.prosciutto.adviser

import android.os.Parcel
import android.os.Parcelable

data class PaneMode(var single: Boolean) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<PaneMode> = object : Parcelable.Creator<PaneMode> {
            override fun createFromParcel(source: Parcel): PaneMode = PaneMode(source)
            override fun newArray(size: Int): Array<PaneMode?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt((if (single) 1 else 0))
    }
}