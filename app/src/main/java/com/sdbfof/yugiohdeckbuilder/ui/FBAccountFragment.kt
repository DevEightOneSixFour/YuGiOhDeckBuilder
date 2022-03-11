package com.sdbfof.yugiohdeckbuilder.ui

import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.sdbfof.yugiohdeckbuilder.utils.LoginTextWatcher

open class FBAccountFragment: Fragment() {
    lateinit var textWatcher: LoginTextWatcher

    fun setTextWatcher(list: List<TextInputEditText>, btn: Button) {
        textWatcher = LoginTextWatcher(list, btn)
    }
}