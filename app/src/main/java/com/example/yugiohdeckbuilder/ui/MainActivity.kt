package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yugiohdeckbuilder.databinding.ActivityMainBinding
import com.example.yugiohdeckbuilder.di.DI
import com.example.yugiohdeckbuilder.presentation.CardViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CardViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = DI.provideViewModel(this)
    }
}