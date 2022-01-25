package com.example.yugiohdeckbuilder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.yugiohdeckbuilder.data.api.ApiService
import com.example.yugiohdeckbuilder.domain.CardRepositoryImpl
import com.example.yugiohdeckbuilder.domain.CardUseCase
import com.example.yugiohdeckbuilder.presentation.CardViewModel
import com.example.yugiohdeckbuilder.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DI {
    private val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun getRepositoryImpl() = CardRepositoryImpl(service)

    fun provideViewModel(storeOwner: ViewModelStoreOwner): CardViewModel =
        buildViewModel(storeOwner)[CardViewModel::class.java]

    private fun buildViewModel(viewModelStoreOwner: ViewModelStoreOwner) =
        ViewModelProvider(viewModelStoreOwner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                CardViewModel(provideUseCase()) as T
        })

    private fun provideUseCase() = CardUseCase(getRepositoryImpl())
}