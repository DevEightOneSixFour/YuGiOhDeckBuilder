package com.sdbfof.yugiohdeckbuilder.data.api

import com.sdbfof.yugiohdeckbuilder.data.model.response.Card
import com.sdbfof.yugiohdeckbuilder.data.model.response.YuGiOhResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("cardinfo.php")
    suspend fun getCards(
        @Query("name") name: String?,
        @Query("archetype") archetype: String?,
        @Query("level") level: String?, //*
        @Query("attribute") attribute: String?, //*
        @Query("sort") sort: String?,
        @Query("banlist") banList: String?,
        @Query("cardset") cardSet: String?,
        @Query("fname") fName: String?,
        @Query("type") type: String?, //*
        @Query("race") race: String?, //*
        @Query("format") format: String?,
        @Query("linkmarker") linkMarker: String?,
        @Query("staple") staple: String?,
        @Query("language") language: String?, //*
        @Query("num") num: Int?,
        @Query("offset") offset: Int?
    ): Response<YuGiOhResponse>

    @GET("cardinfo.php")
    suspend fun getCardByName(
        @Query("fname") fName: String?
    ): Response<YuGiOhResponse>

    @GET("randomcard.php")
    suspend fun getRandomCard(): Response<Card>
}

/*
TODO
cardsets.php
cardsetsinfo.php?setcode=SDY-046
archetypes.php
checkDBVer.php
 */