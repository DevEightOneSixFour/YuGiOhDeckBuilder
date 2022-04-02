package com.sdbfof.yugiohdeckbuilder.data.model.user

import com.sdbfof.yugiohdeckbuilder.data.model.response.Card
import com.sdbfof.yugiohdeckbuilder.utils.*

data class Deck(
    val thumbnail: String? = null,
    val name: String? = null,
    val deckType: DeckType? = null,
    val cards: List<Card> = mutableListOf(),
    val price: String? = null
) {
    private val priceMap = HashMap<String,Double>()
    fun getDeckPrice() = priceMap
    fun calcDeckPrice(){
        populateMap()
        for (i in cards) {
            priceMap[CARD_MARKET]!!.plus(i.cardPrices[0].cardMarketPrice.toDouble())
            priceMap[COOL_STUFF]!!.plus(i.cardPrices[0].coolStuffIncPrice.toDouble())
            priceMap[TCG_PLAYER]!!.plus(i.cardPrices[0].tcgPlayerPrice.toDouble())
            priceMap[EBAY]!!.plus(i.cardPrices[0].ebayPrice.toDouble())
            priceMap[AMAZON]!!.plus(i.cardPrices[0].amazonPrice.toDouble())
        }
    }
    private fun populateMap() {
        priceMap[CARD_MARKET] = 0.00
        priceMap[COOL_STUFF] = 0.00
        priceMap[TCG_PLAYER] = 0.00
        priceMap[EBAY] = 0.00
        priceMap[AMAZON] = 0.00
    }
}
