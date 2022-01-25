package com.example.yugiohdeckbuilder.data.api

import com.example.yugiohdeckbuilder.data.model.response.Card
import com.example.yugiohdeckbuilder.data.model.response.YuGiOhResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("cardinfo.php")
    fun getCards(
        @Query("name") name: String? = null,
        @Query("archetype") archetype: String? = null,
        @Query("level") level: String? = null,
        @Query("attribute") attribute: String? = null,
        @Query("sort") sort: String? = null,
        @Query("banlist") banList: String? = null,
        @Query("cardset") cardSet: String? = null,
        @Query("fname") fName: String? = null,
        @Query("type") type: String? = null,
        @Query("race") race: String? = null,
        @Query("format") format: String? = null,
        @Query("linkmarker") linkMarker: String? = null,
        @Query("staple") staple: String? = null,
        @Query("language") language: String? = "en"
    ): Response<YuGiOhResponse>

    @GET("randomcard.php")
    fun getRandomCard(): Response<Card>
}

/*
TODO
cardsets.php
cardsetsinfo.php?setcode=SDY-046
archetypes.php
checkDBVer.php
 */