package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardSet(
    @SerializedName("set_name")
    val setName: String? = null,
    @SerializedName("set_code")
    val setCode: String? = null,
    @SerializedName("set_rarity")
    val setRarity: String? = null,
    @SerializedName("set_rarity_code")
    val setRarityCode: String? = null,
    @SerializedName("set_price")
    val setPrice: String? = null
): Parcelable
