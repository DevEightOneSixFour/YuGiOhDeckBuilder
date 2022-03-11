package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BanListInfo(
    @SerializedName("ban_tcg")
    val banTCG: String,
    @SerializedName("ban_ocg")
    val banOCG: String,
    @SerializedName("ban_goat")
    val banGOAT: String
) : Parcelable
