package com.sdbfof.yugiohdeckbuilder.domain

import com.sdbfof.yugiohdeckbuilder.data.api.ApiService
import com.sdbfof.yugiohdeckbuilder.data.model.YUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardRepositoryImpl(private val service: ApiService) : CardRepository {
    override suspend fun getCards(
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
        staple: String?,
        language: String?,
        num: Int?,
        offset: Int?
    ): Flow<YUIState> =
        flow {
            emit(YUIState.Loading)

            val response = service.getCards(
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

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(YUIState.SuccessList(it))
                } ?: run {
                    emit(YUIState.Error("Empty response"))
                }
            } else {
                emit(YUIState.Error("Failed network response"))
            }
        }

    override suspend fun getCardByName(fName: String?): Flow<YUIState> =
        flow {
            emit(YUIState.Loading)

            val response = service.getCardByName(fName)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(YUIState.SuccessList(it))
                } ?: run {
                    emit(YUIState.Error("Empty response"))
                }
            } else {
                emit(YUIState.Error("Failed network response"))
            }
        }

    override suspend fun getRandomCard(): Flow<YUIState> =
        flow {
            emit(YUIState.Loading)

            val response = service.getRandomCard()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(YUIState.SuccessCard(it))
                } ?: run {
                    emit(YUIState.Error("Empty response"))
                }
            } else {
                emit(YUIState.Error("Failed network response"))
            }
        }
}