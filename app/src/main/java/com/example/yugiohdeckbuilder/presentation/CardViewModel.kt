package com.example.yugiohdeckbuilder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yugiohdeckbuilder.data.model.YUIState
import com.example.yugiohdeckbuilder.domain.CardUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardViewModel(private val useCase: CardUseCase): ViewModel() {

    private val _cardLiveData = MutableLiveData<YUIState>()
    val cardLiveData: LiveData<YUIState>
        get() = _cardLiveData

    private val _randomLiveData = MutableLiveData<YUIState>()
    val randomLiveData: LiveData<YUIState>
        get() = _randomLiveData

    fun fetchCards(
        name: String?,
        archetype: String?,
        level: String?,
        attribute: String?,
        sort: String?,
        banList: String?,
        cardSet: String?,
        fName: String?,
        type: String?,
        race: String?,
        format: String?,
        linkMarker: String?,
        staple: String?,
        language: String?
    ) {
        viewModelScope.launch {
            useCase.getCards(
                name = name,
                archetype = archetype,
                level = level,
                attribute = attribute,
                sort = sort,
                banList = banList,
                cardSet = cardSet,
                fName = fName,
                type = type,
                race = race,
                format = format,
                linkMarker = linkMarker,
                staple = staple,
                language = language
            ).collect {
                _cardLiveData.postValue(it)
            }
        }
    }

    fun fetchRandomCard() {
        viewModelScope.launch {
            useCase.getRandomCard().collect {
                _randomLiveData.postValue(it)
            }
        }
    }
}
