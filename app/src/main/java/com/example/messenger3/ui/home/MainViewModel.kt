package com.example.messenger3.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.messenger3.data.CloudDatabase
import com.example.messenger3.ui.account.User
import com.example.messenger3.utils.UserListUtils.removeUserFromList
import kotlinx.coroutines.awaitAll

object MainViewModel {

    private lateinit var uid: String

    val usersLiveData = MutableLiveData<Pair<List<User>, String>>()

    fun getUsersList(id: String){
        uid = id
        var message = ""
        CloudDatabase.refOnUsers.get().addOnSuccessListener {
            val usersList = removeUserFromList(uid, it.documents).filter { user ->
                if (user.friends == null) false
                else{
                    user.friends.contains(uid)
                }
            }
            usersLiveData.value = Pair(usersList, "")
        }
    }

    fun setListenerOnList(){
        CloudDatabase.refOnUsers.addSnapshotListener { usersFromWeb, firebaseFirestoreException ->
            Log.d("Messenger", "Snapshot has come")
            val usersList = removeUserFromList(uid, usersFromWeb!!.documents).filter { user ->
                if (user.friends == null) false
                else{
                    user.friends.contains(uid)
                }
            }
            usersLiveData.value = Pair(usersList, "")
        }
    }


}