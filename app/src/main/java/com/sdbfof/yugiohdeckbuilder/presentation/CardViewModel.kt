package com.sdbfof.yugiohdeckbuilder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdbfof.yugiohdeckbuilder.data.model.states.PageState
import com.sdbfof.yugiohdeckbuilder.data.model.states.YUIState
import com.sdbfof.yugiohdeckbuilder.domain.CardUseCase
import com.sdbfof.yugiohdeckbuilder.utils.CardType
import com.sdbfof.yugiohdeckbuilder.utils.PAGE_SIZE
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

    private val currentPageState = PageState()

    fun updatePageState(
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
        language: String? = null,
        num: Int = PAGE_SIZE,
        offset: Int? = currentPageState.offset ?: 0
    ){
        currentPageState.apply {
            this.name = name
            this.archetype = archetype
            this.level = level
            this.attribute = attribute
            this.sort = sort
            this.banList = banList
            this.cardSet = cardSet
            this.fName = fName
            this.type = type
            this.race = race
            this.format = format
            this.linkMarker = linkMarker
            this.staple = staple
            this.language = language
            this.num = num
            this.offset = offset
        }

        fetchCards(currentPageState)
    }

    private fun fetchCards(pageState: PageState) {
        viewModelScope.launch {
            useCase.getCards(
                name = pageState.name,
                archetype = pageState.archetype,
                level = pageState.level,
                attribute = pageState.attribute,
                sort = pageState.sort,
                banList = pageState.banList,
                cardSet = pageState.cardSet,
                fName = pageState.fName,
                type = pageState.type,
                race = pageState.race,
                format = pageState.format,
                linkMarker = pageState.linkMarker,
                staple = pageState.staple,
                language = pageState.language,
                num = pageState.num,
                offset = pageState.offset
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

    fun updateOffset(amount: Int) {
        currentPageState.offset = currentPageState.offset?.plus(amount)
        fetchCards(currentPageState)
    }

    fun clearPageState() {
        currentPageState.run {
            name = null
            archetype = null
            level = null
            attribute = null
            sort = null
            banList = null
            cardSet = null
            fName = null
            type = null
            race = null
            format = null
            linkMarker = null
            staple = null
            language = null
            offset = null
        }
    }

    fun getPageState() = currentPageState

}
