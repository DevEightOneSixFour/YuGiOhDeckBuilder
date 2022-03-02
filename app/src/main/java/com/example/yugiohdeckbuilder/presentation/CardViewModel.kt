package com.example.yugiohdeckbuilder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yugiohdeckbuilder.data.model.PageState
import com.example.yugiohdeckbuilder.data.model.YUIState
import com.example.yugiohdeckbuilder.domain.CardUseCase
import com.example.yugiohdeckbuilder.utils.CardType
import com.example.yugiohdeckbuilder.utils.PAGE_SIZE
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

    lateinit var currentPageState: PageState

    private var pageOffset = 0

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
        offset: Int = pageOffset
    ){
        currentPageState = PageState(
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
            language = language,
            num = num,
            offset = offset
        )
        fetchCards(currentPageState)
    }

    fun fetchCards(pageState: PageState) {
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
        currentPageState.offset += amount
        fetchCards(currentPageState)
    }

    fun resetOffset() {
        pageOffset = 0
    }
}
