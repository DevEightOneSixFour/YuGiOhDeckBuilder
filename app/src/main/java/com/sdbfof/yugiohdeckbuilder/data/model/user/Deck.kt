package com.sdbfof.yugiohdeckbuilder.data.model.user

import com.sdbfof.yugiohdeckbuilder.data.model.response.Card
import com.sdbfof.yugiohdeckbuilder.utils.DeckType

data class Deck(
    val thumbnail: String? = null,
    val name: String,
    val deckType: DeckType,
    val cards: List<Card>? = emptyList(),
    val price: String? = null
)
