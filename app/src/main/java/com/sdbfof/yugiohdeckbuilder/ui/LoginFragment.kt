package com.sdbfof.yugiohdeckbuilder.ui

import android.content.res.Resources
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentLoginBinding

class LoginFragment: BaseFBFragment() {
    private lateinit var binding: FragmentLoginBinding

    //todo clickable span for creating new accounts
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        createTextWatcher()
        createAccountLink()
        binding.apply {
            tietUsername.addTextChangedListener(textWatcher)
            tietPassword.addTextChangedListener(textWatcher)
            btnLogin.setOnClickListener {
                moveToFilters()
            }
            btnDebug.setOnClickListener {
                debugToFilters()
            }
        }
        return binding.root
    }

    private fun moveToFilters() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionNavLoginToNavFilter()
        )
    }

    private fun createTextWatcher() {
        setTextWatcher(
            listOf(binding.tietPassword, binding.tietUsername),
            binding.btnLogin
        )
    }

    private fun createAccountLink() {
        val ss = SpannableString(resources.getString(R.string.login_new_account))
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ResourcesCompat.getColor(resources, R.color.purple_200, null)
                ds.isUnderlineText = true
            }
            override fun onClick(p0: View) {
                binding.root.findNavController().navigate(
                    LoginFragmentDirections.actionNavLoginToNavCreateAccount()
                )
            }
        }
        ss.setSpan(clickableSpan, 5, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvAccountCreation.run {
            text = ss
            movementMethod = LinkMovementMethod()
        }
    }

    private fun debugToFilters() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionNavLoginToNavFilter()
        )
    }
}

/*
object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = binding.tietUsername.text!!.length > 8 &&
                        binding.tietPassword.text!!.length > 8
            }
            override fun afterTextChanged(p0: Editable?) {}
        }
 */