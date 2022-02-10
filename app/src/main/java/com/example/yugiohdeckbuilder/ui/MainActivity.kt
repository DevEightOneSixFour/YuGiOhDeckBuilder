package com.example.yugiohdeckbuilder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yugiohdeckbuilder.R
import com.example.yugiohdeckbuilder.di.DI
import com.example.yugiohdeckbuilder.presentation.CardViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = DI.provideViewModel(this)
    }
}