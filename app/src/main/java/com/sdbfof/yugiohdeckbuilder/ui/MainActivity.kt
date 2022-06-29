package com.sdbfof.yugiohdeckbuilder.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.security.crypto.MasterKey
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.databinding.ActivityMainBinding
import com.sdbfof.yugiohdeckbuilder.di.DI
import com.sdbfof.yugiohdeckbuilder.presentation.AccountViewModel
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel
import com.sdbfof.yugiohdeckbuilder.ui.cards.CardDetailsFragmentDirections
import com.sdbfof.yugiohdeckbuilder.ui.cards.CardListFragmentDirections
import com.sdbfof.yugiohdeckbuilder.ui.cards.FilterFragmentDirections
import com.sdbfof.yugiohdeckbuilder.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cardViewModel: CardViewModel
    private lateinit var accountViewModel: AccountViewModel
    private val preferences by lazy { createPrefs() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbUserMenu)
        cardViewModel = DI.provideCardViewModel(this)
        accountViewModel = DI.provideAccountViewModel(this)
        configureObservers()
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.yuser_menu, menu)
        return true
    }

    //todo
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                "Profile".showToast(this)
                true
            }
            R.id.action_decks -> {
                "Decks".showToast(this)
                true
            }
            R.id.action_settings -> {
                "Settings".showToast(this)
                true
            }
            R.id.action_logout -> {
                val fragmentId = binding.hostFragment.findNavController().currentDestination!!.id
                showLogOutAlert(fragmentId)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun configureObservers() {
        accountViewModel.accountStatus.observe(this) { status ->
            when (status) {
                AccountStatus.SIGNED_IN -> {
                    binding.apply {
                        tbUserMenu.title = accountViewModel.currentYuser.value?.username ?: "Yuser"
                        ablTopBar.visibility = View.VISIBLE
                    }
                }
                AccountStatus.SIGNED_OUT -> {
                    preferences.edit()
                        .putString(PREF_KEY_1, null)
                        .putString(PREF_KEY_2, null)
                        .apply()
                    binding.apply {
                        tbUserMenu.title = null
                        ablTopBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun showLogOutAlert(fragmentId: Int, context: Context? = null) {
        CustomAlertView(context ?: this)
            .buildUpLogoutAlertView(accountViewModel.currentYuser.value?.username, fragmentId, this)
    }

    fun moveToLogin(fragment: Int) {
        accountViewModel.loggingOut()
        val nav = binding.hostFragment.findNavController()
        when (fragment) {
            R.id.nav_filter -> nav.navigate(FilterFragmentDirections.actionBackToLogin())
            R.id.nav_card_list -> nav.navigate(CardListFragmentDirections.actionBackToLogin())
            R.id.nav_card_detail -> nav.navigate(CardDetailsFragmentDirections.actionBackToLogin())
        }
    }

    override fun onBackPressed() {
        if (binding.hostFragment.findNavController().currentDestination!!.id == R.id.nav_filter) {
            showLogOutAlert(R.id.nav_filter)
        }
        else super.onBackPressed()
    }

    private fun createPrefs(): SharedPreferences =
        YuserSharedPrefs.create(
            context = this,
            masterKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        )
}