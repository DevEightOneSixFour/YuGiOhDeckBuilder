package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    val id: Int? = 0,
    val name: String? = null,
    var type: String? = null,
    val desc: String? = null,
    val atk: Int? = 0,
    val def: Int? = 0,
    val level: Int? = 0,
    val race: String? = null,
    val attribute: String? = null,
    @SerializedName("card_sets")
    val cardSets: List<CardSet> = emptyList(),
    @SerializedName("card_images")
    val cardImages: List<CardImage> = emptyList(),
    @SerializedName("card_prices")
    val cardPrices: List<CardPrice> = emptyList(),
    @SerializedName("banlist_info")
    val banListInfo: BanListInfo? = null,
    @SerializedName("misc_info")
    val miscInfo: List<MiscInfo> = emptyList()
) : Parcelable

