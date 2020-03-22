package com.example.messenger3.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item.view.*

class ViewHolder(view: View): RecyclerView.ViewHolder(view){
    val userSurname = view.surname_user_item!!
    val userName = view.name_user_item!!
    val userImage = view.image_user_item!!
}