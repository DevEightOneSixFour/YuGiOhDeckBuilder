package com.sdbfof.yugiohdeckbuilder.data.model.yuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Yuser(
    val username: String?,
    val email: String?,
    var password: String?,
    var avatar: String? = null,
    val decks: MutableList<Deck?>? = mutableListOf()
): Parcelable
