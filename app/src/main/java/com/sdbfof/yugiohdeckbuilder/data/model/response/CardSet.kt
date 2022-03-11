package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardSet(
    @SerializedName("set_name")
    val setName: String,
    @SerializedName("set_code")
    val setCode: String,
    @SerializedName("set_rarity")
    val setRarity: String,
    @SerializedName("set_rarity_code")
    val setRarityCode: String,
    @SerializedName("set_price")
    val setPrice: String
): Parcelable
