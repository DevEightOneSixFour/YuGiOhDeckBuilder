package com.sdbfof.yugiohdeckbuilder.domain

import com.sdbfof.yugiohdeckbuilder.data.model.states.YUIState
import kotlinx.coroutines.flow.Flow

interface CardRepository {
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
        language: String?,
        num: Int?,
        offset: Int?
    ): Flow<YUIState>

    suspend fun getCardByName(fName: String?): Flow<YUIState>
    suspend fun getRandomCard(): Flow<YUIState>
}