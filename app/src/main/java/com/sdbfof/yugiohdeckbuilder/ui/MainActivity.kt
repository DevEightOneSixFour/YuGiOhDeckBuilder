package com.sdbfof.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sdbfof.yugiohdeckbuilder.databinding.ActivityMainBinding
import com.sdbfof.yugiohdeckbuilder.di.DI
import com.sdbfof.yugiohdeckbuilder.presentation.AccountViewModel
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel

class MainActivity : AppCompatActivity() {

    lateinit var cardViewModel: CardViewModel
    lateinit var accountViewModel: AccountViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        cardViewModel = DI.provideCardViewModel(this)
        accountViewModel = DI.provideAccountViewModel(this)
    }
}