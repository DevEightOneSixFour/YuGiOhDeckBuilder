package com.sdbfof.yugiohdeckbuilder.data.model.user

data class Yuser(
    val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String? = null,
    val decks: List<Deck> = emptyList(),
)
