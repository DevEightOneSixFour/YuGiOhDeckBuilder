package com.example.yugiohdeckbuilder.utils

import com.example.yugiohdeckbuilder.data.model.response.Card

interface CardSelectListener {
    fun onCardSelected (card: Card)
}