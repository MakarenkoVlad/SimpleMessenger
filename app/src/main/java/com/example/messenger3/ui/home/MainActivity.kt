package com.example.messenger3.ui.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger3.R
import com.example.messenger3.data.Auth
import com.example.messenger3.ui.account.AccountInfoActivity
import com.example.messenger3.ui.auth.LogInActivity
import com.example.messenger3.ui.people.PeopleActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      Go to AccountInfoActivity
        account_info_button.setOnClickListener {
            startActivity(Intent(this, AccountInfoActivity::class.java))
        }

//      Go to log in activity and log out
        log_out_button.setOnClickListener {
            Auth.firebaseAuth.signOut()
            logOut()
        }

//      Go to people activity
        people_button.setOnClickListener{
            startActivity(Intent(this, PeopleActivity::class.java))
        }


        rv_user_list.layoutManager = LinearLayoutManager(this)
        rv_user_list.adapter = UserListAdapter(this, listOf())

        getAllData()

    }

    override fun onResume() {
        super.onResume()
        (rv_user_list.adapter as UserListAdapter).notifyDataSetChanged()
    }

    private fun getAllData(){
        Log.d("Messenger", "Data has been get!")

        MainViewModel.getUsersList(FirebaseAuth.getInstance().currentUser!!.uid)
        MainViewModel.setListenerOnList()

        MainViewModel.usersLiveData.observe(this, Observer { result ->
            if (result.second.isEmpty()){
                (rv_user_list.adapter as UserListAdapter).setUsersList(result.first)
                (rv_user_list.adapter as UserListAdapter).notifyDataSetChanged()
            }
            else{
                Log.w("Messenger", "Because: ${result.second}")
                account_info_button.setBackgroundColor(Color.RED)
            }
        })


    }

    private fun logOut(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LogInActivity::class.java))
    }


}
