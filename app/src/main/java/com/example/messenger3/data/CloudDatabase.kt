package com.example.messenger3.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object CloudDatabase {
    val refOnUsers: CollectionReference by lazy {
        refOnDatabase.collection("users")
    }

    val refOnDatabase: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getRefOnUser(id: String): DocumentReference{
        return refOnUsers.document(id)
    }
}