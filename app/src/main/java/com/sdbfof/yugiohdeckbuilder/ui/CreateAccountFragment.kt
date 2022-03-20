package com.sdbfof.yugiohdeckbuilder.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.data.model.user.Yuser
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentCreateAccountBinding
import com.sdbfof.yugiohdeckbuilder.di.DI
import java.util.*

class CreateAccountFragment : BaseFBFragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    private val db = DI.provideDatabase()

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
                validateInput()
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

    private fun validateInput() {
        when {
            !checkPasswords(
                Pair(
                    binding.tietInputPassword.text.toString(),
                    binding.tietInputPasswordRepeat.text.toString()
                )
            ) -> {
                binding.tietInputPasswordRepeat.error =
                    resources.getString(R.string.login_valid_password)
            }
            !checkEmail(binding.tietInputEmail.text.toString()) -> {
                binding.tietInputEmail.error = resources.getString(R.string.login_valid_email)
            }
            !checkDatabaseForEmail(binding.tietInputEmail.text.toString()) -> {
                binding.tietInputEmail.error = resources.getString(R.string.login_existing_email)
            }
            !checkDatabaseForUsername(binding.tietInputUsername.text.toString()) -> {
                binding.tietInputUsername.error = resources.getString(R.string.login_existing_username)
            }
            else -> submitProfile()
        }
    }

    // Todo encrypt password
    private fun submitProfile() {
        val yuser = Yuser(
            id = generateYuserId(),
            username = binding.tietInputUsername.text.toString(),
            email = binding.tietInputEmail.text.toString(),
            password = binding.tietInputPassword.text.toString()
        )
        db.child(yuser.username).setValue(yuser).addOnSuccessListener {
            binding.root.findNavController().navigate(
                CreateAccountFragmentDirections.actionNavCreateAccountToNavLogin()
            )
            //TODO snackbar?
            Toast.makeText(MainActivity().applicationContext, "Account Submitted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error: Please try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateYuserId() =
        "${UUID.randomUUID()}-${System.currentTimeMillis().rotateLeft(Random().nextInt(7))}"

    // Validation checks
    private fun checkPasswords(passwords: Pair<String, String>) =
        passwords.first == passwords.second
    private fun checkEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    // Todo -> read database for existing email
    private fun checkDatabaseForEmail(email: String): Boolean {
        return false
    }
    // Todo -> read database for existing username
    private fun checkDatabaseForUsername(username: String): Boolean {
        return false
    }
}