package com.example.messenger3.ui.home


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.messenger3.R
import com.example.messenger3.data.Auth
import com.example.messenger3.data.CloudDatabase
import com.example.messenger3.ui.chat.ChatActivity
import com.example.messenger3.ui.account.User


class UserListAdapter(private val context: Context, private var usersData: List<User>): RecyclerView.Adapter<ViewHolder>() {


    override fun getItemCount(): Int {
//        Log.d("Messenger", "Size:${usersData.size}")
        return usersData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Messenger", "Creating view holder")
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Messenger", "Binding view holder")
        val user = usersData[position]
        holder.userName.text = user.name
        holder.userSurname.text = user.surname
        Glide.with(context).load(user.downloadURL).into(holder.userImage)
        holder.itemView.setOnClickListener {
            val tempIntent = Intent(context, ChatActivity::class.java)
            tempIntent.putExtra("toID", user.id)
            tempIntent.putExtra("photoURL", user.downloadURL)
            CloudDatabase.refOnUsers.document(Auth.firebaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                CloudDatabase.refOnUsers.document(Auth.firebaseAuth.currentUser!!.uid)
                    .update("friends", getStr(it["friends"].toString(), user.id!!))
            }
            CloudDatabase.refOnUsers.document(user.id!!).get().addOnSuccessListener {
                CloudDatabase.refOnUsers.document(user.id)
                    .update("friends", getStr(it["friends"].toString(), Auth.firebaseAuth.currentUser!!.uid))
            }
            context.startActivity(tempIntent)
        }
    }

    fun setUsersList(usersData: List<User>){
        this.usersData = usersData
    }

    private fun getStr(str1: String, str2: String) = if (str1.contains(str2)) str1 else if (str1 == "null") str2 else "$str1$str2 "
}
