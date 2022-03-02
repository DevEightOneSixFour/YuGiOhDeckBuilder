package com.example.yugiohdeckbuilder.data.model

import com.example.yugiohdeckbuilder.utils.PAGE_SIZE

data class PageState(
    val name: String? = null,
    val archetype: String? = null,
    val level: String? = null,
    val attribute: String? = null,
    val sort: String? = null,
    val banList: String? = null,
    val cardSet: String? = null,
    val fName: String? = null,
    val type: String? = null,
    val race: String? = null,
    val format: String? = null,
    val linkMarker: String? = null,
    val staple: String? = null,
    val language: String? = null,
    val num: Int = PAGE_SIZE,
    var offset: Int = 0
)
