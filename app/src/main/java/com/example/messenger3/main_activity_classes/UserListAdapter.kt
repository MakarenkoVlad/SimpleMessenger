package com.example.messenger3.main_activity_classes

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_user_item.view.*
import java.io.File


class UserListAdapter(private val context: Context, private var usersData: List<DocumentSnapshot>): RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        Log.d("Messenger", "${usersData.size}")
        return usersData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Messenger", "Creating view holder")
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Messenger", "Binding view holder")
        var user = usersData[position]
        holder.userName.text = user["Name"].toString()
        holder.userSurname.text = user["Surname"].toString()
    }

    fun setUsersList(usersData: List<DocumentSnapshot>){
        this.usersData = usersData
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view){
    val userSurname = view.surname_user_item
    val userName = view.name_user_item
}