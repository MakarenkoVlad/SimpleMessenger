package com.example.messenger3.ui.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.messenger3.data.CloudDatabase
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

object ChatViewModel {
    private val ref = CloudDatabase.refOnDatabase.collection("messages").document("messageList")
    private var nameOfSession = ""
    val messagesLiveData = MutableLiveData<List<DocumentSnapshot>>()

    fun getListOfMessages(fromId: String, toId: String){
        ref.collection(fromId + toId).get().addOnSuccessListener {
            if (it.documents.isEmpty()) {
                nameOfSession = toId + fromId
                ref.collection(nameOfSession).get().addOnSuccessListener {messagesFromWeb ->
                    messagesLiveData.value = messagesFromWeb.documents
                }
            }
            else {
                nameOfSession = fromId + toId
                messagesLiveData.value = it.documents
            }


            ref.collection(nameOfSession).addSnapshotListener{ snapshot, e ->
                if (e != null){
                    Log.d("Messenger", "Listening failed: ${e.message}")
                    return@addSnapshotListener
                }
                messagesLiveData.value = snapshot?.documents
            }
        }
    }

    fun sendMessage(message: Message){
        ref.collection(nameOfSession)
            .document(Calendar.getInstance().timeInMillis.toString())
            .set(message)
    }
}