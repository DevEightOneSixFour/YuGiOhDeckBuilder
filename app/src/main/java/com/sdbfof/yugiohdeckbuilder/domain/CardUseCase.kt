package com.sdbfof.yugiohdeckbuilder.domain

import com.sdbfof.yugiohdeckbuilder.data.model.states.YUIState
import kotlinx.coroutines.flow.Flow

class CardUseCase(private val repository: CardRepository) {

    suspend fun getCards(
        name: String? = null,
        archetype: String? = null,
        level: String? = null,
        attribute: String? = null,
        sort: String? = null,
        banList: String? = null,
        cardSet: String? = null,
        fName: String? = null,
        type: String? = null,
        race: String? = null,
        format: String? = null,
        linkMarker: String? = null,
        staple:String? = null,
        language: String? = null,
        num: Int? = null,
        offset: Int? = null
    ): Flow<YUIState> = repository.getCards(
        name = name,
        archetype = archetype,
        level = level,
        attribute = attribute,
        sort = sort,
        banList = banList,
        cardSet = cardSet,
        fName = fName,
        type = type,
        race = race,
        format = format,
        linkMarker = linkMarker,
        staple = staple,
        language = language,
        num = num,
        offset = offset
    )

    suspend fun getCardByName(fName: String?) = repository.getCardByName(fName = fName)
    suspend fun getRandomCard() = repository.getRandomCard()
}