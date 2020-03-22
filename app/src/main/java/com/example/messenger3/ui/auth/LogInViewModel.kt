package com.example.messenger3.ui.auth


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.messenger3.data.Auth

object LogInViewModel{
    private val refOnAuth = Auth.firebaseAuth
    val loginLiveData = MutableLiveData<Pair<Boolean, String>>()

    fun loginUser(email: String, password: String){

        refOnAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("Messenger", "Succesfully logged in")
                loginLiveData.value = Pair(true, "")
            }
            .addOnFailureListener {
                Log.d("Messenger", "Failure: ${it.message}")
                loginLiveData.value = Pair(false, it.toString())
            }

    }

}