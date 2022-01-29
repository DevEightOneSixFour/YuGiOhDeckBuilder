package com.example.yugiohdeckbuilder.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YuGiOhResponse(
    val data: List<Card>? = emptyList()
): Parcelable