package com.example.assignment.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class AllTimeHigh(): Parcelable {
    @SerializedName("price")
    var price: String? = null

    @SerializedName("timestamp")
    var timestamp: Long? = null

    constructor(parcel: Parcel) : this() {
        price = parcel.readString()
        timestamp = parcel.readSerializable() as Long?
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(price)
        parcel.writeValue(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AllTimeHigh> {
        override fun createFromParcel(parcel: Parcel): AllTimeHigh {
            return AllTimeHigh(parcel)
        }

        override fun newArray(size: Int): Array<AllTimeHigh?> {
            return arrayOfNulls(size)
        }
    }
}