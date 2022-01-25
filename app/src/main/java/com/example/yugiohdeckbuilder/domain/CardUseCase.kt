package com.example.yugiohdeckbuilder.domain

import com.example.yugiohdeckbuilder.data.model.YUIState
import kotlinx.coroutines.flow.Flow

class CardUseCase(private val repository: CardRepository) {
    suspend fun getCards(
        name: String?,
        archetype: String?,
        level: String?,
        attribute: String?,
        sort: String?,
        banList: String?,
        cardSet: String?,
        fName: String?,
        type: String?,
        race: String?,
        format: String?,
        linkMarker: String?,
        staple:String?,
        language: String?
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
        language = language
    )

    suspend fun getRandomCard() = repository.getRandomCard()
}