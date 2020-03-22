package com.example.messenger3.ui.account

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.messenger3.data.CloudDatabase
import com.example.messenger3.data.CloudStorage

object AccountInfoViewModel{
    val urlLiveData = MutableLiveData<Uri>()
    val resultLiveData = MutableLiveData<Pair<Boolean, String>>()
    val currentUserLiveData = MutableLiveData<User>()

    fun putPicture(name: String, id: String, uri: Uri){
        val ref = CloudStorage.refOnStorage.getReference("images/${name+id}")
        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {url ->
                urlLiveData.value = url
            }
            Log.d("Messenger", "Picture has uploaded")
        }

    }

    fun putUser(user: User){
        val userDocument = CloudDatabase.getRefOnUser(user.id!!)

        userDocument.set(user).addOnSuccessListener {
            resultLiveData.value = Pair(true, "")
        }.addOnFailureListener {
            resultLiveData.value = Pair(false, it.message.toString())
        }

    }

    fun getUser(id: String){
        CloudDatabase.getRefOnUser(id).get().addOnSuccessListener {
            val user = it.toObject(User::class.java) ?: return@addOnSuccessListener
            currentUserLiveData.value = user
        }
    }

}