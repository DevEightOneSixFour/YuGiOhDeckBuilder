package com.sdbfof.yugiohdeckbuilder.ui.account

import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.MasterKey
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentLoginBinding
import com.sdbfof.yugiohdeckbuilder.utils.*

class LoginFragment : BaseAccountFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private val viewModel by lazy { provideAccountViewModel() }
    private val preferences by lazy { createPrefs() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        createTextWatcher()
        configureObservers()
        createAccountLink()
        readPreferences()
        binding.apply {
            tietUsername.addTextChangedListener(textWatcher)
            tietPassword.addTextChangedListener(textWatcher)
            btnLogin.setOnClickListener {
                loginYuser(binding.cbLoginSave.isChecked)
            }
            btnDebug.setOnClickListener {
                viewModel.fetchYuserData(binding.tietUsername.text.toString())
                debugToFilters()
            }
        }
        return binding.root
    }
    
    private fun configureObservers() {
        viewModel.accountStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                AccountStatus.SIGN_IN_ERROR -> {
                    resources.getString(R.string.login_not_found)
                        .showToast(requireContext(), Toast.LENGTH_LONG)
                }
                AccountStatus.SIGNED_IN -> {
                    resources.getString(
                        R.string.login_signed_in,
                        viewModel.currentYuser.value?.username
                    ).showToast(requireContext(), Toast.LENGTH_LONG)
                    moveToFilters()
                }
                else -> {}
            }
        }
    }

    private fun createPrefs(): SharedPreferences =
        YuserSharedPrefs.create(
            context = requireContext(),
            masterKey = MasterKey.Builder(requireContext().applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        )

    private fun readPreferences() {
        val key1 = preferences.getString(PREF_KEY_1, null)
        val key2 = preferences.getString(PREF_KEY_2, null)
        Log.d(TAG, "Username: $key1")
        Log.d(TAG, "Password: $key2")

        if (key1 != null && key2 != null) {
            viewModel.signInWithUsernameAndPassword(key1, key2)
        } else {
            viewModel.loggingOut()
            binding.apply {
                tietUsername.text?.clear()
                tietPassword.text?.clear()
            }
        }
    }

    private fun loginYuser(shouldSave: Boolean) {
        if (shouldSave) {
            preferences.edit()
                .putString(PREF_KEY_1, binding.tietUsername.text.toString())
                .putString(PREF_KEY_2, binding.tietPassword.text.toString())
                .apply()
        }

        viewModel.signInWithUsernameAndPassword(
            binding.tietUsername.text.toString(),
            binding.tietPassword.text.toString()
        )
    }

    private fun moveToFilters() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionNavLoginToNavFilter(viewModel.currentYuser.value)
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
        binding.tvAccountCreation.apply {
            text = ss
            movementMethod = LinkMovementMethod()
        }
    }

    override fun onDestroyView() {
        clearTextWatcher()
        super.onDestroyView()
        _binding = null
    }

    private fun debugToFilters() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionNavLoginToNavFilter()
        )
    }
}