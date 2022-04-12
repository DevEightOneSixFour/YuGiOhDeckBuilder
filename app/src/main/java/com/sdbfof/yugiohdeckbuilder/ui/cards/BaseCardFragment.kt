package com.sdbfof.yugiohdeckbuilder.ui.cards

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.databinding.CustomFilterAlertBinding
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel
import com.sdbfof.yugiohdeckbuilder.ui.MainActivity

open class BaseCardFragment : Fragment() {

    fun provideCardViewModel() = ViewModelProvider(requireActivity())[CardViewModel::class.java]

}