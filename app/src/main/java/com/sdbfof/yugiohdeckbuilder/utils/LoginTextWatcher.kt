package com.sdbfof.yugiohdeckbuilder.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class LoginTextWatcher(
    private val listOfInput: List<TextInputEditText>,
    private val button: Button
): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        button.isEnabled = checkInputLength(listOfInput)
    }
    override fun afterTextChanged(p0: Editable?) {}

    private fun checkInputLength(list: List<TextInputEditText>): Boolean {
        for (item in list) {
           if (item.text!!.length < 8) return false
        }
        return true
    }
}