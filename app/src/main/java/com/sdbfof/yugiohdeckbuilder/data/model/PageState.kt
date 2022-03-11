package com.sdbfof.yugiohdeckbuilder.data.model

import com.sdbfof.yugiohdeckbuilder.utils.PAGE_SIZE

data class PageState(
    var name: String? = null,
    var archetype: String? = null,
    var level: String? = null,
    var attribute: String? = null,
    var sort: String? = null,
    var banList: String? = null,
    var cardSet: String? = null,
    var fName: String? = null,
    var type: String? = null,
    var race: String? = null,
    var format: String? = null,
    var linkMarker: String? = null,
    var staple: String? = null,
    var language: String? = null,
    var num: Int = PAGE_SIZE,
    var offset: Int? = 0
)
