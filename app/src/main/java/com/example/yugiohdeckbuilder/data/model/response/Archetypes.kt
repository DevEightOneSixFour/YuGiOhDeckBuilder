package com.example.yugiohdeckbuilder.data.model.response

import com.google.gson.annotations.SerializedName

data class Archetypes(
    val archetypes: List<Archetype?> = emptyList()
)

data class Archetype(
    @SerializedName("archetype_name")
    val archetypeName: String? = null
)
