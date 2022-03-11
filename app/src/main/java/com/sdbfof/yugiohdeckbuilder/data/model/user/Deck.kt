package com.sdbfof.yugiohdeckbuilder.data.model.user

import com.sdbfof.yugiohdeckbuilder.data.model.response.Card

data class Deck(
    val thumbnail: String,
    val name: String,
    val cards: List<Card>,
    val price: String
)
