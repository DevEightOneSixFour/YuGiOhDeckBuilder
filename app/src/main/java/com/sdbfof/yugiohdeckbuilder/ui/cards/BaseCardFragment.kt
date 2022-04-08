package com.sdbfof.yugiohdeckbuilder.ui.cards

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel

open class BaseCardFragment: Fragment() {
    fun provideCardViewModel() = ViewModelProvider(requireActivity())[CardViewModel::class.java]
}