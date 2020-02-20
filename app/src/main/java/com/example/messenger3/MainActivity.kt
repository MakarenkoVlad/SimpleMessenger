package com.example.messenger3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger3.main_activity_classes.UserListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_account_info.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class MainActivity : AppCompatActivity() {
    private var usersData: List<DocumentSnapshot> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      Go to AccountInfoActivity
        account_info_button.setOnClickListener {
            startActivity(Intent(this, AccountInfoActivity::class.java))
        }

//      Go to log in activity and log out
        log_out_button.setOnClickListener {
            logOut()
        }

        rv_user_list.layoutManager = LinearLayoutManager(this)
        rv_user_list.adapter = UserListAdapter(this, usersData)

        GlobalScope.a
        (rv_user_list.adapter as UserListAdapter).setUsersList(usersData)
        (rv_user_list.adapter as UserListAdapter).notifyDataSetChanged()
    }

    private fun getAllData(){
        Log.d("Messenger", "Data has been get!")

        FirebaseFirestore.getInstance().collection("users").get()
           .addOnSuccessListener {
               Log.d("Messenger", "Data has been get!")
               usersData = it.documents
           }
           .addOnFailureListener {
               Log.w("Messenger", "Because: ${it.message}")
           }

    }

    private fun logOut(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }


}
