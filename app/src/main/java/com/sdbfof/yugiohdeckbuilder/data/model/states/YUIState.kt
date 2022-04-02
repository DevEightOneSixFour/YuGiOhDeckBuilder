package com.sdbfof.yugiohdeckbuilder.data.model.states

import com.sdbfof.yugiohdeckbuilder.data.model.response.Card
import com.sdbfof.yugiohdeckbuilder.data.model.response.YuGiOhResponse

sealed class YUIState {
    object Loading: YUIState()
    data class Error(val errorMsg: String): YUIState()
    data class SuccessList(val response: YuGiOhResponse): YUIState()
    data class SuccessCard(val card: Card): YUIState()
}
