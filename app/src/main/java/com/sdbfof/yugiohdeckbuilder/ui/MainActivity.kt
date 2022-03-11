package com.sdbfof.yugiohdeckbuilder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sdbfof.yugiohdeckbuilder.databinding.ActivityMainBinding
import com.sdbfof.yugiohdeckbuilder.di.DI
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CardViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel = DI.provideViewModel(this)
    }
}