package com.example.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    val id: Int,
    val name: String,
    var type: String,
    val desc: String,
    val atk: Int,
    val def: Int,
    val level: Int,
    val race: String,
    val attribute: String,
    @SerializedName("card_sets")
    val cardSets: List<CardSet>,
    @SerializedName("card_images")
    val cardImages: List<CardImage>,
    @SerializedName("card_prices")
    val cardPrices: List<CardPrice>,
    @SerializedName("banlist_info")
    val banListInfo: BanListInfo? = null,
    @SerializedName("misc_info")
    val miscInfo: List<MiscInfo>
) : Parcelable

