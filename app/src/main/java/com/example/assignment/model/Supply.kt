package com.example.assignment.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Supply(): Parcelable {
    @SerializedName("confirmed")
    var confirmed: Boolean? = null

    @SerializedName("total")
    var total: String? = null

    @SerializedName("circulating")
    var circulating: String? = null

    constructor(parcel: Parcel) : this() {
        confirmed = parcel.readSerializable() as Boolean?
        total = parcel.readString()
        circulating = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(confirmed)
        parcel.writeString(total)
        parcel.writeString(circulating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Supply> {
        override fun createFromParcel(parcel: Parcel): Supply {
            return Supply(parcel)
        }

        override fun newArray(size: Int): Array<Supply?> {
            return arrayOfNulls(size)
        }
    }
}