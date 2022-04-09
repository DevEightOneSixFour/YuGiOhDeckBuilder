package com.sdbfof.yugiohdeckbuilder.ui.account

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.sdbfof.yugiohdeckbuilder.presentation.AccountViewModel
import com.sdbfof.yugiohdeckbuilder.utils.LoginTextWatcher

open class BaseAccountFragment : Fragment() {
    var textWatcher: LoginTextWatcher? = null

    fun provideAccountViewModel() = ViewModelProvider(requireActivity())[AccountViewModel::class.java]

    fun setTextWatcher(list: List<TextInputEditText>, btn: Button) {
        textWatcher = LoginTextWatcher(list, btn)
    }

    fun clearTextWatcher() { textWatcher = null }
}