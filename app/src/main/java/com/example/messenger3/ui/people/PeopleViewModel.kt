package com.example.messenger3.ui.people

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.messenger3.data.CloudDatabase
import com.example.messenger3.ui.account.User
import com.example.messenger3.utils.UserListUtils

object PeopleViewModel {
    private val refOnDatabase = CloudDatabase.refOnUsers
    private lateinit var uid: String

    val usersLiveData = MutableLiveData<Pair<List<User>, String>>()

    fun getUsersList(id: String){
        uid = id
        var message = ""
        refOnDatabase.get()
            .addOnSuccessListener {usersFromWeb ->
                val usersList = UserListUtils.removeUserFromList(id, usersFromWeb.documents)
                if (usersList.isEmpty()) message = "Haven't registered"
                usersLiveData.value = Pair(usersList, message)
            }
            .addOnFailureListener {
                message = it.message.toString()
                usersLiveData.value = Pair(listOf(), message)
            }
    }

    fun setListenerOnList(){
        refOnDatabase.addSnapshotListener { usersFromWeb, firebaseFirestoreException ->
            Log.d("Messenger", "Snapshot has come")
            val usersList = UserListUtils.removeUserFromList(uid, usersFromWeb!!.documents)
            usersLiveData.value = Pair(usersList, "")
        }
    }
}