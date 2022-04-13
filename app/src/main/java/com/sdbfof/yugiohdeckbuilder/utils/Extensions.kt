package com.sdbfof.yugiohdeckbuilder.utils

import android.content.Context
import android.widget.Toast
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.data.model.response.YuGiOhResponse
import java.util.*

fun YuGiOhResponse.toFName(context: Context): List<String?> {
    return this.data?.map { it.name }
        ?: listOf(context.resources.getString(R.string.error_response))
}

fun generateYuserId() =
    "${UUID.randomUUID()}-${System.currentTimeMillis().rotateLeft(Random().nextInt(7))}"

fun String.showToast(context: Context, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, length).show()
}