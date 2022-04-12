package com.sdbfof.yugiohdeckbuilder.ui.account

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentLoginBinding
import com.sdbfof.yugiohdeckbuilder.utils.AccountStatus
import com.sdbfof.yugiohdeckbuilder.utils.showToast

class LoginFragment: BaseAccountFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding : FragmentLoginBinding
        get() = _binding!!

    private val viewModel by lazy { provideAccountViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        createTextWatcher()
        configureObservers()
        createAccountLink()
        binding.apply {
            tietUsername.addTextChangedListener(textWatcher)
            tietPassword.addTextChangedListener(textWatcher)
            btnLogin.setOnClickListener {
                viewModel.signInWithUsernameAndPassword(
                    binding.tietUsername.text.toString(),
                    binding.tietPassword.text.toString()
                )
            }
            btnDebug.setOnClickListener {
                viewModel.fetchYuserData(binding.tietUsername.text.toString())
                debugToFilters()
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loggingOut()
        binding.apply {
            tietUsername.text?.clear()
            tietPassword.text?.clear()
        }
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
                    ).showToast(requireContext(),Toast.LENGTH_LONG)
                    moveToFilters()
                }
                else -> {}
            }
        }
    }

    private fun moveToFilters() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionNavLoginToNavFilter(viewModel.currentYuser.value)
        )
//        viewModel.clearAccountStatus()
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
        super.onDestroyView()
        _binding = null
        clearTextWatcher()
    }

    private fun debugToFilters() {
        this.findNavController().navigate(
            LoginFragmentDirections.actionNavLoginToNavFilter()
        )
    }
}