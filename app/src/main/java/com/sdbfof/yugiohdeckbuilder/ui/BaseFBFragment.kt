package com.sdbfof.yugiohdeckbuilder.ui

import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.sdbfof.yugiohdeckbuilder.utils.LoginTextWatcher

open class BaseFBFragment: Fragment() {
    lateinit var textWatcher: LoginTextWatcher

    fun setTextWatcher(list: List<TextInputEditText>, btn: Button) {
        textWatcher = LoginTextWatcher(list, btn)
    }

    fun yuserInputValidation(list: List<TextInputEditText>) {
        for (i in list) {
            when {

            }
        }
    }
}