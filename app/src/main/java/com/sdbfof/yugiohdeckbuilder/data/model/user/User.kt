package com.sdbfof.yugiohdeckbuilder.data.model.user

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val avatar: String,
    val decks: List<Deck>
)
