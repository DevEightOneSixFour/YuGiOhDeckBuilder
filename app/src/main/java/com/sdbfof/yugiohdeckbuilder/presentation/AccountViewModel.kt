package com.sdbfof.yugiohdeckbuilder.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.sdbfof.yugiohdeckbuilder.data.model.yuser.Deck
import com.sdbfof.yugiohdeckbuilder.data.model.yuser.Yuser
import com.sdbfof.yugiohdeckbuilder.utils.AccountStatus
import kotlinx.coroutines.launch
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
class AccountViewModel(
    private val database: DatabaseReference,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _accountStatus = MutableLiveData<AccountStatus>()
    val accountStatus: LiveData<AccountStatus>
        get() = _accountStatus

    private val _currentYuser = MutableLiveData<Yuser?>()
    val currentYuser: LiveData<Yuser?>
        get() = _currentYuser

    fun readRemoteDatabase(checkForThis: String) {
        val task = database.child(checkForThis)

        viewModelScope.launch {
            task.get().addOnSuccessListener {
                if (it.exists()) _accountStatus.postValue(AccountStatus.EXISTS)
                else _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
            }.addOnCanceledListener {
                _accountStatus.postValue(AccountStatus.CANCELED)
            }.addOnFailureListener {
                _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
            }
        }
    }

    fun createAccount(yuser: Yuser) {
        auth.createUserWithEmailAndPassword(yuser.email.orEmpty(), yuser.password.orEmpty())
            .addOnCompleteListener { task ->
                if (task.isComplete) {
                    database.child(task.result.user?.uid.toString()).setValue(yuser).addOnCompleteListener {
                        if (it.isSuccessful) {
                            _accountStatus.postValue(AccountStatus.SUBMITTED)
                        } else {
                            _accountStatus.postValue(AccountStatus.CREATION_ERROR)
                        }
                    }
                } else {
                    _accountStatus.postValue(AccountStatus.CREATION_ERROR)
                }
            }
    }

    fun signInWithUsernameAndPassword(username: String, password: String) {
        database.child(username).get().addOnCompleteListener { task ->
            try {
                if (task.isComplete) {
                    val result = task.result
                    if (!result.exists() || result.child("password").value != password) {
                        _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
                    } else {
                        val decks = result.child("decks")
                        _currentYuser.postValue(convertToYuser(result, decks))
                        _accountStatus.postValue(AccountStatus.SIGNED_IN)
                    }
                } else {
                    throw Exception("Error signing in")
                }
            } catch (e: Exception) {
                Log.e("*****", e.toString())
                _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
            }
        }
//        auth.signInWithEmailAndPassword(username, password)
//            .addOnCompleteListener { task ->
//                if (task.isComplete) {
//                    fetchYuserData(task.result.user?.uid.toString())
//                } else {
//                    _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
//                }
//            }.addOnCanceledListener {
//                _accountStatus.postValue(AccountStatus.CANCELED)
//            }.addOnFailureListener {
//                _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
//            }
    }

    // debug function -> delete or move later
    fun fetchYuserData(username: String) {
        database.child(username).get().addOnCompleteListener {
            try {
                if (it.isComplete) {
                    val result = it.result
                    val decks = result.child("decks")
                    _currentYuser.postValue(convertToYuser(result, decks))
                    _accountStatus.postValue(AccountStatus.SIGNED_IN)
                } else {
                    Log.d("***** Value?", "${it.exception}")
                }
            } catch (e: Exception) {
                Log.e("*****", e.toString())
                _accountStatus.postValue(AccountStatus.SIGN_IN_ERROR)
            }
        }
    }

    private fun convertToYuser(
        resultSnapShot: DataSnapshot,
        decksSnapShot: DataSnapshot
    ): Yuser {
        val username: String = resultSnapShot.child("username").value.toString()
        val email: String = resultSnapShot.child("email").value.toString()
        val password: String = resultSnapShot.child("password").value.toString()
        val avatar: String? = resultSnapShot.child("avatar").value as String?
        val decks : MutableList<Deck?>? = decksSnapShot.value as MutableList<Deck?>? //todo refactor?

        return Yuser(
            username = username,
            email = email,
            password = password,
            avatar = avatar,
            decks = decks
        )
    }

    fun loggingOut() {
        _currentYuser.value = null
        _accountStatus.postValue(AccountStatus.SIGNED_OUT)
    }
}
