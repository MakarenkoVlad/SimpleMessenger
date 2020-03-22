package com.example.messenger3.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.messenger3.R
import com.example.messenger3.data.Auth
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private var receiverId = ""
    private val senderId = Auth.firebaseAuth.currentUser!!.uid



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        recycler_view_chat.layoutManager = LinearLayoutManager(this)
        (recycler_view_chat.layoutManager as LinearLayoutManager).reverseLayout = true
        recycler_view_chat.adapter = ChatMessageAdapter(
                this,
                mutableListOf(),
                senderId
            )

        actionBar?.setDisplayHomeAsUpEnabled(true)

        receiverId = intent.extras!!["toID"].toString()
        Glide.with(this).load(intent.extras!!["photoURL"].toString()).into(avatar_image_view_chat)


        send_message_button.setOnClickListener {
            sendMessage()
        }

        ChatViewModel.getListOfMessages(senderId, receiverId)

        listenMessageListUpdates()

    }

    private fun sendMessage(){
        ChatViewModel.sendMessage(Message(
            message_text_view_chat.text.toString(),
            senderId,
            receiverId
        ))
        message_text_view_chat.text = Editable.Factory.getInstance().newEditable("")
    }

    private fun listenMessageListUpdates(){
        ChatViewModel.messagesLiveData.observe(this, Observer {
            (recycler_view_chat.adapter as ChatMessageAdapter).setMessageList(it)
            (recycler_view_chat.adapter as ChatMessageAdapter).notifyDataSetChanged()
        })
    }



}
