package com.example.yugiohdeckbuilder.data.api

import com.example.yugiohdeckbuilder.data.model.response.Card
import com.example.yugiohdeckbuilder.data.model.response.YuGiOhResponse
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
        @Query("num") num: Int = 21,
        @Query("offset") offset: Int = 0
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