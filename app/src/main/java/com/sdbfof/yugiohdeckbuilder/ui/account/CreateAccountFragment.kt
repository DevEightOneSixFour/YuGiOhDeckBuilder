package com.sdbfof.yugiohdeckbuilder.ui.account

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.data.model.user.Yuser
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentCreateAccountBinding
import com.sdbfof.yugiohdeckbuilder.utils.AccountStatus

class CreateAccountFragment : BaseAccountFragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding: FragmentCreateAccountBinding
        get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel by lazy {
        println(provideAccountViewModel().toString())
        provideAccountViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAccountBinding.inflate(layoutInflater)
        createTextWatcher()
        configureObservers()
        binding.apply {
            tietInputUsername.addTextChangedListener(textWatcher)
            tietInputEmail.addTextChangedListener(textWatcher)
            tietInputPassword.addTextChangedListener(textWatcher)
            tietInputPasswordRepeat.addTextChangedListener(textWatcher)
            btnCreateAccount.setOnClickListener {
                //todo checks
                // - for existing account
                // - email valid
                validateInput()
            }
        }
        return binding.root
    }

    private fun configureObservers() {
        var msg = ""
        viewModel.accountStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                AccountStatus.EXISTS -> {
                    msg = resources.getString(R.string.account_existing_email)
                }
                AccountStatus.CANCELED -> {
                    msg = "CANCELED"
                }
                AccountStatus.CREATION_ERROR -> {
                    msg = "ERROR"
                }
                AccountStatus.SUBMITTED -> {
                    msg = resources.getString(R.string.account_create_title)
                    findNavController().navigate(
                        CreateAccountFragmentDirections.actionNavCreateAccountToNavLogin()
                    )
                }
                AccountStatus.SIGN_IN_ERROR -> {
                    msg = resources.getString(R.string.account_not_found)
                }
                AccountStatus.SIGNED_IN -> {
                    msg = resources.getString(R.string.account_signed_in)
                }
                AccountStatus.SUBMITTING -> {
                    msg = "Submitting account"
                }
            }
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
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

    private fun validateInput() {
        when {
            !checkPasswords(
                Pair(
                    binding.tietInputPassword.text.toString(),
                    binding.tietInputPasswordRepeat.text.toString()
                )
            ) -> {
                binding.tietInputPasswordRepeat.error =
                    resources.getString(R.string.account_valid_password)
            }
            !checkEmail(binding.tietInputEmail.text.toString()) -> {
                binding.tietInputEmail.error = resources.getString(R.string.account_valid_email)
            }
            checkUserNameForSpecialCharacters(binding.tietInputUsername.text.toString()) -> {
                binding.tietInputUsername.error =
                    resources.getString(R.string.account_username_credentials)
            }
            !checkDatabaseForEmail(binding.tietInputEmail.text.toString()) -> {
                binding.tietInputEmail.error = resources.getString(R.string.account_existing_email)
            }
            !checkDatabaseForUsername(binding.tietInputUsername.text.toString()) -> {
                binding.tietInputUsername.error =
                    resources.getString(R.string.account_existing_username)
            }
            else -> {
                val yuser = Yuser(
                    username = binding.tietInputUsername.text.toString(),
                    email = binding.tietInputEmail.text.toString(),
                    password = binding.tietInputPassword.text.toString()
                )
                viewModel.createAccount(yuser)
            }
        }
    }

    // Validation checks
    private fun checkPasswords(passwords: Pair<String, String>) =
        passwords.first == passwords.second

    private fun checkEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun checkUserNameForSpecialCharacters(username: String) = username.contains("[.#$]")

    // Todo -> read database for existing email
    private fun checkDatabaseForEmail(email: String): Boolean {
//        viewmModel.readRemoteDatabase(email)
        return true
    }

    // Todo -> read database for existing username
    private fun checkDatabaseForUsername(username: String): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        clearTextWatcher()
    }
}