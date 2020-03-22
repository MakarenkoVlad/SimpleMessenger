package com.example.messenger3.ui.chat

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger3.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.chat_item.view.*


class ChatMessageAdapter (private val context: Context,
                          private var usersData: List<DocumentSnapshot>,
                          private var userId: String): RecyclerView.Adapter<MessageHolder>(){
    private val TAG = "MessengerChat"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        Log.d(TAG, "Creating item")
        return MessageHolder(
            LayoutInflater.from(context).inflate(
                R.layout.chat_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "Size in chat = ${usersData.size}")
        return usersData.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        Log.d(TAG, "Binding item")
        var message = usersData[position]
        holder.textView.text = message["message"].toString()
        val right = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.RIGHT
        }
        val left = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
            .apply {
            gravity = Gravity.LEFT
        }
        holder.textView.layoutParams = if (message["from"].toString() == userId) right else left
    }


    fun setMessageList(messageData: List<DocumentSnapshot>){
        usersData = messageData.asReversed()
    }
}
class MessageHolder(view: View): RecyclerView.ViewHolder(view){
    val textView = view.message_text_view!!
}