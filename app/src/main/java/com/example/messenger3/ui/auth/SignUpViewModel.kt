package com.example.messenger3.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.messenger3.data.Auth

object SignUpViewModel {
    private val refOnAuth = Auth.firebaseAuth
    val signupLiveData = MutableLiveData<Pair<Boolean, String>>()

    fun registerUser(email: String, password: String){


        refOnAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("Messenger", "Succesfully logged in")
                signupLiveData.value = Pair(true, "")
            }
            .addOnFailureListener {
                Log.d("Messenger", "Failure: ${it.message}")
                signupLiveData.value = Pair(false, it.toString())
            }
    }
}