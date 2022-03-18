package com.sdbfof.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sdbfof.yugiohdeckbuilder.data.model.user.Yuser
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentCreateAccountBinding

class CreateAccountFragment: FBAccountFragment() {
    private lateinit var binding: FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(layoutInflater)
        createTextWatcher()
        binding.apply {
            tietInputUsername.addTextChangedListener(textWatcher)
            tietInputEmail.addTextChangedListener(textWatcher)
            tietInputPassword.addTextChangedListener(textWatcher)
            tietInputPasswordRepeat.addTextChangedListener(textWatcher)
            btnCreateAccount.setOnClickListener {
                //todo checks
                // - for existing account
                // - email valid
                // - password matches
                submitProfile()
            }
        }
        return binding.root
    }

    private fun createTextWatcher() {
        setTextWatcher(
            listOf(
                binding.tietInputUsername,
                binding.tietInputEmail,
                binding.tietInputPassword,
                binding.tietInputPasswordRepeat
            ),
            binding.btnCreateAccount
        )
    }

    private fun submitProfile() {
//        val yuser = Yuser(
//            id =  , // todo 3/12 use firebase auto-incrementation
//            username = binding.tietInputUsername.text.toString(),
//            email = binding.tietInputEmail.text.toString(),
//            password = binding.tietInputPassword.text.toString()
//        )
        Toast.makeText(context,"Account Submitted", Toast.LENGTH_SHORT).show()
    }
}