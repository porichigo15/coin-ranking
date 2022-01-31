package com.example.assignment.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Link(): Parcelable {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("type")
    var type: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        url = parcel.readString()
        type = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Link> {
        override fun createFromParcel(parcel: Parcel): Link {
            return Link(parcel)
        }

        override fun newArray(size: Int): Array<Link?> {
            return arrayOfNulls(size)
        }
    }
}