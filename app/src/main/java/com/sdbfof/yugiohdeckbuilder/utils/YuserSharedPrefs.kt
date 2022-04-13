package com.sdbfof.yugiohdeckbuilder.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException


object YuserSharedPrefs {
    @Throws(GeneralSecurityException::class, IOException::class)
    fun create(
        context: Context,
        fileName: String = PREFS_FILE,
        masterKey: MasterKey,
        prefKeyEncryptionScheme: PrefKeyEncryptionScheme = PrefKeyEncryptionScheme.AES256_SIV,
        prefValueEncryptionScheme: PrefValueEncryptionScheme = PrefValueEncryptionScheme.AES256_GCM
    ): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            fileName,
            masterKey,
            prefKeyEncryptionScheme,
            prefValueEncryptionScheme
        )
    }
}

