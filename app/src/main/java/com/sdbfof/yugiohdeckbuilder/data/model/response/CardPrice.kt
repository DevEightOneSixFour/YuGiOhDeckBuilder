package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardPrice(
    @SerializedName("cardmarket_price")
    val cardMarketPrice: String,
    @SerializedName("tcgplayer_price")
    val tcgPlayerPrice: String,
    @SerializedName("ebay_price")
    val ebayPrice: String,
    @SerializedName("amazon_price")
    val amazonPrice: String,
    @SerializedName("coolstuffinc_price")
    val coolStuffIncPrice: String
): Parcelable
