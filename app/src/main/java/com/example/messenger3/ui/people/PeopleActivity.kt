package com.example.messenger3.ui.people

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger3.R
import com.example.messenger3.ui.home.UserListAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_people.*

class PeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        rv_people_list.layoutManager = LinearLayoutManager(this)
        rv_people_list.adapter = UserListAdapter(this, listOf())

        getAllData()
    }

    private fun getAllData(){
        Log.d("Messenger", "Data has been get!")

        PeopleViewModel.getUsersList(FirebaseAuth.getInstance().currentUser!!.uid)
        PeopleViewModel.setListenerOnList()

        PeopleViewModel.usersLiveData.observe(this, Observer { result ->
            if (result.second.isEmpty()){
                (rv_people_list.adapter as UserListAdapter).setUsersList(result.first)
                (rv_people_list.adapter as UserListAdapter).notifyDataSetChanged()
            }
            else{
                Log.w("Messenger", "Because: ${result.second}")
            }
        })
    }
}
