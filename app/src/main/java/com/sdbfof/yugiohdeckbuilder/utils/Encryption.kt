package com.sdbfof.yugiohdeckbuilder.utils

import android.annotation.TargetApi
import java.security.SecureRandom
import java.util.HashMap
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal class Encryption {

    fun encrypt(dataToEncrypt: ByteArray,
                password: CharArray): HashMap<String, ByteArray> {

        val map = HashMap<String, ByteArray>()

        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)

        val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(dataToEncrypt)

        map["salt"] = salt
        map["iv"] = iv
        map["encrypted"] = encrypted

        for((key,value) in map) {
            println("Encrypted Map: Key[${key}] Value[${value.joinToString("|")}]")
        }

        return map
    }

    fun decrypt(map: HashMap<String, ByteArray>, password: CharArray): ByteArray? {

        var decrypted: ByteArray? = null

        val salt = map["salt"]
        val iv = map["iv"]
        val encrypted = map["encrypted"]

        // 2
        //regenerate key from password
        val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        // 3
        //Decrypt
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        decrypted = cipher.doFinal(encrypted)


        return decrypted
    }
}

