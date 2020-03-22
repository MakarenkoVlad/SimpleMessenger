package com.example.messenger3.data

import com.google.firebase.auth.FirebaseAuth

object Auth {
    val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

}