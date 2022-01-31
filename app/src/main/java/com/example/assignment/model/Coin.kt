package com.example.assignment.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Coin(): Parcelable {
    @SerializedName("uuid")
    var uuid: String? = null

    @SerializedName("symbol")
    var symbol: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("color")
    var color: String? = null

    @SerializedName("iconUrl")
    var iconUrl: String? = null

    @SerializedName("marketCap")
    var marketCap: String? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("change")
    var change: String? = null

    @SerializedName("coinrankingUrl")
    var coinrankingUrl: String? = null

    @SerializedName("24hVolume")
    var `24hVolume`: String? = null

    @SerializedName("btcPrice")
    var btcPrice: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("websiteUrl")
    var websiteUrl: String? = null

    @SerializedName("rank")
    var rank: Int? = null

    @SerializedName("tier")
    var tier: Int? = null

    @SerializedName("numberOfMarkets")
    var numberOfMarkets: Int? = null

    @SerializedName("numberOfExchanges")
    var numberOfExchanges: Int? = null

    @SerializedName("priceAt")
    var priceAt: Long? = null

    @SerializedName("listedAt")
    var listedAt: Long? = null

    @SerializedName("lowVolume")
    var lowVolume: Boolean? = null

    @SerializedName("supply")
    var supply: Supply? = null

    @SerializedName("allTimeHigh")
    var allTimeHigh: AllTimeHigh? = null

    @SerializedName("links")
    var links: List<Link>? = null

    @SerializedName("sparkline")
    var sparkline: List<String>? = null

    var type: Int = 0

    constructor(parcel: Parcel) : this() {
        uuid = parcel.readString()
        symbol = parcel.readString()
        name = parcel.readString()
        color = parcel.readString()
        iconUrl = parcel.readString()
        marketCap = parcel.readString()
        price = parcel.readString()
        change = parcel.readString()
        coinrankingUrl = parcel.readString()
        `24hVolume` = parcel.readString()
        btcPrice = parcel.readString()
        description = parcel.readString()
        websiteUrl = parcel.readString()
        rank = parcel.readInt()
        tier = parcel.readInt()
        numberOfMarkets = parcel.readInt()
        numberOfExchanges = parcel.readInt()
        lowVolume = parcel.readSerializable() as Boolean?
        supply = parcel.readSerializable() as Supply?
        allTimeHigh = parcel.readSerializable() as AllTimeHigh?
        priceAt = parcel.readSerializable() as Long?
        listedAt = parcel.readSerializable() as Long?
        sparkline = arrayListOf<String>().apply {
            parcel.readArrayList(String::class.java.classLoader)
        }
        links = arrayListOf<Link>().apply {
            parcel.readArrayList(Link::class.java.classLoader)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(uuid)
        dest?.writeString(name)
        dest?.writeString(symbol)
        dest?.writeString(color)
        dest?.writeString(iconUrl)
        dest?.writeString(marketCap)
        dest?.writeString(price)
        dest?.writeString(change)
        dest?.writeString(coinrankingUrl)
        dest?.writeString(`24hVolume`)
        dest?.writeString(btcPrice)
        dest?.writeString(description)
        dest?.writeString(websiteUrl)
        dest?.writeInt(rank!!)
        dest?.writeInt(tier!!)
        dest?.writeInt(numberOfMarkets!!)
        dest?.writeInt(numberOfExchanges!!)
        dest?.writeValue(lowVolume)
        dest?.writeValue(supply)
        dest?.writeValue(allTimeHigh)
        dest?.writeValue(priceAt)
        dest?.writeValue(listedAt)
        dest?.writeList(sparkline)

        if (Build.VERSION.SDK_INT >= 29) {
            dest?.writeParcelableList(links,flags)
        } else {
            dest?.writeList(links as List<Link>)
        }
    }

    companion object CREATOR: Parcelable.Creator<Coin> {
        override fun createFromParcel(source: Parcel): Coin {
            return Coin(source)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }
}