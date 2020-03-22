package com.example.messenger3.utils

import com.example.messenger3.ui.account.User
import com.google.firebase.firestore.DocumentSnapshot

object UserListUtils {
    fun removeUserFromList(id: String, list: List<DocumentSnapshot>): List<User>{
        val resultList = mutableListOf<User>()

        list.forEach {user ->
            resultList.add(
                user.toObject(User::class.java)!!
            )
        }
        val cUserIndex = resultList.indexOf(resultList.find { elem ->
            elem.id == id
        })
        if (cUserIndex != -1) resultList.removeAt(cUserIndex)
        else{
            return mutableListOf()
        }
        return resultList
    }
}