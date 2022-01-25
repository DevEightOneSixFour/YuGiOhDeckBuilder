package com.example.yugiohdeckbuilder.data.model

import com.example.yugiohdeckbuilder.data.model.response.Card
import com.example.yugiohdeckbuilder.data.model.response.YuGiOhResponse

sealed class YUIState {
    object Loading: YUIState()
    data class Error(val msg: String): YUIState()
    data class SuccessList(val response: YuGiOhResponse): YUIState()
    data class SuccessCard(val card: Card): YUIState()
}
