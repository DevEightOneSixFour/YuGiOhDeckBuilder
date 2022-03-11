package com.sdbfof.yugiohdeckbuilder.utils

import android.content.Context
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.data.model.response.YuGiOhResponse

fun YuGiOhResponse.toFName(context: Context): List<String> {
    return this.data?.map { it.name } ?: listOf(context.resources.getString(R.string.error_response))
}