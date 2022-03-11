package com.sdbfof.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardImage(
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("image_url_small")
    val imageUrlSmall: String
): Parcelable
