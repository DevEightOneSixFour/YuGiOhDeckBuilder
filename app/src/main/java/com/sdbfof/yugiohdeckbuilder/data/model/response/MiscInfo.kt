package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MiscInfo(
    val views: Int,
    @SerializedName("viewsweek")
    val viewsWeek: Int,
    val upvotes: Int,
    val downvotes: Int,
    val formats: List<String>,
    @SerializedName("tcg_date")
    val tcgDate: String,
    @SerializedName("ocg_date")
    val ocgDate: String,
    @SerializedName("konami_id")
    val konamiId: Int,
    @SerializedName("has_effect")
    val hasEffect: Int
): Parcelable
