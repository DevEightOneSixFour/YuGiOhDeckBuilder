package com.example.yugiohdeckbuilder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yugiohdeckbuilder.data.model.YUIState
import com.example.yugiohdeckbuilder.domain.CardUseCase
import com.example.yugiohdeckbuilder.utils.CardType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardViewModel(private val useCase: CardUseCase): ViewModel() {

    private val _cardListLiveData = MutableLiveData<YUIState>()
    val cardListLiveData: LiveData<YUIState>
        get() = _cardListLiveData

    private val _cardByNameLiveData = MutableLiveData<YUIState>()
    val cardByNameLiveData: LiveData<YUIState>
        get() = _cardByNameLiveData

    private val _randomLiveData = MutableLiveData<YUIState>()
    val randomLiveData: LiveData<YUIState>
        get() = _randomLiveData

    private val _currentType = MutableLiveData<CardType>()
    val currentType: LiveData<CardType>
    get() = _currentType

    fun fetchCards(
        name: String? = null,
        archetype: String? = null,
        level: String? = null,
        attribute: String? = null,
        sort: String? = null,
        banList: String? = null,
        cardSet: String? = null,
        fName: String? = null,
        type: String? = null,
        race: String? = null,
        format: String? = null,
        linkMarker: String? = null,
        staple: String? = null,
        language: String? = null
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
                _cardListLiveData.postValue(it)
            }
        }
    }

    fun fetchCardByName(fName: String?) {
        viewModelScope.launch {
            useCase.getCardByName(fName).collect {
                _cardByNameLiveData.postValue(it)
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

    fun updateSelectedType(type: CardType) {
        _currentType.value = type
    }
}
