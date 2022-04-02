package com.sdbfof.yugiohdeckbuilder.data.model.user

data class Yuser(
    val username: String?,
    val email: String?,
    var password: String?,
    var avatar: String? = null,
    val decks: MutableList<Deck?>? = mutableListOf()
)
