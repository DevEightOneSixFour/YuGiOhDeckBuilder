package com.sdbfof.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentLoginBinding
import com.sdbfof.yugiohdeckbuilder.utils.LoginTextWatcher

class LoginFragment: FBAccountFragment() {
    private lateinit var binding: FragmentLoginBinding

    //todo clickable span for creating new accounts
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        createTextWatcher()
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