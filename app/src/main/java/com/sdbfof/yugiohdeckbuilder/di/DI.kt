package com.sdbfof.yugiohdeckbuilder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.sdbfof.yugiohdeckbuilder.data.api.ApiService
import com.sdbfof.yugiohdeckbuilder.domain.CardRepositoryImpl
import com.sdbfof.yugiohdeckbuilder.domain.CardUseCase
import com.sdbfof.yugiohdeckbuilder.presentation.AccountViewModel
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel
import com.sdbfof.yugiohdeckbuilder.utils.BASE_URL
import com.sdbfof.yugiohdeckbuilder.utils.FB_DATABASE_TITLE
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

    fun provideCardViewModel(storeOwner: ViewModelStoreOwner): CardViewModel =
        buildCardViewModel(storeOwner)[CardViewModel::class.java]

    private fun buildCardViewModel(viewModelStoreOwner: ViewModelStoreOwner) =
        ViewModelProvider(viewModelStoreOwner,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                CardViewModel(provideUseCase()) as T
        })

    private fun provideUseCase() = CardUseCase(getRepositoryImpl())

    fun provideDatabase() = FirebaseDatabase.getInstance().getReference(FB_DATABASE_TITLE)
    fun provideAuth() = FirebaseAuth.getInstance()

    fun provideAccountViewModel(storeOwner: ViewModelStoreOwner) =
        buildAccountViewModel(storeOwner)[AccountViewModel::class.java]

    private fun buildAccountViewModel(viewModelStoreOwner: ViewModelStoreOwner) =
        ViewModelProvider(viewModelStoreOwner, object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                AccountViewModel(provideDatabase(), provideAuth()) as T
        })
}