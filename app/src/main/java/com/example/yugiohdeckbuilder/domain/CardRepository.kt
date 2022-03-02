package com.example.yugiohdeckbuilder.domain

import com.example.yugiohdeckbuilder.data.model.YUIState
import com.example.yugiohdeckbuilder.data.model.response.YuGiOhResponse
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