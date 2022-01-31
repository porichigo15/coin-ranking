package com.example.assignment.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Stats(): Parcelable {
    @SerializedName("total")
    var total: Int = 0

    @SerializedName("totalCoins")
    var totalCoins: Int = 0

    @SerializedName("totalMarkets")
    var totalMarkets: Int = 0

    @SerializedName("totalExchanges")
    var totalExchanges: Int = 0

    @SerializedName("totalMarketCap")
    var totalMarketCap: String? = null

    @SerializedName("total24hVolume")
    var total24hVolume: String? = null

    constructor(parcel: Parcel) : this() {
        total = parcel.readInt()
        totalCoins = parcel.readInt()
        totalMarkets = parcel.readInt()
        totalExchanges = parcel.readInt()
        totalMarketCap = parcel.readString()
        total24hVolume = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(total)
        parcel.writeValue(totalCoins)
        parcel.writeValue(totalMarkets)
        parcel.writeValue(totalExchanges)
        parcel.writeString(totalMarketCap)
        parcel.writeString(total24hVolume)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stats> {
        override fun createFromParcel(parcel: Parcel): Stats {
            return Stats(parcel)
        }

        override fun newArray(size: Int): Array<Stats?> {
            return arrayOfNulls(size)
        }
    }
}